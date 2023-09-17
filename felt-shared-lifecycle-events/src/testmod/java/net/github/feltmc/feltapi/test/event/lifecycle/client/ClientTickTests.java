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

package net.github.feltmc.feltapi.test.event.lifecycle.client;

import java.util.HashMap;
import java.util.Map;

import net.feltmc.feltapi.api.client.event.lifecycle.v1.ClientSharedTickEvents;
import net.github.feltmc.feltapi.test.event.lifecycle.ServerTickTests;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ClientTickTests implements ClientModInitializer {
	private final Map<RegistryKey<World>, Integer> tickTracker = new HashMap<>();
	private int ticks;

	@Override
	public void onInitializeClient() {
		ClientSharedTickEvents.CLIENT_TICK.register((client, start) -> {
			if (start) {
				this.ticks++; // Just track our own tick since the client doesn't have a ticks value.
			}
			String startString = start ? "start" : "end";

			if (this.ticks % 200 == 0) {
				ServerTickTests.LOGGER.info("Ticked Client at " + startString + " of " + this.ticks + " ticks.");
			}
		});

		ClientSharedTickEvents.WORLD_TICK.register((world, start) -> {
			final int worldTicks = this.tickTracker.computeIfAbsent(world.getRegistryKey(), k -> 0);
			String startString = start ? "start" : "end";
			if (worldTicks % 200 == 0) { // Log every 200 ticks to verify the tick callback works on the client world
				ServerTickTests.LOGGER.info("Ticked Client World - at " + startString + " of " + worldTicks + " ticks:" + world.getRegistryKey());
			}

			if (!start) {
				this.tickTracker.put(world.getRegistryKey(), worldTicks + 1);
			}
		});
	}
}
