package stasisclient.modid.clickgui

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

class ClickGuiScreen : Screen(Text.of("ClickGUI")) {

    // A list of frames (Categories)
    private val frames = mutableListOf<Frame>()

    init {
        // Example: Adding a Combat category
        frames.add(Frame("Combat", 20, 20))
        // You can add more categories here (Movement, Player, etc.)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        // Draw a dark background overlay
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

    override fun shouldPause(): Boolean = false // Keeps the game running in background
}
