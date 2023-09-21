package net.feltmc.feltapi.api.tool;

import net.feltmc.feltapi.api.tool.interactions.Tilling;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class FeltHoeItem extends HoeItem implements Tilling {
    public FeltHoeItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return till(context);
    }
}