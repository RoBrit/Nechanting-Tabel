package com.robrit.nechantingtabel.tileentity;

import com.robrit.nechantingtabel.screen.NechantingTabelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class NechantingTabelBlockEntity extends BlockEntity implements MenuProvider {
    public NechantingTabelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.NECHANTING_TABEL.get(), blockPos, blockState);
    }

    @Override
    @ParametersAreNonnullByDefault
    public CompoundTag save(CompoundTag compoundTag) {
        return super.save(compoundTag);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
    }

    @Override
    @Nonnull
    public Component getDisplayName() {
        return new TranslatableComponent("container.nechanting_tabel.disenchant");
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int windowId, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new NechantingTabelMenu(windowId, playerInventory, getBlockPos());
    }
}
