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
package org.bvsd.manhattanplugin.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Donovan
 */
public class ChatHandle implements Listener{
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String prefix = "";
//        e.setMessage(e.getMessage().replace("swcd", ChatColor.GOLD + "[Looser]" + ChatColor.WHITE));
        if(p.isOp()){
            prefix = ChatColor.RED + "[Admin] " + prefix + ChatColor.WHITE;
        }else if(p.hasPermission("ManhattanPlugin.Mod")){
            prefix = ChatColor.BLUE + "[Mod] " + prefix + ChatColor.WHITE;
        }
        if(p.getName().equalsIgnoreCase("swcd")){
            prefix = ChatColor.GOLD + "[Looser] " + prefix + ChatColor.WHITE;
        }
        Bukkit.broadcastMessage(prefix + ChatColor.WHITE + "<" + e.getPlayer().getName() + "> " + e.getMessage());
        e.setCancelled(true);
    }
}
