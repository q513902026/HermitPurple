package me.hopeasd.hp;

import me.hopeasd.hp.config.LangConfig;
import me.hopeasd.hp.config.PluginConfig;
import me.hopeasd.hp.listeners.PlayerListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class HermitPurple extends JavaPlugin {
    private static HermitPurple instance;
    private static Logger log;
    private PluginConfig commonConfig;
    private LangConfig langConfig;
    private PlayerManager playerManager;
    private PlayerListeners playerListener;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();
        saveDefaultConfig();
        commonConfig = new PluginConfig(getConfig());
        langConfig = new LangConfig("lang");
        playerManager = new PlayerManager();
        playerListener = new PlayerListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }



    public void info(String message) {
        log.info(message);
    }
    public void sendMessage(Player player, String message){
        player.sendMessage(String.format("§d[%s]§e : %s",commonConfig.getPrefix(),message));
    }
    public void sendFormatMessage(Player player,String formatKey){
        sendMessage(player,langConfig.getLang(formatKey));
    }
    public void sendFormatMessage(Player player,String formatKey,String... args){
        sendMessage(player,String.format(langConfig.getLang(formatKey),args));
    }

    public static HermitPurple INSTANCE() {
        return instance;
    }
    public PluginConfig getCommonConfig(){
        return this.commonConfig;
    }
    public Logger getLog(){
        return this.log;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public PlayerListeners getPlayerListener() {
        return playerListener;
    }
}
