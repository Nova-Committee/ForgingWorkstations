package com.iafenvoy.mxt.forging;

import com.iafenvoy.mxt.forging.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ForgingWorkstations.MOD_ID)
@EventBusSubscriber
public final class ForgingWorkstations {
    public static final String MOD_ID = "mxt_forging";

    public ForgingWorkstations(IEventBus bus) {
        ForgingBlockEntities.REGISTRY.register(bus);
        ForgingBlocks.REGISTRY.register(bus);
        ForgingDataComponents.REGISTRY.register(bus);
        ForgingItems.REGISTRY.register(bus);
        ForgingMenus.REGISTRY.register(bus);

        ForgingSources.init();
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        ForgingPickers.register();
    }
}
