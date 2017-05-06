package org.BattleField;

import java.util.HashMap;
import java.util.Map;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

//Factory Design pattern
public class TileFactory {

	public static TileInterface getTile(String tileType, Triple<Integer, Integer, PersonnelType> location) {
		if(tileType == null) {
			return null;
		}
		
		/*if(tileType.equalsIgnoreCase("BattleField")) {
			return (new BattleFieldDimensions()); 
		}*/
		
		if(tileType.equalsIgnoreCase("BlueTile")) {
			return (new BlueTile(new RegularTile(location))); 
		}
		
		if(tileType.equalsIgnoreCase("RedTile")) {
			return (new RedTile(new RegularTile(location))); 
		}
		
		//Set location before creating this tiletype
		if(tileType.equalsIgnoreCase("ChiefBlueTile")) {
			if (location != null) {
				return (new BlueTile(new ChiefTile(location))); 
			} else {
				return null;
			}
			
		}
		
		if(tileType.equalsIgnoreCase("ChiefRedTile")) {
			if (location != null) {
				return (new RedTile(new ChiefTile(location)));
			} else {
				return null;
			}
		}
			
		
		if(tileType.equalsIgnoreCase("SoldierBlueTile")) {
			if (location != null) {
				return (new BlueTile(new SoldierTile(location))); 
			} else {
				return null;
			}
			
		}
		
		if(tileType.equalsIgnoreCase("SoldierRedTile")) {
			if (location != null) {
				return (new RedTile(new SoldierTile(location)));
			} else {
				return null;
			}
		}
		
		if(tileType.equalsIgnoreCase("LandMineBlueTile")) {
			if (location != null) {
				return (new BlueTile(new LandMine(location))); 
			} else {
				return null;
			}
			
		}
		
		if(tileType.equalsIgnoreCase("LandMineRedTile")) {
			if (location != null) {
				return (new RedTile(new LandMine(location)));
			} else {
				return null;
			}
		}
		
		return null;
		
	}
	
}
