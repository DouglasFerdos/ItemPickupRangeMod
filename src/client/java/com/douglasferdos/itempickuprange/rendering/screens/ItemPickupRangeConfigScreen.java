package com.douglasferdos.itempickuprange.rendering.screens;

import com.douglasferdos.itempickuprange.networking.ItemPickupRangePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ItemPickupRangeConfigScreen extends Screen{

    private byte customRange;

    public ItemPickupRangeConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {

        // TextFieldWidget for specifying the `customRange` value
        TextFieldWidget textFieldWidget = new TextFieldWidget(this.textRenderer, 40, 40, 60, 20, Text.of(""));
        textFieldWidget.setMaxLength(2);
        textFieldWidget.setPlaceholder(Text.literal("0 - 99"));

        // adds the TextFieldWidget to the custom screen
        this.addDrawableChild(textFieldWidget);

        // ButtonWidget that sets the new `customRange` value onClick
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.translatable("text.pickuprange.button"), (button -> {

            // Removes all non-numeric values from the field
            // e.g. "a1" -> "1"
            // e.g.2. "aa" -> "". Making it invalid
            String fieldText = textFieldWidget.getText().replaceAll("\\D", "");

            // checks if the text is not empty before assigning it to the custom range
            if (!fieldText.isEmpty()) {
                // parse the text to byte
                customRange = (Byte.parseByte(fieldText));

                // creates and sends a payload with the `customRange` to the server side logic
                ItemPickupRangePayload payload = new ItemPickupRangePayload(customRange);
                ClientPlayNetworking.send(payload);

            } else {
                // if the validation is out-of-range then this message is sent to the player's chat
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Invalid Value. Min = 0 & Max = 99"), false);
            }
        })).dimensions(105, 40, 60, 20).build(); // TODO: Make it more dynamic instead of absolute values

        // adds the ButtonWidget to the custom screen
        this.addDrawableChild(buttonWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Render the translatable text to the custom screen
        context.drawText(this.textRenderer, Text.translatable("text.pickuprange.title"), 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }
}
