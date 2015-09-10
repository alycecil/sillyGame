package com.wcecil.rts.common.pojo.core;

import com.wcecil.rts.common.pojo.core.abstracts.HasAverageColor
import groovy.transform.Canonical;
import groovy.transform.CompileStatic;

@Canonical
@CompileStatic
public class Sprite extends HasAverageColor{
	def name
	def normal
	def damaged
	def heavyDamaged
	def directional
}
