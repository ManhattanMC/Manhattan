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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveItemStack;

/**
 *
 * @author Donovan
 */
public class DeathGame {
    @Getter @Setter
    public static List<String> DGplayers = new ArrayList<>();
    @Getter @Setter
    public static String DGtarget = "dallen1393";
    @Getter @Setter
    public static Location DGsign;
    @Getter @Setter
    public static PlayerSaveItemStack[] Gear;
    
    public DeathGame(ItemStack[] Gear){ // used in code
        Calendar c = Calendar.getInstance();c.add(Calendar.DAY_OF_MONTH, 1);c.set(Calendar.HOUR_OF_DAY, 0);c.set(Calendar.MINUTE, 0);c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
        long seconds = (c.getTimeInMillis()-System.currentTimeMillis())/1000;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ManhattanPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DGtarget).isOnline()){
                    if(!Bukkit.getPlayer(DGtarget).getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
                        Bukkit.getPlayer(DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                        if(ManhattanPlugin.getOldTargets().containsKey(DGtarget)){
                            ManhattanPlugin.getOldTargets().put(DGtarget, ManhattanPlugin.getOldTargets().get(DGtarget)+1);
                        }else{
                            ManhattanPlugin.getOldTargets().put(DGtarget, 1);
                        }
                    }
                }else{
                    if(ManhattanPlugin.getOldTargets().containsKey(DGtarget)){
                        ManhattanPlugin.getOldTargets().put(DGtarget, ManhattanPlugin.getOldTargets().get(DGtarget)+1);
                    }else{
                        ManhattanPlugin.getOldTargets().put(DGtarget, 1);
                    }
                }
                Random gen = new Random();
                String ntar = DGplayers.get((int) (gen.nextInt(DGplayers.size())));
                while(ntar.equals(DGtarget)&&DGplayers.size()>1){
                    ntar = DGplayers.get((int) (gen.nextInt(DGplayers.size())));
                }
                DGtarget = ntar;
                DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DGtarget);
                DS.update();
            }
        }, seconds*20, 24 * (60 * 60 * 20));
    }
    public DeathGame(){ // used by JSON
        Calendar c = Calendar.getInstance();c.add(Calendar.DAY_OF_MONTH, 1);c.set(Calendar.HOUR_OF_DAY, 0);c.set(Calendar.MINUTE, 0);c.set(Calendar.SECOND, 0);c.set(Calendar.MILLISECOND, 0);
        long seconds = (c.getTimeInMillis()-System.currentTimeMillis())/1000;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ManhattanPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DGtarget).isOnline()){
                    if(!Bukkit.getPlayer(DGtarget).getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
                        Bukkit.getPlayer(DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                        if(ManhattanPlugin.getOldTargets().containsKey(DGtarget)){
                            ManhattanPlugin.getOldTargets().put(DGtarget, ManhattanPlugin.getOldTargets().get(DGtarget)+1);
                        }else{
                            ManhattanPlugin.getOldTargets().put(DGtarget, 1);
                        }
                    }
                }else{
                                            if(ManhattanPlugin.getOldTargets().containsKey(DGtarget)){
                            ManhattanPlugin.getOldTargets().put(DGtarget, ManhattanPlugin.getOldTargets().get(DGtarget)+1);
                        }else{
                            ManhattanPlugin.getOldTargets().put(DGtarget, 1);
                        }
                }
                Random gen = new Random();
                String ntar = DGplayers.get((int) (gen.nextInt(DGplayers.size())));
                while(ntar.equals(DGtarget)&&DGplayers.size()>1){
                    ntar = DGplayers.get((int) (gen.nextInt(DGplayers.size())));
                }
                DGtarget = ntar;
                DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DGtarget);
                DS.update();
            }
        }, seconds*20, 24 * (60 * 60 * 20));
    }
    public static void NewTarget(){ // wip
        
    }
}
