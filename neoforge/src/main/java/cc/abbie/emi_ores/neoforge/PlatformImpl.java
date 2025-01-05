package cc.abbie.emi_ores.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformImpl {
    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
