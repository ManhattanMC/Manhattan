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
import org.bukkit.World;
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
    public static World CreativeWorld;
    public static World SurvivalWorld;
    public static DeathGame dg;
    @Override
    public void onEnable(){
        this.getConfig().options().copyDefaults(true);
        JSon = new ObjectMapper();//.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
        if(this.getConfig().contains("worlds")){
            for(String s : this.getConfig().getStringList("worlds")){
                Bukkit.getServer().getWorlds().add(Bukkit.getServer().createWorld(new WorldCreator(s)));
            }
        }
        dg = new DeathGame();
        CreativeWorld = Bukkit.getWorld("C-Main");
        SurvivalWorld = Bukkit.getWorld("S-Main");
        getCommand("worldjump").setExecutor(new Commands());
        getCommand("deathgames").setExecutor(new Commands());
        getCommand("vanish").setExecutor(new Commands());
//        getCommand("HZone").setExecutor(new Commands());
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Listeners(), this);
        this.plugin = this;
        
        DBmanager.LoadDG();
        DBmanager.LoadHZones();
        
        DBmanager.vanished.add("none");
    }
    @Override
    public void onDisable(){
        DBmanager.SavePlayers();
        DBmanager.SaveDG();
//        DBmanager.SaveHZones();
    }
}
