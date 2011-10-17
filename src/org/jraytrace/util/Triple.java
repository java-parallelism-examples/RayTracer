package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Triple implements Cloneable {

	public double x;
	public double y;
	public double z;

	public Triple() {
		x=y=z;
	}

	public Triple(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Triple(Triple b) {
		this.x = b.x;
		this.y = b.y;
		this.z = b.z;
	}

	public Matrix toMatrix() {
		double[][] data = new double[1][3];
		data[0][0] = x;
		data[0][1] = y;
		data[0][2] = z;
		return new Matrix(data);
	}

	public void neg() {
		x = -x;
		y = -y;
		z = -z;
	}

	public void scale(double k) {
		x = x*k;
		y = y*k;
		z = z*k;
	}

	public void add(Triple b) {
		x += b.x;
		y += b.y;
		z += b.z;
	}

	public void sub(Triple b) {
		x -= b.x;
		y -= b.y;
		z -= b.z;
	}

	public void mult(Triple b) {
		x *= b.x;
		y *= b.y;
		z *= b.z;
	}

	public void divide(Triple b) {
		x /= b.x;
		y /= b.y;
		z /= b.z;
	}

	protected Object clone() {
		return (Object) new Triple(x,y,z);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Triple) {
			Triple b = (Triple) obj;
			return ((b.x == this.x) && (b.y == this.y) && (b.z == this.z));
		} else {
			return false;
		}
	}

	public double maxComponent() {
		return Math.max(x, Math.max(y, z));
	}

}
