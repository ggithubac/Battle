package org.ArmyPersonnel;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

//Decorator Design pattern
public class ArmyChief implements ArmyPersonnel {
	
	boolean isAlive = false;
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Pair<Integer, Integer> moveForward(Pair<Integer, Integer> currentCoordinates, List<Integer> TileDimensions) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
