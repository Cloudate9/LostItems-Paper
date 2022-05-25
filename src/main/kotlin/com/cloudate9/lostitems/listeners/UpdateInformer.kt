package com.cloudate9.lostitems.listeners

import com.cloudate9.lostitems.updater.UpdateChecker
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin

class UpdateInformer(
    private val miniMessage: MiniMessage,
    private val plugin: Plugin,
    private val updateChecker: UpdateChecker
) : Listener {

    @EventHandler
    fun informUpdate(e: PlayerJoinEvent) {
        if (!e.player.hasPermission("lostitems.updater")) return
        val config = plugin.config
        if (config.getBoolean("updateCheck") && updateChecker.updateFound) {
            e.player.sendMessage(
                miniMessage
                    .deserialize(config.getString("message.miniMessage.updateFound")!!)
                    .clickEvent(
                        ClickEvent.openUrl(
                            "https://www.curseforge.com/minecraft/bukkit-plugins/insta-mine-deepslate/"
                        )
                    )
            )
        }
    }
}