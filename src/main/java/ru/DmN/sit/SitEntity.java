package ru.DmN.sit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SitEntity extends ArmorStand {
    public Vec3 old;

    public SitEntity(Level world, double x, double y, double z, Vec3 old) {
        super(EntityType.ARMOR_STAND, world);
        this.noPhysics = true;
        var nbt = new CompoundTag();
        nbt.putByte("NoGravity", (byte) 1);
        nbt.putByte("Invisible", (byte) 1);
        this.deserializeNBT(nbt);
        this.setPos(x, y, z);
        this.old = old;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getFirstPassenger() != null) {
            this.setXRot(this.getFirstPassenger().getXRot());
            this.setYRot(this.getFirstPassenger().getYRot());
        }
    }


    @Override
    protected void removePassenger(@NotNull Entity passenger) {
        passenger.setPos(old);
        super.removePassenger(passenger);
        this.kill();
    }

    @Override
    public void ejectPassengers() {
        this.getPassengers().forEach((passenger) -> passenger.setPos(old));
        super.ejectPassengers();
        this.kill();
    }

    @Override
    public CompoundTag serializeNBT() {
        this.ejectPassengers();
        return super.serializeNBT();
    }
}