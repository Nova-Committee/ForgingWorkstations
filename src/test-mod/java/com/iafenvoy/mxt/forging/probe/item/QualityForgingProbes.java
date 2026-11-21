package com.iafenvoy.mxt.forging.probe.item;

import com.iafenvoy.mxt.forging.data.ForgingBlueprint;
import com.iafenvoy.mxt.forging.registry.ForgingResourceKeys;
import com.iafenvoy.mxt.forging.runtime.ForgingSurface;
import com.iafenvoy.mxt.forging.runtime.ForgingWorkstationService;
import com.iafenvoy.mxt.probe.Probe;
import com.iafenvoy.mxt.probe.ProbeContext;
import com.iafenvoy.mxt.registry.*;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class QualityForgingProbes {
    private QualityForgingProbes() {
    }

    public static List<Probe> probes() {
        return List.of(
                Probe.of("item/quality/forge_input", QualityForgingProbes::forgeInput));
    }

    private static void forgeInput(ProbeContext context) {
        RegistryAccess access = context.registries();
        ItemStack clay = new ItemStack(Items.CLAY_BALL);         // table: normal
        ItemStack cobble = new ItemStack(Items.COBBLESTONE);     // table: poor
        ForgingBlueprint gate = blueprint(access, "quality_gate");
        SizedIngredient entry = gate == null || gate.input().isEmpty() ? null : gate.input().getFirst();
        SimpleContainer forge = new SimpleContainer(ForgingSurface.TOTAL_SLOTS);
        forge.setItem(ForgingSurface.INPUT_START, clay.copy());
        boolean covered = entry != null && ForgingWorkstationService.materialsCovered(forge, gate.input());
        int have = entry == null ? -1 : ForgingWorkstationService.availableCount(forge, entry);
        forge.setItem(ForgingSurface.INPUT_START, cobble.copy());
        leg(context, "forge_input",
                entry != null && entry.count() == 1 && entry.ingredient().isCustom() && covered && have == 1
                        && !ForgingWorkstationService.materialsCovered(forge, gate.input()),
                entry == null ? "blueprint missing"
                        : "custom=" + entry.ingredient().isCustom() + " needs=" + entry.count() + " covered=" + covered
                        + " graded=" + have + " poor_covered=" + ForgingWorkstationService.materialsCovered(forge, gate.input()),
                "custom=true needs=1 covered=true graded=1 poor_covered=false");
    }

    private static ForgingBlueprint blueprint(RegistryAccess access, String path) {
        return MxtDatapackRegistries.holder(access, ForgingResourceKeys.FORGING_BLUEPRINT, Identifier.fromNamespaceAndPath("mxt_test", path))
                .map(Holder::value).orElse(null);
    }

    private static boolean leg(ProbeContext context, String name, boolean ok, String actual, String expected) {
        String line = "quality probe: " + name + " actual=" + actual + " expected=" + expected + (ok ? " OK" : " MISMATCH");
        if (ok) context.note(line);
        else context.fail(line);
        return ok;
    }
}
