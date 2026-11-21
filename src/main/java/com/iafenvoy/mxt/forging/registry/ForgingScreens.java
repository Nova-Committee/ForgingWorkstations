package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.screen.ForgingMenu;
import com.iafenvoy.mxt.forging.screen.ForgingScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.iafenvoy.mxt.registry.MxtRenderers.page;

@EventBusSubscriber(Dist.CLIENT)
public final class ForgingScreens {
    @SubscribeEvent
    public static void register(RegisterMenuScreensEvent event) {
        page(event, ForgingMenus.FORGING_TABLE.get(), (container, inventory, title) -> new ForgingScreen((ForgingMenu) container, inventory, title));
    }
}
