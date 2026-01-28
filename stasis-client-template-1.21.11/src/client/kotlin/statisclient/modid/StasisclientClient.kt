package stasisclient.modid

import net.fabricmc.api.ClientModInitializer
import stasisclient.modid.features.AutoTotem

class StasisClient : ClientModInitializer {

    // This companion object makes "INSTANCE" accessible from Java Mixins
    companion object {
        lateinit var INSTANCE: StasisClient
    }

    // Initialize your modules here
    val autoTotem = AutoTotem()
    // val autoCrystal = AutoCrystal()

    override fun onInitializeClient() {
        INSTANCE = this
        println("Stasis Client Initialized!")
        
        // This is also a great place to register your Keybinds
        // as we discussed in the "Better Way" earlier.
    }
}
	// Top of your Client class
val mappingKey = KeyBindingHelper.registerKeyBinding(KeyBinding(
    "key.stasis.clickgui", 
    InputUtil.Type.KEYSYM, 
    GLFW.GLFW_KEY_RIGHT_SHIFT, 
    "category.stasis.client"
))
}
