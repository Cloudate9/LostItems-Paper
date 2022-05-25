package com.cloudate9.lostitems

import com.cloudate9.lostitems.commands.SubCommand
import com.cloudate9.lostitems.updater.UpdateChecker
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class PluginComponent : KoinComponent {
    private val subCommands = getKoin().getAll<SubCommand>()
    val subCommandManager by inject<CommandExecutor> { parametersOf(subCommands) }

    private val plugin by inject<Plugin>()
    fun registerAllListeners() =
        getKoin().getAll<Listener>().forEach { Bukkit.getPluginManager().registerEvents(it, plugin) }

    fun startUpdateCheck() = getKoin().get<UpdateChecker>().check()
}