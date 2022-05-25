package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.Plugin

class EnabledSubCommand(miniMessage: MiniMessage, plugin: Plugin) :
    SimpleBooleanSubCommand("enabled", miniMessage, plugin) {
    override val name: List<String>
        get() = listOf("enabled")
}