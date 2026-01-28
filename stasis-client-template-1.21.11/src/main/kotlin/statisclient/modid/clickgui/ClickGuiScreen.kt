package stasisclient.modid.clickgui

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import stasisclient.modid.StasisClient // Make sure this import matches your main class
import java.awt.Color

class ClickGuiScreen : Screen(Text.of("ClickGUI")) {

    private val frames = mutableListOf<Frame>()

    init {
        // We add the "Combat" category at X:20, Y:20
        frames.add(Frame("Combat", 20, 20))
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context, mouseX, mouseY, delta)
        for (frame in frames) {
            frame.render(context, mouseX, mouseY)
        }
        super.render(context, mouseX, mouseY, delta)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (frame in frames) {
            frame.handleClicked(mouseX, mouseY, button)
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun shouldPause(): Boolean = false
}

// --- NEW CODE GOES BELOW THE SCREEN CLASS ---

fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
    // 1. Draw Category Header (Twilight Indigo)
    context.fill(x, y, x + width, y + height, 0xFF363F59.toInt()) 
    context.drawText(null, name, x + 5, y + 4, 0xFF8EA0C1.toInt(), false) // Slate Blue Text

    var currentY = y + height
    
    val module = StasisClient.INSTANCE.autoTotem
    // Module is Shadow Grey if off, a slightly brighter Indigo if on
    val moduleBgColor = if (module.enabled) 0xFF363F59.toInt() else 0xFF1F2433.toInt()
    val textColor = if (module.enabled) -1 else 0xFF8EA0C1.toInt() // White if on, Slate if off
    
    // 2. Draw Module Button
    context.fill(x, currentY, x + width, currentY + height, moduleBgColor)
    context.drawText(null, "AutoTotem", x + 10, currentY + 4, textColor, false)

    // 3. Draw Settings (Shadow Grey background for contrast)
    if (expandedModule == "AutoTotem") {
        currentY += height
        // Setting background
        context.fill(x, currentY, x + width, currentY + (height * 3), 0xFF1F2433.toInt())
        
        // Setting Text (Slate Blue)
        context.drawText(null, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, 0xFF8EA0C1.toInt(), false)
        context.drawText(null, "> Delay: ${module.configDelay.toInt()}", x + 15, currentY + height + 4, 0xFF8EA0C1.toInt(), false)
        context.drawText(null, "> Silent: ${module.silent}", x + 15, currentY + (height * 2) + 4, 0xFF8EA0C1.toInt(), false)
    }
}
        
        // 2. Reference the AutoTotem module from your main class
        val module = StasisClient.INSTANCE.autoTotem
        val moduleColor = if (module.enabled) Color(100, 255, 100).rgb else Color(180, 180, 180).rgb
        
        // Draw the main Toggle Button
        context.fill(x, currentY, x + width, currentY + height, Color(45, 45, 45).rgb)
        context.drawText(null, "AutoTotem", x + 10, currentY + 4, moduleColor, false)

        // 3. Draw Settings if Right-Clicked (Expanded)
        if (expandedModule == "AutoTotem") {
            currentY += height
            
            // Health Setting
            context.fill(x, currentY, x + width, currentY + height, Color(35, 35, 35).rgb)
            context.drawText(null, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, -1, false)
            
            // Delay Setting
            currentY += height
            context.fill(x, currentY, x + width, currentY + height, Color(35, 35, 35).rgb)
            context.drawText(null, "> Delay:
