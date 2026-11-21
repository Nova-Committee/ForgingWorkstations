package com.iafenvoy.mxt.forging.registry;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.screen.ForgingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ForgingMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, ForgingWorkstations.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<ForgingMenu>> FORGING_TABLE = REGISTRY.register("forging_table", () -> new MenuType<>(ForgingMenu::new, FeatureFlags.VANILLA_SET));
}
