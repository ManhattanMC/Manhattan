/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import java.util.Arrays;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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
import org.bvsd.manhattanplugin.HostileZones.HostileZone;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveData;

/**
 *
 * @author Donovan
 */
public class Commands implements CommandExecutor{ // need to make TabExecutor
    /**
     * Called when a player uses a mms command
     * 
     * @param cs The sender, console or player
     * @param cmd The command ie /<command>
     * @param label The string version of the command (don't use it)
     * @param args The argument provided with the command ie /<command> [args[0]] [args[1]] ...
     * @return if the command succeeded
     */
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
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647 , 2));
                player.getWorld().playEffect(player.getLocation(),Effect.MOBSPAWNER_FLAMES,4);
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
                if(DeathGame.DGplayers.contains(player.getName())){
                    player.sendMessage("You are already in the Death Games");
                }else{
                    DeathGame.DGplayers.add(player.getName());
                    player.sendMessage("You are now part of the deathgames");
                }
                return true;
            }else if(args[0].equalsIgnoreCase("leave")){
                if(!DeathGame.DGplayers.contains(player.getName())){
                    player.sendMessage("You are not in the Death Games");
                }else{
                    if(DeathGame.DGtarget.equalsIgnoreCase(player.getName())){
                        DeathGame.DGplayers.remove(player.getName());
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
                player.sendMessage(DeathGame.DGtarget);
                return true;
            }else if(args[0].equalsIgnoreCase("reroll")&&player.isOp()){
                Bukkit.broadcastMessage("Selecting new DeathGames Target...");
                if(Bukkit.getOfflinePlayer(DeathGame.DGtarget).isOnline()){
                    if(!Bukkit.getPlayer(DeathGame.DGtarget).getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
                        Bukkit.getPlayer(DeathGame.DGtarget).getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                    }else{
                        if(ManhattanPlugin.oldTargets.containsKey(DeathGame.DGtarget)){
                            ManhattanPlugin.oldTargets.put(DeathGame.DGtarget, ManhattanPlugin.oldTargets.get(DeathGame.DGtarget)+1);
                        }else{
                            ManhattanPlugin.oldTargets.put(DeathGame.DGtarget, 1);
                        }
                    }
                }else{
                    if(ManhattanPlugin.oldTargets.containsKey(DeathGame.DGtarget)){
                        ManhattanPlugin.oldTargets.put(DeathGame.DGtarget, ManhattanPlugin.oldTargets.get(DeathGame.DGtarget)+1);
                    }else{
                        ManhattanPlugin.oldTargets.put(DeathGame.DGtarget, 1);
                    }
                }
                Random gen = new Random();
                String ntar = DeathGame.DGplayers.get((int) (gen.nextInt(DeathGame.DGplayers.size())));
                while(ntar.equals(DeathGame.DGtarget)&&DeathGame.DGplayers.size()>1){
                    ntar = DeathGame.DGplayers.get((int) (gen.nextInt(DeathGame.DGplayers.size())));
                }
                DeathGame.DGtarget = ntar;
                DeathGame.DGsign.getBlock().setType(Material.SIGN_POST);
                Sign DS = (Sign)DeathGame.DGsign.getBlock().getState();
                DS.setLine(1, ChatColor.AQUA + "Death Games:");
                DS.setLine(2, ChatColor.BLUE + DeathGame.DGtarget);
                DS.update();
                
                return true;
            }
        }
        //WorldJump
        if(cmd.getName().equalsIgnoreCase("worldjump")){
            PlayerSaveData pd = DBmanager.Playerdats.get(player.getName());
            if(player.getLocation().getWorld().equals(ManhattanPlugin.CreativeWorld)){
                pd.CreativeSave.Imprint(player);
                if(!player.isOp()){
                    for(PotionEffect effect : player.getActivePotionEffects()){
                        player.removePotionEffect(effect.getType());
                    }
                }
                pd.SurvivalSave.SetImprint(player);
                return true;
            }else if(player.getLocation().getWorld().getName().contains(ManhattanPlugin.SurvivalWorld.getName())){
                pd.SurvivalSave.Imprint(player);
                pd.CreativeSave.SetImprint(player);
                return true;
            }
            DBmanager.SavePlayer(player.getName());
        }
        return false;
    }
    
}
