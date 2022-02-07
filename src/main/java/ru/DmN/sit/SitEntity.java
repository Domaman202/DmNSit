package ru.DmN.sit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SitEntity extends ArmorStandEntity {
    public Vec3d old;

    public SitEntity(World world, double x, double y, double z, Vec3d old) {
        super(EntityType.ARMOR_STAND, world);
        this.noClip = true;
        var nbt = new NbtCompound();
        nbt.putByte("NoGravity", (byte) 1);
        nbt.putByte("Invisible", (byte) 1);
        this.readNbt(nbt);
        this.setPosition(x, y, z);
        this.old = old;
    }

    @Override
    protected void removePassenger(Entity passenger) {
        passenger.setPosition(old);
        super.removePassenger(passenger);
        this.kill();
    }

    @Override
    public void removeAllPassengers() {
        this.getPassengerList().forEach((passenger) -> passenger.setPosition(old));
        super.removeAllPassengers();
        this.kill();
    }
}
