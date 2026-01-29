package statisclient.modid.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import statisclient.modid.StasisClient

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
        val tr = MinecraftClient.getInstance().textRenderer
        context.fill(x, y, x + width, y + height, 0xFF363F59.toInt())
        context.drawText(tr, name, x + 5, y + 4, 0xFF8EA0C1.toInt(), false)

        var currentY = y + height
        val module = StasisClient.INSTANCE.autoTotem
        
        val moduleBg = if (module.enabled) 0xFF363F59.toInt() else 0xFF1F2433.toInt()
        context.fill(x, currentY, x + width, currentY + height, moduleBg)
        context.drawText(tr, "AutoTotem", x + 10, currentY + 4, -1, false)

        if (expandedModule == "AutoTotem") {
            currentY += height
            context.fill(x, currentY, x + width, currentY + (height * 3), 0xFF1F2433.toInt())
            context.drawText(tr, "> Health: ${module.healthThreshold.toInt()}", x + 15, currentY + 4, 0xFF8EA0C1.toInt(), false)
        }
    }

    fun handleClicked(mouseX: Double, mouseY: Double, button: Int) {
        val module = StasisClient.INSTANCE.autoTotem
        if (mouseY >= y + height && mouseY <= y + height * 2) {
            if (button == 0) module.enabled = !module.enabled
            if (button == 1) expandedModule = if (expandedModule == null) "AutoTotem" else null
        }
    }
}
