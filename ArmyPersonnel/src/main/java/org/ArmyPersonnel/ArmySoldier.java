package org.ArmyPersonnel;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

//Decorator Design pattern
public class ArmySoldier implements ArmyPersonnel {
	
	boolean isAlive = false;
	
	//Random randy = new Random();
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public Pair<Integer, Integer> moveForward(Pair<Integer, Integer> currentCoordinates,
											   List<Integer> TileDimensions) {	
		/*if (!isAlive()) {
			System.out.println( "I'm Dead!" );
			return null;
		}
		
		
		 * Move forward on x-axis by TileLength
		 * Move Randomly between three possible Tiles who's Y-Co-ordinate if either
		 *  1. Current y-coordinate - TileBreadth 
		 *  2. or Current y-coordinate
		 *  3. or Current y-coordinate + TileBreadth
		 *  as Long as the move it within the BattleFieldBounds
		 
		
		int ry = randy.nextInt(3); // 0 to 2
		int y = 0;
		
		if (ry == 0) {
			y = currentCoordinates.getRight() - TileDimensions.get(1);
		} else if (ry == 1) {
			y = currentCoordinates.getRight();
		} else {
			y = currentCoordinates.getRight() + TileDimensions.get(1);
		}
		
		Pair<Integer, Integer> newCoordinates = Pair.of(currentCoordinates.getLeft() + TileDimensions.get(0), y);
		
		return newCoordinates;*/
		
		return null;
	}
	
	public static void main(String[] args) {

	}
}
