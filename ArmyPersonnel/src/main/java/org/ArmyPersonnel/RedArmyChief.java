package org.ArmyPersonnel;

public class RedArmyChief extends ArmyType {

	public RedArmyChief(ArmyPersonnel aPersonnel) {
		super(aPersonnel);
	}
	
	public void setAlive(boolean isAlive) {
		this.aPersonnel.setAlive(isAlive);
	}
	
	@Override
	public void setGroup() {
		this.group = BattleGroup.RED;
	}
}
