package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.data.ForgingBlueprint;
import com.iafenvoy.mxt.forging.data.ForgingMethod;
import com.iafenvoy.mxt.forging.data.ForgingResultComponent;
import com.iafenvoy.saturn.util.codec.TolerantListCodec;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.DataComponents;

import java.util.List;

public final class ForgingDataComponents {
    public static final DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ForgingWorkstations.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ForgingResultComponent>> FORGING_RESULT = register("forging_result", ForgingResultComponent.CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Holder<ForgingMethod>>>> FORGING_METHODS = register("forging_methods", TolerantListCodec.create(ForgingMethod.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Holder<ForgingBlueprint>>>> FORGING_BLUEPRINTS = register("forging_blueprints", TolerantListCodec.create(ForgingBlueprint.CODEC));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String id, Codec<T> codec) {
        return REGISTRY.registerComponentType(id, b -> b.persistent(codec).networkSynchronized(ByteBufCodecs.fromCodecWithRegistries(codec)));
    }
}
