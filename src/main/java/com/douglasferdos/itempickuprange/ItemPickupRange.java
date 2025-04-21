package com.douglasferdos.itempickuprange;

import com.douglasferdos.itempickuprange.configs.PlayerEntityConfigs;
import com.douglasferdos.itempickuprange.networking.ItemPickupRangeC2SPayload;
import com.douglasferdos.itempickuprange.networking.ItemPickupRangeS2CPayload;
import com.douglasferdos.itempickuprange.networking.RequestCustomDataC2SPayload;
import com.douglasferdos.itempickuprange.networking.TogglePickupRangeC2SPayload;
import com.douglasferdos.itempickuprange.networking.handler.PayloadHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ItemPickupRange implements ModInitializer {
	public static final String MOD_ID = "itempickuprange";

	@Override
	public void onInitialize() {

		// Client-To-Server Payloads
		// CustomPayload for setting the custom `ItemPickupRange` in the mixin `PlayerEntityMixin`
		PayloadTypeRegistry.playC2S().register(ItemPickupRangeC2SPayload.ID, ItemPickupRangeC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ItemPickupRangeC2SPayload.ID, PayloadHandler.itemPickupRangePayloadHandler());
		// CustomPayload for setting the `toggleBool` in the mixin `PlayerEntityMixin`
		PayloadTypeRegistry.playC2S().register(TogglePickupRangeC2SPayload.ID, TogglePickupRangeC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(TogglePickupRangeC2SPayload.ID, PayloadHandler.togglePickupRangePayloadHandler());
		// CustomPayload for requesting the custom data from the server
		PayloadTypeRegistry.playC2S().register(RequestCustomDataC2SPayload.ID, RequestCustomDataC2SPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(RequestCustomDataC2SPayload.ID, PayloadHandler.requestCustomDataC2SPayloadHandler());

		// Server-To-Client Payloads registry
		PayloadTypeRegistry.playS2C().register(ItemPickupRangeS2CPayload.ID, ItemPickupRangeS2CPayload.CODEC);

		// listener that will set the custom data to the player again after respawn
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			// restore the `customRange`
			byte oldCustomRange = ((PlayerEntityConfigs) oldPlayer).getCustomPickUpRange();
			((PlayerEntityConfigs) newPlayer).setCustomPickUpRange(oldCustomRange);
			// restore the `togglePickupRange`
			boolean oldTogglePickupRange = ((PlayerEntityConfigs) oldPlayer).getTogglePickupRange();
			((PlayerEntityConfigs) newPlayer).togglePickupRange(oldTogglePickupRange);
		});
	}
}