package com.koyomiji.refound.mixin.client.gui;

import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.refound.interfaces.ISearchFieldGetterAccessor;
import java.io.IOException;
import net.minecraft.client.gui.*;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiWorldSelection.class)
public class MixinGuiWorldSelection extends GuiScreen {
  @Shadow private GuiListWorldSelection selectionList;
  @Unique private GuiTextField refound$searchField;

  @Inject(method = "initGui", at = @At(value = "RETURN"))
  private void mixin2(CallbackInfo ci) {
    Keyboard.enableRepeatEvents(true);

    String prevSearch =
        refound$searchField == null ? "" : refound$searchField.getText();
    refound$searchField =
        new GuiTextField(0, this.fontRenderer, width / 2 - 100, 22, 200, 20);
    refound$searchField.setText(prevSearch);
    ISearchFieldGetterAccessor accessor =
        (ISearchFieldGetterAccessor)selectionList;
    accessor.refound$setSearchFieldGetter(() -> refound$searchField.getText());

    if (ReFoundConfig.enableWorldSearch) {
      selectionList.refreshList();
    }

    refound$searchField.setFocused(true);
  }

  @ModifyConstant(method = "initGui", constant = @Constant(intValue = 32))
  private int mixin2(int constant) {
    if (ReFoundConfig.enableWorldSearch) {
      return 48;
    }

    return constant;
  }

  public void updateScreen() {
    if (ReFoundConfig.enableWorldSearch) {
      this.refound$searchField.updateCursorCounter();
    }
  }

  @Override
  protected void keyTyped(char typedChar, int keyCode) throws IOException {
    if (ReFoundConfig.enableWorldSearch) {
      if (this.refound$searchField.isFocused()) {
        this.refound$searchField.textboxKeyTyped(typedChar, keyCode);
        selectionList.refreshList();
      }
    }

    super.keyTyped(typedChar, keyCode);
  }

  @Inject(
      method = "drawScreen",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/gui/GuiWorldSelection;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
          ordinal = 0))
  private void
  mixin3(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
    if (ReFoundConfig.enableWorldSearch) {
      refound$searchField.drawTextBox();
    }
  }

  @ModifyConstant(method = "drawScreen", constant = @Constant(intValue = 20))
  private int mixin3(int constant) {
    if (ReFoundConfig.enableWorldSearch) {
      return 8;
    }

    return constant;
  }

  @Inject(method = "mouseClicked", at = @At("RETURN"))
  private void mixin4(int mouseX, int mouseY, int mouseButton,
                      CallbackInfo ci) {
    if (ReFoundConfig.enableWorldSearch) {
      refound$searchField.mouseClicked(mouseX, mouseY, mouseButton);
    }
  }

  @Override
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }
}
