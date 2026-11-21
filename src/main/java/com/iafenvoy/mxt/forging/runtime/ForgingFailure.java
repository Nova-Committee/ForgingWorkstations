package com.iafenvoy.mxt.forging.runtime;

import com.iafenvoy.mxt.forging.registry.ForgingSources;
import com.iafenvoy.saturn.runtime.resulting.Failure;
import com.iafenvoy.saturn.runtime.resulting.Failures;
import com.iafenvoy.saturn.runtime.resulting.SimpleFailure;
import org.jetbrains.annotations.Nullable;

public final class ForgingFailure {
    public static final Failure INVALID_BLUEPRINT = SimpleFailure.of("invalid_blueprint", ForgingSources.FAILURE);
    public static final Failure INVALID_STRIKE = SimpleFailure.of("invalid_strike", ForgingSources.FAILURE);
    public static final Failure NOT_COMPLETE = SimpleFailure.of("not_complete", ForgingSources.FAILURE);
    public static final Failure NO_SESSION = SimpleFailure.of("no_session", ForgingSources.FAILURE);
    public static final Failure OUT_OF_RANGE = SimpleFailure.of("out_of_range", ForgingSources.FAILURE);
    public static final Failure BLUEPRINT_NOT_HELD = SimpleFailure.of("blueprint_not_held", ForgingSources.FAILURE);
    // The struck method is not in the intersection of the blueprint's allowed methods and the methods the
    // placed tools unlock.
    public static final Failure METHOD_NOT_AVAILABLE = SimpleFailure.of("method_not_available", ForgingSources.FAILURE);
    public static final Failure INSUFFICIENT_MATERIALS = SimpleFailure.of("insufficient_materials", ForgingSources.FAILURE);
    public static final Failure OUTPUT_BLOCKED = SimpleFailure.of("output_blocked", ForgingSources.FAILURE);
    // A listener for the phase threw, so the operation was refused instead of half-performed. Apart from
    // CANCELLED: one is a script that said no, the other a script that broke.
    public static final Failure LISTENER_ERROR = SimpleFailure.of("listener_error", ForgingSources.FAILURE);

    private ForgingFailure() {
    }

    // A session stopped this way is left exactly as it was, unlike one failed with NOT_COMPLETE.
    public static boolean refusedByListener(@Nullable Failure failure) {
        return failure == Failures.CANCELLED || failure == LISTENER_ERROR;
    }
}
