package org.ArmyPersonnel;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

//Decorator Design pattern
public abstract class ArmyType implements ArmyPersonnel {
	protected ArmyPersonnel aPersonnel;
	BattleGroup group;

	/**
	 * @param aPersonnel
	 */
	public ArmyType(ArmyPersonnel aPersonnel) {
		super();
		this.aPersonnel = aPersonnel;
	}

	public BattleGroup getGroup() {
		return group;
	}

	public void setGroup() {
		this.group = BattleGroup.NONE;
		
	}
	
	public boolean isAlive() {
		return this.aPersonnel.isAlive();
	}
	
	public Pair<Integer, Integer> moveForward(Pair<Integer, Integer> currentCoordinates, List<Integer> TileDimensions) {
		return aPersonnel.moveForward(currentCoordinates, TileDimensions);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
