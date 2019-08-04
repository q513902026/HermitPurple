package me.hopeasd.hp.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.hopeasd.hp.HermitPurple;
import me.hopeasd.hp.util.FileHelper;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LangConfig {
    private FileConfiguration config;
    private List<String> langList= Lists.newArrayList();
    private Map<String,String> lang = Maps.newHashMap();
    private String name= "lang";
    private File folder = null;
    public LangConfig(String name){
        FileHelper.saveDefaultFileData((this.name=name),(folder=HermitPurple.INSTANCE().getDataFolder()));
        config = FileHelper.getFileData(name,folder);
        loadConfig();
    }

    private void loadConfig(){
        langList = config.getStringList("lang.list");
        for(String key:langList){
            lang.put(key,config.getString(key));
        }
    }
    public String getLang(String key){
        if (langList.contains(key)){
            return lang.get(key);
        }
        return "";
    }
    public void reloadConfig(){
        FileHelper.reloadFileData(name,folder);
    }
}
