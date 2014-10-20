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

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author Donovan
 */
class PlayerSaveLeatherArmorMeta {
    
    @Getter
    @Setter
    private int rgb;
    @Getter
    @Setter
    private String displayName;
    @Getter
    @Setter
    private List<String> lore;

    public PlayerSaveLeatherArmorMeta(LeatherArmorMeta meta) {
        this.rgb = meta.getColor().asRGB();
        if (meta.hasDisplayName()) {
            this.displayName = meta.getDisplayName();
        }
        if (meta.hasLore()) {
            this.lore = meta.getLore();
        }
    }
    public PlayerSaveLeatherArmorMeta() {

    }
}
