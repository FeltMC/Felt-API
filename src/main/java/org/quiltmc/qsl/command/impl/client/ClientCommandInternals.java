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

package org.quiltmc.qsl.command.impl.client;

import static org.quiltmc.qsl.command.api.client.ClientCommandManager.DISPATCHER;

import java.util.Map;

import com.mojang.brigadier.AmbiguityConsumer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;

import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class ClientCommandInternals {
	private static final Logger LOGGER = LoggerFactory.getLogger(net.fabricmc.fabric.impl.command.client.ClientCommandInternals
.class);
	private static final char PREFIX = '/';
	private static final String API_COMMAND_NAME = "quilt_commands:client_commands";
	private static final String SHORT_API_COMMAND_NAME = "qcc";
	private static final Command<FabricClientCommandSource> DUMMY_COMMAND = ctx -> 0;

	/**
	 * Executes a client-sided command from a message.
	 *
	 * @param  message the command message
	 *
	 * @return {@code true} if the message was executed as a command client-side (and therefore should not be sent to the
	 *         server), {@code false} otherwise
	 */
	public static boolean executeCommand(String message) {
	return	net.fabricmc.fabric.impl.command.client.ClientCommandInternals.executeCommand(message);
	}

	/**
	 * Tests whether a parse result is invalid or the command it resolves to is a dummy command.
	 *
	 * Used to work out whether a command in the main dispatcher is a dummy command added by {@link ClientCommandInternals#addDummyCommands(CommandDispatcher, FabricClientCommandSource)}.
	 *
	 * @param parse the parse results to test
	 * @param <S>   the command source type
	 *
	 * @return {@code true} if the parse result is invalid or the command is a dummy, {@code false} otherwise
	 */
	//Credit to QuiltMC
	public static <S extends CommandSource> boolean isCommandInvalidOrDummy(final ParseResults<S> parse) {
		if (parse.getReader().canRead()) {
			return true;
		}

		final String command = parse.getReader().getString();
		final CommandContext<S> context = parse.getContext().build(command);

		return context.getCommand() == null || context.getCommand() == DUMMY_COMMAND;		
	
	}

	/**
	 * Tests whether a command syntax exception with the type
	 * should be ignored and the message sent to the server.
	 *
	 * @param type the exception type
	 * @return {@code true} if ignored, {@code false} otherwise
	 */
	//private static boolean shouldIgnore(CommandExceptionType type) {} Unneeded, it was private, referring to a private bool in Fabric API which could not be referenced and it was only used in the Fabric Execute Command constructor which we already have handled

	/**
	 * Analogous to {@code CommandSuggestor#formatException}, but returns a {@link Text} rather than an
	 * {@link net.minecraft.text.OrderedText OrderedText}.
	 *
	 * @param e the exception to get the error message from
	 *
	 * @return the error message as a {@link Text}
	 */
	//private static Text getErrorMessage(CommandSyntaxException e) {} Similar to last

	/**
	 * Registers client-sided commands, then runs final initialization tasks such as
	 * {@link CommandDispatcher#findAmbiguities(AmbiguityConsumer)} on the command dispatcher. Also registers
	 * a {@code /qcc help} command if there are other commands present.
	 */
	public static void initialize() {	net.fabricmc.fabric.impl.command.client.ClientCommandInternals.finalizeInit();}

	/**
	 * @return the {@code run} subcommand for {@code /qcc}
	 */
	

	/**
	 * Shows usage hints for a command node.
	 *
	 * @param startNode the node to get the usages of
	 * @param context the command context
	 *
	 * @return the amount of usage hints (i.e. the number of subcommands of startNode)
	 */


	/**
	 * Adds dummy versions of the client commands to a given command dispatcher. Used to add the commands to
	 * {@link ClientPlayNetworkHandler}'s command dispatcher for autocompletion.
	 *
	 * @param target the target command dispatcher
	 * @param source the command source - commands which the source cannot use are filtered out
	 */
	public static void addDummyCommands(CommandDispatcher<FabricClientCommandSource> target, FabricClientCommandSource source) {
		var originalToCopy = new Object2ObjectOpenHashMap<CommandNode<FabricClientCommandSource>, CommandNode<FabricClientCommandSource>>();
		originalToCopy.put(DISPATCHER.getRoot(), target.getRoot());
		copyChildren(DISPATCHER.getRoot(), target.getRoot(), source, originalToCopy);
	}

	/**
	 * Copies the child commands from origin to target, filtered by {@code child.canUse(source)}.
	 * Mimics vanilla's {@code CommandManager#makeTreeForSource}. Runs recursively.
	 *
	 * @param origin         the source command node
	 * @param target         the target command node
	 * @param source         the command source
	 * @param originalToCopy a mutable map from original command nodes to their copies, used for redirects;
	 *                       should contain a mapping from origin to target
	 */
	private static void copyChildren(
			CommandNode<FabricClientCommandSource> origin,
			CommandNode<FabricClientCommandSource> target,
			FabricClientCommandSource source,
			Map<CommandNode<FabricClientCommandSource>, CommandNode<FabricClientCommandSource>> originalToCopy
	) {
		for (CommandNode<FabricClientCommandSource> child : origin.getChildren()) {
			if (!child.canUse(source)) continue;

			if (target.getChild(child.getName()) != null) {
				continue;
			}

			ArgumentBuilder<FabricClientCommandSource, ?> builder = child.createBuilder();

			// Reset the unnecessary non-completion stuff from the builder
			builder.requires(s -> true); // This is checked with the if check above.

			if (builder.getCommand() != null) {
				builder.executes(DUMMY_COMMAND);
			}

			// Set up redirects
			if (builder.getRedirect() != null) {
				builder.redirect(originalToCopy.get(builder.getRedirect()));
			}

			CommandNode<FabricClientCommandSource> result = builder.build();
			originalToCopy.put(child, result);
			target.addChild(result);

			if (!child.getChildren().isEmpty()) {
				copyChildren(child, result, source, originalToCopy);
			}
		}
	}
}
