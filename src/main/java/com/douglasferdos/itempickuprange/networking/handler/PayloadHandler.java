package com.douglasferdos.itempickuprange.networking.handler;

import com.douglasferdos.itempickuprange.configs.PlayerEntityConfigs;
import com.douglasferdos.itempickuprange.networking.ItemPickupRangeC2SPayload;
import com.douglasferdos.itempickuprange.networking.ItemPickupRangeS2CPayload;
import com.douglasferdos.itempickuprange.networking.RequestCustomDataC2SPayload;
import com.douglasferdos.itempickuprange.networking.TogglePickupRangeC2SPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class PayloadHandler {

    public static ServerPlayNetworking.PlayPayloadHandler<ItemPickupRangeC2SPayload> itemPickupRangePayloadHandler() {
        return (payload, context) -> {
            ServerPlayerEntity player = context.player();
            // Value Verification
            if(payload.customRange() > 99 || payload.customRange() < 0) {
                // if the validation is out-of-range then this message is sent to the player's chat
                player.sendMessage(Text.literal("Invalid Value. Min = 0 & Max = 99"), false);
            } else {
                // setting the `customRange`
                Objects.requireNonNull(player.getServer()).execute(() -> {
                    byte customRange = payload.customRange();
                    ((PlayerEntityConfigs) player).setCustomPickUpRange(customRange);
                    player.sendMessage(Text.literal(String.format("Range set to %d", customRange)), true);
                });
            }
        };
    }

    public static ServerPlayNetworking.PlayPayloadHandler<TogglePickupRangeC2SPayload> togglePickupRangePayloadHandler() {
        return (payload, context) -> {
            ServerPlayerEntity player = context.player();
            Objects.requireNonNull(player.getServer()).execute(() -> {
                boolean toggleBool = payload.toggleBool();
                ((PlayerEntityConfigs) player).togglePickupRange(toggleBool);
            });
        };
    }

    public static ServerPlayNetworking.PlayPayloadHandler<RequestCustomDataC2SPayload> requestCustomDataC2SPayloadHandler() {
        return ((payload, context) -> {
            ServerPlayerEntity player = context.player();
            Objects.requireNonNull(player.getServer()).execute(() -> {
                ItemPickupRangeS2CPayload S2CPayload = new ItemPickupRangeS2CPayload(((PlayerEntityConfigs) player).getCustomPickUpRange(),((PlayerEntityConfigs) player).getTogglePickupRange());

                ServerPlayNetworking.send(player, S2CPayload);
            });
        });
    }
}
