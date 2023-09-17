/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.github.feltmc.feltapi.test.event.lifecycle;

import java.util.HashMap;
import java.util.Map;

import net.feltmc.feltapi.api.event.lifecycle.v1.ServerSharedTickEvents;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test related to ticking events on the server.
 */
public final class ServerTickTests implements ModInitializer {
	private final Map<RegistryKey<World>, Integer> tickTracker = new HashMap<>();
	public static final Logger LOGGER = LoggerFactory.getLogger("SharedLifecycleEventsTest");


	@Override
	public void onInitialize() {
		ServerSharedTickEvents.SERVER_TICK.register((server, start) -> {
			if (server.getTicks() % 200 == 0) { // Log every 200 ticks to verify the tick callback works on the server
				String startString = start ? "start" : "end";
				LOGGER.info("Ticked Server at " + startString + " of " + server.getTicks() + " ticks.");
			}
		});

		ServerSharedTickEvents.WORLD_TICK.register((world, start) -> {
			// Verify we are inside the tick
			if (start && !world.isInBlockTick()) {
				throw new AssertionError("Start tick event should be fired while ServerWorld is inside of block tick");
			}

			String startString = start ? "start" : "end";
			final int worldTicks = tickTracker.computeIfAbsent(world.getRegistryKey(), k -> 0);

			if (worldTicks % 200 == 0) { // Log every 200 ticks to verify the tick callback works on the server world
				LOGGER.info("Ticked Server World - at " + startString + " of " + worldTicks + " ticks:" + world.getRegistryKey().getValue());
			}

			if (!start) {
				this.tickTracker.put(world.getRegistryKey(), worldTicks + 1);
			}
		});
	}
}
