package org.BattleField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.ArmyPersonnel.PersonnelType;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

//public class BattleFieldDimensions extends CommonTile {
public class BattleFieldDimensions {

	Pair<Integer, Integer> leftTop;
	Pair<Integer, Integer> leftBottom;
	Pair<Integer, Integer> rightTop;
	Pair<Integer, Integer> rightBottom;
	
	final int tileLength = 4; //in feet
	final int tileBreadth = 4; //in feet
	
	int numberOfSoldiersPerArmy = 0;

	Map<Pair<Integer, Integer>, TileInterface> Tiles = new HashMap<Pair<Integer, Integer>, TileInterface>();
	
	Set<Pair<Integer, Integer>> BlueSoldiers = new HashSet<Pair<Integer, Integer>> ();
	
	Set<Pair<Integer, Integer>> BlueLandMines = new HashSet<Pair<Integer, Integer>> ();
	
	Set<Pair<Integer, Integer>> RedSoldiers = new HashSet<Pair<Integer, Integer>>();
	
	Set<Pair<Integer, Integer>> RedLandMines = new HashSet<Pair<Integer, Integer>>();
	
	Pair<Integer, Integer> BlueChiefLocation = Pair.of(0, 0);
	
	Pair<Integer, Integer> RedChiefLocation = Pair.of(0, 0);

	public Set<Pair<Integer, Integer>> getRedLandMines() {
		return RedLandMines;
	}

	public Set<Pair<Integer, Integer>> getBlueLandMines() {
		return BlueLandMines;
	}

	public Pair<Integer, Integer> getBlueChiefLocation() {
		return BlueChiefLocation;
	}

	public Pair<Integer, Integer> getRedChiefLocation() {
		return RedChiefLocation;
	}

	public Set<Pair<Integer, Integer>> getBlueSoldiers() {
		return BlueSoldiers;
	}

	public Set<Pair<Integer, Integer>> getRedSoldiers() {
		return RedSoldiers;
	}
	
	public void removeRedLandMine(Pair<Integer, Integer> newCoordinates) {
		if (this.RedLandMines.contains(newCoordinates)) {
			this.RedLandMines.remove(newCoordinates);
		}
	}
	
	public void removeBlueLandMine(Pair<Integer, Integer> newCoordinates) {
		if (this.BlueLandMines.contains(newCoordinates)) {
			this.BlueLandMines.remove(newCoordinates);
		}
	}

	//Given a location lookup the Tile
	public TileInterface getTile(Pair<Integer, Integer> location) {

		if (location == null) {
			return null;
		}

		return (Tiles.get(location));

	}

	//Insert Tile based on its location
	public boolean insertTile(Pair<Integer, Integer> location, TileInterface tile) {
		if (location == null || tile == null) {
			return false;
		}

		TileInterface prevTile = Tiles.put(location, tile);
		if ( prevTile != null) {
			;
		}
		

		return true;
	}
	
	public boolean setAllBattleFieldDimensions(Pair<Integer, Integer> leftTop,
			Pair<Integer, Integer> leftBottom,
			Pair<Integer, Integer> rightTop,
			Pair<Integer, Integer> rightBottom) {
		
		if (leftTop == null || leftBottom == null || 
				rightTop == null || rightBottom == null) {	
			return false;
		}
		
		this.leftTop = leftTop;
		this.leftBottom = leftBottom;
		this.rightTop = rightTop;
		this.rightBottom = rightBottom;
		
		return true;
		
	}
	
	public List<Integer> getTileDimensions() {
		
		List<Integer> TileDimensions = new ArrayList<Integer>();
		
		TileDimensions.add(0, this.tileLength);
		TileDimensions.add(1, this.tileBreadth);
		
		return TileDimensions;
		
	}
	
	public List<Pair<Integer, Integer>> getAllBattleFieldDimensions() {
		
		if (this.leftTop == null || this.leftBottom == null || 
				this.rightTop == null || this.rightBottom == null) {	
			return null;
		}
		
		List<Pair<Integer, Integer>> BattleFieldBounds = new ArrayList<Pair<Integer, Integer>>();
				
		BattleFieldBounds.add(0, this.leftTop);
		BattleFieldBounds.add(1, this.leftBottom);
		BattleFieldBounds.add(2, this.rightTop);
		BattleFieldBounds.add(3, this.rightBottom);
		
		return BattleFieldBounds;
		
	}
	
	private void buildTile(int breadthLimit, int lengthStart, int lengthLimit, PersonnelType pType, String tileTypeStr) {
		for (int breadth = 0; breadth < breadthLimit; breadth+=this.tileBreadth) {
			for (int length = lengthStart; length < lengthLimit; length+=this.tileLength) {
				Pair<Integer, Integer> tileLocation = Pair.of((length+this.tileLength)/2, (breadth+this.tileBreadth)/2);
				Triple<Integer, Integer, PersonnelType> tileType = Triple.of(tileLocation.getLeft(),
																			 tileLocation.getRight(),
																			 pType);
				TileInterface tile = TileFactory.getTile(tileTypeStr, tileType);
				insertTile(tileLocation, tile);
			}
		}
		
		return;
		
	}
	
	private void addSoldier(int minx, int maxx, int miny, int maxy, int maxCount, 
							PersonnelType pType, String tileStr, boolean Blue) {
		Random randx = new Random();
		Random randy = new Random();
		for(int index =0; index < maxCount; index++) {
			int xcoordinate = randx.nextInt((maxx - minx) + 1) + minx;
			int ycoordinate = randy.nextInt((maxy - miny) + 1) + miny;
			
			Pair<Integer, Integer> location = Pair.of(xcoordinate + (this.tileLength/2),  ycoordinate + (this.tileBreadth/2)); 
			Triple<Integer, Integer, PersonnelType> TileType = Triple.of(location.getLeft(),
					 													  location.getRight(),
					 													  pType);
			TileInterface tile = TileFactory.getTile(tileStr, TileType);
			insertTile(location, tile);
			if (Blue) {
				this.BlueSoldiers.add(location);
			} else {
				this.RedSoldiers.add(location);
			}
		}
		
		return;
	}
	
	private void addLandMine(int minx, int maxx, int miny, int maxy, int maxCount, PersonnelType pType, String tileStr,
							 boolean Blue) {
		Random randx = new Random();
		Random randy = new Random();
		for(int index =0; index < maxCount; index++) {
			int xcoordinate = randx.nextInt((maxx - minx) + 1) + minx;
			int ycoordinate = randy.nextInt((maxy - miny) + 1) + miny;
			
			Pair<Integer, Integer> location = Pair.of(xcoordinate + (this.tileLength/2),  ycoordinate + (this.tileBreadth/2)); 
			Triple<Integer, Integer, PersonnelType> TileType = Triple.of(location.getLeft(),
					 													  location.getRight(),
					 													  pType);
			TileInterface tile = TileFactory.getTile(tileStr, TileType);
			insertTile(location, tile);
			
			if (Blue) {
				this.BlueLandMines.add(location);
			} else {
				this.RedLandMines.add(location);
			}
		}
		
		return;
	}
	
	public boolean buildBattleField() {
		
		if (leftTop == null || leftBottom == null || 
				rightTop == null || rightBottom == null) {	
			return false;
		}
		
		//Calculate total number of Tiles given the battlefield dimensions
		int battleLength = rightTop.getLeft() - leftTop.getLeft();
		int battleBreadth = rightBottom.getRight() - rightTop.getRight();
		int area = battleLength * battleBreadth;
		int tileArea = tileLength * tileBreadth; //Each tile is 4ft X 4ft = 16 square feet
		
		int totalTiles = area/tileArea; //for debugging purposes
		
		System.out.println( "buildBattleField: area " + area + " tileArea "+ tileArea + " totalTiles: " + totalTiles );
		
		//Build Blue region with Chief, Soldiers and LandMines
		int BlueRegionLimit = battleLength/2;
		buildTile(battleBreadth, 0, BlueRegionLimit, PersonnelType.NONE, "BlueTile");
		
		//Build Red region with Chief, Soldiers and LandMines
		buildTile(battleBreadth, BlueRegionLimit, battleLength, PersonnelType.NONE, "RedTile");
		
		//Add the Chiefs for both Blue and Red Army
		//Blue Army Chief
		this.BlueChiefLocation = Pair.of((this.tileLength/2),  (battleBreadth+this.tileBreadth)/2);
		Triple<Integer, Integer, PersonnelType> cTileType = Triple.of(this.BlueChiefLocation.getLeft(),
				 													  this.BlueChiefLocation.getRight(),
				 													  PersonnelType.CHIEF);
		TileInterface ctile = TileFactory.getTile("ChiefBlueTile", cTileType);
		insertTile(this.BlueChiefLocation, ctile);
		
		//Red Army Chief 
		this.RedChiefLocation = Pair.of(battleLength - (this.tileLength/2),  (battleBreadth+this.tileBreadth)/2);
		Triple<Integer, Integer, PersonnelType> rTileType = Triple.of(this.RedChiefLocation.getLeft(),
				 													  this.RedChiefLocation.getRight(),
				 													  PersonnelType.CHIEF);
		TileInterface rtile = TileFactory.getTile("ChiefRedTile", rTileType);
		insertTile(this.RedChiefLocation, rtile);
		
		this.numberOfSoldiersPerArmy = battleLength;
		//Add Blue Army Soldiers
		int minx = battleLength/8; //  1/4 of Blue region
		int maxx = 3*battleLength/8; //  3/4 of Blue region
		addSoldier(minx, maxx, 0, battleBreadth, this.numberOfSoldiersPerArmy, 
					PersonnelType.SOLDIER, "SoldierBlueTile", true);
	
		//Add Red Army Soldiers
		minx = 5*battleLength/8; //  3/4 of Red region
		maxx = 7*battleLength/8; //  1/4 of Red region
		addSoldier(minx, maxx, 0, battleBreadth, this.numberOfSoldiersPerArmy, 
					PersonnelType.SOLDIER, "SoldierRedTile", false);
		
		//Add LandMine to Blue Region
		minx = 3*battleLength/8; //  3/4 of Blue region
		maxx = battleLength/2; //  end of Blue region
		int landmineMax = area/800; // 1% of 1/4 of Blue or Red region
		System.out.println( "area: " + area + "landmineMax: " + landmineMax );
		addLandMine(minx, maxx, 0, battleBreadth, landmineMax, PersonnelType.LANDMINE, "LandMineBlueTile", true);
		
		//Add LandMine to Red Region
		minx = battleLength/2; //  end of Red region
		maxx = 5*battleLength/8; //  3/4 of Red region
		addLandMine(minx, maxx, 0, battleBreadth, landmineMax,  PersonnelType.LANDMINE, "LandMineRedTile", false);
		
		return true;
		
	}
	
	public static void main( String[] args )
    {
        System.out.println( "Hello Welcome to the war!" );
        
        BattleFieldDimensions bFieldDimensions = new BattleFieldDimensions();
		
		//Battle Field coordinates the distance is in feet
		Pair<Integer, Integer> leftTop = Pair.of(0, 0);
		Pair<Integer, Integer> leftBottom = Pair.of(0, 1000);
		Pair<Integer, Integer> rightTop = Pair.of(1000, 0);
		Pair<Integer, Integer> rightBottom = Pair.of(1000, 1000);

		System.out.println( "Coordinates! leftTop: " + leftTop + " leftBottom: " + leftBottom + " rightTop: " + rightTop + " rightBottom: " + rightBottom );
		
		bFieldDimensions.setAllBattleFieldDimensions(leftTop, leftBottom, rightTop, rightBottom);
		if (!bFieldDimensions.buildBattleField()) {
			//Print BattleField couldn't be built
			System.out.println( "BattleField build failed!" );
		}
		
		//BlueSoldier Location
		Iterator<Pair<Integer, Integer>> iterator = bFieldDimensions.BlueSoldiers.iterator();
		System.out.println( "Blue Soldier Count: " + bFieldDimensions.BlueSoldiers.size());
	    while(iterator.hasNext()) {
	        Pair<Integer, Integer> setElement = iterator.next();
	        System.out.println( "Blue Soldier Location: " + setElement );
	    }
	    
	    //BlueChief Location
	    System.out.println( "Blue Chief Location: " + bFieldDimensions.BlueChiefLocation );
	    
	   //RedSoldier Location
	    Iterator<Pair<Integer, Integer>> iter = bFieldDimensions.RedSoldiers.iterator();
	    System.out.println( "Red Soldier Count: " + bFieldDimensions.RedSoldiers.size());
	    while(iter.hasNext()) {
	        Pair<Integer, Integer> setElement = iter.next();
	        System.out.println( "Red Soldier Location: " + setElement );
	    }
	    
	  //RedChief Location
	    System.out.println( "Red Chief Location: " + bFieldDimensions.RedChiefLocation );
	    
	    //BlueLandMines Location
	    Iterator<Pair<Integer, Integer>> biterland = bFieldDimensions.BlueLandMines.iterator();
		System.out.println( "Blue LandMine Count: " + bFieldDimensions.BlueLandMines.size());
	    while(biterland.hasNext()) {
	        Pair<Integer, Integer> setElement = biterland.next();
	        System.out.println( "Blue LandMine Location: " + setElement );
	    }
	    
	  //RedLandMines Location
	    Iterator<Pair<Integer, Integer>> riterland = bFieldDimensions.RedLandMines.iterator();
		System.out.println( "Red LandMine Count: " + bFieldDimensions.RedLandMines.size());
	    while(riterland.hasNext()) {
	        Pair<Integer, Integer> setElement = riterland.next();
	        System.out.println( "Red LandMine Location: " + setElement );
	    }

    }
}
