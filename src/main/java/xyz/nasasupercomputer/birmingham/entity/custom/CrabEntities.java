package xyz.nasasupercomputer.birmingham.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags.Items;
import xyz.nasasupercomputer.birmingham.entity.ModEntities;

import org.jetbrains.annotations.Nullable;

public class CrabEntities extends Animal {
	public CrabEntities(EntityType<? extends Animal> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.SLIMEBALLS), false));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3f));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));


	}
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 10D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, 2D)
				.add(Attributes.FOLLOW_RANGE, 20D);
	}
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
		return ModEntities.crab.get().create(pLevel);
	}
	
	@Override
	public boolean isFood(ItemStack pStack) {
		return pStack.is(Items.SLIMEBALLS);
	}
}
