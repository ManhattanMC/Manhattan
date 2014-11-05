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
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Donovan
 */
public class DBmanager {
    public static HashMap<String, PlayerSaveData> Playerdats = new HashMap<>();
    
    public static List<HostileZone> HZones = new ArrayList<>();
    
    public static File PSF = new File(mms.plugin.getDataFolder() + System.getProperty("file.separator") + "PlayerSaves");
    
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
                mms.JSon.writeValue(NLoc, Playerdats.get(name));
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
                mms.JSon.writeValue(NLoc, Playerdats.get(name));
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
                pd = mms.JSon.readValue(new File(PSF.toString() + System.getProperty("file.separator") + Bukkit.getPlayer(pName).getUniqueId().toString() + ".playerdat"), PlayerSaveData.class);
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
                mms.dg = mms.JSon.readValue(new File(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class);
        } catch (FileNotFoundException ex) {
                mms.dg = new DeathGame();
        } catch (IOException ex) {
            System.out.println("IO ERROR");
        }
    }
    public static void LoadDG(){
        if(!new File(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat").exists()){
            DeathGame.DGplayers = new ArrayList<>();
            DeathGame.DGtarget = "none";
            DeathGame.DGsign = mms.SurvivalWorld.getSpawnLocation();
            return;
        }else{
            try {
                DeathGame dg = mms.JSon.readValue(new File(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), DeathGame.class);
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
            }
        }
    }
    public static void SaveHZones(){
        FileConfiguration configF = mms.plugin.getConfig();
        configF.set("HZones.size", HZones.size());
        for(int i = 1; i<=HZones.size(); i++){
            HostileZone hz = HZones.get(i-1);
            configF.set("HZones.hz"+i+".rad", hz.radius);
            configF.set("HZones.hz"+i+".diff", hz.difficulty);
            //cent loc
            configF.set("HZones.hz"+i+".cent.Wname", hz.center.getWorld().getName());
            configF.set("HZones.hz"+i+".cent.X", hz.center.getBlockX());
            configF.set("HZones.hz"+i+".cent.Y", hz.center.getBlockY());
            configF.set("HZones.hz"+i+".cent.Z", hz.center.getBlockZ());
            //
        }
        mms.plugin.saveConfig();
        
    }
    public static void LoadHZones(){
        FileConfiguration configF = mms.plugin.getConfig();
        for(int i = 1; i<=configF.getInt("HZones.size"); i++){
            int rad = configF.getInt("HZones.hz"+i+".rad");
            int diff = configF.getInt("HZones.hz"+i+".diff");
            //cent loc
            Location loc = new Location(Bukkit.getWorld(configF.getString("HZones.hz"+i+".cent.Wname")), configF.getInt("HZones.hz"+i+".cent.X"), configF.getInt("HZones.hz"+i+".cent.Y"),  configF.getInt("HZones.hz"+i+".cent.Z"));
            //
            HZones.add(new HostileZone(loc, rad, diff));
        }
    }
}
