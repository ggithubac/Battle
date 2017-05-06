package org.BattleField;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Triple;

public class RegularTile extends CommonTile{
	
	Triple<Integer, Integer, PersonnelType> TileLocation;

	/**
	 * @param tileLocation
	 */
	public RegularTile(Triple<Integer, Integer, PersonnelType> tileLocation) {
		super();
		TileLocation = tileLocation;
	}

}
