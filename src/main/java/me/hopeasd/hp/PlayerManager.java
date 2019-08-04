package me.hopeasd.hp;

import me.hopeasd.hp.HermitPurple;
import me.hopeasd.hp.config.PluginConfig;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PlayerManager {
    private static Economy econ = null;
    private boolean hasEcon = false;
    private PluginConfig config;
    public PlayerManager(){
        config = HermitPurple.INSTANCE().getCommonConfig();
        if (config.getUseEcon()) {
            if (!setupEconomy()) {
                HermitPurple.INSTANCE().getLog().severe(String.format("[%s] - Disabled due to no Vault dependency found!", HermitPurple.INSTANCE().getDescription().getName()));
                hasEcon = false;
                return;
            }
            hasEcon = true;
        }
    }

    private boolean setupEconomy() {
        if (HermitPurple.INSTANCE().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = HermitPurple.INSTANCE().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public boolean checkEcon(Player player){
        if (hasEcon){
            return econ.has(player,config.getCost());
        }
        return true;
    }
    public boolean checkWarn(Player player,double distance){
        if (config.getWarnPlayer()){
            if(distance <= config.getWarnRange()){
                return true;
            }
        }
        return false;
    }

    public void withdraw(Player player){
        if(hasEcon){
            EconomyResponse r = econ.withdrawPlayer(player,config.getCost());
            HermitPurple.INSTANCE().sendFormatMessage(player,"cost.succuse",econ.format(r.amount));
        }
    }
    public static Economy getEconomy() {
        return econ;
    }
}
