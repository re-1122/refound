package com.koyomiji.refound.mixin.client.gui.inventory;

import com.koyomiji.refound.config.ReFoundConfig;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GuiContainerCreative.class)
public class MixinGuiContainerCreative {
  @Shadow @Final private static ResourceLocation CREATIVE_INVENTORY_TABS;
  @Unique
  private static final ResourceLocation CREATIVE_INVENTORY_TABS_ARRANGED =
      new ResourceLocation(
          "refound", "textures/gui/container/creative_inventory/tabs.png");

  @Redirect(
      method = "drawGuiContainerBackgroundLayer",
      at = @At(
          value = "FIELD",
          target =
              "Lnet/minecraft/client/gui/inventory/GuiContainerCreative;CREATIVE_INVENTORY_TABS:Lnet/minecraft/util/ResourceLocation;")
      )
  private ResourceLocation
  mixin() {
    return ReFoundConfig.rearrangeCreativeInventory
        ? CREATIVE_INVENTORY_TABS_ARRANGED
        : CREATIVE_INVENTORY_TABS;
  }

  /*
   * isMouseOverTab
   */
  // int j = 26 * i;
  @ModifyConstant(method = "isMouseOverTab",
                  constant = @Constant(intValue = 28, ordinal = 0))
  private int
  mixin2(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  // j = this.xSize - 27 * (7 - i) + 1;
  @ModifyConstant(method = "isMouseOverTab",
                  constant = @Constant(intValue = 28, ordinal = 1))
  private int
  mixin9(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 27 : original;
  }

  @ModifyConstant(method = "isMouseOverTab",
                  constant = @Constant(intValue = 6, ordinal = 0))
  private int
  mixin17(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 7 : original;
  }

  @ModifyConstant(method = "isMouseOverTab", constant = @Constant(intValue = 2))
  private int mixin25(int original) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      return 1;
    }

    if (ReFoundConfig.fixCreativeTabArea) {
      return 0;
    }

    return original;
  }

  // return mouseX >= j && mouseX <= j + 26 && mouseY >= k && mouseY <= k + 32;
  @ModifyConstant(method = "isMouseOverTab",
                  constant = @Constant(intValue = 28, ordinal = 2))
  private int
  mixin11(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  /*
   * renderCreativeInventoryHoveringText
   */
  // int j = 26 * i;
  @ModifyConstant(method = "renderCreativeInventoryHoveringText",
                  constant = @Constant(intValue = 28, ordinal = 0))
  private int
  mixin3(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  // j = this.xSize - 27 * (7 - i) + 1;
  @ModifyConstant(method = "renderCreativeInventoryHoveringText",
                  constant = @Constant(intValue = 28, ordinal = 1))
  private int
  mixin10(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 27 : original;
  }

  @ModifyConstant(method = "renderCreativeInventoryHoveringText",
                  constant = @Constant(intValue = 6, ordinal = 0))
  private int
  mixin18(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 7 : original;
  }

  @ModifyConstant(method = "renderCreativeInventoryHoveringText",
                  constant = @Constant(intValue = 2))
  private int
  mixin23(int original) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      return 1;
    }

    if (ReFoundConfig.fixCreativeTabArea) {
      return 0;
    }

    return original;
  }

  // if (this.isPointInRegion(j + 3, k + 3, 21, 27, mouseX, mouseY))
  @ModifyConstant(method = "renderCreativeInventoryHoveringText",
                  constant = @Constant(intValue = 23))
  private int
  mixin24(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 21 : original;
  }

  /*
   * drawTab
   */
  // int j = i * 28;
  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 28, ordinal = 0))
  private int
  mixin4(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  // int l = this.guiLeft + 26 * i;
  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 28, ordinal = 1))
  private int
  mixin5(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  // l = this.guiLeft + this.xSize - 27 * (7 - i) + 1;
  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 28, ordinal = 2))
  private int
  mixin6(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 27 : original;
  }

  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 6, ordinal = 0))
  private int
  mixin19(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 7 : original;
  }

  @ModifyVariable(method = "drawTab", name = "l",
                  at = @At(value = "STORE", ordinal = 1))
  private int
  mixin21(int original) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      return original + 1;
    }

    return original;
  }

  // this.drawTexturedModalRect(l, i1, j, k, 26, 32);
  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 28, ordinal = 4))
  private int
  mixin7(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 26 : original;
  }

  // l = l + 5;
  @ModifyConstant(method = "drawTab",
                  constant = @Constant(intValue = 6, ordinal = 1))
  private int
  mixin22(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 5 : original;
  }

  /*
   * drawScreen
   */
  // int start = tabPage * 12;
  // int end = Math.min(CreativeTabs.CREATIVE_TAB_ARRAY.length, ((tabPage + 1) *
  // 12) + 4);
  @ModifyConstant(method = "drawScreen", constant = @Constant(intValue = 10))
  private int mixin12(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 12 : original;
  }

  @ModifyConstant(method = "drawScreen",
                  constant = @Constant(intValue = 2, ordinal = 0))
  private int
  mixin13(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 4 : original;
  }

  // if (tabPage != 0) start += 4;
  @ModifyConstant(method = "drawScreen",
                  constant = @Constant(intValue = 2, ordinal = 1))
  private int
  mixin14(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 4 : original;
  }

  /*
   * drawGuiContainerBackgroundLayer
   */
  // int start = tabPage * 12;
  // int end = Math.min(CreativeTabs.CREATIVE_TAB_ARRAY.length, ((tabPage + 1) *
  // 12 + 4));
  @ModifyConstant(method = "drawGuiContainerBackgroundLayer",
                  constant = @Constant(intValue = 10))
  private int
  mixin15(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 12 : original;
  }

  // if (tabPage != 0) start += 4;
  @ModifyConstant(method = "drawGuiContainerBackgroundLayer",
                  constant = @Constant(intValue = 2))
  private int
  mixin16(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 4 : original;
  }

  /*
   * initGui
   */
  // if (tabCount > 14)
  // maxPages = (int) Math.ceil((tabCount - 14) / 10D);
  @ModifyConstant(method = "initGui", constant = @Constant(intValue = 12))
  private int mixin20(int original) {
    return ReFoundConfig.rearrangeCreativeInventory ? 14 : original;
  }
}
