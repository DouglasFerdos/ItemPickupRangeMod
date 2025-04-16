package com.douglasferdos.itempickuprange.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ItemPickupRangePayload(byte customRange) implements CustomPayload {

    public static final CustomPayload.Id<ItemPickupRangePayload> ID = new CustomPayload.Id<>(ItemPickupRangeNetworkingConstants.CUSTOM_RANGE_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, ItemPickupRangePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, ItemPickupRangePayload::customRange, ItemPickupRangePayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
