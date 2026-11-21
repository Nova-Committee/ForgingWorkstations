package com.iafenvoy.mxt.forging.testmod;

import com.iafenvoy.mxt.forging.probe.ForgingLifecycleProbes;
import com.iafenvoy.mxt.forging.probe.WorkstationRegistrationProbes;
import com.iafenvoy.mxt.forging.probe.item.QualityForgingProbes;
import com.iafenvoy.mxt.probe.Probe;
import com.iafenvoy.mxt.probe.ProbeTests;
import java.util.ArrayList;
import java.util.List;

import com.iafenvoy.mxt.registry.MxtCreativeTabs;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(WorkstationTestMod.MOD_ID)
public final class WorkstationTestMod {
    public static final String MOD_ID = "mxt_forging_test";
    public WorkstationTestMod(IEventBus bus) {
        MxtTestForgeItems.REGISTRY.register(bus);
        bus.addListener(RegisterEvent.class, event -> ProbeTests.registerFunctions(event, probes()));
        bus.addListener(RegisterGameTestsEvent.class, event -> ProbeTests.registerTests(event, probes()));
    }
    @EventBusSubscriber
    public static final class CreativeItems {
        @SubscribeEvent
        public static void creative(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(MxtCreativeTabs.MAIN.getKey()))
                MxtTestForgeItems.REGISTRY.getEntries().forEach(item -> event.accept(item.get()));
        }
    }
    private static List<Probe> probes() {
        List<Probe> probes = new ArrayList<>();
        probes.addAll(WorkstationRegistrationProbes.probes());
        probes.addAll(ForgingLifecycleProbes.probes());
        probes.addAll(QualityForgingProbes.probes());
        return probes.stream().map(p -> new Probe(Identifier.fromNamespaceAndPath(MOD_ID, p.id().getPath()), p.structure(), p.maxTicks(), p.kit(), p.body())).toList();
    }
}
