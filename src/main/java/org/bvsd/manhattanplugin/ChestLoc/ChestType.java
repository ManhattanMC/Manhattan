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

import java.util.ArrayList;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Donovan
 */
public class ChestType {
    @Getter @Setter
    private UUID owner;
    
    @Getter @Setter
    private int Sec;
    
    /*
     * Sec = 1: All players can open no prompt              (public)
     * Sec = 2: Not banned players will prompt for access   (restricted)
     * Sec = 3: No players can access no prompt             (private)
     */
    
    @Getter @Setter
    private ArrayList<UUID> banned = new ArrayList<>();
    
    @Getter @Setter
    private ArrayList<UUID> approved = new ArrayList<>();
    
    public ChestType(){}
    
    public ChestType(UUID owner){
        this.owner = owner;
    }
    
    public boolean canAccess(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(/*p.hasPermission("ManhattanPlugin.Mod") || */approved.contains(uuid) || Sec == 1 || uuid.equals(owner)){
            return true;
        }else if(banned.contains(uuid) || Sec == 3 || Sec == 2){
            return false;
        }
        return false;
    }
}
