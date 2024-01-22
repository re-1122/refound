package com.koyomiji.refound.setup;

public interface ISetupProcess {
  String getModID();
  boolean needsSetup();
  boolean needsRestart();
  void setup();
}
