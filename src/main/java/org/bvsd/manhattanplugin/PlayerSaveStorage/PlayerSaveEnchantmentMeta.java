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

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Donovan
 */
class PlayerSaveEnchantmentMeta {
    
    @Getter
    @Setter
    private HashMap<String, Integer> enchants = new HashMap();

    public PlayerSaveEnchantmentMeta(ItemMeta meta) {
        for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
            enchants.put(entry.getKey().getName(), entry.getValue());
        }
    }
    
    public PlayerSaveEnchantmentMeta() {
        
    }
}
