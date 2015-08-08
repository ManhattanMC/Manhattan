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

import org.bvsd.manhattanplugin.HostileZones.HostileZone;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

/**
 *
 * @author Donovan
 */
public class DBmanager {
    @Getter
    private static HashMap<String, PlayerSaveData> Playerdats = new HashMap<>();
    
    @Getter
    private static List<HostileZone> HZones = new ArrayList<>();
    
    private static File PSF = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "PlayerSaves");
    
    @Getter @Setter
    private static ArrayList<String> vanished = new ArrayList<>();
    /**
     * Saves all the playerdats through JSON
     */
    public static void SavePlayers(){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        for(String name:Playerdats.keySet()){
            boolean successful = true;
            File NLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat.new");
            File OLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat");
            try {
                ManhattanPlugin.getJSon().writeValue(NLoc, Playerdats.get(name));
            } catch (IOException ex) {
                successful = false;
            } finally {
                if (successful) {
                    if (OLoc.exists()) {
                        OLoc.delete();
                    }
                    NLoc.renameTo(OLoc);
                }
            }
        }
    }
    /**
     * Saves the given playerdat through JSON
     * @param name the players name
     */
    public static void SavePlayer(String name){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        boolean successful = true;
        File NLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat.new");
        File OLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat");
        try {
            ManhattanPlugin.getJSon().writeValue(NLoc, Playerdats.get(name));
        } catch (IOException ex) {
            successful = false;
        } finally {
            if (successful) {
                if (OLoc.exists()) {
                    OLoc.delete();
                }
                NLoc.renameTo(OLoc);
            }
        }
    }
    /**
     * Loads the player's dat through JSON
     * @param pName The players name
     */
    public static void LoadPlayers(String pName){
        
        if(new File(PSF.toString() + System.getProperty("file.separator") + Bukkit.getPlayer(pName).getUniqueId().toString() + ".playerdat").exists()){
            PlayerSaveData pd = null;
            try {
                pd = ManhattanPlugin.getJSon().readValue(new File(PSF.toString() + System.getProperty("file.separator") + Bukkit.getPlayer(pName).getUniqueId().toString() + ".playerdat"), PlayerSaveData.class);
                System.out.println(PlayerSaveData.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
                ex.printStackTrace();
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
            Playerdats.put(pName, pd);
        }else{
            Playerdats.put(pName, new PlayerSaveData(false));
        }
    }
    ///Done
    //with
    //player load
    /**
     * Saves all the Death Games data
     */
    public static void SaveDG(){
        try {
                ManhattanPlugin.setDg(ManhattanPlugin.getJSon().readValue(new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class));
        } catch (FileNotFoundException ex) {
                ManhattanPlugin.setDg(new DeathGame());
        } catch (IOException ex) {
            System.out.println("IO ERROR");
            ex.printStackTrace();
        }
    }
    /**
     * Loads all the Death Games data
     */
    public static void LoadDG(){
        if(!new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat").exists()){
            DeathGame.DGplayers = new ArrayList<>();
            DeathGame.DGtarget = "none";
            DeathGame.DGsign = ManhattanPlugin.getSurvivalWorld().getSpawnLocation();
            return;
        }else{
            try {
                DeathGame dg = ManhattanPlugin.getJSon().readValue(new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class);
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
                ex.printStackTrace();
            }
        }
    }
    /**
     * Saves all the Hostile Zones
     */
    public static void SaveHZones(){
        File base = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "HostileZoneDB");
        if(!base.exists()){
            base.mkdirs();
        }
        boolean successful;
        for(int i = 0; i<HZones.size(); i++){
            File start = new File(base + System.getProperty("file.separator") + "Zone-" + i + ".HZone.new");
            File finish = new File(base + System.getProperty("file.separator") + "Zone-" + i + ".HZone");
            successful = true;
            try {
                ManhattanPlugin.getJSon().writeValue(start, DBmanager.HZones.get(i));
            } catch (IOException ex) {
                successful = false;
            } finally {
                if (successful) {
                    if (finish.exists()) {
                        finish.delete();
                    }
                    start.renameTo(finish);
                }
            }
        }
    }
    /**
     * Loads all the Hostile Zones
     */
    public static void LoadHZones(){
        File base = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "HostileZoneDB");
        for(File f:base.listFiles()){
            try {
                HZones.add(ManhattanPlugin.getJSon().readValue(f, HostileZone.class));
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
                ex.printStackTrace();
            }
        }
    }
}
