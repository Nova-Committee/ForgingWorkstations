package com.iafenvoy.mxt.forging.compat;

import com.iafenvoy.mxt.forging.event.ForgingEvent;
import dev.latvian.mods.kubejs.event.*;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class WorkstationKubeJsPlugin implements KubeJSPlugin {
    private static final EventGroup GROUP = EventGroup.of("ForgingWorkstationsEvents");
    private static final EventHandler PROCESS = GROUP.server("process", () -> ProcessEvent.class);

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(GROUP);
    }

    @Override
    public void init() {
        NeoForge.EVENT_BUS.addListener(ForgingEvent.Start.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.Started.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.StrikePre.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.StrikePost.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.CompletePre.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.CompletePost.class, this::post);
        NeoForge.EVENT_BUS.addListener(ForgingEvent.Cancel.class, this::post);
    }

    private void post(Event event) {
        EventResult result = PROCESS.post(new ProcessEvent(event));
        if (event instanceof ICancellableEvent cancellable) result.applyCancel(cancellable);
    }

    public record ProcessEvent(Event event) implements KubeEvent {
        public Event getEvent() {
            return this.event;
        }

        public boolean isCancellable() {
            return this.event instanceof ICancellableEvent;
        }

        public String getPhase() {
            return this.event.getClass().getSimpleName();
        }
    }
}
