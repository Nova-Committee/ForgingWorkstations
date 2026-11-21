package com.iafenvoy.mxt.forging.runtime;

import com.iafenvoy.mxt.forging.data.ForgingBlueprint;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

/**
 * What a cancelled forging session does with the materials it locked away. The only answer today is that
 * everything the session took goes back; {@code state.consumed()} is what it took, and a null blueprint means
 * the datapack no longer has it.
 */
@FunctionalInterface
public interface ForgingCancellation {
    void settle(ServerPlayer player, ForgingSurface surface, ForgingTableState state, @Nullable ForgingBlueprint blueprint);
}
