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

public class Sphere implements Surface {

	private Vector3 center;
	private double radius;
	private Color color;

	public Sphere() {
		center = new Vector3();
		radius = 0;
		color = new Color(1,1,1);
	}

	public Sphere(Vector3 center, double radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	public double getT(Ray r) {
		return getT(r, Interval.realNumbers);
	}

	public double getT(Ray r, Interval i) {
		double t;
		double dirdotoc;
		double d;
		double dd;
		Vector3 oc;

		d = calcDiscriminate(r);

		if (d >= 0) {
			if (d > 0) {
				d = Math.sqrt(d);
			}
			oc = r.position.dup();
			oc.sub(center);
			dirdotoc = r.direction.dot(oc);
			dd = r.direction.dot(r.direction);
			t = (-dirdotoc - d) / dd;
			if (!i.isIn(t)) { //this should be the low one...
				if ( d != 0 ) {
					t = (-dirdotoc + d) / dd;
					if (!i.isIn(t)) { //neither one is in it.
						t = Double.NaN;
					}
				} else {
					t = Double.NaN;
				}
			}
		} else {
			t = Double.NaN;
		}

		return t;
	}

	public boolean intersects(Ray r) {
		double d;

		d = calcDiscriminate(r);

		return (d >= 0);
	}

	public Intersection hit(Ray r) {
		return hit(r, Interval.realNumbers);
	}

	public Intersection hit(Ray r, Interval i) {
		double t;
		Intersection ret;

		t = getT(r, i);

		if (!Double.isNaN(t)) {
			Vector3 ptOnSurf = r.pointOn(t);
			Vector3 normal = new Vector3(ptOnSurf);
			normal.sub(center);
			normal.unify();
			ret = new Intersection(this, r, i, ptOnSurf, normal, new Color(color), t);
		} else {
			ret = null;
		}
		return ret;
	}

	protected double calcDiscriminate(Ray r) {
		double b;
		double ac;
		double ddotd;
		Vector3 oc;

		oc = r.position.dup();
		oc.sub(center);

		b = r.direction.dot(oc);
		ddotd = r.direction.dot(r.direction);
		ac = oc.dot(oc) - radius * radius;
		ac = ddotd * ac;

		return (b * b - ac);
	}

	protected Color getColor() {
		return new Color(color);
	}

	protected void setColor(Color color) {
		this.color = color;
	}

	protected Vector3 getCenter() {
		return center;
	}

	protected void setCenter(Vector3 center) {
		this.center = new Vector3(center);
	}

	protected double getRadius() {
		return radius;
	}

	protected void setRadius(double radius) {
		this.radius = radius;
	}
}