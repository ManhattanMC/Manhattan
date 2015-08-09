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
package org.bvsd.manhattanplugin.TheGame;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Donovan <dallen@dallen.xyz>
 */
public class GameCommands implements CommandExecutor{
    
    @Getter @Setter
    private static ArrayList<String> Playing = new ArrayList<>();
    
    public GameCommands(){
            GameDBmanager.loadPlayers();
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cs instanceof Player){
            Player p = (Player) cs;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("begin")){
                    if(!Playing.contains(p.getName())){
                        p.sendMessage("It has begun, we will contact you again when you are ready.");
                        GameCommands.Playing.add(p.getName());
                        GameDBmanager.savePlayers();
                    }
                }
            }
        }
        return true;
    }
    
}
