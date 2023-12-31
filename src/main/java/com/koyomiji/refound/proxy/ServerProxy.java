package com.koyomiji.refound.proxy;

import java.nio.file.Path;
import java.nio.file.Paths;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ServerProxy extends CommonProxy {
  @Override
  public Path getCacheDir() {
    return Paths.get("caches");
  }
}
