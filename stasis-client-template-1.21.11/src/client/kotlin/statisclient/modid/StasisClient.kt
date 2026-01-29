package statisclient.modid

import net.fabricmc.api.ClientModInitializer
import statisclient.modid.features.AutoTotem

class StasisClient : ClientModInitializer {
    
    companion object {
        // This MUST be inside the companion object
        lateinit var INSTANCE: StasisClient
    }

    val autoTotem = AutoTotem()

    override fun onInitializeClient() {
        INSTANCE = this
    }
}
