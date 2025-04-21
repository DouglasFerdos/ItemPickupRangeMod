package com.douglasferdos.itempickuprange.rendering.screens;

import com.douglasferdos.itempickuprange.networking.ItemPickupRangeC2SPayload;
import com.douglasferdos.itempickuprange.networking.RequestCustomDataC2SPayload;
import com.douglasferdos.itempickuprange.networking.TogglePickupRangeC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ItemPickupRangeConfigScreen extends Screen {

    private byte customRange;
    private boolean toggleBool;
    private TextFieldWidget textFieldWidget;
    private ButtonWidget toggleMod;

    public ItemPickupRangeConfigScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {

        // Requests the custom data from the server side to set the current values in the custom screen
        RequestCustomDataC2SPayload requestPayload = new RequestCustomDataC2SPayload();
        ClientPlayNetworking.send(requestPayload);

        // TextFieldWidget for specifying the `customRange` value
        textFieldWidget = new TextFieldWidget(this.textRenderer, 40, 40, 60, 20, Text.of(""));
        textFieldWidget.setMaxLength(2);
        textFieldWidget.setPlaceholder(Text.literal("0 - 99"));
        // adds the TextFieldWidget to the custom screen
        this.addDrawableChild(textFieldWidget);

        // ButtonWidget that sets the new `customRange` value onClick
        ButtonWidget setRangeButton = ButtonWidget.builder(Text.translatable("text.pickuprange.setRangeButton"), (button -> {
            // Removes all non-numeric values from the field, except if the first char is a minus sign
            // e.g. "a1" -> "1"
            // e.g.2. "aa" -> "". Making it invalid
            String fieldText = textFieldWidget.getText().replaceAll("(?<!^)-", "").replaceAll("[^\\d-]", "");
            fieldText = fieldText.equals("-") ? "" : fieldText;

            // checks if the text is not empty before assigning it to the custom range
            if (!fieldText.isEmpty()) {
                customRange = (Byte.parseByte(fieldText));
                // creates and sends a payload with the `customRange` to the server side logic
                ItemPickupRangeC2SPayload payload = new ItemPickupRangeC2SPayload(customRange);
                ClientPlayNetworking.send(payload);
            } else {
                // if the validation is out-of-range then this message is sent to the player's chat
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Invalid Value. Min = 0 & Max = 99"), false);
            }
        })).dimensions(105, 40, 60, 20).build(); // TODO: Make it more dynamic instead of absolute values

        // adds the ButtonWidget to the custom screen
        this.addDrawableChild(setRangeButton);

        // Change to the correct text of the toggleMod on init
        String boolValueString;
        if (toggleBool) {
            boolValueString = "text.pickuprange.isModActiveButtonON";
        } else {
            boolValueString = "text.pickuprange.isModActiveButtonOFF";
        }

        toggleMod = ButtonWidget.builder(Text.translatable(boolValueString), button -> {
            toggleBool = !toggleBool;

            // Update the button's text based on the state
            button.setMessage(Text.translatable(toggleBool ? "text.pickuprange.isModActiveButtonON" : "text.pickuprange.isModActiveButtonOFF"));

            // sending the payload to the server side
            TogglePickupRangeC2SPayload togglePickupRangePayload = new TogglePickupRangeC2SPayload(toggleBool);
            ClientPlayNetworking.send(togglePickupRangePayload);

        }).dimensions(170, 40, 80, 20).build();

        // adds the CheckboxWidget to the custom screen
        this.addDrawableChild(toggleMod);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Render the translatable text to the custom screen
        context.drawText(this.textRenderer, Text.translatable("text.pickuprange.title"), 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    public void setCustomRangeValueString(String range) {
        // Setting the value in the textFieldWidget
        if (range != null) {
            textFieldWidget.setText(range);
        }
    }

    public void setToggleBool(boolean toggleBool) {
        this.toggleBool = toggleBool;
        toggleMod.setMessage(Text.translatable(toggleBool ? "text.pickuprange.isModActiveButtonON" : "text.pickuprange.isModActiveButtonOFF"));
    }
}
