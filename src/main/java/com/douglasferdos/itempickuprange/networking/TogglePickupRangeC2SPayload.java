package com.douglasferdos.itempickuprange.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record TogglePickupRangeC2SPayload(boolean toggleBool) implements CustomPayload {

    public static final CustomPayload.Id<TogglePickupRangeC2SPayload> ID = new CustomPayload.Id<>(ItemPickupRangeNetworkingConstants.TOGGLE_BUTTON_ID);
    public static final PacketCodec<RegistryByteBuf, TogglePickupRangeC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, TogglePickupRangeC2SPayload::toggleBool, TogglePickupRangeC2SPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
