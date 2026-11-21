package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.data.BlueprintBinding;
import com.iafenvoy.mxt.forging.data.ForgingBlueprint;
import com.iafenvoy.mxt.forging.data.ForgingMethod;
import com.iafenvoy.mxt.forging.data.ToolBinding;
import com.iafenvoy.mxt.registry.MxtDatapackRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;

@EventBusSubscriber
public final class ForgingDatapackRegistries {
    @SubscribeEvent
    public static void register(NewRegistry event) {
        MxtDatapackRegistries.register(event, ForgingResourceKeys.FORGING_METHOD, ForgingMethod.DIRECT_CODEC);
        MxtDatapackRegistries.register(event, ForgingResourceKeys.FORGING_BLUEPRINT, ForgingBlueprint.DIRECT_CODEC);
        MxtDatapackRegistries.register(event, ForgingResourceKeys.TOOL_BINDING, ToolBinding.DIRECT_CODEC);
        MxtDatapackRegistries.register(event, ForgingResourceKeys.BLUEPRINT_BINDING, BlueprintBinding.DIRECT_CODEC);
    }
}
