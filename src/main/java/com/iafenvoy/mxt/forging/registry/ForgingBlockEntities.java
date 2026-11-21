package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.item.block.entity.ForgingTableBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ForgingBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ForgingWorkstations.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgingTableBlockEntity>> FORGING_TABLE = REGISTRY.register("forging_table", () -> new BlockEntityType<>(ForgingTableBlockEntity::new, ForgingBlocks.FORGING_TABLE.get()));
}
