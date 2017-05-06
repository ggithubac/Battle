package org.ArmyPersonnel;

public class BlueArmyChief extends ArmyType {

	public BlueArmyChief(ArmyPersonnel aPersonnel) {
		super(aPersonnel);
	}
	
	public void setAlive(boolean isAlive) {
		this.aPersonnel.setAlive(isAlive);
	}

	@Override
	public void setGroup() {
		this.group = BattleGroup.BLUE;
	}
}
