package com.cloudate9.lostitems.listeners

import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.plugin.Plugin

class RemoveItem(val plugin: Plugin) : Listener {

    @EventHandler
    fun onPlayerDamage(e: EntityDamageEvent) {
        if (e.entityType != EntityType.PLAYER) return

        val player = e.entity as Player
        if (player.gameMode != GameMode.SURVIVAL && player.gameMode != GameMode.ADVENTURE) return

        val config = plugin.config
        if (!config.getBoolean("options.enabled")) return

        if (config.getBoolean("options.brutalMode")) {
            val inventoryItems =
                player.inventory.contents?.mapNotNull { it?.type } ?: emptyList()
            val enderChestItems = if (config.getBoolean("options.eChestRemove"))
                player.enderChest.contents?.mapNotNull { it?.type }
            else emptyList()

            val allItems = (inventoryItems + enderChestItems).distinct()
            if (allItems.isEmpty()) return
            allItems.random()?.let {
                player.inventory.contents = player.inventory.contents?.apply {
                    forEachIndexed { index, itemStack ->
                        if (itemStack?.type == it) this[index] = null
                    }
                }

                if (enderChestItems?.isNotEmpty() ?: return) {
                    player.enderChest.contents = player.enderChest.contents?.apply {
                        forEachIndexed { index, itemStack ->
                            if (itemStack?.type == it) this[index] = null
                        }
                    }
                }
            }
            return
        }


        val itemNums = mutableListOf<Int>()

        //HotBar is 0-8 (Left to Right), Inventory is 9-35 (LTR, top to bottom), Armor is 36-39 (Boots to Helmet), Offhand is 40.
        player.inventory.contents?.forEachIndexed { index, item ->
            item?.let { itemNums.add(index) }
        }

        if (config.getBoolean("options.eChestRemove")) {
            player.enderChest.contents?.forEachIndexed { index, item ->
                item?.let { itemNums.add(index + 40) } //40 is the max size for the normal player inventory
            }
        }

        if (itemNums.isEmpty()) return
        itemNums.random().let {
            if (it < 41) {
                player.inventory.contents = player.inventory.contents?.apply {
                    this[it] = null
                }
            } else {
                player.enderChest.contents = player.enderChest.contents?.apply {
                    this[it - 40] = null
                }
            }
        }

    }
}
