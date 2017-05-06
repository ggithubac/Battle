package org.WarGame;

import java.util.concurrent.Callable;

import org.ArmyPersonnel.ArmyPersonnel;
import org.ArmyPersonnel.BlueArmy;
import org.ArmyPersonnel.RedArmy;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class RedArmyCallable extends RedArmy implements Callable<Triple<ArmyPersonnel, Integer, Integer>> {

	Pair<Integer, Integer> currentCoordinates;
	WarGame WG = null;
	
	public Pair<Integer, Integer> getCurrentCoordinates() {
		return currentCoordinates;
	}

	public void setCurrentCoordinates(Pair<Integer, Integer> currentCoordinates) {
		this.currentCoordinates = currentCoordinates;
	}

	public RedArmyCallable(ArmyPersonnel aPersonnel, Pair<Integer, Integer> currentCoordinates,
			WarGame wG) {
		super(aPersonnel);
		this.currentCoordinates = currentCoordinates;
		this.WG = wG;
	}

	public Triple<ArmyPersonnel, Integer, Integer> call() throws Exception {
		Pair<Integer, Integer> newCoordinates;
		if (this.currentCoordinates!= null) {
			newCoordinates = aPersonnel.moveForward(this.currentCoordinates, this.WG.bFD.getTileDimensions());
			
			if(WG.isRedArmyOrChief(newCoordinates)) {
				//Red Army or Red Chief already exists 
				//so don't move to this new coordinate return null
				System.out.println("RedSoldier or Chief already there");
				return null;
			}
			
			if (WG.isRedLandMine(newCoordinates)) {
				//Red region Landmine exist
				System.out.println("RedLandMine Tile don't move");
				return null;
			}
			
			if(WG.isBlueArmyOrChief(newCoordinates)) {
				//Blue Army, Blue Chief Blue region Landmine exists mark RedSoldier as Dead and return new coordinates
				aPersonnel.setAlive(false);
				WG.removeRedSoldier((RedArmy)(aPersonnel));
				System.out.println("BlueSoldier or Chief already there!! RedSoldier Dead!!");
			} else if (WG.isBlueLandMine(newCoordinates)) {
				//Blue region Landmine exists mark RedSoldier as Dead, remove landmine as landmine exploded and return new coordinates
				aPersonnel.setAlive(false);
				WG.removeRedSoldier((RedArmy)(aPersonnel));
				WG.setRedLandMineExpoded(newCoordinates);
				System.out.println("BlueLandmine Hit RedSoldier Dead!!");
			}
			
			Triple<ArmyPersonnel, Integer, Integer> tri = Triple.of(aPersonnel, newCoordinates.getLeft(), newCoordinates.getRight());
			return tri;
		}
		return null;
	}

}

