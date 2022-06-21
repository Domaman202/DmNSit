package ru.DmN.sit;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
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
    public void tick() {
        super.tick();
        if (this.getFirstPassenger() != null) {
            this.setPitch(this.getFirstPassenger().getPitch());
            this.setYaw(this.getFirstPassenger().getYaw());
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        if (this.world.getBlockState(new BlockPos(old.x, old.y, old.z)).getBlock() != Blocks.AIR)
            old = old.add(0, 1, 0);
        passenger.setPosition(old);
        this.kill();
        super.removePassenger(passenger);
    }

    @Override
    public void removeAllPassengers() {
        if (this.world.getBlockState(new BlockPos(old.x, old.y, old.z)).getBlock() != Blocks.AIR)
            old = old.add(0, 1, 0);
        this.getPassengerList().forEach((passenger) -> passenger.setPosition(old));
        this.kill();
        super.removeAllPassengers();
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        this.removeAllPassengers();
        return false;
    }

    @Override
    public boolean saveSelfNbt(NbtCompound nbt) {
        this.removeAllPassengers();
        return false;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }
}
