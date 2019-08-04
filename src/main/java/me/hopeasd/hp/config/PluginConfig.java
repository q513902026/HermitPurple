package me.hopeasd.hp.config;

import me.hopeasd.hp.HermitPurple;
import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {
    private FileConfiguration config;

    private boolean useEcon = false;
    private boolean useMode = false;
    private boolean warnPlayer = false;
    private double cost = 40.0D;
    private double warnRange = 15.0D;
    private int timeout = 20;
    private String prefix = "紫色隐者";
    private int cooldown;

    public PluginConfig(FileConfiguration config){
        this.config = config;
        loadConfig();
    }

    private void loadConfig(){
        useEcon = this.config.getBoolean("common.useEcon",false);
        useMode = this.config.getBoolean("common.useMode",false);
        warnPlayer = this.config.getBoolean("common.warn",false);
        cost = this.config.getDouble("common.cost",40.0D);
        warnRange = this.config.getDouble("common.warnRange",15.0D);
        timeout = this.config.getInt("common.timeout",20);
        cooldown = this.config.getInt("common.cooldown", 60);
        prefix = this.config.getString("common.prefix","紫色隐者");
    }

    public void reloadConfig(){

        HermitPurple.INSTANCE().saveConfig();
        HermitPurple.INSTANCE().reloadConfig();

        config = HermitPurple.INSTANCE().getConfig();
        loadConfig();
    }

    public void setUseEcon(boolean useEcon) {
        this.useEcon = useEcon;
    }
    public void setUseMode(boolean useMode) {
        this.useMode = useMode;
    }
    public void setWarnPlayer(boolean warnPlayer) {
        this.warnPlayer = warnPlayer;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setWarnRange(double warnRange) {
        this.warnRange = warnRange;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean getUseEcon(){
        return this.useEcon;
    }
    public boolean getUseMode(){
        return this.useMode;
    }
    public boolean getWarnPlayer(){
        return this.warnPlayer;
    }
    public double getCost() {
        return cost;
    }
    public double getWarnRange() {
        return warnRange;
    }
    public String getPrefix() {
        return prefix;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
