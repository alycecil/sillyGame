package com.wcecil.rts.webservice.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcecil.rts.common.beans.GameResources;
import com.wcecil.rts.common.pojo.core.Building;
import com.wcecil.rts.common.pojo.core.Sprite;
import com.wcecil.rts.common.pojo.core.Tile;

@RestController
@RequestMapping("/debug/resource")
public class ResourceDebugController {
	private @Autowired GameResources resources;

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
}
