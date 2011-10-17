package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Vector2 implements Cloneable {

	public double x;
	public double y;

	public Vector2() {
		x=y=0;
	}

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 b) {
		this.x = b.x;
		this.y = b.y;
	}

	public void neg() {
		x = -x;
		y = -y;
	}

	public void scale(double k) {
		x *= k;
		y *= k;
	}

	public void add(Vector2 b) {
		x += b.x;
		y += b.y;
	}

	public void sub(Vector2 b) {
		x -= b.x;
		y -= b.y;
	}

	public void mult(Vector2 b) {
		x *= b.x;
		y *= b.y;
	}

	public void divide(Vector2 b) {
		x /= b.x;
		y /= b.y;
	}

	protected Object clone() {
		return (Object) new Vector2(x,y);
	}

	public Vector2 dup() {
		return (Vector2) this.clone();
	}
}