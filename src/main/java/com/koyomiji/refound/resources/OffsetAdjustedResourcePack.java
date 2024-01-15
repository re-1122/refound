package com.koyomiji.refound.resources;

import com.google.common.collect.ImmutableSet;
import com.koyomiji.refound.TextureEditor;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class OffsetAdjustedResourcePack extends ResourcePackModifier {
  IResourcePack baseResourcePack;
  private static Set<String> boats =
      new HashSet<>(Arrays.asList("minecraft:textures/items/dark_oak_boat.png",
                                  "minecraft:textures/items/oak_boat.png",
                                  "minecraft:textures/items/spruce_boat.png",
                                  "minecraft:textures/items/birch_boat.png",
                                  "minecraft:textures/items/jungle_boat.png",
                                  "minecraft:textures/items/acacia_boat.png"));
  private static String shulkerShell =
      "minecraft:textures/items/shulker_shell.png";
  private static String prismarineCrystals =
      "minecraft:textures/items/prismarine_crystals.png";

  public OffsetAdjustedResourcePack(IResourcePack baseResourcePack) {
    this.baseResourcePack = baseResourcePack;
  }

  @Override
  public InputStream modifyResource(ResourceLocation location,
                                    InputStream inputStream)
      throws IOException {
    if (boats.contains(location.toString())) {
      BufferedImage image = ImageIO.read(inputStream);
      BufferedImage edited = TextureEditor.offsetImage(image, 0, 1);
      return ResourcePackUtil.toPNGByteArrayInputStream(edited);
    } else if (location.toString().equals(shulkerShell)) {
      BufferedImage image = ImageIO.read(inputStream);
      BufferedImage edited = TextureEditor.offsetImage(image, 0, -1);
      return ResourcePackUtil.toPNGByteArrayInputStream(edited);
    } else if (location.toString().equals(prismarineCrystals)) {
      BufferedImage image = ImageIO.read(inputStream);
      BufferedImage edited = TextureEditor.offsetImage(image, 0, -1);
      return ResourcePackUtil.toPNGByteArrayInputStream(edited);
    }

    return null;
  }

  @Override
  public boolean resourceExists(ResourceLocation location) {
    return boats.contains(location.toString()) ||
        location.toString().equals(shulkerShell) ||
        location.toString().equals(prismarineCrystals);
  }

  @Override
  public Set<String> getResourceDomains() {
    return ImmutableSet.of("minecraft");
  }

  @Nullable
  @Override
  public <T extends IMetadataSection>
      T getPackMetadata(MetadataSerializer metadataSerializer,
                        String metadataSectionName) throws IOException {
    return null;
  }

  @Override
  public BufferedImage getPackImage() throws IOException {
    return null;
  }

  @Override
  public String getPackName() {
    return "OffsetAdjustedResourcePack";
  }
}
