package me.hopeasd.hp.util;

import me.hopeasd.hp.HermitPurple;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileHelper {

    public static FileConfiguration getFileData(String name, File folder) {

        File dataFile = new File(folder, name + ".yml");
        if (!dataFile.exists()) {
            try {
                dataFile.mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        return data;
    }

    public static FileConfiguration saveFileData(String name, File folder) {
        File dataFile = new File(folder, name + ".yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        try {
            data.save(dataFile);

        } catch (IOException e) {

            e.printStackTrace();
        }
        return data;
    }

    public static void saveDefaultFileData(String name, File folder) {
        if (!((new File(folder, name + ".yml")).exists())) {
            HermitPurple.INSTANCE().saveResource(name + ".yml", false);
        }
    }

    public static FileConfiguration reloadFileData(String name, File folder) {
        FileConfiguration data = FileHelper.getFileData(name, folder);
        File dataFile = new File(folder, name + ".yml");
        data = YamlConfiguration.loadConfiguration(dataFile);

        InputStream defConfigStream = HermitPurple.INSTANCE().getResource(dataFile.getPath());
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));

            data.setDefaults(defConfig);
        }
        return data;
    }
}
