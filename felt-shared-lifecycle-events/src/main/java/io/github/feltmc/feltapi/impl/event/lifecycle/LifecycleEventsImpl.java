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

package io.github.feltmc.feltapi.impl.event.lifecycle;

import io.github.feltmc.feltapi.api.event.lifecycle.v1.ServerSharedTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.impl.event.lifecycle.LoadedChunksCache;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.chunk.WorldChunk;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public final class LifecycleEventsImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerTickEvents.START_SERVER_TICK.register(server -> ServerSharedTickEvents.SERVER_TICK.invoker().onTick(server, true));
		ServerTickEvents.END_SERVER_TICK.register(server -> ServerSharedTickEvents.SERVER_TICK.invoker().onTick(server, false));
		ServerTickEvents.START_WORLD_TICK.register(world -> ServerSharedTickEvents.WORLD_TICK.invoker().onWorldTick(world, true));
		ServerTickEvents.END_WORLD_TICK.register(world -> ServerSharedTickEvents.WORLD_TICK.invoker().onWorldTick(world, false));
	}
}
