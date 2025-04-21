package com.douglasferdos.itempickuprange.keybinding;

import com.douglasferdos.itempickuprange.rendering.screens.ItemPickupRangeConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ItemPickupKeyBinds implements ClientModInitializer {

    @Override
    public void onInitializeClient(){

        // KeyBinding for the ItemPickupRangeConfigScreen
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(
                        new ItemPickupRangeConfigScreen(Text.empty())
                );
            }
        });
    }

    public static KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.itempickuprange",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "category.itempickuprange"
    ));
}
