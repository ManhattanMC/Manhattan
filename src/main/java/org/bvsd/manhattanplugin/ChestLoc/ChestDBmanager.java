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
package org.bvsd.manhattanplugin.ChestLoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bvsd.manhattanplugin.DBmanager;
import org.bvsd.manhattanplugin.ManhattanPlugin;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class ChestDBmanager {
    @Getter @Setter
    private static HashMap<ChestLocation, ChestType> ChestDB = new HashMap<>();
    
    @Getter @Setter @JsonIgnore
    private static HashMap<UUID, ChestDBmanager.Request> Waiting = new HashMap<>();
    
    public static class Request{
        
        @Getter @JsonIgnore
        private UUID req;
        
        @Getter @JsonIgnore
        private ChestLocation cl;
        
        public Request(ChestLocation cl, UUID req){
            this.req = req;
            this.cl = cl;
        }
    }
    
    public static void SaveChests(){
        boolean successful = true;
        File NLoc = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "ChestUtil.savedat.new");
        File OLoc = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "ChestUtil.savedat");
        try {
            ManhattanPlugin.getJSon().writeValue(NLoc, ChestDB);
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
    
    public static void LoadChests(){
        File Loc = new File(ManhattanPlugin.getPlugin().getDataFolder() + System.getProperty("file.separator") + "ChestUtil.savedat");
        try {
            ChestDB = ManhattanPlugin.getJSon().readValue(Loc, HashMap.class);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("IO ERROR");
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
