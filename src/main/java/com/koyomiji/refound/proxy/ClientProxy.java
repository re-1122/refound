package com.koyomiji.refound.proxy;

import com.koyomiji.refound.ReFound;
import com.koyomiji.refound.config.ReFoundConfig;
import com.koyomiji.refound.interfaces.IDefaultResourcePacksAccessor;
import com.koyomiji.refound.resources.OffsetAdjustedResourcePack;
import java.nio.file.Path;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ReFound.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
  @Override
  public Path getCacheDir() {
    return Minecraft.getMinecraft().gameDir.toPath().resolve("caches");
  }

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    if (ReFoundConfig.adjustItemTextureOffset) {
      IDefaultResourcePacksAccessor accessor =
          ((IDefaultResourcePacksAccessor)Minecraft.getMinecraft());

      accessor.getDefaultResourcePacks().add(new OffsetAdjustedResourcePack(
          accessor.getDefaultResourcePacks().get(0)));

      FMLClientHandler.instance().refreshResources(
          VanillaResourceType.TEXTURES);
    }
  }
}
