package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class ReloadSubCommand(
    private val miniMessage: MiniMessage,
    private val plugin: Plugin,
) : SubCommand {

    override fun call(sender: CommandSender, args: List<String>) {
        plugin.reloadConfig()
        sender.sendMessage(
            miniMessage.deserialize(
                plugin.config.getString("messages.reload")
                    ?: "<green>Reloaded config file.</green>"
            )
        )
    }

    override val name: List<String>
        get() = listOf("reload", "rl")
}