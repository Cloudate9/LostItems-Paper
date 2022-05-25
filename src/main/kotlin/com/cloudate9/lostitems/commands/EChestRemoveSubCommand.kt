package com.cloudate9.lostitems.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.Plugin

class EChestRemoveSubCommand(miniMessage: MiniMessage, plugin: Plugin) :
    SimpleBooleanSubCommand("eChestRemove", miniMessage, plugin) {
    override val name
        get() = listOf("enderchestremove", "echestremove", "ecr")
}