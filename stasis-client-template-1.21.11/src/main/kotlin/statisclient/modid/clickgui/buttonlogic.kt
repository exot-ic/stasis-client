package stasisclient.modid.clickgui

import net.minecraft.client.gui.DrawContext
import java.awt.Color

class Frame(val name: String, val x: Int, val y: Int) {
    private val width = 100
    private val height = 15
    
    // In a real client, you'd loop through your actual module list here
    private val buttons = listOf("AutoTotem", "AutoCrystal") 

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        // Draw Category Header
        context.fill(x, y, x + width, y + height, Color.BLUE.rgb)
        context.drawText(null, name, x + 5, y + 4, Color.WHITE.rgb, false)

        // Draw Buttons below header
        var currentY = y + height
        for (moduleName in buttons) {
            context.fill(x, currentY, x + width, currentY + height, Color.DARK_GRAY.rgb)
            context.drawText(null, moduleName, x + 10, currentY + 4, Color.LIGHT_GRAY.rgb, false)
            currentY += height
        }
    }
        fun handleClicked(mouseX: Double, mouseY: Double, button: Int) {
    // Basic bounding box check (Simplified)
    // If mouse is over the "AutoTotem" button area:
    
    val module = StasisClient.INSTANCE.autoTotem
    
    if (button == 0) { // 0 is Left Click
        module.enabled = !module.enabled // Toggles true/false
        println("AutoTotem is now ${if (module.enabled) "ON" else "OFF"}")
    }
}
        // Check if mouseX/mouseY is within the bounds of a button
    }
}
