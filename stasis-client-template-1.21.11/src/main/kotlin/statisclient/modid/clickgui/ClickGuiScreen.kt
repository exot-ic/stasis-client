package stasisclient.modid.clickgui // Updated to 'stasis'

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import stasisclient.modid.StasisClient // Updated to 'stasis'
import java.awt.Color

class ClickGuiScreen : Screen(Text.of("ClickGUI")) {
    private val frames = mutableListOf<Frame>()

    init {
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

class Frame(val name: String, var x: Int, var y: Int) {
    private val width = 100
    private val height = 15
    private var expandedModule: String? = null 

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        // Twilight Indigo: #363F59
        context.fill(x, y, x + width, y + height, 0xFF363F59.toInt())
        // Accessing textRenderer via the client instance
        val textRenderer = net.minecraft.client.MinecraftClient.getInstance().textRenderer
        context.drawText(textRenderer, name, x + 5, y + 4, 0xFF8EA0C1.toInt(), false)

        var currentY = y + height
        
        val module = StasisClient.INSTANCE.autoTotem
        val moduleBg = if (module.enabled) 0xFF363F59.toInt() else 0xFF1F2433.toInt()
        val textColor = if (module.enabled) -1 else 0xFF8EA0C1.toInt()
        
        context.fill(x, currentY, x + width, currentY + height, moduleBg)
        context.drawText(textRenderer, "AutoTotem", x + 10, currentY + 4, textColor, false)

        if (expandedModule == "AutoTotem") {
            currentY += height
            context.fill(x, currentY, x + width, currentY + (height * 3), 0xFF1F2433.toInt())
            
            context.drawText(textRenderer, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, 0xFF8EA0C1.toInt(), false)
            context.drawText(textRenderer, "> Delay: ${module.configDelay.toInt()}", x + 15, currentY + height + 4, 0xFF8EA0C1.toInt(), false)
            context.drawText(textRenderer, "> Silent: ${module.silent}", x + 15, currentY + (height * 2) + 4, 0xFF8EA0C1.toInt(), false)
        }
    }

    fun handleClicked(mouseX: Double, mouseY: Double, button: Int) {
        val module = StasisClient.INSTANCE.autoTotem
        val moduleY = y + height

        if (mouseX >= x && mouseX <= x + width && mouseY >= moduleY && mouseY <= moduleY + height) {
            if (button == 0) module.enabled = !module.enabled
            if (button == 1) expandedModule = if (expandedModule == "AutoTotem") null else "AutoTotem"
        }
        
        if (expandedModule == "AutoTotem") {
            if (mouseY >= moduleY + height && mouseY <= moduleY + (height * 2)) {
                module.healthThreshold = if (module.healthThreshold >= 20.0) 2.0 else module.healthThreshold + 2.0
            }
            if (mouseY >= moduleY + (height * 2) && mouseY <= moduleY + (height * 3)) {
                module.configDelay = if (module.configDelay >= 10.0) 0.0 else module.configDelay + 1.0
            }
            if (mouseY >= moduleY + (height * 3) && mouseY <= moduleY + (height * 4)) {
                module.silent = !module.silent
            }
        }
    }
}
