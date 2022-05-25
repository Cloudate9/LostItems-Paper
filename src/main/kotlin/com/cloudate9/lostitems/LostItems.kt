package com.cloudate9.lostitems

import com.cloudate9.lostitems.commands.commandModule
import com.cloudate9.lostitems.listeners.listenerModule
import com.cloudate9.lostitems.updater.updateModule
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

class LostItems: JavaPlugin() {

    override fun onEnable() {

        startKoin {
            modules(commandModule, listenerModule, updateModule)
        }

        Metrics(this, 15295)

        saveDefaultConfig()

        val pluginComponent = PluginComponent()
        pluginComponent.registerAllListeners()

        getCommand("lostitems")?.setExecutor(pluginComponent.subCommandManager)
        pluginComponent.startUpdateCheck()
    }

}