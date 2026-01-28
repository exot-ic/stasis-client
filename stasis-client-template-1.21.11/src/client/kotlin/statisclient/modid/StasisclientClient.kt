package statisclient.modid

import net.fabricmc.api.ClientModInitializer
import statisclient.modid.features.AutoTotem

class StasisclientClient : ClientModInitializer {
    
    // This allows ClickGUI and Mixins to talk to your modules
    companion object {
        lateinit var INSTANCE: StasisclientClient
    }

    val autoTotem = AutoTotem()

    override fun onInitializeClient() {
        INSTANCE = this
    }
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
