package com.cloudate9.lostitems.updater

import com.cloudate9.lostitems.dependencies.dependencyModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val updateModule = module {
    singleOf(::UpdateChecker)
    includes(dependencyModule)
}