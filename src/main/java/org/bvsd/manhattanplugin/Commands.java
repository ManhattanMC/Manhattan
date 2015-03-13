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

import java.util.Arrays;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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
            if(!DBmanager.getVanished().contains(player.getName())){
                player.sendMessage(ChatColor.DARK_PURPLE + "Vanished! Poof!");
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p != player)
                        p.hidePlayer(player);
                }
                player.getWorld().playEffect(player.getLocation(),Effect.MOBSPAWNER_FLAMES, 4, 4);
                DBmanager.getVanished().add(player.getName());
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " left the game");
            }else{
                player.sendMessage(ChatColor.DARK_PURPLE + "Unvanished! Poof!");
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p != player)
                        p.showPlayer(player);
                }
                player.getWorld().playEffect(player.getLocation(),Effect.MOBSPAWNER_FLAMES, 4, 4);
                DBmanager.getVanished().remove(player.getName());
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " joined the game");
            }
            return true;
        }
        
        //hostile Areas
        if(cmd.getName().equalsIgnoreCase("HZone")){
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("add")&&args.length>2){
                    DBmanager.getHZones().add(new HostileZone(player.getLocation(), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                }else if(args[0].equalsIgnoreCase("remove")){
                    for(HostileZone z:DBmanager.getHZones()){
                        if(z.inZone(player.getLocation())){
                            DBmanager.getHZones().remove(z);
                        }
                    }
                }
            }else if(player.hasPermission("group.mod")){
                player.sendMessage(String.valueOf(DBmanager.getHZones()));
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
                        if(ManhattanPlugin.getOldTargets().containsKey(DeathGame.DGtarget)){
                            ManhattanPlugin.getOldTargets().put(DeathGame.DGtarget, ManhattanPlugin.getOldTargets().get(DeathGame.DGtarget)+1);
                        }else{
                            ManhattanPlugin.getOldTargets().put(DeathGame.DGtarget, 1);
                        }
                    }
                }else{
                    if(ManhattanPlugin.getOldTargets().containsKey(DeathGame.DGtarget)){
                        ManhattanPlugin.getOldTargets().put(DeathGame.DGtarget, ManhattanPlugin.getOldTargets().get(DeathGame.DGtarget)+1);
                    }else{
                        ManhattanPlugin.getOldTargets().put(DeathGame.DGtarget, 1);
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
            PlayerSaveData pd = null;
            if(DBmanager.getPlayerdats().containsKey(player.getName())){
                pd = DBmanager.getPlayerdats().get(player.getName());
            }else{
                pd = new PlayerSaveData();
            }
            String WorldName = player.getLocation().getWorld().getName().replace("_nether", "").replace("_the_end", "");
            String DestName = "";
            if(args.length > 0){
                DestName = args[0];
            }else{
                if(WorldName.equalsIgnoreCase(ManhattanPlugin.getCreativeWorld().getName())){
                    DestName = ManhattanPlugin.getSurvivalWorld().getName();
                }else{
                    DestName = ManhattanPlugin.getCreativeWorld().getName();
                }
            }
            pd.getSaves().get(WorldName).Imprint(player);
            if(!player.isOp()){
                for(PotionEffect effect : player.getActivePotionEffects()){
                    player.removePotionEffect(effect.getType());
                }
            }
            pd.getSaves().get(DestName).SetImprint(player);
            DBmanager.SavePlayer(player.getName());
            return true;
        }
        return false;
    }
    
}
