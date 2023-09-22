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

package net.feltmc.feltapi.api.event.lifecycle.v1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ServerSharedTickEvents {
	private ServerSharedTickEvents() {
	}

	/**
	 * Called at both the start and the end of the server tick.
	 * Useful when you want to have less code
	 */
	public static final Event<Tick> SERVER_TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> (server, start) -> {
		for (Tick event : callbacks) {
			event.onTick(server, start);
		}
	});

	/**
	 * Called at both the start and the end of a ServerWorld's tick.
	 * Useful when you want to have less code
	 */
	public static final Event<WorldTick> WORLD_TICK = EventFactory.createArrayBacked(WorldTick.class, callbacks -> (world, start) -> {
		for (WorldTick callback : callbacks) {
			callback.onWorldTick(world, start);
		}
	});

	@FunctionalInterface
	public interface Tick {
		void onTick(MinecraftServer server, boolean start);
	}

	@FunctionalInterface
	public interface WorldTick {
		void onWorldTick(ServerLevel world, boolean start);
	}
}
