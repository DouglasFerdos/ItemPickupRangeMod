package com.douglasferdos.itempickuprange.networking;

import net.minecraft.util.Identifier;

public class ItemPickupRangeNetworkingConstants {
    // Client-To-Server Identifier
    public static final Identifier CUSTOM_RANGE_PACKET_ID = Identifier.of("itempickuprange", "custom_range");
    public static final Identifier TOGGLE_BUTTON_ID = Identifier.of("itempickuprange", "toggle_button");
    public static final Identifier REQUEST_CUSTOM_DATA_ID = Identifier.of("itempickuprange", "request_custom_data");
    // Server-To-Client Identifier
    public static final Identifier GET_CUSTOM_RANGE_PACKET_ID = Identifier.of("itempickuprange", "get_custom_range");
}
