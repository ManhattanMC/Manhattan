/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bvsd.manhattanplugin.PlayerSaveStorage;

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
    @Getter @Setter
    public PlayerSave CreativeSave;
    @Getter @Setter
    public PlayerSave SurvivalSave;
    @Getter @Setter
    public boolean beenCreate = false;
    @Getter @Setter
    @JsonIgnore
    public int money = 0;
    public PlayerSaveData(boolean real){
        if(!real){
            SurvivalSave = new PlayerSave(ManhattanPlugin.SurvivalWorld.getSpawnLocation(), new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
            CreativeSave = new PlayerSave(ManhattanPlugin.CreativeWorld.getSpawnLocation(), new ItemStack[] {}, new ItemStack[] {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
        }
    }
    public PlayerSaveData(){
        
    }
}