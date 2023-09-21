package net.feltmc.feltapi.api.tool;

import net.feltmc.feltapi.api.tool.interactions.Stripping;
import net.feltmc.feltapi.api.tool.interactions.Torching;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class FeltAxeItem extends AxeItem implements Stripping, Torching {
    boolean torchingEnabled;
    public FeltAxeItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        this(material, attackDamage, attackSpeed, settings, false);
    }

    public FeltAxeItem(Tier material, int attackDamage, float attackSpeed, Properties settings, boolean torching) {
        super(material, attackDamage, attackSpeed, settings);
        torchingEnabled = torching;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player playerEntity = context.getPlayer();
        if(!playerEntity.isShiftKeyDown() && canStrip(context)){
            return strip(context);
        } else if (torchingEnabled && canTorch(context)){
            return torch(context);
        }
        return InteractionResult.PASS;
    }
}