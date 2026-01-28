package statisclient.modid // folder name

import net.fabricmc.api.ClientModInitializer
import statisclient.modid.features.AutoTotem 

class StasisClient : ClientModInitializer { // class name
    companion object {
        lateinit var INSTANCE: StasisClient
    }

    val autoTotem = AutoTotem()

    override fun onInitializeClient() {
        INSTANCE = this
    }
}
