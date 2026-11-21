package com.iafenvoy.mxt.forging.probe;

import com.iafenvoy.mxt.forging.ForgingWorkstations;
import com.iafenvoy.mxt.forging.registry.*;
import com.iafenvoy.mxt.probe.Probe;
import com.iafenvoy.mxt.registry.MxtCreativeTabs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class WorkstationRegistrationProbes {
    private WorkstationRegistrationProbes() {
    }

    public static List<Probe> probes() {
        return List.of(Probe.of("verify/workstations/registration", context -> {
            for (DeferredRegister<?> registry : List.of(ForgingBlocks.REGISTRY, ForgingItems.REGISTRY, ForgingBlockEntities.REGISTRY, ForgingMenus.REGISTRY, ForgingDataComponents.REGISTRY)) {
                Registry<?> values = BuiltInRegistries.REGISTRY.getValue(registry.getRegistryKey().identifier());
                registry.getEntries().forEach(entry -> {
                    context.expect(entry.getId() + " owned namespace", entry.getId().getNamespace(), ForgingWorkstations.MOD_ID);
                    context.expect(entry.getId() + " old id absent", values.containsKey(Identifier.fromNamespaceAndPath("mxt", entry.getId().getPath())), false);
                });
            }
            CreativeModeTab tab = MxtCreativeTabs.MAIN.get();
            tab.buildContents(new CreativeModeTab.ItemDisplayParameters(context.level().enabledFeatures(), true, context.registries()));
            ForgingItems.REGISTRY.getEntries().forEach(item -> {
                context.expect(item.getId() + " in MXT tab", tab.getDisplayItems().stream().filter(stack -> stack.is(item.get())).count(), 1L);
                context.expect(item.getId() + " searchable", tab.getSearchTabDisplayItems().stream().filter(stack -> stack.is(item.get())).count(), 1L);
            });
        }));
    }
}
