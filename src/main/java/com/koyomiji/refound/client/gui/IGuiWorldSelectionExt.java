package com.koyomiji.refound.client.gui;

import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.world.storage.WorldSummary;

import java.util.List;

public interface IGuiWorldSelectionExt {
    GuiTextField refound$getSearchField();
    GuiListWorldSelection refound$getWorldSelectionList();
}
