package dateschutz.gunsgonegoofy;

import dateschutz.gunsgonegoofy.item.ModItems;
import dateschutz.gunsgonegoofy.item.custom.ChickenGun;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GunsGoneGoofy implements ModInitializer {
	public static final String MOD_ID = "guns-gone-goofy";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		// LOGGER.info("Hello Fabric world!");

		ModItems.initialize();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
            ChickenGun.tickAll();});

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
