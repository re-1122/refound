package com.koyomiji.refound.mixin.creativetab;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.creativetab.CreativeTabs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeTabs.class)
public abstract class MixinCreativeTabsClient {
  @Shadow @Final private int index;

  @Shadow public abstract int getColumn();

  private int getRemappedIndex(int index) {
    switch (index) {
    case 0:
    case 1:
    case 2:
    case 3:
      return index;
    case 4:
    case 5:
      return index + 1;
    case 6:
      return 4;
    case 7:
    case 8:
    case 9:
    case 10:
      return index;
    case 11:
      return 13;
    case 12:
    case 13:
      return index - 1;
    default:
      return index;
    }
  }

  @Inject(method = "getColumn", at = @At("HEAD"), cancellable = true)
  private void mixin(CallbackInfoReturnable<Integer> cir) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      int index = getRemappedIndex(this.index);

      if (index > 13) {
        cir.setReturnValue(((index - 14) % 12) % 6);
        return;
      }

      cir.setReturnValue(index % 7);
    }
  }

  @Inject(method = "isOnTopRow", at = @At("HEAD"), cancellable = true)
  private void mixin2(CallbackInfoReturnable<Boolean> cir) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      int index = getRemappedIndex(this.index);

      if (index > 13) {
        cir.setReturnValue(((index - 14) % 12) < 6);
        return;
      }

      cir.setReturnValue(index < 7);
    }
  }

  @Inject(method = "isAlignedRight", at = @At("HEAD"), cancellable = true)
  private void mixin3(CallbackInfoReturnable<Boolean> cir) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      cir.setReturnValue(getColumn() == 5 || getColumn() == 6);
    }
  }

  @Inject(method = "getTabPage", at = @At("HEAD"), cancellable = true,
          remap = false)
  private void
  mixin4(CallbackInfoReturnable<Integer> cir) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      int index = getRemappedIndex(this.index);

      if (index > 13) {
        cir.setReturnValue(((index - 14) / 12) + 1);
        return;
      }

      cir.setReturnValue(0);
    }
  }
}
