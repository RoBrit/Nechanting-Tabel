package com.robrit.nechantingtabel.screen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class BaseMenu extends AbstractContainerMenu {
    protected BaseMenu(MenuType<?> menu, int id) {
        super(menu, id);
    }

    protected abstract void addSlots();

    protected abstract boolean mayPickup(Player player, boolean p_39799_);

    protected abstract void onTake(Player player, ItemStack itemStack);

    protected void addPlayerSlots(Inventory inventory, int initialX, int initialY) {
        // Slots for the hotbar
        for (int row = 0; row < 9; row++) {
            int x = initialX + row * 18;
            int y = initialY + 86;
            addSlot(new Slot(inventory, row, x, y));
        }
        // Slots for the main inventory
        for (int row = 1; row < 4; row++) {
            for (int col = 0; col < 9; col++) {
                int x = initialX + col * 18;
                int y = row * 18 + (initialY + 10);
                addSlot(new Slot(inventory, col + row * 9, x, y));
            }
        }
    }
}
