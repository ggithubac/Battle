package org.BattleField;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Triple;

public class SoldierTile extends CommonTile {
	
	Triple<Integer, Integer, PersonnelType> soldierLocation;

	/**
	 * @param soldierLocation
	 */
	public SoldierTile(Triple<Integer, Integer, PersonnelType> soldierLocation) {
		super();
		this.soldierLocation = soldierLocation;
	}

	public Triple<Integer, Integer, PersonnelType> getSoldierLocation() {
		return soldierLocation;
	}

	public void setSoldierLocation(Triple<Integer, Integer, PersonnelType> soldierLocation) {
		this.soldierLocation = soldierLocation;
	}

}
