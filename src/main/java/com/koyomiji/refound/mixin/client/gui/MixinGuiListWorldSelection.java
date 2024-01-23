package com.koyomiji.refound.mixin.client.gui;

import com.koyomiji.refound.client.gui.IGuiListWorldSelectionExt;
import com.koyomiji.refound.client.gui.IGuiWorldSelectionExt;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;

@Mixin(GuiListWorldSelection.class)
public abstract class MixinGuiListWorldSelection
    extends GuiListExtended implements IGuiListWorldSelectionExt {
  @Shadow @Final private List<GuiListWorldSelectionEntry> entries;
  @Shadow @Final private static Logger LOGGER;
  @Shadow @Final private GuiWorldSelection worldSelection;
  @Unique
  private List<WorldSummary> refound$worldSummaries = null;

  public MixinGuiListWorldSelection(Minecraft mcIn, int widthIn, int heightIn,
                                    int topIn, int bottomIn, int slotHeightIn) {
    super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
  }

  public void refound$refreshList2(boolean forceRefresh) {
    this.refound$clearEntries();
    ISaveFormat save = this.mc.getSaveLoader();

    if (refound$worldSummaries == null || forceRefresh) {
      if (((IGuiWorldSelectionExt)worldSelection).refound$getWorldSelectionList() != null) {
        refound$worldSummaries = ((IGuiListWorldSelectionExt)((IGuiWorldSelectionExt)worldSelection).refound$getWorldSelectionList()).refound$getWorldSummaries();
      }

      if (refound$worldSummaries == null) {
        try {
          refound$worldSummaries = save.getSaveList();
        } catch (AnvilConverterException anvilconverterexception) {
          LOGGER.error("Couldn't load level list", (Throwable) anvilconverterexception);
          this.mc.displayGuiScreen(new GuiErrorScreen(I18n.format("selectWorld.unable_to_load"), anvilconverterexception.getMessage()));
          return;
        }

        Collections.sort(refound$worldSummaries);
      }
    }

      String search = ((IGuiWorldSelectionExt)worldSelection).refound$getSearchField().getText();

      for (WorldSummary ws : refound$worldSummaries) {
        if (ws.getDisplayName().toLowerCase(Locale.ROOT).contains(search) ||
                ws.getFileName().toLowerCase(Locale.ROOT).contains(search)) {
          entries.add(new GuiListWorldSelectionEntry(
                  (GuiListWorldSelection)(Object)this, ws, mc.getSaveLoader()));
        }
      }
  }

  /**
   * @author Komichi
   * @reason In order to implement the search function
   */
  @Overwrite
    public void refreshList() {
        refound$refreshList2(false);
    }

  @Unique
  private void refound$clearEntries() {
    entries.clear();
  }

  @Override
  public List<WorldSummary> refound$getWorldSummaries() {
    return refound$worldSummaries;
  }
}
