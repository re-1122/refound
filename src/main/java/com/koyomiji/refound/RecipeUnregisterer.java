package com.koyomiji.refound;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public class RecipeUnregisterer {
  public static List<ResourceLocation> unregisteredRecipes =
      Lists.newArrayList();
  public static List<ResourceLocation> unregisteredAdvancements =
      Lists.newArrayList();

  public static void unregisterRecipe(ResourceLocation rl) {
    unregisteredRecipes.add(rl);
  }

  public static void unregisterAdvancement(ResourceLocation rl) {
    unregisteredAdvancements.add(rl);
  }
}
