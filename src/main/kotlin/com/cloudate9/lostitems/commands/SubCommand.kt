package com.cloudate9.lostitems.commands

import org.bukkit.command.CommandSender


interface SubCommand {

    /**
     * Call the sub command.
     *
     * @param sender  The entity that sent the subcommand.
     * @param args    Arguments passed with the subcommand.
     */
    fun call(sender: CommandSender, args: List<String>)

    /**
     * The names that the subcommand can be called with
     *
     * @return Subcommand names
     */
    val name: List<String>

    /**
     * The tab complete options for a subcommand.
     *
     *
     * The outermost list contains the order of tab complete options.
     * Index 0 for the subcommand, index 1 for subcommand option level 1, index 2 for subcommand option level 2, etc.
     *
     *
     * The map is a key value pair, where the key is the previous list of tab complete options in this tab complete branch,
     * and the value is the next set of tab complete options in this tab complete branch.
     * If there are no prior options for the tab complete branch (e.g. level 0 of tab complete), use an empty list as the key.
     *
     * @return Tab complete that will be sent to a Minecraft client.
     */
    val tabComplete: List<Map<List<String>, List<String>>>
        get() = listOf(
            mapOf(emptyList<String>() to name)
        )
}
