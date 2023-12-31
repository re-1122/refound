package com.koyomiji.refound.mixin.client.gui;

import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.refound.interfaces.ISearchFieldGetterAccessor;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiListWorldSelection.class)
public abstract class MixinGuiListWorldSelection
    extends GuiListExtended implements ISearchFieldGetterAccessor {
  @Shadow @Final private List<GuiListWorldSelectionEntry> entries;
  @Unique private Supplier<String> refound$searchFieldGetter;

  public MixinGuiListWorldSelection(Minecraft mcIn, int widthIn, int heightIn,
                                    int topIn, int bottomIn, int slotHeightIn) {
    super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
  }

  public void refound$setSearchFieldGetter(Supplier<String> searchFieldGetter) {
    this.refound$searchFieldGetter = searchFieldGetter;
  }

  @Redirect(
      method = "<init>",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/client/gui/GuiListWorldSelection;refreshList()V"))
  private void
  mixin2(GuiListWorldSelection instance) {
    if (ReFoundConfig.enableWorldSearch) {
      return;
    }

    instance.refreshList();
  }

  @Redirect(method = "refreshList",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private boolean
  mixin(List instance, Object e) {
    if (ReFoundConfig.enableWorldSearch) {
      return false;
    }

    return instance.add(e);
  }

  @Inject(method = "refreshList", at = @At(value = "TAIL"),
          locals = LocalCapture.CAPTURE_FAILHARD)
  private void
  mixin2(CallbackInfo ci, ISaveFormat isaveformat, List<WorldSummary> list) {
    if (ReFoundConfig.enableWorldSearch) {
      String search =
          refound$searchFieldGetter == null
              ? ""
              : refound$searchFieldGetter.get().toLowerCase(Locale.ROOT);

      for (WorldSummary ws : list) {
        if (ws.getDisplayName().toLowerCase(Locale.ROOT).contains(search) ||
            ws.getFileName().toLowerCase(Locale.ROOT).contains(search)) {
          entries.add(new GuiListWorldSelectionEntry(
              (GuiListWorldSelection)(Object)this, ws, mc.getSaveLoader()));
        }
      }
    }
  }

  @Inject(method = "refreshList", at = @At(value = "HEAD"))
  private void mixin3(CallbackInfo ci) {
    refound$clearEntries();
  }

  @Unique
  private void refound$clearEntries() {
    entries.clear();
  }
}
