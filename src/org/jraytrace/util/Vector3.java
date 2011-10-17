package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Vector3 extends Triple {

	public Vector3() {
		super();
	}

	public Vector3(double x, double y, double z) {
		super(x,y,z);
	}

	public Vector3(Vector3 b) {
		super((Triple) b);
	}

	public Vector3(Matrix matrix) {
		super();
		x = matrix.getValueAt(0,0);
		y = matrix.getValueAt(0,1);
		z = matrix.getValueAt(0,2);
	}

	public void unify() {
		this.scale(1/this.length());
	}

	public double dot(Vector3 b) {
		double ret;
		ret = x * b.x;
		ret += y * b.y;
		ret += z * b.z;
		return ret;
	}

	public Vector3 cross(Vector3 b) {
		return new Vector3(y * b.z - z * b.y,
			   z * b.x - x * b.z,
			   x * b.y - y * b.x);
	}

	public double length() {
		return Math.sqrt(lengthSquared());
	}

	public double lengthSquared() {
		return (x * x + y * y + z * z);
	}

	public double distanceTo(Vector3 pt) {
		double dx, dy, dz;
		dx = pt.x - x;
		dy = pt.y - y;
		dz = pt.z - z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public Vector3 dup() {
		return new Vector3(this);
	}
}