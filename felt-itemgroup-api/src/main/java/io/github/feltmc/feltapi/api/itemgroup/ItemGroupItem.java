package io.github.feltmc.feltapi.api.itemgroup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public interface ItemGroupItem {
    /**
     * Gets a list of tabs that items belonging to this class can display on,
     * combined properly with getSubItems allows for a single item to span many
     * sub-items across many tabs.
     *
     * @return A list of all tabs that this item could possibly be one.
     */
    default java.util.Collection<ItemGroup> getGroups()
    {
        return java.util.Collections.singletonList(((Item)this).getGroup());
    }
}
