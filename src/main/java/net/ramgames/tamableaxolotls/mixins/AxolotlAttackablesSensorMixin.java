package net.ramgames.tamableaxolotls.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import net.minecraft.entity.passive.AxolotlEntity;
import net.ramgames.tamableaxolotls.AxolotlEntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(AxolotlAttackablesSensor.class)
public class AxolotlAttackablesSensorMixin {


    @Inject(method = "canHunt", at = @At(value = "RETURN"), cancellable = true)
    public void allowAttackingAll(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof AxolotlEntity axolotl && ((AxolotlEntityAccess) axolotl).isTamed()) {
            LivingEntity owner = ((AxolotlEntityAccess) axolotl).tamableAxolotls$getOwner();
            if(owner != null) cir.setReturnValue(owner.getAttacking() == target);
        }
    }

    @Inject(method = "isAlwaysHostileTo", at = @At("RETURN"), cancellable = true)
    public void removeConstantHostility(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof AxolotlEntity axolotl && ((AxolotlEntityAccess) axolotl).isTamed()) cir.setReturnValue(false);
    }
}
