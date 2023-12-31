package com.koyomiji.refound;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ReFoundCreativeTabs {
  public static CreativeTabs MATERIALS =
      new CreativeTabs(CreativeTabs.getNextID(), "materials") {
        @Override
        public ItemStack createIcon() {
          return new ItemStack(Items.STICK);
        }
      };
  public static CreativeTabs OP =
      new CreativeTabs(CreativeTabs.getNextID(), "op") {
        @Override
        public ItemStack createIcon() {
          return new ItemStack(Blocks.COMMAND_BLOCK);
        }
      };
}
