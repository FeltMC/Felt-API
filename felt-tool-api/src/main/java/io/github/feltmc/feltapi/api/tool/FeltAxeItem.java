package io.github.feltmc.feltapi.api.tool;

import io.github.feltmc.feltapi.api.tool.interactions.Stripping;
import io.github.feltmc.feltapi.api.tool.interactions.Torching;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

public class FeltAxeItem extends AxeItem implements Stripping, Torching {
    boolean torchingEnabled;
    public FeltAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        this(material, attackDamage, attackSpeed, settings, false);
    }

    public FeltAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, boolean torching) {
        super(material, attackDamage, attackSpeed, settings);
        torchingEnabled = torching;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        if(!playerEntity.isSneaking() && canStrip(context)){
            return strip(context);
        } else if (torchingEnabled && canTorch(context)){
            return torch(context);
        }
        return ActionResult.PASS;
    }
}