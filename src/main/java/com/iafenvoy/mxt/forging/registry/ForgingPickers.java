package com.iafenvoy.mxt.forging.registry;

import static com.iafenvoy.mxt.data.PickerCategories.registerMatcher;

public final class ForgingPickers {
    public static void register() {
        registerMatcher(ForgingResourceKeys.TOOL_BINDING);
        registerMatcher(ForgingResourceKeys.BLUEPRINT_BINDING);
    }
}
