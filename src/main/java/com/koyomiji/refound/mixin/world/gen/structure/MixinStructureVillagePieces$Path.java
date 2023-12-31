package com.koyomiji.refound.mixin.world.gen.structure;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StructureVillagePieces.Path.class)
public abstract class MixinStructureVillagePieces$Path
    extends StructureVillagePieces.Road {
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
      at = @At(value = "STORE"), name = "iblockstate3")
  private IBlockState
  mixin1(IBlockState original) {
    return replaceBlock(original);
  }
}
