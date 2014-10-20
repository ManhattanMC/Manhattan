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
            try {
                FileWriter fr = new FileWriter(PSF.toString() + System.getProperty("file.separator") + name + ".playerdat");
                try (PrintWriter writer = new PrintWriter(fr)) {
                    writer.println("Armor:");
                    for(ItemStack iss:Playerdats.get(name).armorInven){
                        writer.println(" - ArmorItemStack:");
                        writer.println("   - type: " + iss.getType().name());
                        writer.println("   - number: " + iss.getAmount());
                        writer.println("   - durab: " + iss.getDurability());
                        if(iss.hasItemMeta()){
                            ItemMeta ism = iss.getItemMeta();
                            if(ism.hasDisplayName()){
                                writer.println("   - name: " + ism.getDisplayName());
                            }
                            if(ism.hasLore()){
                                writer.println("     - lore:");
                                for(String l : ism.getLore()){
                                    writer.println("       - " + l);
                                }
                            }
                            if(ism.hasEnchants()){
                                writer.println("     - enchants:");
                                for(Enchantment e:ism.getEnchants().keySet()){
                                    writer.println("       - name: " + e.getName());
                                    writer.println("       - lvl: " + ism.getEnchantLevel(e));
                                }
                            }
                        }
                    }

                    writer.println("Main:");
                    for(ItemStack iss:Playerdats.get(name).mainInven){
                        writer.println(" - MainItemStack:");
                        if(iss != null){
                            writer.println("   - type: " + iss.getType().name());
                            writer.println("   - number: " + iss.getAmount());
                            writer.println("   - durab: " + iss.getDurability());
                            if(iss.hasItemMeta()){
                                ItemMeta ism = iss.getItemMeta();
                                if(ism.hasDisplayName()){
                                    writer.println("   - name: " + ism.getDisplayName());
                                }
                                if(ism.hasLore()){
                                    writer.println("     - lore:");
                                    for(String l : ism.getLore()){
                                        writer.println("       - " + l);
                                    }
                                }
                                if(ism.hasEnchants()){
                                    writer.println("     - enchants:");
                                    for(Enchantment e:ism.getEnchants().keySet()){
                                        writer.println("       - name: " + e.getName());
                                        writer.println("       - lvl: " + ism.getEnchantLevel(e));
                                    }
                                }
                            }
                        }else{
                            writer.println("   - nil");
                        }
                    }

                    writer.println("OldLoc:");
                    writer.println(" - Wname: " + Playerdats.get(name).lastLoc.getWorld().getName());
                    writer.println(" - X: " + Playerdats.get(name).lastLoc.getBlockX());
                    writer.println(" - Y: " + Playerdats.get(name).lastLoc.getBlockY());
                    writer.println(" - Z: " + Playerdats.get(name).lastLoc.getBlockZ());

                    writer.println("beenC: " + Playerdats.get(name).beenCreate);
                    writer.close();
                }
                fr.close();
            }catch (IOException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void SavePlayer(String name){
        if(!PSF.exists()){
            PSF.mkdirs();
        }
        try {
            FileWriter fr = new FileWriter(PSF.toString() + System.getProperty("file.separator") + name + ".playerdat");
            try (PrintWriter writer = new PrintWriter(fr)) {
                writer.println("Armor:");
                for(ItemStack iss:Playerdats.get(name).armorInven){
                    writer.println(" - ArmorItemStack:");
                    writer.println("   - type: " + iss.getType().name());
                    writer.println("   - number: " + iss.getAmount());
                    writer.println("   - durab: " + iss.getDurability());
                    if(iss.hasItemMeta()){
                        ItemMeta ism = iss.getItemMeta();
                        if(ism.hasDisplayName()){
                            writer.println("   - name: " + ism.getDisplayName());
                        }
                        if(ism.hasLore()){
                            writer.println("     - lore:");
                            for(String l : ism.getLore()){
                                writer.println("       - " + l);
                            }
                        }
                        if(ism.hasEnchants()){
                            writer.println("     - enchants:");
                            for(Enchantment e:ism.getEnchants().keySet()){
                                writer.println("       - name: " + e.getName());
                                writer.println("       - lvl: " + ism.getEnchantLevel(e));
                            }
                        }
                    }
                }

                writer.println("Main:");
                for(ItemStack iss:Playerdats.get(name).mainInven){
                    writer.println(" - MainItemStack:");
                    if(iss != null){
                        writer.println("   - type: " + iss.getType().name());
                        writer.println("   - number: " + iss.getAmount());
                        writer.println("   - durab: " + iss.getDurability());
                        if(iss.hasItemMeta()){
                            ItemMeta ism = iss.getItemMeta();
                            if(ism.hasDisplayName()){
                                writer.println("   - name: " + ism.getDisplayName());
                            }
                            if(ism.hasLore()){
                                writer.println("     - lore:");
                                for(String l : ism.getLore()){
                                    writer.println("       - " + l);
                                }
                            }
                            if(ism.hasEnchants()){
                                writer.println("     - enchants:");
                                for(Enchantment e:ism.getEnchants().keySet()){
                                    writer.println("       - name: " + e.getName());
                                    writer.println("       - lvl: " + ism.getEnchantLevel(e));
                                }
                            }
                        }
                    }else{
                        writer.println("   - nil");
                    }
                }

                writer.println("OldLoc:");
                writer.println(" - Wname: " + Playerdats.get(name).lastLoc.getWorld().getName());
                writer.println(" - X: " + Playerdats.get(name).lastLoc.getBlockX());
                writer.println(" - Y: " + Playerdats.get(name).lastLoc.getBlockY());
                writer.println(" - Z: " + Playerdats.get(name).lastLoc.getBlockZ());

                writer.println("beenC: " + Playerdats.get(name).beenCreate);
                writer.close();
            }
            fr.close();
        }catch (IOException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void LoadPlayers(String pName){
        if(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat").exists()){
            char wrld = ' ';
            boolean bc = true;
            Location oldLoc = null;
            ArrayList<ItemStack> isl1 = new ArrayList<>();
            ArrayList<ItemStack> isl2 = new ArrayList<>();
            try {
                RandomAccessFile s = new RandomAccessFile(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat"), "rw");
    //            Scanner s = new Scanner(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat"));
                while(s.getFilePointer() < s.length()){
                    String type = s.readLine();
                    if(type.equalsIgnoreCase("armor:")){
                        long lh = s.getFilePointer();
                        String h1 = s.readLine();
                        while(h1.equalsIgnoreCase(" - ArmorItemStack:")){
                            String h2 = s.readLine();
                            String name = "-1";
                            String durab = "-1";
                            String num = "";
                            String typ  = "";
                            boolean hasMeta = false;
                            boolean isNil = false;
                            List<String> lore;
                            lore = new ArrayList<>();
                            Map<Enchantment, Integer> ench;
                            ench = new HashMap<>();
                            if(h2.equalsIgnoreCase("   - nil")){
                                isNil = true;
                            }else{
                                typ = h2.replace("   - type: ","");
                                num = s.readLine().replace("   - number: ", "");
                                lh = s.getFilePointer();
                                String ha = s.readLine();
                                if(ha.contains("   - durab: ")){
                                    durab = ha.replace("   - durab: ", "");
                                    lh = s.getFilePointer();
                                }else{
                                    s.seek(lh);
                                }
                                lore = new ArrayList<>();
                                ench = new HashMap<>();
                                h2 = s.readLine();
                                while(h2.equalsIgnoreCase("   - name: ")||h2.equalsIgnoreCase("     - lore:")||h2.equalsIgnoreCase("     - enchants:")){

                                    if(h2.equalsIgnoreCase("   - name: ")){
                                        hasMeta = true;
                                        name = h2.replace("   - name: ", "");
                                    }else if(h2.equalsIgnoreCase("     - lore:")){
                                        hasMeta = true;
                                        String l1 = s.readLine();
                                        while(l1.contains("       - ")){
                                            lore.add(l1.replace("       - ", ""));
                                            lh = s.getFilePointer();
                                            l1 = s.readLine();
                                        }
                                        s.seek(lh);
                                    }else if(h2.equalsIgnoreCase("     - enchants:")){
                                        hasMeta = true;
                                        String enchtyp = s.readLine().replace("       - name: ", "");
                                        int enchlvl = Integer.parseInt(s.readLine().replace("       - lvl: ", ""));
                                        ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                        lh = s.getFilePointer();
                                        String h3 = s.readLine();
                                        while(h3.contains("       - name: ")){
                                            enchtyp = h3.replace("       - name: ", "");
                                            enchlvl = Integer.parseInt(s.readLine().replace("       - lvl: ", ""));
                                            ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                            lh = s.getFilePointer();
                                            h3 = s.readLine();
                                        }
                                        s.seek(lh);
                                    }
                                    lh = s.getFilePointer();
                                    h2 = s.readLine();
                                }
                                s.seek(lh);
                            }
                            if(!isNil){
                                isl1.add(DBmanager.mkIs(name, lore, Material.getMaterial(typ), Short.valueOf(durab), ench, Integer.parseInt(num), hasMeta));
                            }else{
                                isl1.add(null);
                            }
                           h1 = s.readLine();
                        }
                        s.seek(lh);
                    }else if(type.equalsIgnoreCase("main:")){
                        long lh = s.getFilePointer();
                        String h1 = s.readLine();
                        while(h1.equalsIgnoreCase(" - MainItemStack:")){

                            String h2 = s.readLine();
                            String name = "-1";
                            String durab = "-1";
                            String num = "";
                            String typ  = "";
                            boolean hasMeta = false;
                            boolean isNil = false;
                            List<String> lore;
                            lore = new ArrayList<>();
                            Map<Enchantment, Integer> ench;
                            ench = new HashMap<>();
                            if(h2.equalsIgnoreCase("   - nil")){
                                isNil = true;
                            }else{
                                typ = h2.replace("   - type: ","");
                                num = s.readLine().replace("   - number: ", "");
                                lh = s.getFilePointer();
                                String ha = s.readLine();
                                if(ha.contains("   - durab: ")){
                                    durab = ha.replace("   - durab: ", "");
                                    lh = s.getFilePointer();
                                }else{
                                    s.seek(lh);
                                }
                                lore = new ArrayList<>();
                                ench = new HashMap<>();
                                h2 = s.readLine();
                                while(h2.equalsIgnoreCase("   - name: ")||h2.equalsIgnoreCase("     - lore:")||h2.equalsIgnoreCase("     - enchants:")){
                                    if(h2.equalsIgnoreCase("   - name: ")){
                                        hasMeta = true;
                                        name = h2.replace("   - name: ", "");
                                    }else if(h2.equalsIgnoreCase("     - lore:")){
                                        hasMeta = true;
                                        String l1 = s.readLine();
                                        while(l1.contains("       - ")){
                                            lore.add(l1.replace("       - ", ""));
                                            lh = s.getFilePointer();
                                            l1 = s.readLine();
                                        }
                                        s.seek(lh);
                                    }else if(h2.equalsIgnoreCase("     - enchants:")){
                                        hasMeta = true;
                                        String enchtyp = s.readLine().replace("       - name: ", "");
                                        int enchlvl = Integer.parseInt(s.readLine().replace("       - lvl: ", ""));
                                        ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                        lh = s.getFilePointer();
                                        String h3 = s.readLine();
                                        while(h3.contains("       - name: ")){
                                            enchtyp = h3.replace("       - name: ", "");
                                            enchlvl = Integer.parseInt(s.readLine().replace("       - lvl: ", ""));
                                            ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                            lh = s.getFilePointer();
                                            h3 = s.readLine();
                                        }
                                        s.seek(lh);
                                    }
                                    lh = s.getFilePointer();
                                    h2 = s.readLine();
                                }
                                s.seek(lh);
                            }
                            if(!isNil){
                                isl2.add(DBmanager.mkIs(name, lore, Material.getMaterial(typ), Short.valueOf(durab), ench, Integer.parseInt(num), hasMeta));
                            }else{
                                isl2.add(null);
                            }
                           h1 = s.readLine();
                        }
                        s.seek(lh);
                    }else if(type.equalsIgnoreCase("oldloc:")){
                        oldLoc = new Location(Bukkit.getWorld(s.readLine().replace(" - Wname: ", "")), Integer.parseInt(s.readLine().replace(" - X: ", "")), Integer.parseInt(s.readLine().replace(" - Y: ", "")), Integer.parseInt(s.readLine().replace(" - Z: ", "")));
                    }else if(type.contains("beenC: ")){
                        bc = Boolean.valueOf(type.replace("beenC: ", ""));
                    }
                }
                s.close();
                s=null;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("IO ERROR");
            }
            ItemStack[] inven1 = new ItemStack[isl1.size()];
            isl1.toArray(inven1); // fill the array
            ItemStack[] inven2 = new ItemStack[isl2.size()];
            isl2.toArray(inven2); // fill the array
            Playerdats.put(pName, new PlayerSaveData(wrld, oldLoc, inven2, inven1, bc));
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
