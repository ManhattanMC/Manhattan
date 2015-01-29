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

import java.util.HashMap;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
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
        
        @Getter
        private UUID req;
        
        @Getter
        private ChestLocation cl;
        
        public Request(ChestLocation cl, UUID req){
            this.req = req;
            this.cl = cl;
        }
    }
}
