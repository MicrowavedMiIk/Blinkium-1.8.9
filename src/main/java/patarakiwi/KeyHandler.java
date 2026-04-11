package patarakiwi;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    private final KeyBinding blinkKey = new KeyBinding("Blink", Keyboard.KEY_G, "PataraKiwi");
    private boolean wasDown = false;

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(blinkKey);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (blinkKey.isKeyDown() && !wasDown) {
            Blink.toggle();
        }
        wasDown = blinkKey.isKeyDown();
    }
}