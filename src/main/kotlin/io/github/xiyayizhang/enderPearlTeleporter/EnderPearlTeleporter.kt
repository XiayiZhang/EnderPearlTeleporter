package io.github.xiyayizhang.enderPearlTeleporter

//import org.bukkit.ChatColor
//import org.bukkit.ChatColor.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class EnderPearlTeleporter : JavaPlugin() {
    fun say(msg: String) {
        val sender = Bukkit.getConsoleSender()
        sender.sendMessage(msg)
    }

    override fun onEnable() {
        // Plugin startup logic
        //tpe指令
        say("Loading EndPearlTeleporter...")
        val bindeCommand = this.getCommand("binde")
        val tpeCommand = this.getCommand("tpe")

        if (bindeCommand == null) {
            say("Failed to register command: binde")
        } else {
            bindeCommand.setExecutor(EndPearlBinder())
            say("Command 'binde' registered successfully!")
        }

        if (tpeCommand == null) {
            say("Failed to register command: tpe")
        } else {
            tpeCommand.setExecutor(Teleporter())
            say("Command 'tpe' registered successfully!")
        }

        say("Successfully loaded 2 commands.")    }

    inner class EndPearlBinder : CommandExecutor {
        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
            say("Bind!")
            if (sender is Player) {
                val player = sender as Player
                var itemInHand = player.inventory.itemInMainHand

                if (itemInHand.type == Material.ENDER_PEARL) {
                    bindEnderPearlToPlayer(player, itemInHand)
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

        private fun bindEnderPearlToPlayer(player: Player, item: ItemStack) {
            // 绑定
            // UUID和末影珍珠的信息存储在一个Map中
            //val boundItems = mutableMapOf<UUID, ItemStack>()
            // boundItems[player.uniqueId] = item
            var itemInHand = player.inventory.itemInMainHand

            var value = if (itemInHand.type == Material.ENDER_PEARL) {
                var meta = itemInHand.itemMeta
                if (meta != null) {
                    // 清除旧的lore
                    meta.lore = null
                    // 添加新的lore
                    val playername = player.name
                    meta.lore = listOf(playername)

                    // 应用更改
                    itemInHand.itemMeta = meta
                    //#######player.inventory.itemInMainHand.itemMeta.persistentDataContainer.set(meta)
                    /// player.sendMessage( "已绑定玩家"+ player )
                    // } else {
                    //player.sendMessage( "该物品没有元数据，无法更新lore。")
                    // }
                    //val entity: Entity = ... // 获取或创建一个实体
                    /////val container: PersistentDataContainer =
                    /////    itemInHand.persistentDataContainer as PersistentDataContainer
                    // 定义不同的键
                    ////val intKey = NamespacedKey(this, "my-int-key")
                    ////val stringKey = NamespacedKey(this, "my-string-key")
                    ////val booleanKey = NamespacedKey(this, "my-boolean-key")
                    // 存储一个整数
                    ////container.set(intKey, PersistentDataType.INTEGER, 123)
                    // 存储一个字符串
                    /////container.set(stringKey, PersistentDataType.STRING, "hello world")
                    // 存储一个布尔值
                    ////container.set(booleanKey, PersistentDataType.BOOLEAN, true)
                    player.sendMessage("绑定成功！")

                } else {
                    player.sendMessage("你手中没有物品。")
                }
                player.sendMessage("绑定成功！")
            } else {
                player.sendMessage("请拿着末影珍珠")
            }


        }
    }
}
