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

public class PointSource extends Sphere implements LightSource {

	public PointSource() {
		super(new Vector3(0,0,0), EPSILON, new Color(1,1,1));
	}

	public PointSource(Vector3 pos, Color color) {
		super(pos, EPSILON, color);
	}

	public Color getLightColor() {
		return getColor();
	}

	public Ray getRayToLight(Vector3 pt) {
		Ray ret;
		Vector3 dir = new Vector3(getCenter());

		dir.sub(pt);
		dir.unify();
		ret = new Ray(pt, dir);
		return ret;
	}

	public Intersection hit(Ray r) {
		return hit(r, Interval.realNumbers);
	}

	public Intersection hit(Ray r, Interval i) {
		Intersection ret = super.hit(r, i);
		if (ret != null) {
			Color color = ret.getMatteColor();
			color.scale(1 / Math.pow(ret.getT(), 2));
			//ret.setSurface(this);
			ret.setMatteColor(color);
		}
		return ret;
	}

}
