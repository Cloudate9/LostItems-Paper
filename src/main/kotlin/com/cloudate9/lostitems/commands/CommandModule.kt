package com.cloudate9.lostitems.commands

import com.cloudate9.lostitems.dependencies.dependencyModule
import org.bukkit.command.CommandExecutor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.named
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module


val commandModule = module {

    factoryOf(::BrutalModeSubCommand) {
        named("brutalMode")
        bind<SubCommand>()
    }

    factoryOf(::EChestRemoveSubCommand) {
        named("eChestRemove")
        bind<SubCommand>()
    }

    factoryOf(::EnabledSubCommand) {
        named("enabled")
        bind<SubCommand>()
    }

    factoryOf(::ReloadSubCommand) {
        named("reload")
        bind<SubCommand>()
    }

    factoryOf(::HelpSubCommand) {
        named("default")
        bind<SubCommand>()
    }

    single {(subCommands: List<SubCommand>) ->
        SubCommandManager(get(qualifier = named("default")), get(), get(), subCommands)
    } bind CommandExecutor::class
    includes(dependencyModule)
}
