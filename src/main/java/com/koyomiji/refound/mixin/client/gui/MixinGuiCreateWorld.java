package com.koyomiji.refound.mixin.client.gui;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiCreateWorld.class)
public abstract class MixinGuiCreateWorld {
  @Redirect(
      method = "getUncollidingSaveDirName",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/storage/ISaveFormat;getWorldInfo(Ljava/lang/String;)Lnet/minecraft/world/storage/WorldInfo;")
      )
  private static WorldInfo
  mixin(ISaveFormat instance, String s) {
    if (ReFoundConfig.duplicateWorldNameNumber) {
      return null;
    }

    return instance.getWorldInfo(s);
  }

  @Inject(method = "getUncollidingSaveDirName", at = @At(value = "RETURN"),
          cancellable = true)
  private static void
  mixin2(ISaveFormat saveLoader, String name,
         CallbackInfoReturnable<String> cir) {
    if (ReFoundConfig.duplicateWorldNameNumber) {
      if (saveLoader.getWorldInfo(name) != null) {
        int i = 1;

        while (saveLoader.getWorldInfo(String.format("%s (%d)", name, i)) !=
               null) {
          i++;
        }

        name = String.format("%s (%d)", name, i);
        cir.setReturnValue(name);
      }
    }
  }
}
