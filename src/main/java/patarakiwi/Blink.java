package patarakiwi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.util.ArrayList;
import java.util.List;

public class Blink {

    public static boolean enabled = false;
    private static final List<Packet<?>> packets = new ArrayList<>();
    public static EntityOtherPlayerMP ghostPlayer = null;

    public static void toggle() {
        enabled = !enabled;
        if (enabled) {
            spawnGhost();
        } else {
            removeGhost();
            flush();
        }
    }

    public static void buffer(Packet<?> packet) {
        if (packet instanceof C03PacketPlayer) {
            packets.add(packet);
        }
    }

    private static void spawnGhost() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        ghostPlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        ghostPlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        ghostPlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        ghostPlayer.inventory.copyInventory(mc.thePlayer.inventory);

        mc.theWorld.addEntityToWorld(-1337, ghostPlayer);
    }

    private static void removeGhost() {
        Minecraft mc = Minecraft.getMinecraft();
        if (ghostPlayer != null && mc.theWorld != null) {
            mc.theWorld.removeEntityFromWorld(ghostPlayer.getEntityId());
            ghostPlayer = null;
        }
    }

    // calling this shit every tick
    public static void renderUpdate() {
        Minecraft mc = Minecraft.getMinecraft();
        if (ghostPlayer == null || mc.thePlayer == null) return;
        ghostPlayer.hurtTime = mc.thePlayer.hurtTime;
        ghostPlayer.maxHurtTime = mc.thePlayer.maxHurtTime;
        ghostPlayer.attackedAtYaw = mc.thePlayer.attackedAtYaw;
        ghostPlayer.inventory.copyInventory(mc.thePlayer.inventory);
    }

    public static void flush() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.getNetHandler() == null || mc.getNetHandler().getNetworkManager() == null) return;

        for (Packet<?> p : packets) {
            mc.getNetHandler().getNetworkManager().sendPacket(p);
        }
        packets.clear();
    }
}