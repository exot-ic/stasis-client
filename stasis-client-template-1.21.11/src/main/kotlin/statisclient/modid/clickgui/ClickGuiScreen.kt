package stasisclient.modid.clickgui

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import stasisclient.modid.StasisclientClient // Verify this matches your Client file name
import java.awt.Color

class ClickGuiScreen : Screen(Text.of("ClickGUI")) {

    private val frames = mutableListOf<Frame>()

    init {
        // Combat Category
        frames.add(Frame("Combat", 20, 20))
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        // Subtle dark overlay behind the GUI
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

// --- ALL LOGIC FOR THE WINDOWS GOES HERE ---

class Frame(val name: String, var x: Int, var y: Int) {
    private val width = 100
    private val height = 15
    private var expandedModule: String? = null 

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        // 1. Header (Twilight Indigo: #363F59)
        context.fill(x, y, x + width, y + height, 0xFF363F59.toInt())
        context.drawText(null, name, x + 5, y + 4, 0xFF8EA0C1.toInt(), false) // Text (Slate Blue: #8EA0C1)

        var currentY = y + height
        
        // 2. Module Toggle
        val module = StasisclientClient.INSTANCE.autoTotem
        
        // Background: Twilight Indigo if ON, Shadow Grey (#1F2433) if OFF
        val moduleBg = if (module.enabled) 0xFF363F59.toInt() else 0xFF1F2433.toInt()
        val textColor = if (module.enabled) -1 else 0xFF8EA0C1.toInt() // White if ON, Slate if OFF
        
        context.fill(x, currentY, x + width, currentY + height, moduleBg)
        context.drawText(null, "AutoTotem", x + 10, currentY + 4, textColor, false)

        // 3. Settings (Expanded view)
        if (expandedModule == "AutoTotem") {
            currentY += height
            
            // Shared background for the settings area (Shadow Grey)
            context.fill(x, currentY, x + width, currentY + (height * 3), 0xFF1F2433.toInt())
            
            // Health Setting
            context.drawText(null, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, 0xFF8EA0C1.toInt(), false)
            
            // Delay Setting
            currentY += height
            context.drawText(null, "> Delay: ${module.configDelay.toInt()}", x + 15, currentY + 4, 0xFF8EA0C1.toInt(), false)
            
            // Silent Setting
            currentY += height
            val silentColor = if (module.silent) 0xFF8EA0C1.toInt() else 0xFF363F59.toInt()
            context.drawText(null, "> Silent: ${module.silent}", x + 15, currentY + 4, silentColor, false)
        }
    }

    fun handleClicked(mouseX: Double, mouseY: Double, button: Int) {
        val module = StasisclientClient.INSTANCE.autoTotem
        val moduleY = y + height

        // If clicking the AutoTotem row
        if (mouseX >= x && mouseX <= x + width && mouseY >= moduleY && mouseY <= moduleY + height) {
            if (button == 0) module.enabled = !module.enabled
            if (button == 1)
