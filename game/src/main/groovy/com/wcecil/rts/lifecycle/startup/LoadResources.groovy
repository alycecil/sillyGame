package com.wcecil.rts.lifecycle.startup;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.rts.common.settings.ApplicationSettings
import com.wcecil.rts.common.util.StreamUtils
import com.wcecil.rts.lifecycle.BasicLifecycle
import com.wcecil.rts.pojo.core.Building
import com.wcecil.rts.pojo.core.Sprite

@Component
public class LoadResources extends BasicLifecycle{
	ObjectMapper mapper = new ObjectMapper()
	Map<String, Sprite> sprites;
	Map<String, Building> buildings;

	@Autowired ApplicationSettings settings;

	public def load(){
		println 'Loading...'
		try{
			sprites = loadSprites(
					StreamUtils.convertStreamToString(
					this.getClass().getClassLoader().getResourceAsStream(settings.getSprites())
					)
					)

			println 'Ready!'
		}catch(Throwable t){
			t.printStackTrace()
			System.exit(1)
		}
	}


	private Map<String, Sprite> loadSprites(String json){
		Map<String, Sprite> map = new HashMap<>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Sprite.class)
		try {
			List<Sprite> myCards = mapper.readValue(json, type)

			//TODO load images

			myCards?.each {
				map.put(it.name, it);
			}
		} catch (e) {
			System.err.println("Error reading sprites, $e; stack trace to follow")
			e.printStackTrace()
		}
		return map;
	}


	@Override
	public void start() {
		super.start()
		load();
	}

	@Override
	public int getPhase() {
		return 0;
	}
}
