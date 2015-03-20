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
package org.bvsd.manhattanplugin.ChestLoc;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bvsd.manhattanplugin.ManhattanPlugin;

/**
 *
 * @author Donovan
 */
public class ChestLock implements Listener{
    
    @Getter
    private static HashMap<UUID, Integer> wands = new HashMap<>();
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (ChestDBmanager.getChestDB().containsKey(new ChestLocation(e.getClickedBlock().getLocation()).toString())){
            ChestType ct = ChestDBmanager.getChestDB().get(new ChestLocation(e.getClickedBlock().getLocation()).toString());
            System.out.println(ct.canAccess(e.getPlayer().getUniqueId()));
            if(ct.getOwner().equals(e.getPlayer().getUniqueId()) && wands.containsKey(e.getPlayer().getUniqueId())){
                ct.setSec(wands.get(e.getPlayer().getUniqueId()));
                e.getPlayer().sendMessage(ChatColor.BLUE + "Chest set!");
                e.setCancelled(true);
            }else if(!ct.canAccess(e.getPlayer().getUniqueId())){
                if(ct.getSec() == 2 && Bukkit.getOfflinePlayer(ct.getOwner()).isOnline()){
                    Bukkit.getOfflinePlayer(ct.getOwner()).getPlayer().sendMessage(ChatColor.YELLOW + "[Chest Util] " + 
                            ChatColor.AQUA + e.getPlayer().getName() + " Wants to access your restricted chest at " + "X:" + e.getClickedBlock().getX() + ", Y:" + e.getClickedBlock().getY() + ", Z: " + e.getClickedBlock().getZ());
                    ChestDBmanager.getWaiting().put(ct.getOwner(), new ChestDBmanager.Request(new ChestLocation(e.getClickedBlock().getLocation()), e.getPlayer().getUniqueId()));
                }
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.YELLOW + "[Chest Util] " + 
                            ChatColor.AQUA + "You do not have access to this chest");
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            System.out.println(new ChestLocation(e.getBlock().getLocation()));
            ChestDBmanager.getChestDB().put(new ChestLocation(e.getBlock().getLocation()).toString(), new ChestType(e.getPlayer().getUniqueId()));
//            try {
//                System.out.println(ManhattanPlugin.getJSon().writeValueAsString(ChestDBmanager.getChestDB()));
//            } catch (IOException ex) {
//                Logger.getLogger(ChestLock.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            ChestType ct = ChestDBmanager.getChestDB().get(new ChestLocation(e.getBlock().getLocation()).toString());
            System.out.println(ct);
            if(ct.getOwner().equals(e.getPlayer().getUniqueId()) && wands.containsKey(e.getPlayer().getUniqueId())){
                ct.setSec(wands.get(e.getPlayer().getUniqueId()));
                e.getPlayer().sendMessage(ChatColor.BLUE + "Chest set!");
                e.setCancelled(true);
            }else if(ct.getOwner().equals(e.getPlayer().getUniqueId())){
                ChestDBmanager.getChestDB().remove(new ChestLocation(e.getBlock().getLocation()).toString());
            }else{
                e.setCancelled(true);
            }
        }
    }
    
}
