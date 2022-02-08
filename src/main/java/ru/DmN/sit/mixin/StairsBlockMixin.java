package ru.DmN.sit.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.DmN.sit.Main;

@Mixin(StairsBlock.class)
public class StairsBlockMixin extends Block {
    @Shadow @Final public static EnumProperty<BlockHalf> HALF;

    public StairsBlockMixin(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || state.get(HALF) != BlockHalf.BOTTOM)
            return ActionResult.PASS;
        Main.sit((ServerPlayerEntity) player, (ServerWorld) world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), player.getPos());
        return ActionResult.SUCCESS;
    }
}
