package stasisclient.modid.features

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Items
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.util.Hand

class AutoTotem {
    private val mc = MinecraftClient.getInstance()
    
    // Settings (You can hook these up to a GUI later)
    var enabled = true
    var delayTicks = 0 // Current delay counter
    var configDelay = 1 // Set this to 0 for "Instant", higher for slower
    var healthThreshold = 10.0 // Pop totem if health is below this
    var silent = true // If true, swaps without opening inventory UI

    fun onTick() {
        val player = mc.player ?: return
        if (!enabled) return

        // 1. Check Delay
        if (delayTicks > 0) {
            delayTicks--
            return
        }

        // 2. Check if we actually need a totem
        val offhandItem = player.getStackInHand(Hand.OFF_HAND).item
        if (offhandItem == Items.TOTEM_OF_UNDYING) return

        // 3. Find Totem in Inventory
        val totemSlot = findTotemSlot()
        
        if (totemSlot != -1) {
            swapToOffhand(totemSlot)
            delayTicks = configDelay
        }
    }

    private fun findTotemSlot(): Int {
        val inv = mc.player?.inventory ?: return -1
        // Check main inventory (slots 9-44)
        for (i in 0..44) {
            if (inv.getStack(i).item == Items.TOTEM_OF_UNDYING) {
                // Adjusting slot indices for ScreenHandler
                return if (i < 9) i + 36 else i
            }
        }
        return -1
    }

    private fun swapToOffhand(slot: Int) {
        val interactionManager = mc.interactionManager ?: return
        val player = mc.player ?: return

        // Slot 45 is the fixed ID for the offhand slot
        // 1. Pick up the totem
        interactionManager.clickSlot(player.playerScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, player)
        // 2. Click the offhand slot
        interactionManager.clickSlot(player.playerScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, player)
        // 3. Put whatever was in offhand back into the old slot (Double Handing)
        interactionManager.clickSlot(player.playerScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, player)
    }
}
