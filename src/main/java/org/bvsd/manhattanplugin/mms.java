/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 *
 * @author Donovan
 */
public class mms extends JavaPlugin{
    public static mms plugin;
    public static HashMap<String, Integer> oldTargets = new HashMap<>();
    public static ObjectMapper JSon;
    @Override
    public void onEnable(){
        this.getConfig().options().copyDefaults(true);
        JSon = new ObjectMapper();//.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
        if(this.getConfig().contains("worlds")){
            for(String s : this.getConfig().getStringList("worlds")){
                Bukkit.getServer().getWorlds().add(Bukkit.getServer().createWorld(new WorldCreator(s)));
            }
        }
        getCommand("worldjump").setExecutor(new Commands());
        getCommand("deathgames").setExecutor(new Commands());
        getCommand("vanish").setExecutor(new Commands());
//        getCommand("HZone").setExecutor(new Commands());
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Listeners(), this);
        this.plugin = this;
        Calendar c = Calendar.getInstance();c.add(Calendar.DAY_OF_MONTH, 1);c.set(Calendar.HOUR_OF_DAY, 0);c.set(Calendar.MINUTE, 0);c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
        long seconds = (c.getTimeInMillis()-System.currentTimeMillis())/1000;
        DBmanager.LoadDG();
        DBmanager.LoadHZones();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DBmanager.DGtarget).isOnline()){
                    if(!Bukkit.getPlayer(DBmanager.DGtarget).getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
                        Bukkit.getPlayer(DBmanager.DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                        if(mms.oldTargets.containsKey(DBmanager.DGtarget)){
                            mms.oldTargets.put(DBmanager.DGtarget, mms.oldTargets.get(DBmanager.DGtarget)+1);
                        }else{
                            mms.oldTargets.put(DBmanager.DGtarget, 1);
                        }
                    }
                }else{
                                            if(mms.oldTargets.containsKey(DBmanager.DGtarget)){
                            mms.oldTargets.put(DBmanager.DGtarget, mms.oldTargets.get(DBmanager.DGtarget)+1);
                        }else{
                            mms.oldTargets.put(DBmanager.DGtarget, 1);
                        }
                }
                Random gen = new Random();
                String ntar = DBmanager.DGplayers.get((int) (gen.nextInt(DBmanager.DGplayers.size())));
                while(ntar.equals(DBmanager.DGtarget)&&DBmanager.DGplayers.size()>1){
                    ntar = DBmanager.DGplayers.get((int) (gen.nextInt(DBmanager.DGplayers.size())));
                }
                DBmanager.DGtarget = ntar;
                DBmanager.DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DBmanager.DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DBmanager.DGtarget);
                DS.update();
            }
        }, seconds*20, 24 * (60 * 60 * 20));
        DBmanager.vanished.add("none");
    }
    @Override
    public void onDisable(){
        DBmanager.SavePlayers();
        DBmanager.SaveDG();
//        DBmanager.SaveHZones();
    }
}
