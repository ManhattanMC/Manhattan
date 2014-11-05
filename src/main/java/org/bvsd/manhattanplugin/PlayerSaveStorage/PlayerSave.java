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
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bvsd.manhattanplugin.mms;
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
    @JsonIgnore
    private Player pHold;
    
    public PlayerSave (Location loc, ItemStack[] is, ItemStack[] is2){
        lastLoc = new PlayerSaveLocation(loc);
        for(ItemStack i : is)
            mainInven.add(new PlayerSaveItemStack(i));
        armorInven = new PlayerSaveArmor(is2);
    }
    public PlayerSave(){
        
    }
    @JsonIgnore
    public boolean Imprint(Player p){ //sets PlayerSave to Player
        mainInven.clear();
        for(ItemStack s:p.getInventory().getContents()){
            mainInven.add(new PlayerSaveItemStack(s));
        }
        armorInven = new PlayerSaveArmor(p.getInventory().getArmorContents());
        lastLoc = new PlayerSaveLocation(p.getLocation());
        return true;
    }
    @JsonIgnore
    public void SetImprint(Player p){ //sets Player to PlayerSave
        p.getInventory().clear();
        p.teleport(lastLoc.toLocation());
        pHold = p;
        Bukkit.getScheduler().scheduleSyncDelayedTask(mms.plugin, new Runnable() {
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
