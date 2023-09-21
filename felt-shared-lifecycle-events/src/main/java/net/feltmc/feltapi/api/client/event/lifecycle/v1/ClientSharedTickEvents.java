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

package net.feltmc.feltapi.api.client.event.lifecycle.v1;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

@Environment(EnvType.CLIENT)
public final class ClientSharedTickEvents {
	public ClientSharedTickEvents() {
	}

	/**
	 * Called at the start of the client tick.
	 */
	public static final Event<Tick> CLIENT_TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> (client, start) -> {
		for (Tick event : callbacks) {
			event.onTick(client, start);
		}
	});

	/**
	 * Called at the start of a ClientWorld's tick.
	 */
	public static final Event<WorldTick> WORLD_TICK = EventFactory.createArrayBacked(WorldTick.class, callbacks -> (world, start) -> {
		for (WorldTick callback : callbacks) {
			callback.onWorldTick(world, start);
		}
	});

	@FunctionalInterface
	public interface Tick {
		void onTick(Minecraft client, boolean start);
	}

	@FunctionalInterface
	public interface WorldTick {
		void onWorldTick(ClientLevel world, boolean start);
	}
}
