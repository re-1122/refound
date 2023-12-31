package com.koyomiji.refound.mixin.client;

import com.koyomiji.refound.interfaces.IDefaultResourcePacksAccessor;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IDefaultResourcePacksAccessor {
  @Shadow @Final private List<IResourcePack> defaultResourcePacks;

  public List<IResourcePack> getDefaultResourcePacks() {
    return defaultResourcePacks;
  }
}
