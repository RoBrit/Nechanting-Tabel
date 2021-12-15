package com.robrit.nechantingtabel.screen;

import com.robrit.nechantingtabel.NechantingTabel;
import com.robrit.nechantingtabel.References;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.CONTAINERS, NechantingTabel.MOD_ID);

    public static final RegistryObject<MenuType<NechantingTabelMenu>> NECHANTING_TABEL = REGISTRY.register(References.ID_NECHANTING_TABEL, () -> IForgeMenuType.create(NechantingTabelMenu::new));

    public static void init() {
        MenuRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}