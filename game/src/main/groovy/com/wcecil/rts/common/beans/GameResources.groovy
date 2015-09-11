package com.wcecil.rts.common.beans;

import groovy.transform.CompileStatic

import org.springframework.stereotype.Component

import com.wcecil.rts.common.pojo.core.Building
import com.wcecil.rts.common.pojo.core.GameMap
import com.wcecil.rts.common.pojo.core.Sprite
import com.wcecil.rts.common.pojo.core.Tile

@Component
@CompileStatic
public class GameResources {
	Map<String, Sprite> sprites;
	Map<String, Building> buildings;
	Map<String, Tile> tiles;
	Map<String, GameMap> maps;
}
