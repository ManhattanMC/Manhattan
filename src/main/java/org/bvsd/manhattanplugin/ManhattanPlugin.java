/*
 * This file is part of ManhattanPlugin.
 * 
 * ManhattanPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ManhattanPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ManhattanPlugin.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 */

package org.bvsd.manhattanplugin;

import org.bvsd.manhattanplugin.Listeners.WorldJump;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bvsd.manhattanplugin.ChestLoc.ChestCommands;
import org.bvsd.manhattanplugin.ChestLoc.ChestLock;
import org.bvsd.manhattanplugin.Listeners.ChatHandle;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 *
 * @author Donovan
 */
public class ManhattanPlugin extends JavaPlugin{
    @Getter
    private static ManhattanPlugin plugin;
    @Getter @Setter
    private static HashMap<String, Integer> oldTargets = new HashMap<>();
    @Getter
    private static ObjectMapper JSon;
    @Getter
    private static World CreativeWorld;
    @Getter
    private static World SurvivalWorld;
    @Getter @Setter
    private static DeathGame dg;
//    @Getter
    /**
     * The function called by the server to enable this plugin, should not be called directly
     */
    @Override
    public void onEnable(){
        this.getConfig().options().copyDefaults(true);
        JSon = new ObjectMapper()
                .configure(SerializationConfig.Feature.INDENT_OUTPUT, true)
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if(this.getConfig().contains("worlds")){
            for(String s : this.getConfig().getStringList("worlds")){
                Bukkit.getServer().getWorlds().add(Bukkit.getServer().createWorld(new WorldCreator(s)));
            }
        }
//        dg = new DeathGame();
        CreativeWorld = Bukkit.getWorld("C-Main");
        SurvivalWorld = Bukkit.getWorld("S-Main");
        
        getCommand("worldjump").setExecutor(new Commands());
        getCommand("deathgames").setExecutor(new Commands());
        getCommand("vanish").setExecutor(new Commands());
        getCommand("chest").setExecutor(new ChestCommands());
//        getCommand("HZone").setExecutor(new Commands());
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new WorldJump(), this);
        pm.registerEvents(new ChestLock(), this);
        pm.registerEvents(new ChatHandle(), this);
        this.plugin = this;
        
        DBmanager.LoadDG();
//        DBmanager.LoadHZones();
        
        DBmanager.getVanished().add("none");
        
    }
    /**
     * The function called by the server to disable this plugin, should not be called directly
     */
    @Override
    public void onDisable(){
        DBmanager.SavePlayers();
        DBmanager.SaveDG();
//        DBmanager.SaveHZones();
    }
}
