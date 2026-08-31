package dateschutz.gunsgonegoofy.item;




import dateschutz.gunsgonegoofy.GunsGoneGoofy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ModItemIds {

    public static final ResourceKey<Item> CHICKEN_GUN = create("chicken_gun");


    public static ResourceKey<Item> create(String name) {

        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GunsGoneGoofy.MOD_ID, name));
    }
}
