/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveArmor;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveData;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveItemStack;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveLocation;

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
        //Vanish
        if(cmd.getName().equalsIgnoreCase("vanish")&&player.isOp()){
            if(!DBmanager.vanished.contains(player.getName())){
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000, 0, false));
                DBmanager.vanished.add(player.getName());
            }else{
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                DBmanager.vanished.remove(player.getName());
            }
            return true;
        }
        
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
                    player.sendMessage("You are now part of the deathgames");
                }
                return true;
            }else if(args[0].equalsIgnoreCase("leave")){
                if(!DBmanager.DGplayers.contains(player.getName())){
                    player.sendMessage("You are not in the Death Games");
                }else{
                    if(DBmanager.DGtarget.equalsIgnoreCase(player.getName())){
                        DBmanager.DGplayers.remove(player.getName());
                        player.sendMessage("You are no longer in the deathgames");
                    }else{
                        player.sendMessage("You are the target, you cannot leave untill midnight!");
                    }
                }
                return true;
            }else if(args[0].equalsIgnoreCase("equip")&&player.isOp()){
                ItemStack sword = new ItemStack(Material.IRON_SWORD);
                ItemMeta smeta = sword.getItemMeta();
                smeta.setDisplayName("DeathGames Sword");
                smeta.setLore(Arrays.asList(new String[] {"For use in the DeathGames only!"}));
                sword.setItemMeta(smeta);
                player.getInventory().addItem(sword);
                return true;
            }else if(args[0].equalsIgnoreCase("target")&&player.isOp()){
                player.sendMessage(DBmanager.DGtarget);
                return true;
            }else if(args[0].equalsIgnoreCase("reroll")&&player.isOp()){
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DBmanager.DGtarget).isOnline()){
                    if(!Bukkit.getPlayer(DBmanager.DGtarget).getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
                        Bukkit.getPlayer(DBmanager.DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                                                if(mms.oldTargets.containsKey(DBmanager.DGtarget)){
                            mms.oldTargets.put(DBmanager.DGtarget, mms.oldTargets.get(DBmanager.DGtarget)+1);
                        }else{
                            mms.oldTargets.put(DBmanager.DGtarget, 1);
                        }
                    }
                }else{
                                            if(mms.oldTargets.containsKey(DBmanager.DGtarget)){
                            mms.oldTargets.put(DBmanager.DGtarget, mms.oldTargets.get(DBmanager.DGtarget)+1);
                        }else{
                            mms.oldTargets.put(DBmanager.DGtarget, 1);
                        }
                }
                Random gen = new Random();
                String ntar = DBmanager.DGplayers.get((int) (gen.nextInt(DBmanager.DGplayers.size())));
                while(ntar.equals(DBmanager.DGtarget)&&DBmanager.DGplayers.size()>1){
                    ntar = DBmanager.DGplayers.get((int) (gen.nextInt(DBmanager.DGplayers.size())));
                }
                DBmanager.DGtarget = ntar;
                DBmanager.DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DBmanager.DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DBmanager.DGtarget);
                DS.update();
                
                return true;
            }
        }
        //WorldJump
        if(cmd.getName().equalsIgnoreCase("worldjump")){
            PlayerSaveData pd = DBmanager.Playerdats.get(player.getName());
            if(player.getLocation().getWorld().getName().equalsIgnoreCase("c-main")){
                pd.CreativeSave.Imprint(player);
                for(PotionEffect effect : player.getActivePotionEffects()){
                    player.removePotionEffect(effect.getType());
                }
                pd.SurvivalSave.SetImprint(player);
                return true;
            }
            if(player.getLocation().getWorld().getName().contains("S-")){
                pd.SurvivalSave.Imprint(player);
                pd.CreativeSave.SetImprint(player);
                return true;
            }
        }
        return false;
    }
    
}
