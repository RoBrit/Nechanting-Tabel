package com.robrit.nechantingtabel.block;

import com.robrit.nechantingtabel.NechantingTabel;
import com.robrit.nechantingtabel.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, NechantingTabel.MOD_ID);

    public static final RegistryObject<Block> NECHANTING_TABEL = REGISTRY.register(References.ID_NECHANTING_TABEL, () -> new NechantingTabelBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().lightLevel((p_152692_) -> 7).strength(5.0F, 1200.0F)));

    public static void init() {
        BlockRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
