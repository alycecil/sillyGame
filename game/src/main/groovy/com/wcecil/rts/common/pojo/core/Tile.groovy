package com.wcecil.rts.common.pojo.core;

import groovy.transform.Canonical
import groovy.transform.CompileStatic

import com.wcecil.rts.common.pojo.core.abstracts.HasAverageColor

@Canonical
@CompileStatic
public class Tile extends HasAverageColor {
	def name
	def normal
	boolean moveable
	boolean buildable
	boolean tube
}
