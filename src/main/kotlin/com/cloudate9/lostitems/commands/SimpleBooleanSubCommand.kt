package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

open class SimpleBooleanSubCommand(
    private val configName: String,
    private val miniMessage: MiniMessage,
    private val plugin: Plugin
) :
    SubCommand {

    final override fun call(sender: CommandSender, args: List<String>) {
        val config = plugin.config
        if (args.isEmpty()) {
            sender.sendMessage(
                miniMessage.deserialize(
                    config.getString("messages.help.$configName")!!
                )
            )
            return
        }

        when (args[0].lowercase()) {
            "enable", "true" -> plugin.config.set("options.$configName", true)
            "disable", "false" -> plugin.config.set("options.$configName", false)
            else -> {
                sender.sendMessage(
                    miniMessage.deserialize(
                        config.getString("messages.help.$configName")!!
                    )
                )
                return
            }
        }
        plugin.saveConfig()
        sender.sendMessage(
            miniMessage.deserialize(
                config.getString("messages.success") ?: "<green>Command successful!</green>"
            )
        )
    }

    override val name: List<String>
        get() = listOf("default")

    final override val tabComplete: List<Map<List<String>, List<String>>>
        get() = listOf(mapOf(emptyList<String>() to name), mapOf(name to listOf("true", "false")))
}