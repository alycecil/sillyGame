package com.wcecil.rts.common.settings

import groovy.transform.CompileStatic;

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.*

@Component
@CompileStatic
class ApplicationSettings {
	
	@Value('${resource.sprites}')
	String sprites
	
	@Value('${resource.buildings}')
	String buildings
	
	@Value('${allow.debug}')
	Boolean allowDebug
}
