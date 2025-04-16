package com.douglasferdos.itempickuprange;

import com.douglasferdos.itempickuprange.configs.PlayerEntityConfigs;
import com.douglasferdos.itempickuprange.networking.ItemPickupRangePayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class Itempickuprange implements ModInitializer {
	public static final String MOD_ID = "itempickuprange";

	// TODO: EXP pickup range, and a button to check if the EXP should be picked magnetically or not.

	@Override
	public void onInitialize() {
		// CustomPayload for setting the custom `ItemPickupRange` in the mixin `PlayerEntityMixin`
		PayloadTypeRegistry.playC2S().register(ItemPickupRangePayload.ID, ItemPickupRangePayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(ItemPickupRangePayload.ID, ((payload, context) -> {

			ServerPlayerEntity player = context.player();

			// Value Verification
			if(payload.customRange() > 99 || payload.customRange() < 0) {
				// if the validation is out-of-range then this message is sent to the player's chat
				player.sendMessage(Text.literal("Invalid Value. Min = 0 & Max = 99"), false);

			} else {
				// setting the `customRange`
				player.getServer().execute(() -> {
					byte customRange = payload.customRange();
					((PlayerEntityConfigs) player).setCustomPickUpRange(customRange);
				});
			}
		}));
	}
}