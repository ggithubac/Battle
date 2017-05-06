package org.BattleField;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Triple;

public class BlueTile extends TileType{
	
	Triple<Integer, Integer, PersonnelType> TileLocation;

	public BlueTile(TileInterface tile) {
		super(tile);
		this.tgroup = TileGroup.BLUE;
	}
	
	@Override
	public void setTileGroup() {
		this.tgroup = TileGroup.BLUE;
		
	}

}
