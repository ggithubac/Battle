package org.BattleField;

import org.apache.commons.lang3.tuple.Pair;

public abstract class CommonTile implements TileInterface {

	Pair<Integer, Integer> leftTop;
	Pair<Integer, Integer> leftBottom;
	Pair<Integer, Integer> rightTop;
	Pair<Integer, Integer> rightBottom;
	
	Pair<Integer, Integer> location;

}
