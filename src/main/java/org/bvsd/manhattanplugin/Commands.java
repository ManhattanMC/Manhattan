/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Donovan
 */
public class Commands implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player){}else{
            cs.sendMessage("lol nope");
            return true;
        }
        Player player = (Player) cs;
        //hostile Areas
        if(cmd.getName().equalsIgnoreCase("HZone")){
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("add")&&args.length>2){
                    DBmanager.HZones.add(new HostileZone(player.getLocation(), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                }else if(args[0].equalsIgnoreCase("remove")){
                    for(HostileZone z:DBmanager.HZones){
                        if(z.inZone(player.getLocation())){
                            DBmanager.HZones.remove(z);
                        }
                    }
                }
            }else if(player.hasPermission("group.mod")){
                player.sendMessage(String.valueOf(DBmanager.HZones));
            }
        }
        //
        //DG
        if(cmd.getName().equalsIgnoreCase("deathgames") && args.length>0){
            if(args[0].equalsIgnoreCase("join")){
                if(DBmanager.DGplayers.contains(player.getName())){
                    player.sendMessage("You are already in the Death Games");
                }else{
                    DBmanager.DGplayers.add(player.getName());
                }
                return true;
            }else if(args[0].equalsIgnoreCase("leave")){
                if(!DBmanager.DGplayers.contains(player.getName())){
                    player.sendMessage("You are not in the Death Games");
                }else{
                    if(DBmanager.DGtarget.equalsIgnoreCase(player.getName())){
                        DBmanager.DGplayers.remove(player.getName());
                    }else{
                        player.sendMessage("You are the target, you cannot leave untill midnight!");
                    }
                }
                return true;
            }else if(args[0].equalsIgnoreCase("target")){
                player.sendMessage(DBmanager.DGtarget);
                return true;
            }else if(args[0].equalsIgnoreCase("reroll")&&player.isOp()){
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DBmanager.DGtarget).isOnline()){
                    if(DBmanager.Playerdats.get(DBmanager.DGtarget).world != 'c'){
                        Bukkit.getPlayer(DBmanager.DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                        mms.oldTargets.add(DBmanager.DGtarget);
                    }
                }else{
                    mms.oldTargets.add(DBmanager.DGtarget);
                }
                DBmanager.DGtarget = DBmanager.DGplayers.get((int) (Math.random() * (DBmanager.DGplayers.size()-1)));
                DBmanager.DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DBmanager.DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DBmanager.DGtarget);
                DS.update();
                return true;
            }
        }
        //WorldJump
        if(cmd.getName().equalsIgnoreCase("worldjump") && args.length > 0){
            PlayerDat pd = DBmanager.Playerdats.get(player.getName());
            player.sendMessage(String.valueOf(DBmanager.Playerdats));
            if(args[0].equalsIgnoreCase("survival") && pd.world != 's'){
                Location oldloc = player.getLocation();
                ItemStack[] oldInven1 = player.getInventory().getContents();
                ItemStack[] oldInven2 = player.getInventory().getArmorContents();
                player.teleport(pd.lastLoc);
                player.getInventory().clear();
                player.getInventory().setContents(pd.mainInven);
                player.getInventory().setArmorContents(pd.armorInven);
                player.setGameMode(GameMode.SURVIVAL);
                DBmanager.Playerdats.put(player.getName(), new PlayerDat('s', oldloc, oldInven1, oldInven2, true));
                if(mms.oldTargets.contains(player.getName())){
                    player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    mms.oldTargets.remove(player.getName());
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("creative") && pd.world != 'c'){
                Location oldloc = player.getLocation();
                ItemStack[] oldInven1 = player.getInventory().getContents();
                ItemStack[] oldInven2 = player.getInventory().getArmorContents();
                if(pd.beenCreate){
                    player.teleport(pd.lastLoc);
                }else{
                    player.teleport(Bukkit.getWorld("C-Main").getSpawnLocation());
                    pd.beenCreate = true;
                }
                player.getInventory().clear();
                player.getInventory().setContents(pd.mainInven);
                player.getInventory().setArmorContents(pd.armorInven);
                player.setGameMode(GameMode.CREATIVE);
                pd.mainInven = oldInven1;
                pd.armorInven = oldInven2;
                pd.lastLoc = oldloc;
                pd.world = 'c';
                DBmanager.Playerdats.put(player.getName(), new PlayerDat('c', oldloc, oldInven1, oldInven2, true));
                if(player.hasPermission("group.mod")||player.hasPermission("group.admin")){
                    player.addAttachment(mms.plugin, "voxelsniper.*", true);
                }
                return true;
            }
            DBmanager.SavePlayer(player.getName());
        }
        return false;
    }
    
}
