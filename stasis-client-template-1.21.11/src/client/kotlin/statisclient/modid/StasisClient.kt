package stasisclient.modid // Updated to 'stasis'

import net.fabricmc.api.ClientModInitializer
import stasisclient.modid.features.AutoTotem // Updated to 'stasis'

class StasisClient : ClientModInitializer {
    
    companion object {
        lateinit var INSTANCE: StasisClient
    }

    val autoTotem = AutoTotem()

    override fun onInitializeClient() {
        INSTANCE = this
    }
}
