package org.BattleField;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Triple;

public class LandMine extends CommonTile {

	Triple<Integer, Integer, PersonnelType> mineLocation;

	/**
	 * @param mineLocation
	 */
	public LandMine(Triple<Integer, Integer, PersonnelType> mineLocation) {
		super();
		this.mineLocation = mineLocation;
	}
	
}
