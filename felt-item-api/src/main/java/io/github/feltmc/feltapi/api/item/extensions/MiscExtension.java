package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface MiscExtension {
    private Item self()
    {
        return (Item) this;
    }

    /**
     * Called when a player drops the item into the world, returning false from this
     * will prevent the item from being removed from the players inventory and
     * spawning in the world
     *
     * @param player The player that dropped the item
     * @param item   The item stack, before the item is removed.
     */
    default boolean onDroppedByPlayer(ItemStack item, PlayerEntity player)
    {
        return true;
    }

    /**
     * Allow the item one last chance to modify its name used for the tool highlight
     * useful for adding something extra that can't be removed by a user in the
     * displayed name, such as a mode of operation.
     *
     * @param item        the ItemStack for the item.
     * @param displayName the name that will be displayed unless it is changed in
     *                    this method.
     */
    default Text getHighlightTip(ItemStack item, Text displayName)
    {
        return displayName;
    }


    default Collection<ItemGroup> getGroups()
    {
        return Collections.singletonList(self().getGroup());
    }

    /**
     * Allow or forbid the specific book/item combination as an anvil enchant
     *
     * @param stack The item
     * @param book  The book
     * @return if the enchantment is allowed
     */
    default boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }

    /**
     * Called when the player is mining a block and the item in his hand changes.
     * Allows to not reset blockbreaking if only NBT or similar changes.
     *
     * @param oldStack The old stack that was used for mining. Item in players main
     *                 hand
     * @param newStack The new stack
     * @return True to reset block break progress
     */
    default boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack)
    {
        // Fix MC-176559 mending resets mining progress / breaking animation
        if (!newStack.isOf(oldStack.getItem()))
            return true;

        if (!newStack.isDamageable() || !oldStack.isDamageable())
            return !ItemStack.areNbtEqual(newStack, oldStack);

        NbtCompound newTag = newStack.getNbt();
        NbtCompound oldTag = oldStack.getNbt();

        if (newTag == null || oldTag == null)
            return !(newTag == null && oldTag == null);

        Set<String> newKeys = new HashSet<>(newTag.getKeys());
        Set<String> oldKeys = new HashSet<>(oldTag.getKeys());

        newKeys.remove(ItemStack.DAMAGE_KEY);
        oldKeys.remove(ItemStack.DAMAGE_KEY);

        if (!newKeys.equals(oldKeys))
            return true;

        return !newKeys.stream().allMatch(key -> Objects.equals(newTag.get(key), oldTag.get(key)));
        // return !(newStack.is(oldStack.getItem()) && ItemStack.tagMatches(newStack, oldStack)
        //         && (newStack.isDamageableItem() || newStack.getDamageValue() == oldStack.getDamageValue()));
    }

    /**
     * Called every tick from {@code Horse#playGallopSound(SoundEvent)} on the item in the
     * armor slot.
     *
     * @param stack the armor itemstack
     * @param level the level the horse is in
     * @param horse the horse wearing this armor
     */
    default void onHorseArmorTick(ItemStack stack, World level, MobEntity horse) {
    }

    /**
     * Whether this Item can be used to hide player head for enderman.
     *
     * @param stack the ItemStack
     * @param player The player watching the enderman
     * @param endermanEntity The enderman that the player look
     * @return true if this Item can be used to hide player head for enderman
     */
    default boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
    {
        return stack.getItem() == Blocks.CARVED_PUMPKIN.asItem();
    }

    /**
     * Get a bounding box ({@link Box}) of a sweep attack.
     *
     * @param stack the stack held by the player.
     * @param player the performing the attack the attack.
     * @param target the entity targeted by the attack.
     * @return the bounding box.
     */
    @Nonnull
    default Box getSweepHitBox(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull Entity target)
    {
        return target.getBoundingBox().expand(1.0D, 0.25D, 1.0D);
    }


    /**
     * Get the food properties for this item.
     * Use this instead of the {@link Item#getFoodComponent()} method, for ItemStack sensitivity.
     * <p>
     * The @Nullable annotation was only added, due to the default method, also being @Nullable.
     * Use this with a grain of salt, as if you return null here and true at {@link Item#isFood()}, NPEs will occur!
     *
     * @param stack The ItemStack the entity wants to eat.
     * @param entity The entity which wants to eat the food. Be aware that this can be null!
     * @return The current FoodProperties for the item.
     */
    @Nullable // read javadoc to find a potential problem
    default FoodComponent getFoodComponenet(ItemStack stack, @Nullable LivingEntity entity)
    {
        return self().getFoodComponent();
    }
}
