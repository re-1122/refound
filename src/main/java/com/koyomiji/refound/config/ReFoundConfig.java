package com.koyomiji.refound.config;

import com.koyomiji.refound.ReFound;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ReFound.MODID)
@Config.LangKey("config.refound.title")
public class ReFoundConfig {
  @Config.Comment("Turn off the snooper automatically when the game starts.")
  @Config.RequiresMcRestart
  public static boolean turnOffSnooperAutomatically = true;

  @Config.Comment(
      "Turn off the Realms notifications automatically when the game starts.")
  public static boolean turnOffRealmsNotificationsAutomatically = true;

  @Config.Comment("1.19.3-like creative inventory.")
  @Config.RequiresMcRestart
  public static boolean rearrangeCreativeInventory = true;

  @Config.Comment("Fix armor status alignment.")
  public static boolean fixTooltipAlignment = true;

  @Config.Comment("Adjust texture offset of some items.")
  public static boolean adjustItemTextureOffset = true;

  @Config.Comment("Enable search field in the world selection screen.")
  public static boolean enableWorldSearch = true;

  @Config.Comment(
      "Resolve the world name conflict by adding a number at the end of the world name.")
  public static boolean duplicateWorldNameNumber = true;

  @Config.Comment("Fix some inconsistencies in village structures.")
  public static boolean fixVillageGeneration = true;

  @Config.
  Comment("Fix the issue that creative tabs have different clickable areas.")
  public static boolean fixCreativeTabArea = true;

  @Mod.EventBusSubscriber(modid = ReFound.MODID)
  private static class EventHandler {
    @SubscribeEvent
    public static void
    onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(ReFound.MODID)) {
        ConfigManager.sync(ReFound.MODID, Config.Type.INSTANCE);
      }
    }
  }
}
