package cc.abbie.emi_ores.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import cc.abbie.emi_ores.EmiOres;
import cc.abbie.emi_ores.Platform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public record EmiOresConfig(
        boolean addBiomesToIndex
) {
    public static Codec<EmiOresConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("add_biomes_to_index").forGetter(EmiOresConfig::addBiomesToIndex)
    ).apply(instance, EmiOresConfig::new));

    private static final EmiOresConfig DEFAULT = new EmiOresConfig(
            true
    );
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static EmiOresConfig INSTANCE;

    private static File getConfigFile() {
        return Platform.getConfigDir().resolve(EmiOres.MODID + ".json").toFile();
    }

    private static EmiOresConfig loadInner() {
        try (FileReader fileReader = new FileReader(getConfigFile())) {
            return CODEC.parse(JsonOps.INSTANCE, GSON.fromJson(fileReader, JsonElement.class))
                    .resultOrPartial()
                    .orElse(DEFAULT);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            return DEFAULT;
        }
    }

    public static void load() {
        INSTANCE = loadInner();
    }

    public static void save() {
        try (FileWriter fileWriter = new FileWriter(getConfigFile())) {
            getConfigFile().getParentFile().mkdirs();
            JsonElement jsonElement = CODEC.encodeStart(JsonOps.INSTANCE, INSTANCE).getOrThrow();
            GSON.toJson(jsonElement, fileWriter);
        } catch (IOException | IllegalStateException | JsonIOException e) {
            EmiOres.LOG.error("Failed to save config", e);
        }
    }
}
