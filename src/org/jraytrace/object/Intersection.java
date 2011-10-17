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

public class Intersection {

	private Surface surf;
	private Ray r;
	private Interval i;
	private Vector3 inter;
	private Vector3 normal;
	private Color matteColor;
	private double t;

	public Intersection() {
		surf = null;
		r = null;
		i = null;
		inter = null;
		t = Double.NaN;
		matteColor = null;
		normal = null;
	}

	public Intersection(Surface surf, Ray r, Interval i, Vector3 inter,
		Vector3 normal, Color matteColor, double t)
	{
		this.surf = surf;
		this.r = r;
		this.i = i;
		this.inter = inter;
		this.normal = normal;
		this.matteColor = matteColor;
		this.t = t;
	}

	public void setSurface(Surface surf) {
		this.surf = surf;
	}
	public Surface getSurface() {
		return surf;
	}

	public void setRay(Ray r) {
		this.r = r;
	}
	public Ray getRay() {
		return r;
	}

	public void setInterval(Interval i) {
		this.i = i;
	}
	public Interval getInterval() {
		return i;
	}

	public void setIntersection(Vector3 pt) {
		this.inter = pt;
	}
	public Vector3 getIntersection() {
		return this.inter;
	}

	public void setT(double t) {
		this.t = t;
	}
	public double getT() {
		return t;
	}

	public void setNormal(Vector3 normal) {
		this.normal = normal;
	}
	public Vector3 getNormal() {
		return this.normal;
	}

	public void setMatteColor(Color matteColor) {
		this.matteColor = matteColor;
	}
	public Color getMatteColor() {
		return this.matteColor;
	}
}