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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Donovan
 */
public class PlayerSaveArmor {
    @Getter @Setter
    private PlayerSaveItemStack Helm;
    @Getter @Setter
    private PlayerSaveItemStack Chest;
    @Getter @Setter
    private PlayerSaveItemStack Leg;
    @Getter @Setter
    private PlayerSaveItemStack Boot;
    
    public PlayerSaveArmor(ItemStack[] is){
        Helm = new PlayerSaveItemStack(is[3]);
        Chest = new PlayerSaveItemStack(is[2]);
        Leg = new PlayerSaveItemStack(is[1]);
        Boot = new PlayerSaveItemStack(is[0]);
    }
    public PlayerSaveArmor(){
        
    }
    public void GiveTo(Player p){
        p.getInventory().setHelmet(Helm.toItemStack());
        p.getInventory().setChestplate(Chest.toItemStack());
        p.getInventory().setLeggings(Leg.toItemStack());
        p.getInventory().setBoots(Boot.toItemStack());
        p.updateInventory();
    }
    
}
