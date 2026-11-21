package com.iafenvoy.mxt.forging.network;

import com.iafenvoy.mxt.forging.item.block.entity.ForgingTableBlockEntity;
import com.iafenvoy.mxt.forging.network.payload.ForgingActionC2SPayload;
import com.iafenvoy.mxt.forging.runtime.ForgingWorkstationService;
import com.iafenvoy.mxt.forging.screen.ForgingMenu;
import com.iafenvoy.saturn.profiling.LoggerDispatcher;
import com.iafenvoy.saturn.profiling.LoggingLevel;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ServerNetworkHandler {
    private static final LoggerDispatcher FORGING_TRACE = LoggerDispatcher.of(LogUtils.getLogger(), LoggingLevel.DEBUG, LoggingLevel.NONE);

    static void onForgingAction(ForgingActionC2SPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        // The table comes from the menu the player has open, not from the packet: the client cannot reach the block
        // at all (its ContainerLevelAccess is NULL), so a position in the request would have to be trusted coming
        // back. Resolving here is what leaves the distance check as the only remaining thing to verify.
        if (!(player.containerMenu instanceof ForgingMenu menu)) return;
        if (!(menu.table() instanceof ForgingTableBlockEntity table)) return;
        Identifier definition = payload.definition().orElse(null);
        // `active` is logged after the call for the two actions that can settle a session by themselves: a strike
        // that completes the piece ends it, so "the session is gone afterwards" is the success path.
        switch (payload.action()) {
            case SELECT ->
                    FORGING_TRACE.log("forging SELECT definition={} selectable={} methods={} outcome={} active={}", definition, table.selectableBlueprintIds(), table.availableMethodIds(), ForgingWorkstationService.start(player, table, definition), table.forgingState().active());
            case STRIKE ->
                    FORGING_TRACE.log("forging STRIKE definition={} available={} outcome={} active={}", definition, table.availableMethodIds(), ForgingWorkstationService.strike(player, table, definition), table.forgingState().active());
            case FINISH ->
                    FORGING_TRACE.log("forging FINISH outcome={}", ForgingWorkstationService.finish(player, table));
            case CANCEL ->
                    FORGING_TRACE.log("forging CANCEL outcome={}", ForgingWorkstationService.cancel(player, table));
        }
    }
}
