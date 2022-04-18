package io.github.feltmc.feltapi.api.splash.mixin;

import io.github.feltmc.feltapi.api.splash.SplashAPI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(net.minecraft.client.resource.SplashTextResourceSupplier.class)
public class SplashMixin {
    @Shadow
    private List<String> splashTexts;

    @Inject(method="apply", at=@At("TAIL"))
    private void inject(CallbackInfo info) {
        splashTexts.addAll(SplashAPI.splashesToAdd);
        splashTexts.removeAll(SplashAPI.splashesToRemove);
    }
}

