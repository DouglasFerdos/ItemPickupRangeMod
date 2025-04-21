package com.douglasferdos.itempickuprange.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ItemPickupRangeS2CPayload(byte customRange, boolean toggleMod) implements CustomPayload {

    public static final CustomPayload.Id<ItemPickupRangeS2CPayload> ID = new CustomPayload.Id<>(ItemPickupRangeNetworkingConstants.GET_CUSTOM_RANGE_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, ItemPickupRangeS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, ItemPickupRangeS2CPayload::customRange,
            PacketCodecs.BOOLEAN, ItemPickupRangeS2CPayload::toggleMod,
            ItemPickupRangeS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
