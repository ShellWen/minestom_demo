package com.shellwen.minestom_demo

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.shellwen.minestom_demo.ktx.getLogger
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.generator.GenerationUnit
import net.minestom.server.timer.TaskSchedule

class DemoServer : CliktCommand() {
    private val debug by option().flag(default = false)

    private val logger = getLogger()

    private fun runChucksStorage(instanceContainer: InstanceContainer) {
        MinecraftServer.getSchedulerManager().submitTask {
            instanceContainer.saveChunksToStorage()
            return@submitTask TaskSchedule.seconds(5)
        }
    }
    private fun initInstanceContainer(): InstanceContainer {
        return MinecraftServer.getInstanceManager().createInstanceContainer().apply {
            setGenerator { unit: GenerationUnit ->
                unit.modifier().fillHeight(0, 1, Block.GRASS_BLOCK)
            }
        }
    }
    override fun run() {
        if (debug) {
            logger.info("Debug mode.")
        }

        val instanceContainer = initInstanceContainer()
        runChucksStorage(instanceContainer)

        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent::class.java) {
            it.player.apply {
                respawnPoint = Pos(0.0, 200.0, 0.0)
                gameMode = GameMode.CREATIVE
            }
            it.setSpawningInstance(instanceContainer)
        }

        MinecraftServer.init().start("0.0.0.0", 25565)
    }
}

fun main(args: Array<String>) =
    DemoServer().main(args)