/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import org.bukkit.Bukkit;

/**
 *
 * @author Donovan
 */
public class DBmanager {
    public static HashMap<String, PlayerSaveData> Playerdats = new HashMap<>();
    
    public static List<HostileZone> HZones = new ArrayList<>();
    
    public static File PSF = new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator") + "PlayerSaves");
    
    public static ArrayList<String> vanished = new ArrayList<>();

    public static void SavePlayers(){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        for(String name:Playerdats.keySet()){
            boolean successful = true;
            File NLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat.new");
            File OLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat");
            try {
                ManhattanPlugin.JSon.writeValue(NLoc, Playerdats.get(name));
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
    public static void SavePlayer(String name){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        boolean successful = true;
            File NLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat.new");
            File OLoc = new File(PSF + System.getProperty("file.separator") + Bukkit.getPlayer(name).getUniqueId().toString() + ".playerdat");
            try {
                ManhattanPlugin.JSon.writeValue(NLoc, Playerdats.get(name));
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
    public static void LoadPlayers(String pName){
        
        if(new File(PSF.toString() + System.getProperty("file.separator") + Bukkit.getPlayer(pName).getUniqueId().toString() + ".playerdat").exists()){
            PlayerSaveData pd = null;
            try {
                pd = ManhattanPlugin.JSon.readValue(new File(PSF.toString() + System.getProperty("file.separator") + Bukkit.getPlayer(pName).getUniqueId().toString() + ".playerdat"), PlayerSaveData.class);
                System.out.println(PlayerSaveData.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
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
    public static void SaveDG(){
        try {
                ManhattanPlugin.dg = ManhattanPlugin.JSon.readValue(new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class);
        } catch (FileNotFoundException ex) {
                ManhattanPlugin.dg = new DeathGame();
        } catch (IOException ex) {
            System.out.println("IO ERROR");
        }
    }
    public static void LoadDG(){
        if(!new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat").exists()){
            DeathGame.DGplayers = new ArrayList<>();
            DeathGame.DGtarget = "none";
            DeathGame.DGsign = ManhattanPlugin.SurvivalWorld.getSpawnLocation();
            return;
        }else{
            try {
                DeathGame dg = ManhattanPlugin.JSon.readValue(new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class);
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
            }
        }
    }
    public static void SaveHZones(){
        File base = new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator") + "HostileZoneDB");
        if(!base.exists()){
            base.mkdirs();
        }
        boolean successful;
        for(int i = 0; i<HZones.size(); i++){
            File start = new File(base + System.getProperty("file.separator") + "Zone-" + i + ".HZone.new");
            File finish = new File(base + System.getProperty("file.separator") + "Zone-" + i + ".HZone");
            successful = true;
            try {
                ManhattanPlugin.JSon.writeValue(start, DBmanager.HZones.get(i));
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
    public static void LoadHZones(){
        File base = new File(ManhattanPlugin.plugin.getDataFolder() + System.getProperty("file.separator") + "HostileZoneDB");
        for(File f:base.listFiles()){
            try {
                HZones.add(ManhattanPlugin.JSon.readValue(f, HostileZone.class));
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
            }
        }
    }
}
