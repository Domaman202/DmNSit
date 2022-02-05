package ru.DmN.sit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class SitEntity extends ArmorStandEntity {
    public SitEntity(World world, double x, double y, double z) {
        super(EntityType.ARMOR_STAND, world);
        this.noClip = true;
        var nbt = new NbtCompound();
        nbt.putByte("NoGravity", (byte) 1);
        nbt.putByte("Invisible", (byte) 1);
        this.readNbt(nbt);
        this.setPosition(x, y, z);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        this.kill();
    }

    @Override
    public void removeAllPassengers() {
        super.removeAllPassengers();
        this.kill();
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        this.kill();
        return false;
    }
}
