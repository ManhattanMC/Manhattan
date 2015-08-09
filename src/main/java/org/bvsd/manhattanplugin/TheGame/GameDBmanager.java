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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bvsd.manhattanplugin.ManhattanPlugin;

/**
 *
 * @author Donovan <dallen@dallen.xyz>
 */
public class GameDBmanager {
    
    public static void savePlayers(){
        boolean successful = true;
        File NLoc = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "Players.game.new");
        File OLoc = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "Players.game");
        try {
            ManhattanPlugin.getJSon().writeValue(NLoc, GameCommands.getPlaying());
        } catch (IOException ex) {
            successful = false;
        } finally {
            if (successful) {
                if (OLoc.exists()) {
                    OLoc.delete();
                }
                NLoc.renameTo(OLoc);
            }
        }
    }
    
    public static void loadPlayers(){
        File f = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "Players.game");
        if(f.exists()){
            try {
                GameCommands.getPlaying().addAll(ManhattanPlugin.getJSon().readValue(f, ArrayList.class));
            } catch (IOException ex) {
                Logger.getLogger(GameDBmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            GameCommands.setPlaying(new ArrayList<String>());
        }
    }
}
