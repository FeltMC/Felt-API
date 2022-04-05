package io.github.feltmc.feltapi.mixin.ore_feature;

import io.github.feltmc.feltapi.impl.ore_feature.OreFeaturesData;
import net.minecraft.world.biome.GenerationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsBuilderMixin {

    @Inject(method = "build", at = @At(value = "HEAD"))
    private void felt_build(CallbackInfoReturnable<GenerationSettings> cir){
        OreFeaturesData.FEATURE_MAP.forEach((k, v) -> {

        });

    }
}
