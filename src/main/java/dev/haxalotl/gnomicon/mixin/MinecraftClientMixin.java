package dev.haxalotl.gnomicon.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.haxalotl.gnomicon.GNOMicon;
import dev.haxalotl.gnomicon.GNOMiconConfig;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MinecraftClient.class, priority = Integer.MAX_VALUE)
public class MinecraftClientMixin {
    @ModifyReturnValue(method="getWindowTitle", at = @At("RETURN"))
    private String gnomicon$getWindowTitle(String original) {
        GNOMiconConfig.createConfig();
        GNOMiconConfig.readConfig();
        if (GNOMiconConfig.generateDesktopEntry) {
            GNOMicon.createDesktopEntry();
        }
        return GNOMiconConfig.windowName != null ? GNOMiconConfig.windowName : original ;
    }
}
