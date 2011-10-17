package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class OrthonormalBase {

	private Vector3 u;
	private Vector3 v;
	private Vector3 w;
	private Transform rotation;

	public static OrthonormalBase conanicalBase = new OrthonormalBase();

	public OrthonormalBase() {
		u = new Vector3(1,0,0);
		v = new Vector3(0,1,0);
		w = new Vector3(0,0,1);
		rotation = new Transform();
	}

	public OrthonormalBase(Vector3 u, Vector3 v, Vector3 w) {
		this.u = u.dup();
		this.u.unify();
		this.v = v.dup();
		this.v.unify();
		this.w = w.dup();
		this.w.unify();
		createRotation();
	}

	public static OrthonormalBase createFromUV(Vector3 u, Vector3 v) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.u = u.dup();
		ret.u.unify();
		ret.w = u.cross(v);
		ret.w.unify();
		ret.v = ret.w.cross(ret.u);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromVU(Vector3 v, Vector3 u) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.v = v.dup();
		ret.v.unify();
		ret.w = v.cross(u);
		ret.w.unify();
		ret.u = ret.w.cross(ret.v);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromVW(Vector3 v, Vector3 w) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.v = v.dup();
		ret.v.unify();
		ret.u = v.cross(w);
		ret.u.unify();
		ret.w = ret.u.cross(ret.v);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromWV(Vector3 w, Vector3 v) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.w = w.dup();
		ret.w.unify();
		ret.u = w.cross(v);
		ret.u.unify();
		ret.v = ret.u.cross(ret.w);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromUW(Vector3 u, Vector3 w) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.u = u.dup();
		ret.u.unify();
		ret.v = u.cross(w);
		ret.v.unify();
		ret.w = ret.v.cross(ret.u);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromWU(Vector3 w, Vector3 u) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.w = w.dup();
		ret.w.unify();
		ret.v = w.cross(u);
		ret.v.unify();
		ret.u = ret.v.cross(ret.w);
		ret.createRotation();
		return ret;
	}

	public static OrthonormalBase createFromW(Vector3 a) {
		OrthonormalBase ret = new OrthonormalBase();
		ret.w = a.dup();
		ret.w.unify();
		if ((Math.abs(ret.w.x) < Math.abs(ret.w.y)) &&
			(Math.abs(ret.w.x) < Math.abs(ret.w.z))) {
			ret.v = new Vector3(0, ret.w.z, -ret.w.y);
			ret.v.unify();
		} else if (Math.abs(ret.w.y) < Math.abs(ret.w.z)) {
			ret.v = new Vector3(ret.w.z, 0, -ret.w.x);
			ret.v.unify();
		} else {
			ret.v = new Vector3(ret.w.y, -ret.w.x, 0);
			ret.v.unify();
		}
		ret.u = ret.v.cross(ret.w);
		ret.createRotation();
		return ret;
	}

	public Vector3 getU() {
		return u.dup();
	}

	public Vector3 getV() {
		return v.dup();
	}

	public Vector3 getW() {
		return w.dup();
	}

	public Vector3 convertToBase(Vector3 a) {
		return new Vector3(a.dot(this.u), a.dot(this.v), a.dot(this.w));
	}

	public Transform getTransform() {
		return rotation;
	}

	private void createRotation() {
		Matrix matrix;
		matrix = Matrix.createIdentityMatrix(4,4);
		matrix.setValueAt(0,0,this.u.x);
		matrix.setValueAt(0,1,this.u.y);
		matrix.setValueAt(0,2,this.u.z);
		matrix.setValueAt(1,0,this.v.x);
		matrix.setValueAt(1,1,this.v.y);
		matrix.setValueAt(1,2,this.v.z);
		matrix.setValueAt(2,0,this.w.x);
		matrix.setValueAt(2,1,this.w.y);
		matrix.setValueAt(2,2,this.w.z);
		rotation = new Transform(matrix);
	}
}