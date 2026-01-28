package statisclient.modid.mixin.client;

@Mixin(ClientPlayerEntity.class)
public class PlayerTickMixin {
@Inject(at = @At("HEAD"), method = "tick")
private void onTick(CallbackInfo info) {
    // This calls your Kotlin onTick function 20 times per second
    StasisClient.INSTANCE.getAutoTotem().onTick();
}


    }
}
