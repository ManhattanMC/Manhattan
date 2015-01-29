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

import java.util.HashMap;
import java.util.UUID;
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

/**
 *
 * @author Donovan
 */
public class ChestLock implements Listener{
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getMaterial().equals(Material.CHEST) && e.hasBlock()){
            ChestType ct = ChestDBmanager.getChestDB().get(new ChestLocation(e.getClickedBlock().getLocation()));
            if(!ct.canAccess(e.getPlayer().getUniqueId())){
                if(ct.getSec() == 2 && Bukkit.getOfflinePlayer(ct.getOwner()).isOnline()){
                    Bukkit.getOfflinePlayer(ct.getOwner()).getPlayer().sendMessage(ChatColor.YELLOW + "[Chest Util]" + 
                            ChatColor.AQUA + e.getPlayer().getName() + "Wants to access your restricted chest at " + "X:" + e.getClickedBlock().getX() + ", Y:" + e.getClickedBlock().getY() + ", Z: " + e.getClickedBlock().getZ());
                    ChestDBmanager.getWaiting().put(ct.getOwner(), new ChestDBmanager.Request(new ChestLocation(e.getClickedBlock().getLocation()), e.getPlayer().getUniqueId()));
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            ChestDBmanager.getChestDB().put(new ChestLocation(e.getBlock().getLocation()), new ChestType(e.getPlayer().getUniqueId()));
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock().getType().equals(Material.CHEST)){
            ChestDBmanager.getChestDB().remove(new ChestLocation(e.getBlock().getLocation()));
        }
    }
    
}
