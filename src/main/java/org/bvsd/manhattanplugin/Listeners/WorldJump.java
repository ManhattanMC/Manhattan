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
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bvsd.manhattanplugin.DBmanager;
import org.bvsd.manhattanplugin.DeathGame;
import org.bvsd.manhattanplugin.ManhattanPlugin;

/**
 *
 * @author Donovan
 */
public class WorldJump implements Listener{
    /**
     * Called when a player joins the server (duh)
     * used in this case to load their playerdat 
     * so it can be used for worldjump 
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        DBmanager.LoadPlayers(p.getName());
        if(ManhattanPlugin.getOldTargets().containsKey(p.getName())&&!p.getLocation().getWorld().equals(ManhattanPlugin.getCreativeWorld())){
            p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, ManhattanPlugin.getOldTargets().get(p.getName())));
            ManhattanPlugin.getOldTargets().remove(p.getName());
        }
    }
    /**
     * Called when a player picks up an item (duh)
     * used to make vanished players unable to
     * pick up items (which would ruin their invis)
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        if(DBmanager.getVanished().contains(p.getName())){
            e.setCancelled(true);
        }
    }
    /**
     * Called when a player tries to open a chest (duh)
     * This is used to stop players from moving item
     * from creative to survival via ender chests
     */
    @EventHandler
    public void blockChestInterract(PlayerInteractEvent e) {
    Block t = e.getClickedBlock();
    Player p = e.getPlayer();
    if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(t.getType().equals(Material.ENDER_CHEST)&&p.getWorld().equals(ManhattanPlugin.getCreativeWorld())){
                p.sendMessage(ChatColor.RED + "You cannot use ender chests in creative world (nice try though)");
                e.setCancelled(true);
            }
        }
    }
    /**
     * Called when an entity, (item, mod, ect.), changes worlds via a portal (duh)
     * stops that item from creative to survival trick through portals that max
     * showed me
     */
    @EventHandler
    public void onEntityPortal(EntityPortalEvent e){
        if(e.getFrom().getWorld().equals(ManhattanPlugin.getCreativeWorld())){
            e.getEntity().remove();
        }
    }
    /**
     * Called when a player changes worlds (duh)
     * makes sure that the player is in the right
     * game mode after worldjump
     */
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()){
            if(p.getWorld().equals(ManhattanPlugin.getCreativeWorld())){
                p.setGameMode(GameMode.CREATIVE);
            }else{
                if(e.getFrom().equals(ManhattanPlugin.getCreativeWorld())){
                    p.getInventory().clear();
                    p.setExp(0);
                    p.setLevel(0);
                    p.getInventory().setArmorContents(new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
                    if(ManhattanPlugin.getOldTargets().containsKey(p.getName())){
                        p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, ManhattanPlugin.getOldTargets().get(p.getName())));
                        ManhattanPlugin.getOldTargets().remove(p.getName());
                    }
                }
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    /**
     * Called when an entity takes damage (duh)
     * works with the DeathGames to stop pvp
     */
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        final Entity damager = e.getDamager();
        final Entity target = e.getEntity();
        if (damager instanceof Player && target instanceof Player){
            Player att = (Player) damager;
            Player targ = (Player) target;
            if(!targ.getName().equalsIgnoreCase(DeathGame.DGtarget)&&!att.getName().equalsIgnoreCase(DeathGame.DGtarget)){
                att.sendMessage(ChatColor.RED + "You are in a no pvp zone");//.toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString().toString());
                e.setCancelled(true);
            }else if(targ.getName().equalsIgnoreCase(DeathGame.DGtarget)){
                if(!DeathGame.DGplayers.contains(att.getName())){
                    att.sendMessage(ChatColor.RED + "You must be in the deathgames to do this, use /deathgames join");
                    e.setCancelled(true);
                }else if(att.getItemInHand().hasItemMeta()){
                    if(att.getItemInHand().getItemMeta().hasLore()){
                        if(!att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                            att.sendMessage(ChatColor.RED + "You must use a DeathGames weapon!");
                            att.sendMessage(ChatColor.RED + "Use /deathgames equip");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }else if (damager instanceof Projectile && target instanceof Player){
            if(((Projectile)damager).getShooter() instanceof LivingEntity){
                final LivingEntity shooter = (LivingEntity) ((Projectile)damager).getShooter();
                if (shooter instanceof Player){
                    Player att = (Player) shooter;
                    Player targ = (Player) target;
                    if(!targ.getName().equalsIgnoreCase(DeathGame.DGtarget)&&!att.getName().equalsIgnoreCase(DeathGame.DGtarget)){
                            att.sendMessage(ChatColor.RED + "You are in a no pvp zone");
                            e.setCancelled(true);
                    }else if(targ.getName().equalsIgnoreCase(DeathGame.DGtarget)){
                        if(!DeathGame.DGplayers.contains(att.getName())){
                            att.sendMessage(ChatColor.RED + "You must be in the deathgames to do this, use /deathgames join");
                            e.setCancelled(true);
                        }else if(att.getItemInHand().hasItemMeta()){
                            if(att.getItemInHand().getItemMeta().hasLore()){
                                if(!att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                                    att.sendMessage(ChatColor.RED + "You must use a DeathGames weapon!");
                                    att.sendMessage(ChatColor.RED + "Use /deathgames equip");
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }else if(damager instanceof Player){
            Player att = (Player) damager;
            if(att.getItemInHand().hasItemMeta()){
                if(att.getItemInHand().getItemMeta().hasLore()){
                    if(att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                        att.getInventory().removeItem(att.getItemInHand());
                        att.sendMessage(ChatColor.RED + "Deathgames only");
                        e.setCancelled(true);
                    }
                }
            }
        }else if(damager instanceof Projectile){
            if(((Projectile)damager).getShooter() instanceof LivingEntity){
                final LivingEntity shooter = (LivingEntity) ((Projectile)damager).getShooter();
                if(shooter instanceof Player){
                    Player att = (Player) shooter;
                    if(att.getItemInHand().hasItemMeta()){
                        if(att.getItemInHand().getItemMeta().hasLore()){
                            if(att.getItemInHand().getItemMeta().getLore().contains("For use in the DeathGames only!")){
                                att.getInventory().removeItem(att.getItemInHand());
                                att.sendMessage(ChatColor.RED + "Deathgames only");
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * Called when a player sends a command (comes before the onCommand)
     * used to make tp safer for mods
     */
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(!p.isOp()&&e.getMessage().contains("tp")){
            if(p.getLocation().getWorld().equals(ManhattanPlugin.getCreativeWorld())){
                DBmanager.getPlayerdats().get(p.getName()).CreativeSave.Imprint(p);
            }else if(p.getLocation().getWorld().getName().contains(ManhattanPlugin.getSurvivalWorld().getName())){
                DBmanager.getPlayerdats().get(p.getName()).SurvivalSave.Imprint(p);
            }
        }
    }
    /**
     * Called when the server saves the world (duh)
     * synchronises world save with data file save
     */
    @EventHandler
    public void onWorldSave(WorldSaveEvent e){
        DBmanager.SavePlayers();
    }

}
