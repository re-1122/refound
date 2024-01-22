package com.koyomiji.refound.setup;

import com.google.common.collect.Lists;

import java.util.List;

public class SetupQueue {
  public static List<ISetupProcess> setupProcesses = Lists.newArrayList();

  public static void addSetupProcess(ISetupProcess handler) {
    setupProcesses.add(handler);
  }
}
