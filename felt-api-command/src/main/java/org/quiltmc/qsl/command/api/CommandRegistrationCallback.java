package org.quiltmc.qsl.command.api;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.command.ServerCommandSource;

public interface CommandRegistrationCallback {
	Event<net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback> EVENT = net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT;

	void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated);
}