package io.github.feltmc.feltapi.api.item.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public class LivingEntityUsageTickCallback {
    Event<Start> START = EventFactory.createArrayBacked(Start.class,
            listeners -> (entity, item, duration) -> {
                for (Start event : listeners) {
                    int eventDur = event.start(entity, item, duration);
                    if (eventDur != duration){
                        return eventDur;
                    }
                }
                return duration;
            }
    );

    public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class,
            listeners -> (entity, item, duration) -> {
                for (Tick event : listeners) {
                    int eventDur = event.tick(entity, item, duration);
                    if (eventDur != duration){
                        return eventDur;
                    }
                }
                return duration;
            }
    );

    @FunctionalInterface
    public interface Start{
        int start(LivingEntity entity, ItemStack item, int duration);
    }

    @FunctionalInterface
    public interface Tick{
        int tick(LivingEntity entity, ItemStack item, int duration);
    }

    @FunctionalInterface
    public interface Stop{
        boolean stop(LivingEntity entity, ItemStack item, int duration);
    }

    @FunctionalInterface
    public interface Finish{
        ItemStack finish(LivingEntity entity, ItemStack item, int duration, ItemStack result);
    }
}
