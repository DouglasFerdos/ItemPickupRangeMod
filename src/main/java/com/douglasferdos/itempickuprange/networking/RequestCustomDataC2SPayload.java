package com.douglasferdos.itempickuprange.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record RequestCustomDataC2SPayload() implements CustomPayload {

    public static final CustomPayload.Id<RequestCustomDataC2SPayload> ID = new CustomPayload.Id<>(ItemPickupRangeNetworkingConstants.REQUEST_CUSTOM_DATA_ID);
    public static final PacketCodec<RegistryByteBuf, RequestCustomDataC2SPayload> CODEC = PacketCodec.unit(new RequestCustomDataC2SPayload());

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
