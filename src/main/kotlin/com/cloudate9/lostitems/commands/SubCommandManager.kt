package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.util.StringUtil

class SubCommandManager(
    private val defaultSubCommand: SubCommand,
    private val miniMessage: MiniMessage,
    private val plugin: Plugin,
    private val subCommands: List<SubCommand>
) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender, command: Command, alias: String, args: Array<out String>
    ): Boolean {

        if (sender is Player && !sender.hasPermission("lostitems.configure")) {
            sender.sendMessage(
                miniMessage.deserialize(
                    plugin.config.getString("messages-noPerms")
                        ?: "<red>You don't have permission to use this command.</red>"
                )
            )
            return true
        }

        val subArgs = args.filterIndexed { index, _ -> index != 0 }

        if (args.isNotEmpty()) {
            for (subCommand in subCommands) {
                if (subCommand.name.contains(args[0].lowercase())) {
                    subCommand.call(sender, subArgs)
                    return true
                }
            }
        }

        defaultSubCommand.call(sender, subArgs)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, alias: String, args: Array<out String>
    ): List<String> {
        //Perhaps this implementation isn't the most efficient...

        //Always work with args.length - 1, as the latest arg is the command that is being typed.
        if (args.size == 1) {
            val tabCompleteList = mutableListOf<String>()

            for (subCommand in subCommands) subCommand.tabComplete[0][emptyList()]?.let {
                tabCompleteList.addAll(
                    it
                )
            }

            return StringUtil.copyPartialMatches(args[0], tabCompleteList, mutableListOf())
        }

        for (subCommand in subCommands) {
            if (subCommand.tabComplete.size < args.size) continue
            for (i in 1 until args.size) { //Start with i = 1, because i = 0 has no requirements.
                var found = false

                //Check if prior subcommand is valid!
                for (entry in subCommand.tabComplete[i].entries) {
                    //Key should contain past argument.
                    if (entry.key.contains(args[i - 1].lowercase())) {
                        return if (i + 1 == args.size) StringUtil.copyPartialMatches(
                            args[i], entry.value, ArrayList()
                        ) else { //Unnecessary else, but just here for clarity.
                            found = true
                            break
                        }
                    }
                }
                if (!found) break
            }
        }
        return emptyList()
    }
}
