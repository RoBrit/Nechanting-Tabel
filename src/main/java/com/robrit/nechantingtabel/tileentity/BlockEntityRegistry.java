package com.robrit.nechantingtabel.tileentity;

import com.robrit.nechantingtabel.NechantingTabel;
import com.robrit.nechantingtabel.References;
import com.robrit.nechantingtabel.block.BlockRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NechantingTabel.MOD_ID);


    public static final RegistryObject<BlockEntityType<?>> NECHANTING_TABEL = REGISTRY.register(References.ID_NECHANTING_TABEL, () -> BlockEntityType.Builder.of(NechantingTabelBlockEntity::new, BlockRegistry.NECHANTING_TABEL.get()).build(null));

    public static void init() {
        BlockEntityRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
