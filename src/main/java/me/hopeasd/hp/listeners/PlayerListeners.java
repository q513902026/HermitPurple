package me.hopeasd.hp.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.hopeasd.hp.HermitPurple;
import me.hopeasd.hp.util.PosHelper;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerListeners implements Listener {
    private List<UUID> cooldown;
    private Map<UUID,Boolean> interact;
    private HermitPurple instance;

    public PlayerListeners()
    {
        instance = HermitPurple.INSTANCE();
        instance.getServer().getPluginManager().registerEvents(this,instance);
        cooldown = Lists.newArrayList();
        interact = Maps.newHashMap();
    }

    public void increaseCooldown(final Player player){
        cooldown.add(player.getUniqueId());
        instance.getServer().getScheduler().scheduleSyncDelayedTask(HermitPurple.INSTANCE(),new Runnable(){
            public void run() {
                cooldown.remove(player.getUniqueId());
                instance.sendFormatMessage(player,"player.interact.cooldown.succuse");
            }
        },instance.getCommonConfig().getCooldown()*20);
    }
    public void interactTrack(final Player player){
        this.interact.put(player.getUniqueId(), true);
        instance.getServer().getScheduler().scheduleSyncDelayedTask(HermitPurple.INSTANCE(),new Runnable(){
            public void run() {
                if (checkInteract(player)) {
                    interact.put(player.getUniqueId(), false);
                    instance.sendFormatMessage(player, "player.interact.close");
                }
            }
        },instance.getCommonConfig().getTimeout()*20);
        instance.sendFormatMessage(player,"player.interact.succuse");
    }

    public boolean checkCooldown(final Player player){
        if (player.hasPermission("hermitpurple.cooldown.ignore")){
            return true;
        }
        boolean inCooldown;
        if ((inCooldown = cooldown.contains(player.getUniqueId()))){
            instance.sendFormatMessage(player,"player.interact.cooldown.error");
        }
        return !inCooldown;
    }
    public boolean checkInteract(final Player player){
        return interact.containsKey(player.getUniqueId()) && interact.get(player.getUniqueId());
    }
    public void toggleInteract(final Player player){

        if (checkInteract(player)){
            interact.put(player.getUniqueId(), false);
            instance.sendFormatMessage(player, "player.interact.close");
            return ;
        }
        interactTrack(player);
        return;
    }
    public boolean checkEcon(final Player player){
        if (player.hasPermission("hermitpurple.econ.ignore")){
            return true;
        }
        return instance.getPlayerManager().checkEcon(player);
    }
    public boolean checkDistance(final Player target,final double distance){

        if(target.hasPermission("hermitpurple.warn")){
            return true;
        }
        return instance.getPlayerManager().checkWarn(target,distance);
    }

    public boolean checkUnit(final Player source,final Player target){
        if(target == null){
            instance.sendFormatMessage(source,"player.notfound");
            return false;
        }
        if(source.getUniqueId().equals(target.getUniqueId())){
            instance.sendFormatMessage(source,"player.me");
            return false;
        }
        if(!source.getWorld().getUID().equals(target.getWorld().getUID())){
            instance.sendFormatMessage(source,"player.track.distance.otherworld");
            return false;
        }
        return true;
    }
    public void withdraw(final Player source){
        instance.getPlayerManager().withdraw(source);
    }
    public double getDistance(final Player source,final Player target){
        return PosHelper.TwoDistance(source.getLocation(),target.getLocation());
    }

    public String getPosition(final Player source,final Player target){
        return PosHelper.TwoPosition(source.getLocation(),target.getLocation());
    }


    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent e)
    {
         Player player = e.getPlayer();
         Action action = e.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (e.getMaterial() == Material.COMPASS) {
                if (player.isSneaking()){
                    player.setCompassTarget(player.getWorld().getSpawnLocation());
                    instance.sendFormatMessage(player,"compass.reset");
                    return;
                }
                if (checkEcon(player) && checkCooldown(player)) {
                    toggleInteract(player);
                    return;
                }
            }
        }
    }
    @EventHandler()
    public void onChatSender(AsyncPlayerChatEvent e)
    {
        final Player source = e.getPlayer();
        final String targetName = e.getMessage();
        if(checkInteract(source)){
            e.setCancelled(true);
            try{
                final Player target = instance.getServer().getPlayer(targetName);
                if(checkUnit(source,target)){
                    source.setCompassTarget(target.getLocation().clone());
                    double distance = getDistance(source,target);
                    if(distance<0.0D){
                        instance.sendFormatMessage(source,"player.track.distance.error");
                        return;
                    }
                    instance.info(checkDistance(target,distance)+"|"+distance+"|"+instance.getCommonConfig().getWarnRange()+"");
                    if(checkDistance(target,distance)){
                        instance.sendFormatMessage(target,"player.track.warn.target");
                    }
                    withdraw(source);
                    increaseCooldown(source);
                    instance.sendFormatMessage(source,"player.track.distance.succuse",target.getDisplayName(),getPosition(source,target),(int)distance+"");
                }
            }finally {
                interact.put(source.getUniqueId(), false);
            }
        }

    }
}
