package com.douglasferdos.itempickuprange.configs;

public interface PlayerEntityConfigs {
    void setCustomPickUpRange(byte range);
    byte getCustomPickUpRange();
    void togglePickupRange(boolean toggleMod);
    boolean getTogglePickupRange();
}
