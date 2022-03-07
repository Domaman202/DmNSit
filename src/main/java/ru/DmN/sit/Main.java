package ru.DmN.sit;

import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Main implements ModInitializer {
    public static final Set<UUID> autoSit = new HashSet<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("sit").executes(context -> {
                var pos = context.getSource().getPosition();
                sit(context.getSource().getPlayer(), context.getSource().getWorld(), pos, pos);
                return 1;
            }));

            dispatcher.register(literal("asit").then(argument("active", BoolArgumentType.bool()).executes(context -> {
                if (context.getArgument("active", boolean.class))
                    autoSit.add(context.getSource().getPlayer().getGameProfile().getId());
                else autoSit.remove(context.getSource().getPlayer().getGameProfile().getId());
                return 1;
            })));
        });
    }

    public static void sit(ServerPlayerEntity player, ServerWorld world, Vec3d pos, Vec3d old) {
        var block = world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock();
        var ss = block instanceof SlabBlock || block instanceof StairsBlock;
        var entity = new SitEntity(world, ss ? (Math.floor(pos.x) + 0.5) : pos.x, pos.y - (ss && pos.x != player.getX() && pos.z != player.getZ() ? 1.2 : 1.7), ss ? (Math.floor(pos.z) + 0.5) : pos.z, old);
        world.spawnEntity(entity);
        player.startRiding(entity);
    }
}