@Mixin(ClientPlayerEntity.class)
public class PlayerTickMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        // Assuming you initialized your mod in a companion object or singleton
        StasisClient.INSTANCE.getAutoTotem().onTick();
    }
}
