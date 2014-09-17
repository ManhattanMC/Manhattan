/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
        public char world;
        public Location lastLoc;
        public ItemStack[] mainInven;
        public ItemStack[] armorInven;
        public boolean beenCreate = false;
        
        public int money = 0;
        public ArrayList<Plot> land = new ArrayList<>();
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2){
            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
    }
