package com.koyomiji.refound.client.gui;

import net.minecraft.world.storage.WorldSummary;

import java.util.List;

public interface IGuiListWorldSelectionExt {
    void refound$refreshList2(boolean forceRefresh);
    List<WorldSummary> refound$getWorldSummaries();
}
