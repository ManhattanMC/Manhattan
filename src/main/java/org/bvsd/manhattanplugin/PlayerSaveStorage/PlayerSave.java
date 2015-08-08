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
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.ManhattanPlugin;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Donovan
 */
public class PlayerSave {
    @Getter @Setter
    private PlayerSaveLocation lastLoc;
    @Getter @Setter
    public ArrayList<PlayerSaveItemStack> mainInven = new ArrayList<>();
    @Getter @Setter
    public PlayerSaveArmor armorInven;
    @Getter @Setter
    private float Xp;
    @Getter @Setter
    private int lvl;
    @JsonIgnore
    private Player pHold;
    
    public PlayerSave (Location loc, ItemStack[] is, ItemStack[] is2){ //for use in code
        lastLoc = new PlayerSaveLocation(loc);
        for(ItemStack i : is)
            mainInven.add(new PlayerSaveItemStack(i));
        armorInven = new PlayerSaveArmor(is2);
        Xp=0;
    }
    public PlayerSave(World w){
        
    }
    public PlayerSave(){ //for use by JSON
        
    }
        
    /**
     * sets PlayerSave to Player
     * @param p The player whose player should be copied
     * @return If the process succeeded
     */
    @JsonIgnore
    public boolean Imprint(Player p){ //sets PlayerSave to Player
        Xp = p.getExp();
        lvl = p.getLevel();
        mainInven.clear();
        for(ItemStack s:p.getInventory().getContents()){
            mainInven.add(new PlayerSaveItemStack(s));
        }
        armorInven = new PlayerSaveArmor(p.getInventory().getArmorContents());
        lastLoc = new PlayerSaveLocation(p.getLocation());
        return true;
    }
    /**
     * sets Player to PlayerSave
     * @param p The player who should be set to the save
     */
    @JsonIgnore
    public void SetImprint(Player p){ //sets Player to PlayerSave
        p.getInventory().clear();
        try{
            p.teleport(lastLoc.toLocation());
        }catch(Exception ex){
            if(p.getWorld().equals(ManhattanPlugin.getSurvivalWorld())){
                p.teleport(ManhattanPlugin.getSurvivalWorld().getSpawnLocation());
            }else{
                p.teleport(ManhattanPlugin.getCreativeWorld().getSpawnLocation());
            }
            
        }
        p.setExp(Xp);
        p.setLevel(lvl);
        pHold = p;
        Bukkit.getScheduler().scheduleSyncDelayedTask(ManhattanPlugin.getPlugin(), new Runnable() {
            @Override
            public void run(){
                for (PlayerSaveItemStack i : mainInven) {
                    pHold.getInventory().addItem(i.toItemStack());
                }
                armorInven.GiveTo(pHold);
                pHold.updateInventory();
            }}, 5);
        
    }
}
