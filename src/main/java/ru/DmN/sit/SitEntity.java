package ru.DmN.sit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;

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
        super.removePassenger(passenger);
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (passenger.getVehicle() == null) {
//                System.out.println(passenger.getVehicle() == this);
                System.out.println("NIGGER KILLED!");
                passenger.setPosition(old);
                this.kill();
            }
        });
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        System.out.println("KILLED!");
        this.kill();
        return false;
    }
}
