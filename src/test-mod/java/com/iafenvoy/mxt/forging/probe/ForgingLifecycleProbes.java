package com.iafenvoy.mxt.forging.probe;

import com.google.gson.JsonElement;
import com.iafenvoy.mxt.forging.item.block.entity.ForgingTableBlockEntity;
import com.iafenvoy.mxt.forging.registry.ForgingBlocks;
import com.iafenvoy.mxt.forging.runtime.ForgingSurface;
import com.iafenvoy.mxt.forging.runtime.ForgingTableState;
import com.iafenvoy.mxt.forging.runtime.ForgingWorkstationService;
import com.iafenvoy.mxt.forging.testmod.MxtTestForgeItems;
import com.iafenvoy.mxt.probe.Probe;
import com.iafenvoy.mxt.probe.ProbeContext;
import com.iafenvoy.saturn.runtime.resulting.Result;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public final class ForgingLifecycleProbes {
    public static List<Probe> probes() {
        return List.of(
                Probe.kitted("workstation/forging/session", ForgingLifecycleProbes::session),
                Probe.kitted("workstation/forging/insufficient", ForgingLifecycleProbes::insufficient));
    }

    private static ForgingTableBlockEntity table(ProbeContext context) {
        BlockPos pos = context.at(8, 1, 8);
        context.level().setBlockAndUpdate(pos, ForgingBlocks.FORGING_TABLE.get().defaultBlockState());
        context.playerAt(new Vec3(8.5, 1, 7.5));
        ForgingTableBlockEntity table = (ForgingTableBlockEntity) context.level().getBlockEntity(pos);
        table.setItem(ForgingSurface.BLUEPRINT_START, MxtTestForgeItems.TEST_MANUAL.toStack());
        table.setItem(ForgingSurface.TOOL_START, MxtTestForgeItems.TEST_HAMMER.toStack());
        return table;
    }

    private static void session(ProbeContext context) {
        ForgingTableBlockEntity table = table(context);
        ItemStack input = new ItemStack(Items.IRON_SWORD);
        input.set(DataComponents.CUSTOM_NAME, Component.literal("Locked material"));
        table.setItem(ForgingSurface.INPUT_START, input.copy());
        Result<Void> started = ForgingWorkstationService.start(context.player(), table, id("iron_sword"));
        context.expect("session starts", started.getFailure().isEmpty(), true);
        context.expect("input locked", table.getItem(ForgingSurface.INPUT_START).isEmpty(), true);
        context.expect("locked stack preserved", ItemStack.matches(table.forgingState().consumed().getFirst(), input), true);
        Result<Integer> struck = ForgingWorkstationService.strike(context.player(), table, id("heavy_strike"));
        context.expect("strike succeeds", struck.getFailure().isEmpty(), true);
        context.expect("strike value", struck.getData().orElse(-1), 2);
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, context.registries());
        JsonElement encoded = ForgingTableState.CODEC.encodeStart(ops, table.forgingState()).getOrThrow();
        ForgingTableState restored = ForgingTableState.CODEC.parse(ops, encoded).getOrThrow();
        context.expect("active session restored", restored.active(), true);
        context.expect("starter restored", restored.starter(), table.forgingState().starter());
        context.expect("progress restored", restored.session(), table.forgingState().session());
        context.expect("plan restored", restored.plan(), table.forgingState().plan());
        table.forgingState().copyFrom(restored);
        context.expect("cancel restored session", ForgingWorkstationService.cancel(context.player(), table).getFailure().isEmpty(), true);
        context.expect("exact input returned", ItemStack.matches(table.getItem(ForgingSurface.INPUT_START), input), true);
        context.expect("session cleared", table.forgingState().active(), false);
        context.expect("locked materials cleared", table.forgingState().consumed().isEmpty(), true);
        context.expect("no output on cancel", table.getItem(ForgingSurface.OUTPUT_SLOT).isEmpty(), true);
    }

    private static void insufficient(ProbeContext context) {
        ForgingTableBlockEntity table = table(context);
        ItemStack wrongInput = new ItemStack(Items.IRON_INGOT, 3);
        table.setItem(ForgingSurface.INPUT_START, wrongInput.copy());
        context.expect("wrong material refused", ForgingWorkstationService.start(context.player(), table, id("iron_sword")).getFailure().isEmpty(), false);
        context.expect("input unchanged", ItemStack.matches(table.getItem(ForgingSurface.INPUT_START), wrongInput), true);
        context.expect("no session", table.forgingState().active(), false);
        context.expect("nothing locked", table.forgingState().consumed().isEmpty(), true);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath("mxt_test", path);
    }
}
