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

package org.bvsd.manhattanplugin.PlayerSaveStorage;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author Donovan
 */
public class PlayerSaveLocation {
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter @Setter
    private double z;
    @Getter @Setter
    private float yaw;
    @Getter @Setter
    private float pitch;
    @Getter @Setter
    private String world;

    public PlayerSaveLocation(Location loc) {//for use in code
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
        this.world = loc.getWorld().getName();
    }
    
    public PlayerSaveLocation() { //for use by JSON
        
    }
    /**
     * Get a bukkit copy of this object
     * @return  A bukkit Location object of this object
     */
    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
