package net.feltmc.feltapi.api.armor;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface HorseArmorTickItem {
    /**
     * Called every tick from {@code Horse#playGallopSound(SoundEvent)} on the item in the
     * armor slot.
     *
     * @param stack the armor itemstack
     * @param level the level the horse is in
     * @param horse the horse wearing this armor
     */
    void onHorseArmorTick(ItemStack stack, World level, MobEntity horse);
}
