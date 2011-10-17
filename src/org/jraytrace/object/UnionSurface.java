package org.jraytrace.object;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import java.util.Vector;
import org.jraytrace.util.*;

public class UnionSurface implements CSGSurface {

	private Surface right;
	private Surface left;

	public UnionSurface(Surface right, Surface left) {
		this.right = right;
		this.left = left;
	}

	public Surface getRight() {
		return right;
	}

	public void setRight(Surface right) {
		this.right = right;
	}

	public Surface getLeft() {
		return left;
	}

	public void setLeft(Surface left) {
		this.left = left;
	}

	public Vector getLightSources() {
		Vector ret = new Vector();
		CSGSurface tempCSG;

		if (left instanceof CSGSurface) {
			tempCSG = (CSGSurface) left;
			ret.addAll(tempCSG.getLightSources());
		} else if (left instanceof LightSource) {
			ret.add(left);
		}
		if (right instanceof CSGSurface) {
			tempCSG = (CSGSurface) right;
			ret.addAll(tempCSG.getLightSources());
		} else if (right instanceof LightSource) {
			ret.add(right);
		}
		return ret;
	}

	public Ray getRayToLight(LightSource light, Vector3 point) {
		Ray ret = null;

		if (left instanceof CSGSurface) {
			ret = ((CSGSurface) left).getRayToLight(light, point);
		} else if (left.equals(light)) {
			ret = ((LightSource) left).getRayToLight(point);
		}
		if (ret == null) {
			if (right instanceof CSGSurface) {
				ret = ((CSGSurface) right).getRayToLight(light, point);
			} else if (right.equals(light)) {
				ret = ((LightSource) right).getRayToLight(point);
			}
		}
		return ret;
	}

	public double getT(Ray r) {
		return getT(r, Interval.realNumbers);
	}
	public double getT(Ray r, Interval i) {
		double rT;
		double lT;

		lT = left.getT(r, i);
		rT = right.getT(r, i);

		if (Double.isNaN(lT) && Double.isNaN(rT)) { //hit neither
			return Double.NaN;
		} else if (Double.isNaN(lT)) {  //hit right only
			return rT;
		} else if (Double.isNaN(rT)) {  //hit left only
			return lT;
		} else if (lT <= rT) {  //hit left first
			return lT;
		} else {    //hit right first
			return rT;
		}
	}
	public boolean intersects(Ray r) {
		return !Double.isNaN(getT(r));
	}

	public Intersection hit(Ray r) {
		return hit(r, Interval.realNumbers);
	}
	public Intersection hit(Ray r, Interval i) {
		Intersection lI;
		Intersection rI;
		double rT;
		double lT;

		lT = left.getT(r, i);
		rT = right.getT(r, i);

		if (Double.isNaN(lT) && Double.isNaN(rT)) { //hit neither
			return null;
		} else if (Double.isNaN(lT)) {  //hit right only
			return right.hit(r,i);
		} else if (Double.isNaN(rT)) {  //hit left only
			return left.hit(r,i);
		} else if (lT <= rT) {  //hit left first
			return left.hit(r,i);
		} else {    //hit right first
			return right.hit(r,i);
		}
	}
}