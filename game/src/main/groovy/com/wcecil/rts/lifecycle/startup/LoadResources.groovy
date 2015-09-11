package com.wcecil.rts.lifecycle.startup;

import javax.imageio.ImageIO

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.wcecil.rts.common.beans.GameResources
import com.wcecil.rts.common.pojo.core.Building
import com.wcecil.rts.common.pojo.core.GameMap
import com.wcecil.rts.common.pojo.core.Sprite
import com.wcecil.rts.common.pojo.core.Tile
import com.wcecil.rts.common.settings.ApplicationSettings
import com.wcecil.rts.common.util.StreamUtils
import com.wcecil.rts.lifecycle.BasicLifecycle

@Component
public class LoadResources extends BasicLifecycle{
	private ObjectMapper mapper = new ObjectMapper()

	private @Autowired GameResources gameResources;
	private @Autowired ApplicationSettings settings;

	public def load(){
		println 'Loading...'
		try{
			gameResources.sprites = loadSprites(
					StreamUtils.convertStreamToString(
					getStream(settings.getSprites())
					)
					)

			gameResources.buildings = loadBuildings(
					StreamUtils.convertStreamToString(
					getStream(settings.getBuildings())
					)
					)

			gameResources.tiles = loadTiles(
					StreamUtils.convertStreamToString(
					getStream(settings.getTiles())
					)
					)

			gameResources.maps = loadMaps(
					StreamUtils.convertStreamToString(
					getStream(settings.getMaps())
					)
					)
			println 'Ready!'
		}catch(Throwable t){
			t.printStackTrace()
			System.exit(1)
		}
	}

	private def getStream(def name){
		this.getClass().getClassLoader().getResourceAsStream(name)
	}

	private Map<String, GameMap> loadMaps(String json){
		Map<String, GameMap> map = new HashMap<>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, String.class)
		try {
			List<String> values = mapper.readValue(json, type)

			values?.each { String name ->
				try{
					def mapDefn = StreamUtils.convertStreamToString(getStream(name))

					def m = new GameMap();

					Integer width = null;
					
					def tilesList = [] as List

					mapDefn.eachLine { String line ->
						line = line.trim()

						if(line.startsWith('!')){
							m.name=line.substring 1;
						}else if(line.startsWith('~')){
							if(m.description){
								m.description = "${m.description}<br>\n${line.substring(1)}"
							}else{
								m.description=line.substring 1
							}
						}else if(line.startsWith('#')){
							//ignore comment
						}else{
							def rawTiles = line.split("\\W+");
							if(width){
								if(width!=rawTiles.length){
									throw new IllegalStateException("Unable to read map as lengths are inconsistent [expected ${width}, found ${rawTiles.length}]");
								}
							}else{
								width = rawTiles.length;
							}
							def tiles = []

							int i = 0;

							rawTiles.each{ i++; 
								it = it.trim();
								def tile = gameResources.tiles.get(it);
								
								if(!tile){
									tile = new Tile(name: it)
									tile.avgColor = settings.unknownColor
								}
								
								tiles << tile
							}
							
							tilesList << tiles.toArray()
						}
					}
					
					if(!m.name){
						m.name = name
					}
					
					m.tiles = tilesList.toArray()
					
					map.put m.name, m
				}catch (e) {
					System.err.println("Error reading map [$name], $e; stack trace to follow")
					e.printStackTrace()
				}
			}
		} catch (e) {
			System.err.println("Error reading Maps, $e; stack trace to follow")
			e.printStackTrace()
		}
		
		return map;
	}

	private Map<String, Building> loadBuildings(String json){
		Map<String, Building> map = new HashMap<>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Building.class)
		try {
			List<Building> values = mapper.readValue(json, type)

			values?.each {

				it.sprite = gameResources.sprites.get(it.sprite);

				map.put(it.name, it);
			}
		} catch (e) {
			System.err.println("Error reading Buildings, $e; stack trace to follow")
			e.printStackTrace()
		}
		return map;
	}

	private Map<String, Tile> loadTiles(String json){
		Map<String, Tile> map = new HashMap<>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Tile.class)
		try {
			List<Tile> values = mapper.readValue(json, type)

			values?.each {
				if(!it.name){
					System.err.println 'All tiles must have a name, or they are not loadable'
				}else{
					if(it.normal!=null) {
						it.avgColor = calcAverageColor(it.normal)

						map.put(it.name, it);
					}else{
						System.err.println "Must specify a normal image for ${it.name}"
					}
				}
			}
		} catch (e) {
			System.err.println("Error reading tiles, $e; stack trace to follow")
			e.printStackTrace()
		}
		return map;
	}

	private Map<String, Sprite> loadSprites(String json){
		Map<String, Sprite> map = new HashMap<>();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Sprite.class)
		try {
			List<Sprite> values = mapper.readValue(json, type)

			values?.each {
				if(!it.name){
					System.err.println 'All Sprites must have a name, or they are not loadable'
				}else{
					if(it.normal!=null) {
						it.avgColor = calcAverageColor(it.normal)

						map.put(it.name, it);
					}else{
						System.err.println "Must specify a normal image for ${it.name}"
					}
				}
			}
		} catch (e) {
			System.err.println("Error reading Sprites, $e; stack trace to follow")
			e.printStackTrace()
		}
		return map;
	}

	private def calcAverageColor(def name){
		def image = ImageIO.read(getStream(name))
		def array = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())

		int redBucket = 0;
		int greenBucket = 0;
		int blueBucket = 0;
		int pixelCount = 0;
		array.each { int color ->
			if(((color as int) & 0xFFFFFF) != 0){
				pixelCount++;
				redBucket += (color >> 16) & 0xFF; // Color.red
				greenBucket += (color >> 8) & 0xFF; // Color.greed
				blueBucket += (color & 0xFF);
			}
		}


		int rawColor = ((redBucket / pixelCount) as int) << 16 |
				((greenBucket / pixelCount) as int) << 8 |
				((blueBucket / pixelCount) as int);

		return "#${Integer.toHexString(rawColor)}" as String
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
