package com.shellwen.minestom_demo.ktx

import net.kyori.adventure.text.logger.slf4j.ComponentLogger

inline fun <reified T> T.getLogger(): ComponentLogger {
    return ComponentLogger.logger(T::class.java.simpleName ?: "Unknown")
}
