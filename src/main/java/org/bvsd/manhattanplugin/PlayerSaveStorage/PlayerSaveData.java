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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.ManhattanPlugin;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerSaveData{
//        public char world;
    @Getter @Setter @JsonIgnore
    private HashMap<String, PlayerSave> Saves = new HashMap<>(); 
    
    @JsonIgnore
    private PlayerSave CreativeSave;
    @JsonIgnore
    private PlayerSave SurvivalSave;
    
    @Getter @Setter
    private boolean beenCreate = false;
    
    @Getter @Setter
    private ArrayList<UUID> friends = new ArrayList<UUID>();
    
    @Getter @Setter
    @JsonIgnore
    public int money = 0;
    public PlayerSaveData(boolean real){ //for use in code
        if(!real){
            SurvivalSave = new PlayerSave(ManhattanPlugin.getSurvivalWorld().getSpawnLocation(), new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
            CreativeSave = new PlayerSave(ManhattanPlugin.getCreativeWorld().getSpawnLocation(), new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        }
    }
    public PlayerSaveData(){//for use by JSON
        
    }
    public void setCreativeSave(PlayerSave ps){
        Saves.put("C-Main", ps);
    }
    public void setSurvivalSave(PlayerSave ps){
        Saves.put("S-Main", ps);
    }
}