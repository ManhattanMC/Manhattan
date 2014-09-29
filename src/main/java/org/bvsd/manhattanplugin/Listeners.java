/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Donovan
 */
public class Listeners implements Listener{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        DBmanager.LoadPlayers(p.getName());
        if(mms.oldTargets.containsKey(p.getName())&&!p.getLocation().getWorld().getName().equalsIgnoreCase("C-Main")){
            p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, mms.oldTargets.get(p.getName())));
            mms.oldTargets.remove(p.getName());
        }
    }
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        if(DBmanager.vanished.contains(p.getName())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void blockChestInterract(PlayerInteractEvent e) {
    Block t = e.getClickedBlock();
    Player p = e.getPlayer();
    if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(t.getType().equals(Material.ENDER_CHEST)&&p.getWorld().getName().equalsIgnoreCase("c-main")){
                p.sendMessage(ChatColor.RED + "You cannot use ender chests in creative world (nice try though)");
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            if(p.getWorld().getName().equalsIgnoreCase("C-Main")){
                p.setGameMode(GameMode.CREATIVE);
            }else{
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        final Entity damager = e.getDamager();
        final Entity target = e.getEntity();
        if (damager instanceof Player && target instanceof Player){
            Player att = (Player) damager;
            Player targ = (Player) target;
            if(!targ.getName().equalsIgnoreCase(DBmanager.DGtarget)&&!att.getName().equalsIgnoreCase(DBmanager.DGtarget)){
                att.sendMessage(ChatColor.RED + "You are in a no pvp zone".toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString());
                e.setCancelled(true);
            }else if(targ.getName().equalsIgnoreCase(DBmanager.DGtarget)){
                if(!DBmanager.DGplayers.contains(att.getName())){
                    att.sendMessage(ChatColor.RED + "You must be in the deathgames to do this, use /deathgames join");
                    e.setCancelled(true);
                }else if(att.getItemInHand().hasItemMeta()){
                    if(!att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                        att.sendMessage(ChatColor.RED + "You must use a DeathGames weapon!");
                        att.sendMessage(ChatColor.RED + "Use /deathgames equip");
                        e.setCancelled(true);
                    }
                }
            }
        }else if (damager instanceof Projectile && target instanceof Player){
            final LivingEntity shooter = ((Projectile)damager).getShooter();
            if (shooter instanceof Player){
                Player att = (Player) shooter;
                Player targ = (Player) target;
                if(!targ.getName().equalsIgnoreCase(DBmanager.DGtarget)&&!att.getName().equalsIgnoreCase(DBmanager.DGtarget)){
                        att.sendMessage(ChatColor.RED + "You are in a no pvp zone");
                        e.setCancelled(true);
                }else if(targ.getName().equalsIgnoreCase(DBmanager.DGtarget)){
                    if(!DBmanager.DGplayers.contains(att.getName())){
                        att.sendMessage(ChatColor.RED + "You must be in the deathgames to do this, use /deathgames join");
                        e.setCancelled(true);
                    }else if(att.getItemInHand().hasItemMeta()){
                        if(!att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                            att.sendMessage(ChatColor.RED + "You must use a DeathGames weapon!");
                            att.sendMessage(ChatColor.RED + "Use /deathgames equip");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }else if(damager instanceof Player){
            Player att = (Player) damager;
            if(att.getItemInHand().hasItemMeta()){
                if(att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                    att.getInventory().removeItem(att.getItemInHand());
                    att.sendMessage(ChatColor.RED + "Deathgames only");
                    e.setCancelled(true);
                }
            }
        }else if(damager instanceof Projectile){
            final LivingEntity shooter = ((Projectile)damager).getShooter();
            if(shooter instanceof Player){
                Player att = (Player) damager;
                if(att.getItemInHand().hasItemMeta()){
                    if(att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                        att.getInventory().removeItem(att.getItemInHand());
                        att.sendMessage(ChatColor.RED + "Deathgames only");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onWorldSave(WorldSaveEvent e){
        DBmanager.SavePlayers();
    }
    /*@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Block t = e.getClickedBlock();
        Player p = e.getPlayer();
        //no ender chest in creative
        if(t.getType().equals(Material.ENDER_CHEST)&&p.getLocation().getWorld().getName().equalsIgnoreCase("c-main")){
            p.sendMessage(ChatColor.RED + "You cannot use ender chests in creative world (nice try though)");
            e.setCancelled(true);
        }
    }*/
}
