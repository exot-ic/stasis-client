package statisclient.modid.features

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Items
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.util.Hand

class AutoTotem {
    var enabled = false
    var healthThreshold = 10.0
    var configDelay = 2.0
    var silent = false
    private var delayTicks = 0

    fun onTick() {
        val mc = MinecraftClient.getInstance()
        if (!enabled || mc.player == null) return

        if (delayTicks > 0) {
            delayTicks--
            return
        }

        val player = mc.player!!
        // Check if already holding a totem
        if (player.mainHandStack.item == Items.TOTEM_OF_UNDYING || player.offHandStack.item == Items.TOTEM_OF_UNDYING) return

        if (player.health <= healthThreshold) {
            val totemSlot = findTotem(player.inventory)
            if (totemSlot != -1) {
                moveTotemToOffhand(mc, totemSlot)
                delayTicks = configDelay.toInt()
            }
        }
    }

    private fun findTotem(inventory: PlayerInventory): Int {
        for (i in 0..44) {
            if (inventory.getStack(i).item == Items.TOTEM_OF_UNDYING) return i
        }
        return -1
    }

    private fun moveTotemToOffhand(mc: MinecraftClient, slot: Int) {
        val syncId = mc.player!!.playerScreenHandler.syncId
        // Minecraft inventory slot math: 
        val fixedSlot = if (slot < 9) slot + 36 else slot
        
        mc.interactionManager?.clickSlot(syncId, fixedSlot, 0, SlotActionType.PICKUP, mc.player)
        mc.interactionManager?.clickSlot(syncId, 45, 0, SlotActionType.PICKUP, mc.player)
        mc.interactionManager?.clickSlot(syncId, fixedSlot, 0, SlotActionType.PICKUP, mc.player)
    }
}
