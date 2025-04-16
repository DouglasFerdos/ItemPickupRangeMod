package com.douglasferdos.itempickuprange.mixin;

import com.douglasferdos.itempickuprange.configs.PlayerEntityConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements PlayerEntityConfigs {

    // the @Unique annotation grants that:
    // non-public methods are renamed if a matching target method is found,
    // this allows utility methods to be safely assigned meaningful names in code,
    // but renamed if a conflict occurs when a mixin is applied.
    // "Annotation Type Unique", description for private and protected methods - Mixin Documentation.
    @Unique
    private static final byte DEFAULT_RANGE = 3; // the default disable the basically
    @Unique
    private byte customRange;

    public PlayerEntityMixin(EntityType<?> type, World world){
        super(type, world);
    }

    // this Inject in the onTick method the logic for checking for dropped items that are in the customRange,
    // expanding the BoundingBox of the player to pickup items that are eligible for pickup action
    // TODO: adding a alternative method to "magnetically" picking the items
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        if(!getWorld().isClient) {
            List<ItemEntity> items = getWorld().getEntitiesByClass(ItemEntity.class, this.getBoundingBox().expand(customRange, customRange, customRange), Entity::isAlive);
            for(ItemEntity item : items){
                if (!item.cannotPickup() && item.squaredDistanceTo(this) < customRange * customRange){
                    item.onPlayerCollision((PlayerEntity) (Object) this);
                }
            }
        }
    }

    // Custom NBT data for persisting the customRange value
    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomRangeToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putByte("CustomPickupRange.customRange_data", this.customRange);
    }

    // Custom NBT data for reading the persisted customRange value
    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomRangeFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("CustomPickupRange.customRange_data")) {
            this.customRange = nbt.getByte("CustomPickupRange.customRange_data").orElse(DEFAULT_RANGE);
        }
    }

    @Override
    public void setCustomPickUpRange(byte range) {
        this.customRange = range;
    }
}
