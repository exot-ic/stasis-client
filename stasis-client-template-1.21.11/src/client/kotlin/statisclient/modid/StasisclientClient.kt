package statisclient.modid

import net.fabricmc.api.ClientModInitializer

object StasisclientClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
	// Top of your Client class
val mappingKey = KeyBindingHelper.registerKeyBinding(KeyBinding(
    "key.stasis.clickgui", 
    InputUtil.Type.KEYSYM, 
    GLFW.GLFW_KEY_RIGHT_SHIFT, 
    "category.stasis.client"
))
}
