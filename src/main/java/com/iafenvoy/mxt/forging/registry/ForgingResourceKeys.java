package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.data.BlueprintBinding;
import com.iafenvoy.mxt.forging.data.ForgingBlueprint;
import com.iafenvoy.mxt.forging.data.ForgingMethod;
import com.iafenvoy.mxt.forging.data.ToolBinding;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class ForgingResourceKeys {
    public static final ResourceKey<Registry<ForgingMethod>> FORGING_METHOD = create("forging_method");
    public static final ResourceKey<Registry<ForgingBlueprint>> FORGING_BLUEPRINT = create("forging_blueprint");
    public static final ResourceKey<Registry<ToolBinding>> TOOL_BINDING = create("tool_binding");
    public static final ResourceKey<Registry<BlueprintBinding>> BLUEPRINT_BINDING = create("blueprint_binding");

    private static <T> ResourceKey<Registry<T>> create(String path) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ForgingWorkstations.MOD_ID, path));
    }
}
