package org.WarGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.ArmyPersonnel.*;
import org.BattleField.BattleFieldDimensions;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Let the War Begin
 *
 */
public class WarGame 
{
	
	Map<Pair<Integer, Integer>, BlueArmy> BlueArmyLoc = new HashMap<Pair<Integer, Integer>, BlueArmy>();
	Map<Pair<Integer, Integer>, RedArmy> RedArmyLoc = new HashMap<Pair<Integer, Integer>, RedArmy>();
	Map<Pair<Integer, Integer>, BlueArmyChief> BlueChiefLoc = new HashMap<Pair<Integer, Integer>, BlueArmyChief>();
	Map<Pair<Integer, Integer>, RedArmyChief> RedChiefLoc = new HashMap<Pair<Integer, Integer>, RedArmyChief>();
	ConcurrentMap<BlueArmy, Pair<Integer, Integer>> BlueArmy = new ConcurrentHashMap<BlueArmy, Pair<Integer, Integer>>();
	ConcurrentMap<RedArmy, Pair<Integer, Integer>> RedArmy = new ConcurrentHashMap<RedArmy, Pair<Integer, Integer>>();
	ConcurrentMap<BlueArmyChief, Pair<Integer, Integer>> BlueChief = new ConcurrentHashMap<BlueArmyChief, Pair<Integer, Integer>>();
	ConcurrentMap<RedArmyChief, Pair<Integer, Integer>> RedChief = new ConcurrentHashMap<RedArmyChief, Pair<Integer, Integer>>();
	
	BattleFieldDimensions bFD = null;
	
	Random BlueRed = new Random();
	
	int ExecutorThreads = 2;
	
	/**
	 * @param bFD
	 */
	public WarGame(BattleFieldDimensions bFD) {
		super();
		this.bFD = bFD;
	}
	
	public synchronized void removeBlueSoldier(BlueArmy bSoldier) {
		Pair<Integer, Integer> loc = BlueArmy.get(bSoldier);
		
		System.out.println("Before: removeBlueSoldier: BlueArmySize: " + BlueArmy.size() + "BlueArmyLoc: " + BlueArmyLoc.size());
		
		BlueArmy.remove(bSoldier);
		BlueArmyLoc.remove(loc);
		
		System.out.println("After: removeBlueSoldier: BlueArmySize: " + BlueArmy.size() + "BlueArmyLoc: " + BlueArmyLoc.size());
		return;
	}
	
	public synchronized void removeRedSoldier(RedArmy rSoldier) {
		Pair<Integer, Integer> loc = RedArmy.get(rSoldier);
		System.out.println("Before: removeRedSoldier: RedArmySize: " + RedArmy.size() + "RedArmyLoc: " + RedArmyLoc.size());
		
		RedArmy.remove(rSoldier);
		RedArmyLoc.remove(loc);
		
		System.out.println("After: removeRedSoldier: RedArmySize: " + RedArmy.size() + "RedArmyLoc: " + RedArmyLoc.size());
		return;
	}
	
	public synchronized void setRedLandMineExpoded(Pair<Integer, Integer> newCoordinates) {
		this.bFD.removeRedLandMine(newCoordinates);
	}
	
	public synchronized void setBlueLandMineExpoded(Pair<Integer, Integer> newCoordinates) {
		this.bFD.removeBlueLandMine(newCoordinates);
	}

	public synchronized boolean isBlueArmyOrChief(Pair<Integer, Integer> newCoordinates) {
		BlueArmy bA = BlueArmyLoc.get(newCoordinates);
		
		if (bA != null) {
			return true;
		}
		
		BlueArmyChief bAC = BlueChiefLoc.get(newCoordinates);
		
		if (bAC != null) {
			return true;
		}
		return false;
	}

	public synchronized boolean isBlueLandMine(Pair<Integer, Integer> newCoordinates) {
		
		if(this.bFD.getBlueLandMines().contains(newCoordinates)) {
			return true;
		}
		
		return false;
	}


	public synchronized  boolean isRedArmyOrChief(Pair<Integer, Integer> newCoordinates) {
		
		RedArmy rA = RedArmyLoc.get(newCoordinates);
		
		if (rA != null) {
			return true;
		}
		
		RedArmyChief rAC = RedChiefLoc.get(newCoordinates);
		
		if (rAC != null) {
			return true;
		}
		
		return false;
	}

	public synchronized boolean isRedLandMine(Pair<Integer, Integer> newCoordinates) {
		
		if(this.bFD.getRedLandMines().contains(newCoordinates)) {
			return true;
		}
		
		return false;
	}

	void AssociateLiveBlueArmyandChief() {
		
		Set<Pair<Integer, Integer>> blueArmyLocations = this.bFD.getBlueSoldiers();
		Iterator<Pair<Integer, Integer>> armyIterator = blueArmyLocations.iterator();
		
		while(armyIterator.hasNext()) {
			Pair<Integer, Integer> armyLocation = armyIterator.next();
			BlueArmy bArmy = new BlueArmy(new ArmySoldier());
			bArmy.setAlive(true); //Soldier is alive
			this.BlueArmy.put(bArmy, armyLocation);
			this.BlueArmyLoc.put(armyLocation, bArmy);
		}
		
		//Add Blue Army Chief
		Pair<Integer, Integer> blueArmyChiefLocation = this.bFD.getBlueChiefLocation();
		BlueArmyChief bArmyChief = new BlueArmyChief(new ArmyChief());
		bArmyChief.setAlive(true); //Chief is alive
		this.BlueChief.put(bArmyChief, blueArmyChiefLocation);
		this.BlueChiefLoc.put(blueArmyChiefLocation, bArmyChief);
	}
	
	void AssociateLiveRedArmyandChief() {
		
		Set<Pair<Integer, Integer>> redArmyLocations = this.bFD.getRedSoldiers();
		Iterator<Pair<Integer, Integer>> armyIterator = redArmyLocations.iterator();
		
		while(armyIterator.hasNext()) {
			Pair<Integer, Integer> armyLocation = armyIterator.next();
			RedArmy rArmy = new RedArmy(new ArmySoldier());
			rArmy.setAlive(true); //Soldier is alive
			this.RedArmy.put(rArmy, armyLocation);
			this.RedArmyLoc.put(armyLocation, rArmy);
		}
		
		//Add Red Army Chief
		Pair<Integer, Integer> redArmyChiefLocation = this.bFD.getRedChiefLocation();
		RedArmyChief rArmyChief = new RedArmyChief(new ArmyChief());
		rArmyChief.setAlive(true); //Chief is alive
		this.RedChief.put(rArmyChief, redArmyChiefLocation);
		this.RedChiefLoc.put(redArmyChiefLocation, rArmyChief);
	}
	
	synchronized boolean OutofBounds(Pair<Integer, Integer> Coordinates) {
		
		List<Pair<Integer, Integer>> dimensions = this.bFD.getAllBattleFieldDimensions();
		
		if (dimensions == null) {
			return true;
		}
		
		if (Coordinates.getLeft() < dimensions.get(0).getLeft() || 
			Coordinates.getLeft() > dimensions.get(2).getLeft() ||
			Coordinates.getRight() < dimensions.get(0).getRight() ||
			Coordinates.getRight() > dimensions.get(1).getRight()) {
				return true;
		}
		
		return false;
	}
	
	boolean beginFight() {

		int whichArmy = this.BlueRed.nextInt(2);

		System.out.println("beginFight Called");
		
		//Randomize between the two armies, depending if whichArmy value either Red goes first of Blue goes first
		if (whichArmy == 0) {
			System.out.println("Move BlueArmy");
			//Blue Army
			ThreadPoolExecutor blueExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.ExecutorThreads);

			List<Future<Triple<ArmyPersonnel, Integer, Integer>>> blueResultList = new ArrayList<Future<Triple<ArmyPersonnel, Integer, Integer>>>();

			for (Map.Entry<BlueArmy, Pair<Integer, Integer>> pair : BlueArmy.entrySet()) {
				BlueArmyCallable bArmyC = new BlueArmyCallable(pair.getKey(), pair.getValue(), this);
				Future<Triple<ArmyPersonnel, Integer, Integer>> result = blueExecutor.submit(bArmyC);
				blueResultList.add(result);
			}
			
			for(Future<Triple<ArmyPersonnel, Integer, Integer>> future : blueResultList)
			{
				try
				{
					if((future != null) && (future.isDone())) {
						Triple<ArmyPersonnel, Integer, Integer> triple = future.get();
						if (triple != null) {
							System.out.println("Moving the BlueArmy triple is not null");
							Pair<Integer, Integer> newCoordinates = Pair.of(triple.getMiddle(), triple.getRight());
							Pair<Integer, Integer> oldCoordinates = BlueArmy.get((BlueArmy)triple.getLeft());
							BlueArmy.put((BlueArmy)triple.getLeft(), newCoordinates);
							BlueArmyLoc.remove(oldCoordinates);
							BlueArmyLoc.put(newCoordinates, (BlueArmy)triple.getLeft());
							System.out.println("Blue Old Coordinate - " + " - " + oldCoordinates + "; NewCoordinate " + newCoordinates);
						}
					}
					System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
				} 
				catch (InterruptedException i) 
				{
					i.printStackTrace();
					i.getCause();
				}
				catch (ExecutionException e)
				{
					System.out.println("Execution Exception occured");
				}
			}
			//shut down the executor service now
			blueExecutor.shutdown();
			
		} else {
			System.out.println("Move RedArmy");
			//Red Army
			ThreadPoolExecutor redExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.ExecutorThreads);

			List<Future<Triple<ArmyPersonnel, Integer, Integer>>> redResultList = new ArrayList<Future<Triple<ArmyPersonnel, Integer, Integer>>>();

			for (Map.Entry<RedArmy, Pair<Integer, Integer>> pair : RedArmy.entrySet()) {
				//Do something
				RedArmyCallable rArmyC = new RedArmyCallable(pair.getKey(), pair.getValue(), this);
				Future<Triple<ArmyPersonnel, Integer, Integer>> result = redExecutor.submit(rArmyC);
				redResultList.add(result);
			}
			
			for(Future<Triple<ArmyPersonnel, Integer, Integer>> future : redResultList)
			{
				try
				{
					if((future != null) && (future.isDone())) {
						Triple<ArmyPersonnel, Integer, Integer> triple = future.get();
						if (triple != null) {
							System.out.println("Moving the RedArmy triple is not null");
							Pair<Integer, Integer> newCoordinates = Pair.of(triple.getMiddle(), triple.getRight());
							Pair<Integer, Integer> oldCoordinates = RedArmy.get((RedArmy)triple.getLeft());
							RedArmy.put((RedArmy)triple.getLeft(), newCoordinates);
							RedArmyLoc.remove(oldCoordinates);
							RedArmyLoc.put(newCoordinates, (RedArmy)triple.getLeft());
							System.out.println("Red Old Coordinate - " + " - " + oldCoordinates + "; NewCoordinate " + newCoordinates);
						}
					}
					System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
				} 
				catch (InterruptedException i) 
				{
					i.printStackTrace();
					i.getCause();
				}
				catch (ExecutionException e)
				{
					System.out.println("Execution Exception occured");
				}
			}
			//shut down the executor service now
			redExecutor.shutdown();
		}
		return false;
	}

    public static void main( String[] args )
    {
        System.out.println( "Hello Welcome to the war!" );
        
        BattleFieldDimensions bFieldDimensions = new BattleFieldDimensions();
		
        int xCoordinate = 1000;
        int yCoordinate = 1000;
		//Battle Field coordinates the distance is in feet
		Pair<Integer, Integer> leftTop = Pair.of(0, 0);
		Pair<Integer, Integer> leftBottom = Pair.of(0, yCoordinate);
		Pair<Integer, Integer> rightTop = Pair.of(xCoordinate, 0);
		Pair<Integer, Integer> rightBottom = Pair.of(xCoordinate, yCoordinate);

		System.out.println( "Coordinates! leftTop: " + leftTop + " leftBottom: " + leftBottom + " rightTop: " + rightTop + " rightBottom: " + rightBottom );
		
		bFieldDimensions.setAllBattleFieldDimensions(leftTop, leftBottom, rightTop, rightBottom);
		if (!bFieldDimensions.buildBattleField()) {
			//Print BattleField couldn't be built
			System.out.println( "BattleField build failed!" );
		}
		
		 WarGame warGame = new WarGame(bFieldDimensions);
		
		System.out.println( "BattleField build done!" );
		
		//Associate live blue-army to locations on BattleField
		warGame.AssociateLiveBlueArmyandChief();
		
		//Associate live red-army to locations on BattleField
		warGame.AssociateLiveRedArmyandChief();
		
		System.out.println( "Army placed and ready to fight!" );
		System.out.println("Blue Army:" + warGame.BlueArmy.size());
		System.out.println("Red Army:" + warGame.RedArmy.size());
		
		System.out.println("Army Fight begun!!");
		int i = 0;
		while(i < 200) {
			warGame.beginFight();
			i++;
		}
		
		System.out.println("BlueArmy Soldiers: " + warGame.BlueArmy.size());
		System.out.println("RedArmy Soldiers: " + warGame.RedArmy.size());
		if (warGame.BlueArmy.size() > warGame.RedArmy.size()) {
			System.out.println("Blue Army WON!!");
		} else if (warGame.BlueArmy.size() < warGame.RedArmy.size()){
			System.out.println("Red Army WON!!");
		} else {
			System.out.println("Its a Stalemate!!");
		}
		
		System.out.println("Army Fight over!!");
    }
}
