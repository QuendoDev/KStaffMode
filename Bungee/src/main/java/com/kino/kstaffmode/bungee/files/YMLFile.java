package com.kino.kstaffmode.bungee.files;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class YMLFile {

    private File file;
    private Configuration configuration;
    private final Plugin plugin;
    private final String outputFile;
    private final String inputFile;

    public YMLFile(Plugin plugin, String outputFile, String inputFile) {
        this.plugin = plugin;
        this.outputFile = (!outputFile.endsWith(".yml")) ? outputFile + ".yml" : outputFile;
        this.inputFile = (!inputFile.endsWith(".yml")) ? inputFile + ".yml" : inputFile;
    }

    public Configuration get() {
        if (this.configuration == null) {
            this.reload();
        }
        return this.configuration;
    }

    public void reload() {
        if (this.configuration == null) {
            this.file = new File(this.plugin.getDataFolder(), this.outputFile);
        }
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public YMLFile load(String archive, boolean silent) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (this.file == null) {
            this.file = new File(this.plugin.getDataFolder(), this.outputFile);
        }
        if (!this.file.exists()) {
            try {
                if (!silent) {
                    this.plugin.getLogger().info(ChatColor.RED + archive + ChatColor.GRAY + " is not founded, creating archive...");
                }
                if (this.inputFile == null) {
                    this.file.createNewFile();
                    return this;
                }
                InputStream inputStream = this.plugin.getResourceAsStream(this.inputFile);
                if (inputStream == null) {
                    throw new FileNotFoundException("The file " + this.inputFile + "was not founded in plugin files");
                }
                Configuration inputConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(inputStream);
                if (this.file.isDirectory()) {
                    this.file.delete();
                }
                if (!this.file.exists()) {
                    this.file.createNewFile();
                }
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(inputConfig, this.file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            this.reload();
        }
        return this;
    }
}
