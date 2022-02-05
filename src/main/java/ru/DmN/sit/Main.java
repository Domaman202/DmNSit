package ru.DmN.sit;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

import static net.minecraft.server.command.CommandManager.literal;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register((server, world_) -> server.getCommandManager().getDispatcher().register(literal("sit").executes(context -> {
            var world = context.getSource().getWorld();
            var pos = context.getSource().getPosition();
            var entity = new SitEntity(world, pos.x, pos.y - 1.6, pos.z);
            world.spawnEntity(entity);
            context.getSource().getPlayer().startRiding(entity);
            return 1;
        })));
    }
}
