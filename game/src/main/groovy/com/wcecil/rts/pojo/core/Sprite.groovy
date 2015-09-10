package com.wcecil.rts.pojo.core;

import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

@Canonical
@CompileStatic
public class Sprite {
	def name
	def normal
	def damaged
	def heavyDamaged
	def directional
}
