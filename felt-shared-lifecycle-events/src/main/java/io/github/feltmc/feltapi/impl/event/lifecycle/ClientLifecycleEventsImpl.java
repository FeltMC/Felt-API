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

import io.github.feltmc.feltapi.api.client.event.lifecycle.v1.ClientSharedTickEvents;
import io.github.feltmc.feltapi.api.event.lifecycle.v1.ServerSharedTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.impl.event.lifecycle.LoadedChunksCache;
import net.minecraft.block.entity.BlockEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;

@Environment(EnvType.CLIENT)
public final class ClientLifecycleEventsImpl implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.START_CLIENT_TICK.register(server -> ClientSharedTickEvents.CLIENT_TICK.invoker().onTick(server, true));
		ClientTickEvents.END_CLIENT_TICK.register(server -> ClientSharedTickEvents.CLIENT_TICK.invoker().onTick(server, false));
		ClientTickEvents.START_WORLD_TICK.register(world -> ClientSharedTickEvents.WORLD_TICK.invoker().onWorldTick(world, true));
		ClientTickEvents.END_WORLD_TICK.register(world -> ClientSharedTickEvents.WORLD_TICK.invoker().onWorldTick(world, false));
	}
}
