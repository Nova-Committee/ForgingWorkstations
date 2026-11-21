package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.saturn.profiling.ProfilerSource;
import com.iafenvoy.saturn.runtime.resulting.FailureSource;

public final class ForgingSources {
    public static final ProfilerSource PROFILE = ProfilerSource.register(ForgingWorkstations.MOD_ID, "forging");
    public static final FailureSource FAILURE = FailureSource.of(ForgingResourceKeys.FORGING_BLUEPRINT);

    // Force source registration before the registry freezes, even if no workstation has been opened.
    public static void init() {
    }
}
