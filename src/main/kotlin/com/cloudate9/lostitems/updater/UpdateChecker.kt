package com.cloudate9.lostitems.updater

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*

class UpdateChecker(private val plugin: Plugin) {

    private var firstCheck = true
    var updateFound = false
        private set

    fun check() {
        object : BukkitRunnable() {
            override fun run() {
                try {
                    val readGit = Scanner(
                        InputStreamReader(
                            URL(
                                "https://raw.githubusercontent.com/Cloudate9/LostItems-Paper/master/build.gradle.kts"
                            ).openStream()
                        )
                    )

                    var version = ""
                    while (readGit.hasNext()) {
                        val line = readGit.nextLine()

                        if (line.startsWith("version = ")) {
                            //Targeted line example: version = "2.0.0"
                            version = line.split('"')[1].removeSuffix("\"")
                            break
                        }
                    }

                    if (version == plugin.description.version) {
                        if (firstCheck) {
                            plugin.logger.info(
                                "is up to date!"
                            )
                            firstCheck = false
                        }


                        plugin.server.scheduler.scheduleSyncDelayedTask(
                            plugin, { check() }, 20 * 60 * 60 * 8
                        )
                        return
                    }

                    updateFound = true

                    plugin.logger.info(
                        "can be updated at " +
                                "https://www.curseforge.com/minecraft/bukkit-plugins/lost-items/\n" +
                                "If you don't see the update yet, come back in 24-48 hours. It should be ready by then."
                        )
                } catch (ex: IOException) {
                    plugin.logger.info(
                        "Failed to check for updates!"
                    )
                    plugin.server.scheduler.scheduleSyncDelayedTask(
                        plugin, { check() }, 20 * 60 * 60 * 8
                    )
                }
            }
        }.runTaskAsynchronously(plugin)
    }
}