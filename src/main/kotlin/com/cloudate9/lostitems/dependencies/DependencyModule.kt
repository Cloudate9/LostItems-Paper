package com.cloudate9.lostitems.dependencies

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.koin.dsl.module

val dependencyModule = module {
    factory { Bukkit.getPluginManager().getPlugin("LostItems") }
    single { MiniMessage.builder().build() }
}