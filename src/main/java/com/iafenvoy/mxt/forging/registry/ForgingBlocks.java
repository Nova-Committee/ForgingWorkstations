package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.item.block.ForgingTableBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public final class ForgingBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(ForgingWorkstations.MOD_ID);

    public static final DeferredBlock<ForgingTableBlock> FORGING_TABLE = register("forging_table", ForgingTableBlock::new, Properties.of().mapColor(MapColor.STONE).strength(3.5F).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion());

    private static <T extends Block> DeferredBlock<T> register(String path, Function<Properties, T> factory, Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ForgingWorkstations.MOD_ID, path));
        DeferredBlock<T> block = REGISTRY.register(path, () -> factory.apply(properties.setId(key)));
        ForgingItems.REGISTRY.register(path, () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, key.identifier())).useBlockDescriptionPrefix()));
        return block;
    }
}
