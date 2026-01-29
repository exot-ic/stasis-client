package statisclient.modid.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import statisclient.modid.StasisClient
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
        val mc = MinecraftClient.getInstance()
        val tr = mc.textRenderer
        
        // Colors
        val headerColor = 0xFF363F59.toInt()
        val textColor = 0xFF8EA0C1.toInt()
        val bgColor = 0xFF1F2433.toInt()
        
        // 1. Header
        context.fill(x, y, x + width, y + height, headerColor)
        context.drawText(tr, name, x + 5, y + 4, textColor, false)

        var currentY = y + height
        
        // 2. Module
        val module = StasisClient.INSTANCE.autoTotem
        val moduleBg = if (module.enabled) headerColor else bgColor
        val moduleTextColor = if (module.enabled) -1 else textColor
        
        context.fill(x, currentY, x + width, currentY + height, moduleBg)
        context.drawText(tr, "AutoTotem", x + 10, currentY + 4, moduleTextColor, false)

        // 3. Settings
        if (expandedModule == "AutoTotem") {
            currentY += height
            context.fill(x, currentY, x + width, currentY + (height * 3), bgColor)
            
            context.drawText(tr, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, textColor, false)
            context.drawText(tr, "> Delay: ${module.configDelay.toInt()}", x + 15, currentY + height + 4, textColor, false)
            context.drawText(tr, "> Silent: ${module.silent}", x + 15, currentY + (height * 2) + 4, textColor, false)
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
