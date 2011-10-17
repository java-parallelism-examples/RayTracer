package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Ray {

	public Vector3 position;
	public Vector3 direction;

	public Ray() {
		position = new Vector3();
		direction = new Vector3();
	}

	public Ray(Vector3 position, Vector3 direction) {
		this.position=position;
		this.direction = direction;
	}

	public Vector3 pointOn(double t) {
		Vector3 ret = new Vector3(direction);
		ret.scale(t);
		ret.add(position);
		return ret;
	}

	public boolean isPointOn(Vector3 pt) {
		double t = position.distanceTo(pt);
		Vector3 dup = pointOn(t);
		if (dup.equals(pt)) {
			return true;
		} else {
			return false;
		}
	}
}