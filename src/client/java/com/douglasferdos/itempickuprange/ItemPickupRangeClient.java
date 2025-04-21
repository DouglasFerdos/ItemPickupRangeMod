package com.douglasferdos.itempickuprange;

import com.douglasferdos.itempickuprange.networking.ItemPickupRangeS2CPayload;
import com.douglasferdos.itempickuprange.rendering.screens.ItemPickupRangeConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ItemPickupRangeClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(ItemPickupRangeS2CPayload.ID, (payload, context) -> {
			byte loadedCustomRange = payload.customRange();
			boolean loadedToggleMod = payload.toggleMod();
			if (context.client().currentScreen instanceof ItemPickupRangeConfigScreen) {
				((ItemPickupRangeConfigScreen) context.client().currentScreen).setCustomRangeValueString(String.valueOf(loadedCustomRange));
				((ItemPickupRangeConfigScreen) context.client().currentScreen).setToggleBool(loadedToggleMod);
			}
		});
	}
}