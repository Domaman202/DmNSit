package ru.DmN.sit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.commands.Commands.literal;

@Mod("dmnsit")
public class Main {
    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        event.getServer().getCommands().getDispatcher().register(literal("sit").executes(context -> {
            sit(context.getSource().getPlayerOrException(), context.getSource().getLevel(), context.getSource().getPosition());
            return 1;
        }));
    }

    public static void sit(ServerPlayer player, ServerLevel world, Vec3 pos) {
        var block = world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock();
        var ss = block instanceof SlabBlock || block instanceof StairBlock;
        var entity = new SitEntity(world, ss ? (Math.floor(pos.x) + 0.5) : pos.x, pos.y - (ss && pos.x != player.getX() && pos.z != player.getZ() ? 1.2 : 1.7), ss ? (Math.floor(pos.z) + 0.5) : pos.z, pos);
        world.addFreshEntity(entity);
        player.startRiding(entity);
    }
}
