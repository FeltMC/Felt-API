/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 QuiltMC
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

package org.quiltmc.qsl.command.api.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class ClientCommandManager {
	
	public static final CommandDispatcher<FabricClientCommandSource> DISPATCHER = net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER;
	
	public static LiteralArgumentBuilder<FabricClientCommandSource> literal(String name) {
return		net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal(name);

	}

	public static <T> RequiredArgumentBuilder<FabricClientCommandSource, T> argument(String name, ArgumentType<T> type) {
		return		net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.argument(name, type);
	}
	
	
}
