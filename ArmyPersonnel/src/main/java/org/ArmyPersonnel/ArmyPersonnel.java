package org.ArmyPersonnel;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

//Decorator Design pattern
public interface ArmyPersonnel {
	
	boolean isAlive();
	
	public void setAlive(boolean isAlive);
	
	public Pair<Integer, Integer> moveForward(Pair<Integer, Integer> currentCoordinates, List<Integer> TileDimensions);

}
