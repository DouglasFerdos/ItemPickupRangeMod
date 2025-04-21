package com.douglasferdos.itempickuprange.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ItemPickupRangeC2SPayload(byte customRange) implements CustomPayload {

    public static final CustomPayload.Id<ItemPickupRangeC2SPayload> ID = new CustomPayload.Id<>(ItemPickupRangeNetworkingConstants.CUSTOM_RANGE_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, ItemPickupRangeC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, ItemPickupRangeC2SPayload::customRange, ItemPickupRangeC2SPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
