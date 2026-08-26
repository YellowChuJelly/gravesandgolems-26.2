package net.redchujelly.gravesandgolems.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.redchujelly.gravesandgolems.registry.BlockRegistry;

public class GolemEntity extends AbstractGolem {
    public GolemEntity(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return AbstractGolem.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, (double)30.0F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.25F)
                .add(Attributes.KNOCKBACK_RESISTANCE, (double)0.5F)
                .add(Attributes.ATTACK_DAMAGE, (double)6.0F)
                .add(Attributes.STEP_HEIGHT, (double)1.0F)
                .add(Attributes.ARMOR, (double)5.0F)
                .add(Attributes.ARMOR_TOUGHNESS)
                .add(Attributes.MAX_ABSORPTION)
                .add(Attributes.SCALE)
                .add(Attributes.GRAVITY)
                .add(Attributes.SAFE_FALL_DISTANCE, 1000)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0F)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0F)
                .add(Attributes.JUMP_STRENGTH)
                .add(Attributes.ENTITY_INTERACTION_RANGE)
                .add(Attributes.OXYGEN_BONUS)
                .add(Attributes.BURNING_TIME)
                .add(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY)
                .add(Attributes.MOVEMENT_EFFICIENCY)
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(Attributes.CAMERA_DISTANCE)
                .add(Attributes.CAMERA_DISTANCE)
                .add(Attributes.FOLLOW_RANGE, 5)
                ;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(BlockRegistry.GRAVE_DIRT.get().asItem())) {
            return InteractionResult.PASS;
        } else {
            float healthBefore = this.getHealth();
            this.heal(25.0F);
            if (this.getHealth() == healthBefore) {
                return InteractionResult.PASS;
            } else {
                float pitch = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, pitch);
                itemStack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (double)1.0F, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.85F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Monster>(this, Monster.class, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }


}
