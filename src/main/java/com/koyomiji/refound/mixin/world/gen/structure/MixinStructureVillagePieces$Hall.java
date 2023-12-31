package com.koyomiji.refound.mixin.world.gen.structure;

import com.koyomiji.refound.config.ReFoundConfig;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * Butcher's Shop
 * https://minecraft.wiki/w/Village/Structure_(old)
 */
@Mixin(StructureVillagePieces.Hall.class)
public abstract class MixinStructureVillagePieces$Hall
    extends StructureVillagePieces.Village {
  private IBlockState replaceBlock(IBlockState original) {
    if (ReFoundConfig.rearrangeVillageGeneration) {
      if (this.structureType == 2) {
        return Blocks.COBBLESTONE.getDefaultState();
      } else {
        return original;
      }
    }

    return original;
  }

  //  @ModifyVariable(method =
  //  "addComponentParts(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z",
  //  at = @At(value = "LOAD", ordinal = 0), name = "iblockstate") private
  //  IBlockState mixin(IBlockState original) {
  //    return replaceBlock(original);
  //  }

  @ModifyVariable(
      method =
          "addComponentParts(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z",
      at = @At(value = "LOAD", ordinal = 9), name = "iblockstate")
  private IBlockState
  mixin2(IBlockState original) {
    return replaceBlock(original);
  }

  @Inject(
      method = "addComponentParts",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/gen/structure/StructureVillagePieces$Hall;spawnVillagers(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIII)V")
      )
  private void
  mixin3(World worldIn, Random randomIn,
         StructureBoundingBox structureBoundingBoxIn,
         CallbackInfoReturnable<Boolean> cir) {
    if (ReFoundConfig.rearrangeVillageGeneration) {
      if (this.structureType == 2) {
        IBlockState floor = Blocks.COBBLESTONE.getDefaultState();
        this.setBlockState(worldIn, floor, 6, 0, 6, structureBoundingBoxIn);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 5,
                            floor, floor, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 0, 5,
                            floor, floor, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 0, 0,
                            floor, floor, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 0, 5,
                            floor, floor, false);
      }
    }
  }
}
