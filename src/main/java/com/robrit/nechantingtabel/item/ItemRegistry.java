package com.robrit.nechantingtabel.item;

import com.robrit.nechantingtabel.NechantingTabel;
import com.robrit.nechantingtabel.References;
import com.robrit.nechantingtabel.block.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, NechantingTabel.MOD_ID);

    public static final RegistryObject<Item> NECHANTING_TABEL = REGISTRY.register(References.ID_NECHANTING_TABEL, () -> new BlockItem(BlockRegistry.NECHANTING_TABEL.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static void init() {
        ItemRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
