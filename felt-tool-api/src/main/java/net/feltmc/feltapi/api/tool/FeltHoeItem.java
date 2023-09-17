package net.feltmc.feltapi.api.tool;

import net.feltmc.feltapi.api.tool.interactions.Tilling;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

public class FeltHoeItem extends HoeItem implements Tilling {
    public FeltHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return till(context);
    }
}