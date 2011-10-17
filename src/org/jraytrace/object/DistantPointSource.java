package org.jraytrace.object;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import org.jraytrace.util.*;

public class DistantPointSource implements Surface, LightSource{

	private Vector3 direction;
	private Color color;

	public DistantPointSource() {
		this(new Vector3(0, 0, -1), new Color(1, 1, 1));
	}

	public DistantPointSource(Vector3 direction, Color color) {
		this.direction = direction;
		this.color = color;
	}

	public Color getLightColor() {
		return color;
	}

	public Ray getRayToLight(Vector3 pt) {
		Ray ret;
		Vector3 dir = new Vector3(direction);
		dir.neg();
		ret = new Ray(pt, dir);
		return ret;
	}

	public double getT(Ray r) {
		return getT(r, Interval.realNumbers);
	}
	public double getT(Ray r, Interval i) {
		return Double.POSITIVE_INFINITY;
	}
	public boolean intersects(Ray r) {
		//return r.isPointOn(center);
		return false;
	}

	public Intersection hit(Ray r) {
		return hit(r, Interval.realNumbers);
	}
	public Intersection hit(Ray r, Interval i) {
		double t;
		Intersection ret;

		t = getT(r, i);

		if (!(Double.isNaN(t))) {
			Vector3 ptOnSurf = r.pointOn(t);
			Vector3 normal = new Vector3(r.direction);
			normal.neg();
			ret = new Intersection(this, r, i, ptOnSurf, normal, color, t);
		} else {
			ret = null;
		}
		return ret;
	}
}