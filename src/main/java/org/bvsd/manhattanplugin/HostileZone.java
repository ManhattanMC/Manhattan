/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import org.bukkit.Location;

/**
 *
 * @author Donovan
 */
public class HostileZone {
    public Location center;
    public int radius;
    public int difficulty;
    public HostileZone(Location c, int r, int d){
        this.center = c;
        this.radius = r;
        this.difficulty = d;
    }
    public boolean inZone(Location loc){
        double dist = Math.sqrt((loc.getX()-center.getX())*(loc.getX()-center.getX()) + (loc.getY()-center.getY())*(loc.getY()-center.getY()));
        return (dist < radius);
    }
}
