/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
    public static HashMap<String, PlayerDat> Playerdats = new HashMap<>();
    
    public static List<String> DGplayers = new ArrayList<>();
    
    public static String DGtarget = "dallen1393";
    
    public static Location DGsign;
    
    public static List<HostileZone> HZones = new ArrayList<>();
    
    public static File PSF = new File(mms.plugin.getDataFolder() + System.getProperty("file.separator") + "PlayerSaves");

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

                    writer.println("World: " + Playerdats.get(name).world);

                    writer.println("beenC: " + Playerdats.get(name).beenCreate);
                }
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

                    writer.println("World: " + Playerdats.get(name).world);

                    writer.println("beenC: " + Playerdats.get(name).beenCreate);
                }
            }catch (IOException ex) {
                Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public static void LoadPlayers(String pName){
        char wrld = ' ';
        boolean bc = true;
        Location oldLoc = null;
        Set<ItemStack> isl1 = new HashSet<>();
        Set<ItemStack> isl2 = new HashSet<>();
        try {
            Scanner s = new Scanner(new File(PSF.toString() + System.getProperty("file.separator") + pName + ".playerdat"));
            if(s.hasNext()){
                String type = s.nextLine();
                if(type.equalsIgnoreCase("armor:")){
                    Locale lh = s.locale();
                    String h1 = s.nextLine();
                    while(h1.equalsIgnoreCase(" - ArmorItemStack:")){
                        String h2 = s.nextLine();
                        String name = "-1";
                        String durab = "";
                        String num = "";
                        String typ  = "";
                        Set<String> lore;
                        lore = new HashSet<>();
                        Map<Enchantment, Integer> ench;
                        ench = new HashMap<>();
                        if(h2.equalsIgnoreCase("   - nil")){
                        }else{
                            typ = h2.replace("   - type: ","");
                            num = s.nextLine().replace("   - number: ", "");
                            durab = s.nextLine().replace("   - durab: ", "");
                            lore = new HashSet<>();
                            ench = new HashMap<>();
                            h2 = s.nextLine();
                            while(!h2.equalsIgnoreCase(" - ArmorItemStack:")){
                                if(h2.equalsIgnoreCase("   - name: ")){
                                    name = h2.replace("   - name: ", "");
                                }else if(h2.equalsIgnoreCase("     - lore:")){
                                    String l1 = s.nextLine();
                                    while(l1.contains("       - ")){
                                        lore.add(l1.replace("       - ", ""));
                                        lh = s.locale();
                                        l1 = s.nextLine();
                                    }
                                    s.useLocale(lh);
                                }else if(h2.equalsIgnoreCase("     - enchants:")){
                                    String enchtyp = s.nextLine().replace("       - name: ", "");
                                    int enchlvl = Integer.parseInt(s.nextLine().replace("       - lvl: ", ""));
                                    ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                    lh = s.locale();
                                    String h3 = s.nextLine();
                                    while(h3.contains("       - name: ")){
                                        enchtyp = s.nextLine().replace("       - name: ", "");
                                        enchlvl = Integer.parseInt(s.nextLine().replace("       - lvl: ", ""));
                                        ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                        lh = s.locale();
                                        h3 = s.nextLine();
                                    }
                                    s.useLocale(lh);
                                }
                                lh = s.locale();
                                h2 = s.nextLine();
                            }
                            
                            s.useLocale(lh);
                        }
                        isl1.add(mkIs(name, lore, Material.getMaterial(typ), Short.valueOf(durab), ench, Integer.parseInt(num)));
                    }
                    s.useLocale(lh);
                }else if(type.equalsIgnoreCase("main:")){
                    Locale lh = s.locale();
                    String h1 = s.nextLine();
                    while(h1.equalsIgnoreCase(" - ArmorItemStack:")){
                        String h2 = s.nextLine();
                        String name = "-1";
                        String durab = "";
                        String num = "";
                        String typ  = "";
                        Set<String> lore;
                        lore = new HashSet<>();
                        Map<Enchantment, Integer> ench;
                        ench = new HashMap<>();
                        if(h2.equalsIgnoreCase("   - nil")){
                        }else{
                            typ = h2.replace("   - type: ","");
                            num = s.nextLine().replace("   - number: ", "");
                            durab = s.nextLine().replace("   - durab: ", "");
                            lore = new HashSet<>();
                            ench = new HashMap<>();
                            h2 = s.nextLine();
                            while(!h2.equalsIgnoreCase(" - ArmorItemStack:")){
                                if(h2.equalsIgnoreCase("   - name: ")){
                                    name = h2.replace("   - name: ", "");
                                }else if(h2.equalsIgnoreCase("     - lore:")){
                                    String l1 = s.nextLine();
                                    while(l1.contains("       - ")){
                                        lore.add(l1.replace("       - ", ""));
                                        lh = s.locale();
                                        l1 = s.nextLine();
                                    }
                                    s.useLocale(lh);
                                }else if(h2.equalsIgnoreCase("     - enchants:")){
                                    String enchtyp = s.nextLine().replace("       - name: ", "");
                                    int enchlvl = Integer.parseInt(s.nextLine().replace("       - lvl: ", ""));
                                    ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                    lh = s.locale();
                                    String h3 = s.nextLine();
                                    while(h3.contains("       - name: ")){
                                        enchtyp = s.nextLine().replace("       - name: ", "");
                                        enchlvl = Integer.parseInt(s.nextLine().replace("       - lvl: ", ""));
                                        ench.put(Enchantment.getByName(enchtyp), enchlvl);
                                        lh = s.locale();
                                        h3 = s.nextLine();
                                    }
                                    s.useLocale(lh);
                                }
                                lh = s.locale();
                                h2 = s.nextLine();
                            }
                            
                            s.useLocale(lh);
                        }
                        isl1.add(mkIs(name, lore, Material.getMaterial(typ), Short.valueOf(durab), ench, Integer.parseInt(num)));
                    }
                    s.useLocale(lh);
                }else if(type.equalsIgnoreCase("oldloc:")){
                    /////NEEDED
                }else if(type.contains("World: ")){
                    wrld = type.replace("World: ", "").charAt(0);
                }else if(type.contains("beenC: ")){
                    bc = Boolean.valueOf(type.replace("beenC: ", ""));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemStack[] inven1 = new ItemStack[isl1.size()];
        isl1.toArray(inven1); // fill the array
        ItemStack[] inven2 = new ItemStack[isl2.size()];
        isl2.toArray(inven2); // fill the array
        Playerdats.put(pName, new PlayerDat(wrld, oldLoc, inven1, inven2, bc));
    }
    public static void SaveDG(){
        FileConfiguration configF = mms.plugin.getConfig();
        configF.set("DeathGames.players", DGplayers);
        configF.set("DeathGames.target", DGtarget);
        //sign loc
        configF.set("DeathGames.sign.Wname", DGsign.getWorld().getName());
        configF.set("DeathGames.sign.X", DGsign.getBlockX());
        configF.set("DeathGames.sign.Y", DGsign.getBlockY());
        configF.set("DeathGames.sign.Z", DGsign.getBlockZ());
        //
        mms.plugin.saveConfig();
    }
    public static void LoadDG(){
        FileConfiguration configF = mms.plugin.getConfig();
        DGplayers = configF.getStringList("DeathGames.players");
        DGtarget = configF.getString("DeathGames.target");
        //sign loc
        DGsign = new Location(Bukkit.getWorld(configF.getString("DeathGames.sign.Wname")), configF.getInt("DeathGames.sign.X"), configF.getInt("DeathGames.sign.Y"), configF.getInt("DeathGames.sign.Z"));
        //
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
    private static ItemStack mkIs(String name, Set<String> lore, Material type, short durab, Map<Enchantment, Integer> ench, int stack){
        return null;
    }
}
