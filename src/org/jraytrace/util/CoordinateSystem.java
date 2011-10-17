package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class CoordinateSystem {

	private OrthonormalBase base;
	private Vector3 origin;

	public CoordinateSystem() {
		base = new OrthonormalBase();
		origin = new Vector3();
	}

	public CoordinateSystem(OrthonormalBase base, Vector3 origin) {
		this.base = base;
		this.origin = origin;
	}

	public OrthonormalBase getBase() {
		return base;
	}
	public void setBase(OrthonormalBase base) {
		this.base = base;
	}

	public Vector3 getOrigin() {
		return origin;
	}
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
}