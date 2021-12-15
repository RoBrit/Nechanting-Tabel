package com.robrit.nechantingtabel;

import com.robrit.nechantingtabel.block.BlockRegistry;
import com.robrit.nechantingtabel.config.ConfigRegistry;
import com.robrit.nechantingtabel.item.ItemRegistry;
import com.robrit.nechantingtabel.screen.MenuRegistry;
import com.robrit.nechantingtabel.screen.NechantingTabelScreen;
import com.robrit.nechantingtabel.tileentity.BlockEntityRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("nechantingtabel")
public class NechantingTabel {
    public static final String MOD_ID = "nechantingtabel";

    public NechantingTabel() {
        ConfigRegistry.init();
        BlockRegistry.init();
        BlockEntityRegistry.init();
        ItemRegistry.init();
        MenuRegistry.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);
    }

    public void clientInit(FMLClientSetupEvent event) {
        MenuScreens.register(MenuRegistry.NECHANTING_TABEL.get(), NechantingTabelScreen::new);
    }
}
