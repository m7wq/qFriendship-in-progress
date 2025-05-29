package dev.qdevelopment.qfriendship.managers;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

    private final Plugin plugin;
    private Configuration configuration;

    public Config(Plugin plugin) {
        this.plugin = plugin; load(); }

    private void load() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File configFile = new File(plugin.getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                    Files.copy(in, configFile.toPath());
                }
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("error");
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}