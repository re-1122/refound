package com.koyomiji.refound.resources;

import com.google.common.collect.Lists;
import com.koyomiji.refound.interfaces.IDefaultResourcePacksAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;

public abstract class ResourcePackModifier implements IResourcePack {
  @Override
  public InputStream getInputStream(ResourceLocation location) throws IOException {
    IDefaultResourcePacksAccessor accessor =
            ((IDefaultResourcePacksAccessor) Minecraft.getMinecraft());

    for (IResourcePack rp : Lists.reverse(accessor.getDefaultResourcePacks())) {
      if (rp != this && rp.resourceExists(location)) {
        InputStream is = rp.getInputStream(location);
        return modifyResource(location, is);
      }
    }

    return null;
  }

  public abstract InputStream modifyResource(ResourceLocation location, InputStream inputStream) throws IOException;
}
