package com.koyomiji.refound;

import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.refound.interfaces.IDefaultResourcePacksAccessor;
import com.koyomiji.refound.proxy.CommonProxy;
import java.io.File;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = ReFound.MODID)
public class ReFound {
  public static final String MODID = "refound";
  @Mod.Instance public static ReFound instance;
  @SidedProxy(clientSide = "com.koyomiji.refound.proxy.ClientProxy",
              serverSide = "com.koyomiji.refound.proxy.ServerProxy")
  public static CommonProxy proxy;
  public static Logger logger;
  public static File modFile;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    modFile = event.getSourceFile();
    proxy.preInit(event);
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
