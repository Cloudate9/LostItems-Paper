package com.cloudate9.lostitems.listeners

import com.cloudate9.lostitems.dependencies.dependencyModule
import org.bukkit.event.Listener
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.named
import org.koin.dsl.module

val listenerModule = module {
    factoryOf(::RemoveItem) {
        named("removeItem")
        bind<Listener>()
    }
    factoryOf(::UpdateInformer) {
        named("updateInformer")
        bind<Listener>()
    }
    includes(dependencyModule)
}