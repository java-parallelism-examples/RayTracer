package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Color extends Triple {

	public Color() {
		super();
	}

	public Color(double r, double g, double b) {
		super(r,g,b);
	}

	public Color(Color b) {
		super((Triple) b);
	}

	public Color dup() {
		return (Color) this.clone();
	}

	public int toInt() {
		return (int) ((255 << 24) | /* opacity*/
			(((int)(x * 255)) << 16) |
			(((int)(y * 255)) << 8) |
			((int)(z * 255)));
	}
}