package patarakiwi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.LazyLoadBase;

import java.lang.reflect.Field;

public class PacketHook {

    private static boolean injected = false;

    public static void inject(NetworkManager networkManager) {
        if (injected) return;

        try {
            Field channelField = NetworkManager.class.getDeclaredFields()[0];
            channelField.setAccessible(true);

            io.netty.channel.Channel channel =
                    (io.netty.channel.Channel) channelField.get(networkManager);

            channel.pipeline().addBefore(
                    "packet_handler",
                    "patarakiwi_hook",
                    new io.netty.channel.ChannelDuplexHandler() {

                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
                                throws Exception {

                            if (msg instanceof Packet) {
                                Packet<?> packet = (Packet<?>) msg;

                                if (Blink.enabled) {
                                    Blink.buffer(packet);
                                    return; // CANCEL sending
                                }
                            }

                            super.write(ctx, msg, promise);
                        }
                    }
            );

            injected = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}