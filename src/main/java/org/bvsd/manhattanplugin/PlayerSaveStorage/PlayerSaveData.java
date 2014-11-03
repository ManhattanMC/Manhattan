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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerSaveData{
//        public char world;
    @Getter @Setter
    private PlayerSaveLocation lastLoc;
    @Getter @Setter
    public ArrayList<PlayerSaveItemStack> mainInven = new ArrayList<>();
    @Getter @Setter
    public PlayerSaveArmor armorInven;
    @Getter @Setter
    public boolean beenCreate = false;
    @Getter @Setter
    @JsonIgnore
    public int money = 0;
//    @Getter @Setter
//    @JsonIgnore
//    public ArrayList<Plot> land = new ArrayList<>();

    public PlayerSaveData(char w, ItemStack[] is, ItemStack[] is2){
        lastLoc = new PlayerSaveLocation(Bukkit.getWorld("C-Main").getSpawnLocation());
        for(ItemStack i : is)
            mainInven.add(new PlayerSaveItemStack(i));
        armorInven = new PlayerSaveArmor(is2);
    }
    public PlayerSaveData(){
        
    }
    public void SetInven(Player p){
        p.getInventory().clear();
        for (PlayerSaveItemStack i : mainInven) {
            p.getInventory().addItem(i.toItemStack());
        }
        armorInven.GiveTo(p);
        p.updateInventory();
    }
}