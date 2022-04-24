package io.github.feltmc.feltapi.api.tool;

import io.github.feltmc.feltapi.api.tool.interactions.Torching;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

public class FeltPickaxeItem extends PickaxeItem implements Torching {
    boolean torchingEnabled;
    public FeltPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        this(material, attackDamage, attackSpeed, settings, false);
    }

    public FeltPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, boolean torching) {
        super(material, attackDamage, attackSpeed, settings);
        torchingEnabled = torching;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (torchingEnabled){
            return torch(context);
        } else {
            return ActionResult.PASS;
        }
    }
}