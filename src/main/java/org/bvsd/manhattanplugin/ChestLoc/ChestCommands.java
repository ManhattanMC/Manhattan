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

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bvsd.manhattanplugin.DBmanager;

/**
 *
 * @author Donovan
 */
public class ChestCommands implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args){
        if(cs instanceof Player){
            Player p = (Player) cs;
            if(args.length < 2){
                return false;
            }
            if(args[0].equalsIgnoreCase("friend")){
                if(args[1].equalsIgnoreCase("add")){
                    if(args.length < 3){
                        DBmanager.getPlayerdats().get(p.getName()).getFriends().add(Bukkit.getOfflinePlayer(args[2]).getUniqueId()); //POF for rename
                    }else{
                        return false;
                    }
                }else if(args[1].equalsIgnoreCase("remove")){
                    if(args.length < 3){
                        DBmanager.getPlayerdats().get(p.getName()).getFriends().remove(Bukkit.getOfflinePlayer(args[2]).getUniqueId()); //POF for rename
                    }else{
                        return false;
                    }
                }else if(args[1].equalsIgnoreCase("list")){
                    p.sendMessage(Arrays.toString(DBmanager.getPlayerdats().get(p.getName()).getFriends().toArray()));
                }else if(args[1].equalsIgnoreCase("clear")){
                    DBmanager.getPlayerdats().get(p.getName()).getFriends().clear();
                }
            }else if(args[0].equalsIgnoreCase("req") || args[0].equalsIgnoreCase("request")){
                if(args[1].equalsIgnoreCase("b")){ // ban
                    ChestDBmanager.getChestDB().get(ChestDBmanager.getWaiting().get(p.getUniqueId()).getCl()).getBanned().add(ChestDBmanager.getWaiting().get(p.getUniqueId()).getReq());
                }else if(args[1].equalsIgnoreCase("a")){ // accept
                    ChestDBmanager.getChestDB().get(ChestDBmanager.getWaiting().get(p.getUniqueId()).getCl()).getApproved().add(ChestDBmanager.getWaiting().get(p.getUniqueId()).getReq());
                }
            }
        }
        return false;
    }
}
