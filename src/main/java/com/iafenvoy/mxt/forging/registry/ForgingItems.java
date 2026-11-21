package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.registry.MxtCreativeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
public final class ForgingItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ForgingWorkstations.MOD_ID);

    @SubscribeEvent
    public static void creative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(MxtCreativeTabs.MAIN.getKey()))
            REGISTRY.getEntries().forEach(item -> event.accept(item.get()));
    }
}
