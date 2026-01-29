package statisclient.modid

import net.fabricmc.api.ModInitializer
import statisclient.modid.features.AutoTotem

class Stasisclient : ModInitializer {
    
    companion object {
        lateinit var INSTANCE: Stasisclient
    }

    // This must match the name of the class in features/autototem.kt
    val autoTotem = AutoTotem()

    override fun onInitialize() {
        INSTANCE = this
    }
}
