package net.addalch.bottles;

import net.addalch.core.ModRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SipnPotion extends PotionItem {
    public SipnPotion(Settings settings) {
        super(settings);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (!world.isClient) {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var6.next();
                if (statusEffectInstance.getEffectType().isInstant()) {
                    statusEffectInstance.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, statusEffectInstance.getAmplifier(), 1.0D);
                } else {
                    user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                }
            }
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.abilities.creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.abilities.creativeMode) {
            if (stack.isEmpty()) {
                return PotionUtil.setPotion( new ItemStack (ModRegistry.SPLASHN_BOTTLE), (PotionUtil.getPotion(stack)));
            }

            if (playerEntity != null) {
                playerEntity.inventory.insertStack(PotionUtil.setPotion( new ItemStack (ModRegistry.SPLASHN_BOTTLE), (PotionUtil.getPotion(stack))));
            }
        }

        return stack;
    }
}
