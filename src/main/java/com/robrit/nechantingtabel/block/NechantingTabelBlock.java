package com.robrit.nechantingtabel.block;

import com.robrit.nechantingtabel.tileentity.NechantingTabelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class NechantingTabelBlock extends BaseEntityBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public NechantingTabelBlock(Properties properties) {
        super(properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    @Override
    @ParametersAreNonnullByDefault
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collider) {
        return SHAPE;
    }

    @Override
    @ParametersAreNonnullByDefault
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, Random random) {
        super.animateTick(blockState, level, blockPos, random);

        for (int i = 0; i < 360; i += 16) {
            float radian = (float) (i * Math.PI / 180);
            double radius = 2;
            double xp = blockPos.getX() + Math.cos(radian) / radius;
            double zp = blockPos.getZ() + Math.sin(radian) / radius;

            level.addParticle(ParticleTypes.ENCHANT, xp + 0.5, blockPos.getY() + 2, zp + 0.5, Math.cos(radian), 0, Math.sin(radian));
        }
    }


    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new NechantingTabelBlockEntity(blockPos, blockState);
    }

    @Override
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof NechantingTabelBlockEntity))
            return InteractionResult.FAIL;

        NetworkHooks.openGui((ServerPlayer) player, (NechantingTabelBlockEntity) blockEntity, blockPos);

        return InteractionResult.SUCCESS;
    }
}
