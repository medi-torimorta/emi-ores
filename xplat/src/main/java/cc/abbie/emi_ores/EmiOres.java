package cc.abbie.emi_ores;

import net.minecraft.resources.ResourceLocation;

import cc.abbie.emi_ores.config.EmiOresConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmiOres {
    public static final String MODID = "emi_ores";
    public static final Logger LOG = LoggerFactory.getLogger("EMI Ores");

    public static void init() {
        EmiOresConfig.load();
        EmiOresConfig.save();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
