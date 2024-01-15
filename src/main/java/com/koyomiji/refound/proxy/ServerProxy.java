package com.koyomiji.refound.proxy;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.koyomiji.refound.ReFound;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ReFound.MODID, value = Side.SERVER)
public class ServerProxy extends CommonProxy {
  @Override
  public Path getCacheDir() {
    return Paths.get("caches");
  }
}
