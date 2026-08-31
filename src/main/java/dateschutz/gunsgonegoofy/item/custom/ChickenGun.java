package dateschutz.gunsgonegoofy.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class ChickenGun extends Item {

    private static Chicken trackedChicken;
    private static long explosionTime = -1;

    public ChickenGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        Vec3 direction = user.getLookAngle();
        double speed = 2;
        ItemStack handstack = user.getItemInHand(hand);


        ItemStack eggStack = null;

        for (int i = 0; i < user.getInventory().getContainerSize(); i++) {

            ItemStack stack = user.getInventory().getItem(i);

            if (stack.is(Items.EGG)) {
                eggStack = stack;
                break;
            }
        }

        if(eggStack != null || user.isCreative()) {

            if (!user.isCreative()) {
                eggStack.shrink(1);
                handstack.hurtAndBreak(1, user, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            }

            Chicken chicken = new Chicken(EntityTypes.CHICKEN, level);
            chicken.setPos(user.getX(), user.getY() + 0.85, user.getZ());
            chicken.setDeltaMovement(direction.scale(speed));
            chicken.setInvulnerable(true);
            level.addFreshEntity(chicken);

            chicken.playSound(SoundEvents.CHICKEN_DEATH_BABY.value(), 10f, 0.4f);

            this.trackedChicken = chicken;
            this.explosionTime = level.getGameTime() + 15L;
        }

        return InteractionResult.SUCCESS;
    }

    public static void tickAll() {

        if (trackedChicken == null) {
            return;
        }

        Level level = trackedChicken.level();

        if (level.isClientSide()) {
            return;
        }

        if (!trackedChicken.isAlive()) {
            trackedChicken = null;
            explosionTime = -1;
            return;
        }

        if (level.getGameTime() >= explosionTime) {

            level.explode(null, trackedChicken.getX(), trackedChicken.getY(), trackedChicken.getZ(), 4.0F, Level.ExplosionInteraction.TNT);

            trackedChicken.discard();

            trackedChicken = null;
            explosionTime = -1;

        }

    }




    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("itemTooltip.chicken-gun.chicken_gun").withStyle(ChatFormatting.GOLD));
    }
}