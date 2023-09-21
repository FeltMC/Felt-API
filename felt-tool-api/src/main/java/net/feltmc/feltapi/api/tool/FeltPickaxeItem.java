package net.feltmc.feltapi.api.tool;

import net.feltmc.feltapi.api.tool.interactions.Torching;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class FeltPickaxeItem extends PickaxeItem implements Torching {
    boolean torchingEnabled;
    public FeltPickaxeItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        this(material, attackDamage, attackSpeed, settings, false);
    }

    public FeltPickaxeItem(Tier material, int attackDamage, float attackSpeed, Properties settings, boolean torching) {
        super(material, attackDamage, attackSpeed, settings);
        torchingEnabled = torching;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (torchingEnabled){
            return torch(context);
        } else {
            return InteractionResult.PASS;
        }
    }
}