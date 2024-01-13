package net.feltmc.feltapi.api.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ArmorItem {
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    default void onArmorTick(ItemStack stack, Level level, Player player){

    }

    /**
     * Determines if the specific ItemStack can be placed in the specified armor
     * slot, for the entity.
     *
     * @param stack     The ItemStack
     * @param armorType Armor slot to be verified.
     * @param entity    The entity trying to equip the armor
     * @return True if the given ItemStack can be inserted in the slot
     */
    default boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == armorType;
    }

    /**
     * Called every tick from {@code Horse#playGallopSound(SoundEvent)} on the item in the
     * armor slot.
     *
     * @param stack the armor itemstack
     * @param level the level the horse is in
     * @param horse the horse wearing this armor
     */
    default void onHorseArmorTick(ItemStack stack, Level level, Mob horse){

    }
}
