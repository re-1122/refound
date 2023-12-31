package com.koyomiji.refound.mixin.world.gen.structure;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureVillagePieces.Village.class)
public abstract class MixinStructureVillagePieces$Village
    extends StructureComponent {
  @Shadow protected int structureType;

  @Shadow
  protected abstract IBlockState
  getBiomeSpecificBlockState(IBlockState blockstateIn);

  @Redirect(
      method = "replaceAirAndLiquidDownwards",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/gen/structure/StructureVillagePieces$Village;getBiomeSpecificBlockState(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;")
      )
  private IBlockState
  mixin(StructureVillagePieces.Village instance, IBlockState blockstateIn) {
    if (ReFoundConfig.rearrangeVillageGeneration) {
      if (this.structureType == 2) {
        return blockstateIn;
      } else {
        return getBiomeSpecificBlockState(blockstateIn);
      }
    }

    return getBiomeSpecificBlockState(blockstateIn);
  }
}
