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

class Frame(val name: String, val x: Int, val y: Int) {
    private val width = 100
    private val height = 15
    private var expandedModule: String? = null 

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        // 1. Draw Category Header
        context.fill(x, y, x + width, y + height, Color(60, 60, 240).rgb)
        context.drawText(null, name, x + 5, y + 4, -1, false)

        var currentY = y + height
        
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
