package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public interface HighlightTipItem {
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
}
