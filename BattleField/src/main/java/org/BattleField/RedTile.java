package org.BattleField;

public class RedTile extends TileType {

	public RedTile(TileInterface tile) {
		super(tile);
		this.tgroup = TileGroup.RED;
	}
	
	@Override
	public void setTileGroup() {
		this.tgroup = TileGroup.RED;
		
	}
}
