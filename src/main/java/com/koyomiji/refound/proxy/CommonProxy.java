package com.koyomiji.refound.proxy;

import com.google.common.collect.Sets;
import com.koyomiji.refound.ReFound;
import com.koyomiji.refound.ReFoundCreativeTabs;
import com.koyomiji.refound.RecipeUnregisterer;
import com.koyomiji.refound.config.ReFoundConfig;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber(modid = ReFound.MODID)
public abstract class CommonProxy {
  public abstract Path getCacheDir();

  public void preInit(FMLPreInitializationEvent event) {}

  public void init(FMLInitializationEvent event) {}

  public void postInit(FMLPostInitializationEvent event) {}

  private static final Set<String> materials = Sets.newHashSet(
      "minecraft:clay_ball", "minecraft:reeds", "minecraft:sugar",
      "minecraft:prismarine_crystals", "minecraft:wheat",
      "minecraft:wheat_seeds", "minecraft:dye", "minecraft:diamond",
      "minecraft:gold_nugget", "minecraft:brick", "minecraft:nether_wart",
      "minecraft:rabbit_hide", "minecraft:egg", "minecraft:iron_ingot",
      "minecraft:emerald", "minecraft:quartz", "minecraft:nether_star",
      "minecraft:feather", "minecraft:chorus_fruit_popped",
      "minecraft:iron_nugget", "minecraft:gunpowder", "minecraft:netherbrick",
      "minecraft:prismarine_shard", "minecraft:blaze_rod", "minecraft:stick",
      "minecraft:pumpkin_seeds", "minecraft:glowstone_dust",
      "minecraft:gold_ingot", "minecraft:flint", "minecraft:leather",
      "minecraft:bowl", "minecraft:beetroot_seeds", "minecraft:coal",
      "minecraft:chorus_fruit", "minecraft:string", "minecraft:melon_seeds",
      "minecraft:shulker_shell");
  private static final Set<String> op = Sets.newHashSet(
      "minecraft:repeating_command_block", "minecraft:barrier",
      "minecraft:chain_command_block", "minecraft:command_block",
      "minecraft:structure_void", "minecraft:command_block_minecart",
      "minecraft:structure_block");

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      for (Map.Entry<ResourceLocation, Block> e :
           event.getRegistry().getEntries()) {
        String id = e.getKey().toString();
        Block block = e.getValue();

        if (materials.contains(id)) {
          block.setCreativeTab(ReFoundCreativeTabs.MATERIALS);
        }

        if (op.contains(id)) {
          block.setCreativeTab(ReFoundCreativeTabs.OP);
        }
      }
    }
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    if (ReFoundConfig.rearrangeCreativeInventory) {
      for (Map.Entry<ResourceLocation, Item> e :
           event.getRegistry().getEntries()) {
        String id = e.getKey().toString();
        Item item = e.getValue();

        if (materials.contains(id)) {
          item.setCreativeTab(ReFoundCreativeTabs.MATERIALS);
        }

        if (op.contains(id)) {
          item.setCreativeTab(ReFoundCreativeTabs.OP);
        }
      }
    }
  }

  @SubscribeEvent
  public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    IForgeRegistryModifiable modRegistry =
        (IForgeRegistryModifiable)event.getRegistry();

    for (ResourceLocation rl : RecipeUnregisterer.unregisteredRecipes) {
      modRegistry.remove(rl);
    }
  }
}
