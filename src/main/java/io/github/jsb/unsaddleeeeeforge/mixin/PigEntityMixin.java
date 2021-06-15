package io.github.jsb.unsaddleeeeeforge.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {

	protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract boolean isSaddled();

	@Shadow	@Final private static DataParameter<Boolean> DATA_SADDLE_ID;

	@Inject(at = @At("HEAD"), method = "mobInteract" , cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
		if (player.isSecondaryUseActive() && isSaddled() && getPassengers().isEmpty()) {
			dropEquipment();
			entityData.set(DATA_SADDLE_ID, false);
			cir.setReturnValue(ActionResultType.SUCCESS);
		}
	}
}