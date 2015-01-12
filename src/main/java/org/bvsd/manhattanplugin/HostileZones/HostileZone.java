/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
