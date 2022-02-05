package ru.DmN.sit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.literal;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register((server, world_) -> server.getCommandManager().getDispatcher().register(literal("sit").executes(context -> {
            sit(context.getSource().getPlayer(), context.getSource().getWorld(), context.getSource().getPosition());
            return 1;
        })));
    }

    public static void sit(ServerPlayerEntity player, ServerWorld world, Vec3d pos) {
        var block = world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock();
        var ss = block instanceof SlabBlock || block instanceof StairsBlock;
        var entity = new SitEntity(world, ss ? (pos.x + 0.5) : pos.x, pos.y - (ss ? 1.2 : 1.7), ss ? (pos.z + 0.5) : pos.z);
        world.spawnEntity(entity);
        player.startRiding(entity);
    }
}