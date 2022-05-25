package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.Plugin

class BrutalModeSubCommand(miniMessage: MiniMessage, plugin: Plugin) :
    SimpleBooleanSubCommand("brutalMode", miniMessage, plugin) {
    override val name: List<String>
        get() = listOf("brutalmode", "brutal", "bm")

}