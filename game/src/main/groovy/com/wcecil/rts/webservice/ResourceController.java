package com.wcecil.rts.webservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.rts.common.beans.GameResources;
import com.wcecil.rts.common.pojo.core.Building;
import com.wcecil.rts.common.pojo.core.GameMap;
import com.wcecil.rts.common.pojo.core.Sprite;
import com.wcecil.rts.common.pojo.core.Tile;
import com.wcecil.rts.common.settings.ApplicationSettings;

@RestController
@RequestMapping("/resource")
public class ResourceController {
	private @Autowired GameResources resources;
	private @Autowired ApplicationSettings settings;


	@RequestMapping("/buildings")
	public Map<String, Building> getBuildings(){
		return resources.getBuildings();
	}
	
	@RequestMapping("/sprites")
	public Map<String, Sprite> getSprites(){
		return resources.getSprites();
	}
	
	@RequestMapping("/tiles")
	public Map<String, Tile> getTiles(){
		return resources.getTiles();
	}
	
	@RequestMapping("/maps")
	public Map<String, GameMap> getMaps(){
		return resources.getMaps();
	}
	
	@RequestMapping("/tile/size")
	public Integer getTileSize(){
		return settings.getTileSize();
	}
}
