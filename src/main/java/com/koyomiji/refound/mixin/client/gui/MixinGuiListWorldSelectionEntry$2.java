package com.koyomiji.refound.mixin.client.gui;

import com.koyomiji.refound.client.gui.IGuiListWorldSelectionExt;
import net.minecraft.client.gui.GuiListWorldSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/client/gui/GuiListWorldSelectionEntry$2")
public class MixinGuiListWorldSelectionEntry$2 {
    @Redirect(method = "confirmClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiListWorldSelection;refreshList()V"))
    private void mixin(GuiListWorldSelection instance) {
        ((IGuiListWorldSelectionExt)instance).refound$refreshList2(true);
    }
}
