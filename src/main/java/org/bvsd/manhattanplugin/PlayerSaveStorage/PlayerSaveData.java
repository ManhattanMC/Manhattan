/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerSaveData{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerSaveData(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerSaveData(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
package org.bvsd.manhattanplugin.PlayerSaveStorage;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.Plot;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerDat{
//        public char world;
        @Getter @Setter
        private Location lastLoc;
        @Getter WorldJumpItemStackr
        public SaveIS[] mainInven;
        @Getter WorldJumpItemStackr
        public SaveIS[] armorInven;
        @Getter @Setter
        public boolean beenCreate = false;
        @Getter @Setter
        @JsonIgnore
        public int money = 0;
        @Getter @Setter
        @JsonIgnore
        public ArrayList<Plot> land = new ArrayList<>();
        
        public PlayerDat(char w, ItemStack[] is, ItemStack[] is2){
//            world = w;
            lastLoc=Bukkit.getWorld("C-Main").getSpawnLocation();
            mainInven = is;
            armorInven = is2;
        }
        public PlayerDat(char w, Location loc, ItemStack[] is, ItemStack[] is2, boolean beenC){
//            world = w;
            lastLoc = loc;
            mainInven = is;
            armorInven = is2;
            beenCreate = beenC;
        }
    }
