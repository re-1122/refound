package com.koyomiji.refound.mixin.client.settings;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public class MixinGameSettings {
  @Shadow public boolean snooperEnabled;

  @Shadow public boolean realmsNotifications;

  @Inject(method = "<init>()V", at = @At(value = "RETURN"))
  private void mixin(CallbackInfo ci) {
    if (ReFoundConfig.turnOffSnooperAutomatically) {
      snooperEnabled = false;
    }

    if (ReFoundConfig.turnOffRealmsNotificationsAutomatically) {
      realmsNotifications = false;
    }
  }

  @Inject(method = "loadOptions", at = @At(value = "RETURN"))
  private void mixin2(CallbackInfo ci) {
    if (ReFoundConfig.turnOffSnooperAutomatically) {
      snooperEnabled = false;
    }

    if (ReFoundConfig.turnOffRealmsNotificationsAutomatically) {
      realmsNotifications = false;
    }
  }
}
