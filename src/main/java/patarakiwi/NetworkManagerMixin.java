package patarakiwi;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {

    @Inject(
            method = "sendPacket(Lnet/minecraft/network/Packet;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onSend(Packet<?> packet, CallbackInfo ci) {
        if (Blink.enabled && packet instanceof C03PacketPlayer) {
            Blink.buffer(packet);
            ci.cancel(); // stop the packet from actually being sent
        }
    }
}