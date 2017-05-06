package org.WarGame;

import java.util.concurrent.Callable;

import org.ArmyPersonnel.ArmyPersonnel;
import org.ArmyPersonnel.BlueArmy;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class BlueArmyCallable extends BlueArmy implements Callable<Triple<ArmyPersonnel, Integer, Integer>> {

	Pair<Integer, Integer> currentCoordinates;
	WarGame WG = null;
	
	public Pair<Integer, Integer> getCurrentCoordinates() {
		return currentCoordinates;
	}

	public void setCurrentCoordinates(Pair<Integer, Integer> currentCoordinates) {
		this.currentCoordinates = currentCoordinates;
	}

	public BlueArmyCallable(ArmyPersonnel aPersonnel, Pair<Integer, Integer> currentCoordinates,
			WarGame wG) {
		super(aPersonnel);
		this.currentCoordinates = currentCoordinates;
		this.WG = wG;
	}

	public Triple<ArmyPersonnel, Integer, Integer> call() throws Exception {
		Pair<Integer, Integer> newCoordinates;
		if (this.currentCoordinates!= null) {
			newCoordinates = aPersonnel.moveForward(this.currentCoordinates, this.WG.bFD.getTileDimensions());
			
			if(WG.isBlueArmyOrChief(newCoordinates)) {
				//Blue Army or Blue Chief already exists or Blue region Landmine exist
				//so don't move to this new coordinate return null
				System.out.println("BlueSoldier or Chief already there");
				return null;
			}
			
			if (WG.isBlueLandMine(newCoordinates)) {
				System.out.println("BlueLandMine Tile don't move");
				return null;
			}
			
			if(WG.isRedArmyOrChief(newCoordinates)) {
				//Red Army, Red Chief Red region Landmine exists mark BlueSoldier as Dead and return new coordinates
				aPersonnel.setAlive(false);
				WG.removeBlueSoldier((BlueArmy)(aPersonnel));
				System.out.println("RedSoldier or Chief already there!! BlueSoldier Dead!!");
			} else if (WG.isRedLandMine(newCoordinates)) {
				//Red region Landmine exists mark BlueSoldier as Dead, remove landmine as landmine exploded and return new coordinates
				aPersonnel.setAlive(false);
				WG.setRedLandMineExpoded(newCoordinates);
				WG.removeBlueSoldier((BlueArmy)(aPersonnel));
				System.out.println("RedLandmine Hit BlueSoldier Dead!!");
			}
			
			Triple<ArmyPersonnel, Integer, Integer> tri = Triple.of(aPersonnel, newCoordinates.getLeft(), newCoordinates.getRight());
			return tri;
		}
		return null;
	}

}
