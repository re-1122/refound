package com.koyomiji.refound.mixin.world.gen.structure;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/*
 * Blacksmith
 * https://minecraft.wiki/w/Village/Structure_(old)
 */
@Mixin(StructureVillagePieces.House2.class)
public abstract class MixinStructureVillagePieces$House2
    extends StructureVillagePieces.Village {
  @ModifyVariable(
      method =
          "addComponentParts(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/world/gen/structure/StructureBoundingBox;)Z",
      at = @At(value = "STORE"), name = "iblockstate")
  private IBlockState
  mixin(IBlockState original) {
    if (ReFoundConfig.rearrangeVillageGeneration) {
      if (this.structureType != 2) {
        return this.getBiomeSpecificBlockState(original);
      } else {
        return original;
      }
    }

    return original;
  }
}
