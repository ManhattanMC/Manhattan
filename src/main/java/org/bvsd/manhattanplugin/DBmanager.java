/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Donovan
 */
public class DBmanager {
    public static HashMap<String, PlayerSaveData> Playerdats = new HashMap<>();
    
    public static List<String> DGplayers = new ArrayList<>();
    
    public static String DGtarget = "dallen1393";
    
    public static Location DGsign;
    
    public static List<HostileZone> HZones = new ArrayList<>();
    
    public static File PSF = new File(mms.plugin.getDataFolder() + System.getProperty("file.separator") + "PlayerSaves");
    
    public static ArrayList<String> vanished = new ArrayList<>();

    public static void SavePlayers(){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        for(String name:Playerdats.keySet()){
            boolean successful = true;
            File NLoc = new File(PSF + System.getProperty("file.separator") + name + ".playerdat.new");
            File OLoc = new File(PSF + System.getProperty("file.separator") + name + ".playerdat");
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
            File NLoc = new File(PSF + System.getProperty("file.separator") + name + ".playerdat.new");
            File OLoc = new File(PSF + System.getProperty("file.separator") + name + ".playerdat");
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
        if(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat").exists()){
            PlayerSaveData pd = null;
            try {
                pd = mms.JSon.readValue(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat"), PlayerSaveData.class);
                System.out.println(PlayerSaveData.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
            Playerdats.put(pName, pd);
        }else{
            Playerdats.put(pName, new PlayerSaveData('s', new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)}));
        }
    }
    ///Done
    //with
    //player load
    public static void SaveDG(){
        try {
            FileWriter fr = new FileWriter(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat");
            try (PrintWriter writer = new PrintWriter(fr)) {
                writer.println("Players:");
                for(String s:DGplayers){
                    writer.println(" - " + s);
                }
                writer.println("target: " + DGtarget);
                //loc
                writer.println("SignLoc:");
                writer.println(" - Wname: " + DGsign.getWorld().getName());
                writer.println(" - X: " + DGsign.getBlockX());
                writer.println(" - Y: " + DGsign.getBlockY());
                writer.println(" - Z: " + DGsign.getBlockZ());
                //DG old targets save:
                writer.close();
            }
            fr.close();
        } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("IO ERROR");
        }
    }
    public static void LoadDG(){
        if(!new File(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat").exists()){
            DGplayers = new ArrayList<>();
            DGtarget = "none";
            DGsign = Bukkit.getWorld("S-Main").getSpawnLocation();
            return;
        }else{
            try {
                RandomAccessFile s = new RandomAccessFile(new File(mms.plugin.getDataFolder() + System.getProperty("file.separator")+ "DeathGames.DGdat"), "rw");
                String line = s.readLine();
                line = s.readLine();
                while(!line.contains("target: ")){
                    DGplayers.add(line.replace(" - ", ""));
                    line = s.readLine();
                }
                DGtarget = line.replace("target: ", "");
                line = s.readLine();
                DGsign =new Location(Bukkit.getWorld(s.readLine().replace(" - Wname: ", "")), Integer.parseInt(s.readLine().replace(" - X: ", "")), Integer.parseInt(s.readLine().replace(" - Y: ", "")), Integer.parseInt(s.readLine().replace(" - Z: ", "")));
                //DG old targets load:
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
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
    private static ItemStack mkIs(String name, List<String> lore, Material type, short durab, Map<Enchantment, Integer> ench, int stack, boolean hasMeta){
        ItemStack rtn = new ItemStack(type);
        if(durab != -1){
            rtn.setDurability(durab);
        }
        rtn.setAmount(stack);
        if(hasMeta){
            ItemMeta ism = rtn.getItemMeta();
            for(Enchantment e : ench.keySet()){
                ism.addEnchant(e, ench.get(e), true);
            }
            if(!name.equalsIgnoreCase("-1")){
                ism.setDisplayName(name);
            }
            ism.setLore(lore);
            rtn.setItemMeta(ism);
        }
        return rtn;
    }
}
