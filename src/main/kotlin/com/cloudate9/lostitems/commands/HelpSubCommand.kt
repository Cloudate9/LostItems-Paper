package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class HelpSubCommand(private val miniMessage: MiniMessage, private val plugin: Plugin): SubCommand {

    override fun call(sender: CommandSender, args: List<String>) {
        plugin.config.getConfigurationSection("messages.help")?.getKeys(false)?.forEach {
            sender.sendMessage(miniMessage.deserialize(plugin.config.getString("messages.help.$it")!!))
        }

        sender.sendMessage(miniMessage.deserialize(plugin.config.getString("messages.success")!!))
    }

    override val name: List<String>
        get() = listOf("help", "h")
}