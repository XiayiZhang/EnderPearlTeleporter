package io.github.xiyayizhang.enderPearlTeleporter

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Teleporter : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val player = sender as Player
            val itemInHand = player.inventory.itemInMainHand

            if (itemInHand.type == Material.ENDER_PEARL) {
                Teleport(player, itemInHand)
                return true
            } else {
                player.sendMessage("You must hold an Ender Pearl to use this command.")
                return false
            }
        } else {
            sender.sendMessage("This command can only be used by players.")
            return false
        }
    }

    private fun Teleport(player: Player, item: ItemStack) {
        val meta = item.itemMeta
        if (meta != null && meta.lore != null && meta.lore!!.isNotEmpty()) {
            val boundPlayerName = meta.lore!![0].replace("Bound to: ", "")
            val targetPlayer = Bukkit.getPlayer(boundPlayerName)

            if (targetPlayer != null) {
                //开始传送
                val location = player.location
                targetPlayer.world.spawnParticle(Particle.ENCHANT , location, 30, 0.5, 0.5, 0.5, 0.1)
                targetPlayer.teleport(location)
                player.sendMessage("Teleported ${targetPlayer.name} to your location!")
            } else {
                player.sendMessage("Bound player not found or offline.")
            }
        } else {
            player.sendMessage("This Ender Pearl is not bound to any player.")
        }
    }
}