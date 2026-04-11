package patarakiwi;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod(modid = PataraKiwi.MODID, name = PataraKiwi.NAME, version = PataraKiwi.VERSION)
public class PataraKiwi {

    public static final String MODID = "patarakiwi";
    public static final String NAME = "PataraKiwi";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Blink.enabled) {
            Blink.renderUpdate();
        }
    }

    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        event.manager.channel().pipeline().addBefore("packet_handler", "patarakiwi_handler", new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                if (Blink.enabled) {
                    // Intercept Movement
                    if (msg instanceof C03PacketPlayer) {
                        Blink.buffer((Packet<?>) msg);
                        return;
                    }

                    // Replicate Swing Animation on the Ghost
                    if (msg instanceof C0APacketAnimation) {
                        if (Blink.ghostPlayer != null) {
                            Blink.ghostPlayer.swingItem();
                        }
                    }
                }

                super.write(ctx, msg, promise);
            }
        });
    }
}