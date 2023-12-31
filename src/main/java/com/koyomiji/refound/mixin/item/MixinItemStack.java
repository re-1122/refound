package com.koyomiji.refound.mixin.item;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemStack.class)
public class MixinItemStack {
  @ModifyConstant(method = "getTooltip",
                  constant = @Constant(stringValue = " ", ordinal = 1))
  private static String
  mixin(String constant) {
    return ReFoundConfig.fixTooltipAlignment ? "" : constant;
  }

  @ModifyConstant(method = "getTooltip",
                  constant = @Constant(stringValue = " ", ordinal = 2))
  private static String
  mixin2(String constant) {
    return ReFoundConfig.fixTooltipAlignment ? "" : constant;
  }
}
