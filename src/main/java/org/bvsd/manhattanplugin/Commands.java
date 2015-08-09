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
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bvsd.manhattanplugin.HostileZones.HostileZone;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSave;
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
        if(cmd.getName().equalsIgnoreCase("vanish")&&player.hasPermission(PermissionUtil.mod)){
            if(!DBmanager.getVanished().contains(player.getName())){
                player.sendMessage(ChatColor.DARK_PURPLE + "Vanished! Poof!");
                if(player.getName().length() < 14){
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
                }else{
                    String newName = player.getName().substring(0, 13);
                    player.setPlayerListName(ChatColor.BLUE + newName);
                }
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p != player && !p.hasPermission(PermissionUtil.mod))
                        p.hidePlayer(player);
                }
                player.getWorld().playEffect(player.getLocation(),Effect.MOBSPAWNER_FLAMES, 4, 4);
                DBmanager.getVanished().add(player.getName());
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " left the game");
            }else{
                player.sendMessage(ChatColor.DARK_PURPLE + "Unvanished! Poof!");
                player.setPlayerListName(null);
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p != player && !p.hasPermission(PermissionUtil.mod))
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
            if(pd.getSaves().containsKey(WorldName)){
                pd.getSaves().get(WorldName).Imprint(player);
            }else{
                pd.getSaves().put(WorldName, new PlayerSave());
            }
            if(!player.isOp()){
                for(PotionEffect effect : player.getActivePotionEffects()){
                    player.removePotionEffect(effect.getType());
                }
            }
            if(pd.getSaves().containsKey(DestName)){
                pd.getSaves().get(DestName).SetImprint(player);
            }else{
                pd.getSaves().put(DestName, new PlayerSave(Bukkit.getWorld(DestName).getSpawnLocation(), 
                        new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), 
                            new ItemStack(Material.AIR), new ItemStack(Material.AIR)}));
                pd.getSaves().get(DestName).SetImprint(player);
            }
            DBmanager.SavePlayer(player.getName());
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("helpbook")){
            if(cs instanceof Player){
                String Command = "give "+((Player) cs).getName()+" minecraft:written_book 1 0 {author:\"Dallen\",title:\"Getting Started\",pages:["
                        + "\"{text:\\\"Welcome,\\\\nIf you are new here, we are oh so glad to have you join.\\\\n\\\\nI, am Dallen (previously known as dallen1393),"
                        + " the admin of the server. I write and maintain the Manhattan plugin as well as keep things up to date.\\\"}\",\"{text:\\\"Contents:\\\\n\\\","
                        + "extra:[{text:\\\"1) Ranks\\\\n\\\",bold:\\\"true\\\",color:\\\"red\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"3\\\"}},"
                        + "{text:\\\"2) WorldJump\\\\n\\\",bold:\\\"true\\\",color:\\\"gold\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"4\\\"}},"
                        + "{text:\\\"3) Rules\\\\n\\\",bold:\\\"true\\\",color:\\\"green\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"5\\\"}},"
                        + "{text:\\\"4) Url\\\\n\\\",bold:\\\"true\\\",color:\\\"blue\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"6\\\"}},"
                        + "{text:\\\"5) DeathGames\\\\n\\\",bold:\\\"true\\\",color:\\\"gray\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"7\\\"}},"
                        + "{text:\\\"6) Development\\\\n\\\",bold:\\\"true\\\",color:\\\"aqua\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"8\\\"}}]}\",\""
                        + "{text:\\\"<- Ranks:\\\\n\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"},"
                        + "extra:[{text:\\\"There are only 3 ranks on the server: Memeber, Mod, and Admin. As of right now I am the only admin. "
                        + "There are usually around 3 mods, although this can change, and members are not limited. Members consist of all other players.\\\"}]}\",\""
                        + "{text:\\\"<- WorldJump:\\\\n\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"},"
                        + "extra:[{text:\\\"WorldJump is the command used to change worlds here on the server. There are currently two worlds, "
                        + "Creative and Survival. This command will save your inventory, and jumps you. Although it saves your stuff, "
                        + "it might not work, so put your stuff in a chest.\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"\\\"}}]}\",\""
                        + "{text:\\\"<- Rules:\\\\nThe rules are simple, no stealing, killing, griefing, spamming, or swearing. "
                        + "We have LogBlock on this server, so remember... we are always watching. \\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"}}"
                        + "\",\"{text:\\\"<- Url:\\\\nThe default url for this server is 66.85.142.10:25891. That is terrible though, "
                        + "to Dallen made it so you can also use mms.dallen.xyz.\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"}}"
                        + "\",\"{text:\\\"<- DeathGames:\\\\nThe DeathGames are currently closed and may open some time this year. "
                        + "When they do you will be notified.\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"}}\",\""
                        + "{text:\\\"<- Development:\\\\nThe Manhattan plugin source can be found \\\","
                        + "clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"},extra:[{text:\\\"here\\\",bold:\\\"true\\\","
                        + "clickEvent:{action:\\\"open_url\\\",value:\\\"https://github.com/ManhattanMC/Manhattan\\\"}},"
                        + "{text:\\\"\\\\nif you would like to develop for the server, and possibly become a mod/admin please contact myself (Dallen) "
                        + "or make some pull requests to the repo, Ill notice :D\\\"}]}\",\"{text:\\\"<- End Matter:\\\\n\\\",clickEvent:{action:\\\"change_page\\\",value:\\\"2\\\"},"
                        + "extra:[{text:\\\"1) LogBlock Tutorial\\\\n\\\",clickEvent:{action:\\\"open_url\\\",value:\\\"https://github.com/LogBlock/LogBlock/wiki/Commands\\\"}},"
                        + "{text:\\\"2) Essentials\\\",clickEvent:{action:\\\"open_url\\\",value:\\\"http://wiki.ess3.net/wiki/Command_Reference\\\"}}]}\",\""
                        + "{text:\\\"\\\"}\",\"{text:\\\"\\\"}\",\"{text:\\\"You have to let it all go, \\\",extra:[{text:\\\""+((Player) cs).getName()+"\\\",obfuscated:\\\"true\\\"},"
                        + "{text:\\\"\\\\nFear, doubt, and disbelief. Free your mind.\\\\n\\\\n\\\\n\\\\n\\\"},{text:\\\"Go\\\",bold:\\\"true\\\",color:\\\"white\\\","
                        + "clickEvent:{action:\\\"run_command\\\",value:\\\"/thegame begin\\\"}}]}\"]}";
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Command);
                return true;
            }
        }
        
        return false;
    }
    
}
