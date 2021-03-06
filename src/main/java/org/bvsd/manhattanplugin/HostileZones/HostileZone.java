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

package org.bvsd.manhattanplugin.HostileZones;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bvsd.manhattanplugin.PlayerSaveStorage.PlayerSaveLocation;

/**
 *
 * @author Donovan
 */
public class HostileZone {
    @Getter @Setter
    private PlayerSaveLocation center;
    @Getter @Setter
    private int radius;
    @Getter @Setter
    private int difficulty;
    
    public HostileZone(Location c, int r, int d){ //constructor used in code
        this.center = new PlayerSaveLocation(c);
        this.radius = r;
        this.difficulty = d;
    }
    
    public HostileZone(){//constructor used for JSON load
        
    }
    
    /**
     * Test if a location is in the Hostile Zone
     * @param loc The location to test
     * @return If the given location in the Hostile Zone
     */
    public boolean inZone(Location loc){
        double dist = Math.sqrt((loc.getX()-center.getX())*(loc.getX()-center.getX()) + (loc.getY()-center.getY())*(loc.getY()-center.getY()));
        return (dist < radius);
    }
}
