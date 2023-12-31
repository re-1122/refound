package com.koyomiji.refound.mixin.advancements;

import com.koyomiji.refound.RecipeUnregisterer;

import java.util.*;

import net.minecraft.advancements.AdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AdvancementManager.class)
public class MixinAdvancementManager {
  @Redirect(method = "loadBuiltInAdvancements",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/Map;containsKey(Ljava/lang/Object;)Z")
            )
  private boolean
  mixin(Map instance, Object o) {
    if (RecipeUnregisterer.unregisteredAdvancements.contains(o)) {
      return true;
    }

    return instance.containsKey(o);
  }
}
