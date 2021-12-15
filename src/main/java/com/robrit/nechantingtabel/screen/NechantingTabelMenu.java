package com.robrit.nechantingtabel.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.Tags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Map;

public class NechantingTabelMenu extends BaseMenu {
    private final DataSlot disenchantItemCost = DataSlot.standalone();
    private final DataSlot xpLevelCost = DataSlot.standalone();
    private final Container inputSlots = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            NechantingTabelMenu.this.slotsChanged(this);
        }
    };
    private final Container outputSlots = new SimpleContainer(1);

    public NechantingTabelMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory, extraData.readBlockPos());
    }

    public NechantingTabelMenu(int id, Inventory inventory, BlockPos blockPos) {
        super(MenuRegistry.NECHANTING_TABEL.get(), id);
        this.addDataSlot(this.xpLevelCost);

        addSlots();
        addPlayerSlots(inventory, 8, 14 * 4);
    }

    private void update() {
        ItemStack inputItemStack = this.inputSlots.getItem(0);
        ItemStack outputItemStack = inputItemStack.copy();

        int disenchantItemCost = 0;
        int xpLevelCost = 0;

        if (!inputItemStack.isEmpty()) {
            EnchantmentHelper.setEnchantments(Collections.emptyMap(), outputItemStack);
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(inputItemStack);

            for (Enchantment enchantment : enchantments.keySet()) {
                int enchantTier = enchantments.get(enchantment);
                int enchantRarityMultiplier = 0;

                switch (enchantment.getRarity()) {
                    case COMMON -> enchantRarityMultiplier = 1;
                    case UNCOMMON -> enchantRarityMultiplier = 2;
                    case RARE -> enchantRarityMultiplier = 4;
                    case VERY_RARE -> enchantRarityMultiplier = 8;
                }

                disenchantItemCost += enchantTier * enchantRarityMultiplier;
                xpLevelCost += enchantTier * enchantRarityMultiplier;
            }
        } else {
            this.disenchantItemCost.set(0);
            this.xpLevelCost.set(0);
        }

        this.disenchantItemCost.set(disenchantItemCost);
        this.xpLevelCost.set(xpLevelCost);
        this.outputSlots.setItem(0, outputItemStack);
        this.broadcastChanges();
    }

    public int getXpLevelCost() {
        return this.xpLevelCost.get();
    }

    public int getDisenchantItemCost() {
        return this.disenchantItemCost.get();
    }

    public int getTotalXpForLevel(int level) {
        if (level >= 1 && level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if (level >= 17 && level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        } else if (level >= 32) {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        } else {
            return 0;
        }
    }

    @Override
    protected void addSlots() {
        this.addSlot(new Slot(this.inputSlots, 0, 8, 34) {
            @Override
            @ParametersAreNonnullByDefault
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.isEnchanted();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.inputSlots, 1, 44, 34) {
            @Override
            @ParametersAreNonnullByDefault
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(Tags.Items.ENCHANTING_FUELS);
            }
        });

        this.addSlot(new Slot(this.outputSlots, 0, 98, 34) {
            @Override
            @ParametersAreNonnullByDefault
            public boolean mayPlace(ItemStack itemStack) {
                return false;
            }

            @Override
            @ParametersAreNonnullByDefault
            public boolean mayPickup(Player player) {
                return NechantingTabelMenu.this.mayPickup(player, !this.getItem().isEmpty());
            }

            @Override
            @ParametersAreNonnullByDefault
            public void onTake(Player player, ItemStack itemStack) {
                NechantingTabelMenu.this.onTake(player, itemStack);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.inputSlots) {
            this.update();
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void removed(Player player) {
        this.clearContainer(player, this.inputSlots);
    }

    @Override
    @ParametersAreNonnullByDefault
    public ItemStack quickMoveStack(Player player, int slotId) {
        ItemStack playerItemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if (slot.hasItem()) {
            ItemStack slotItemStack = slot.getItem();
            playerItemStack = slotItemStack.copy();

            if (slotId == 0) {
                if (!this.moveItemStackTo(slotItemStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotId == 1) {
                if (!this.moveItemStackTo(slotItemStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotItemStack.is(Tags.Items.ENCHANTING_FUELS)) {
                if (!this.moveItemStackTo(slotItemStack, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(slotItemStack)) {
                    return ItemStack.EMPTY;
                }

                ItemStack updatedSlotItemStack = slotItemStack.copy();
                updatedSlotItemStack.setCount(1);
                slotItemStack.shrink(1);
                this.slots.get(0).set(updatedSlotItemStack);
            }

            if (slotItemStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItemStack.getCount() == playerItemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItemStack);
        }

        return playerItemStack;
    }

    @Override
    protected boolean mayPickup(Player player, boolean p_39799_) {
        ItemStack inputItemStack = this.inputSlots.getItem(0);
        ItemStack costItemStack = this.inputSlots.getItem(1);

        return !inputItemStack.isEmpty() && costItemStack.is(Tags.Items.ENCHANTING_FUELS) && costItemStack.getCount() >= disenchantItemCost.get();
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        // Award player XP
        if (player instanceof ServerPlayer) {
            ExperienceOrb.award((ServerLevel) player.level, player.position(), getTotalXpForLevel(this.xpLevelCost.get()));
        }

        // Reduce size of disenchant fuel stack
        ItemStack costItemStack = this.inputSlots.getItem(1);
        if (costItemStack.getCount() > this.disenchantItemCost.get()) {
            costItemStack.shrink(this.disenchantItemCost.get());
            this.inputSlots.setItem(1, costItemStack);
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        // Remove enchanted item
        this.inputSlots.setItem(0, ItemStack.EMPTY);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean stillValid(Player player) {
        return inputSlots.stillValid(player);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
    }
}
