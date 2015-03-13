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
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveData;

/**
 *
 * @author Donovan
 */
public class TPmCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if(cs instanceof Player && args.length == 1){
            Player p = (Player) cs;
            if(p.hasPermission(PermissionUtil.mod)){
                if(Arrays.asList(Bukkit.getOnlinePlayers()).contains(p)){
                    PlayerSaveData pd = null;
                    if(DBmanager.getPlayerdats().containsKey(p.getName())){
                        pd = DBmanager.getPlayerdats().get(p.getName());
                    }else{
                        pd = new PlayerSaveData();
                    }
                    Player targ = Bukkit.getPlayer(args[0]);
                    String WorldName = p.getLocation().getWorld().getName().replace("_nether", "").replace("_the_end", "");
                    String DestName = targ.getLocation().getWorld().getName().replace("_nether", "").replace("_the_end", "");
                    pd.getSaves().get(WorldName).Imprint(p);
                    if(!p.isOp()){
                        for(PotionEffect effect : p.getActivePotionEffects()){
                            p.removePotionEffect(effect.getType());
                        }
                    }
                    pd.getSaves().get(DestName).SetImprint(p);
                    DBmanager.SavePlayer(p.getName());
                    p.teleport(targ);
                }
            }else{
                p.sendMessage("You ain't no mod");
            }
            return true;
        }
        return false;
    }
    
}
