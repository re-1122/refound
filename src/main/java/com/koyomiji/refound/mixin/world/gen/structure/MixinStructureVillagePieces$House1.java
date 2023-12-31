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
 * Library
 * https://minecraft.wiki/w/Village/Structure_(old)
 */
@Mixin(StructureVillagePieces.House1.class)
public abstract class MixinStructureVillagePieces$House1
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

  @ModifyVariable(
      method =
          "addComponentParts(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z",
      at = @At(value = "LOAD", ordinal = 24), name = "iblockstate")
  private IBlockState
  mixin3(IBlockState original) {
    return replaceBlock(original);
  }

  @Inject(
      method = "addComponentParts",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/gen/structure/StructureVillagePieces$House1;spawnVillagers(Lnet/minecraft/world/World;Lnet/minecraft/world/gen/structure/StructureBoundingBox;IIII)V")
      )
  private void
  mixin3(World worldIn, Random randomIn,
         StructureBoundingBox structureBoundingBoxIn,
         CallbackInfoReturnable<Boolean> cir) {
    if (ReFoundConfig.rearrangeVillageGeneration) {
      if (this.structureType == 2) {
        IBlockState floor = Blocks.COBBLESTONE.getDefaultState();
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5,
                            floor, floor, false);
      }
    }
  }
}
