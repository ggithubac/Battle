package org.BattleField;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Triple;

public class ChiefTile extends CommonTile {

	Triple<Integer, Integer, PersonnelType> chiefLocation;

	/**
	 * @param chiefLocation
	 */
	public ChiefTile(Triple<Integer, Integer, PersonnelType> chiefLocation) {
		super();
		this.chiefLocation = chiefLocation;
	}
	
}
