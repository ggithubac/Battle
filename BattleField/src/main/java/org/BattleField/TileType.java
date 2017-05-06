package org.BattleField;

public abstract class TileType implements TileInterface {
	protected TileInterface tile;
	TileGroup tgroup;
	/**
	 * @param tile
	 */
	public TileType(TileInterface tile) {
		super();
		this.tile = tile;
	}
	
	public TileGroup getTileGroup() {
		return tgroup;
	}

	public void setTileGroup() {
		this.tgroup = TileGroup.NONE;
		
	}
}
