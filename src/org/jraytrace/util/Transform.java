package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Transform {

	private Matrix matrix;

	public Transform() {
		matrix = Matrix.createIdentityMatrix(4,4);
	}

	public Transform(Matrix matrix) {
		this.matrix = new Matrix(matrix);
	}

	public Transform getInverse() {
		return new Transform(matrix.inverse());
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public Matrix transform(Matrix matr) {
		Matrix ret;

		ret = matr.multiply(this.matrix); //this.matrix.multiply(matr);

		return ret;
	}

	public Vector3 transformLocation(Vector3 location) {
		Matrix locMatrix;
		Vector3 ret;

		locMatrix = location.toMatrix();
		locMatrix.resize(1,4);
		locMatrix.setValueAt(0,3,1);

		ret = new Vector3(transform(locMatrix));
		return ret;
	}

	public Vector3 transformVector(Vector3 vector) {
		Matrix vectMatrix;
		Vector3 ret;

		vectMatrix = vector.toMatrix();
		vectMatrix.resize(1,4);
		/* vectMatrix.setValueAt(0,3,0); */

		ret = new Vector3(transform(vectMatrix));
		return ret;
	}

	public Vector3 transformNormal(Vector3 normal) {
		Matrix normMatrix;
		Matrix transMatrix;
		Vector3 ret;

		normMatrix = normal.toMatrix();
		normMatrix.resize(1,4);
		/* normMatrix.setValueAt(0,3,0); */

		transMatrix = this.matrix.inverse().transpose();

		ret = new Vector3(transMatrix.multiply(normMatrix));
		return ret;
	}

	public static Transform createIdentity() {
		return new Transform();
	}

	public static Transform createTranslation(Vector3 translate) {
		Matrix matrix;
		Transform ret;

		matrix = Matrix.createIdentityMatrix(4,4);

		matrix.setValueAt(3,0,translate.x);
		matrix.setValueAt(3,1,translate.y);
		matrix.setValueAt(3,2,translate.z);

		ret = new Transform(matrix);
		return ret;
	}

	public static Transform createScale(Triple coeff) {
		Matrix matrix;
		Transform ret;

		matrix = Matrix.createIdentityMatrix(4,4);

		matrix.setValueAt(0,0,coeff.x);
		matrix.setValueAt(1,1,coeff.y);
		matrix.setValueAt(2,2,coeff.z);

		ret = new Transform(matrix);
		return ret;
	}

	public static Transform createRotationAboutX(double theta) {
		Matrix matrix;
		Transform ret;

		matrix = Matrix.createIdentityMatrix(4,4);

		matrix.setValueAt(1,1,Math.cos(theta));
		matrix.setValueAt(2,2,Math.cos(theta));
		matrix.setValueAt(1,2,Math.sin(theta));
		matrix.setValueAt(2,1,-Math.sin(theta));

		ret = new Transform(matrix);
		return ret;
	}

	public static Transform createRotationAboutY(double theta) {
		Matrix matrix;
		Transform ret;

		matrix = Matrix.createIdentityMatrix(4,4);

		matrix.setValueAt(0,0,Math.cos(theta));
		matrix.setValueAt(2,2,Math.cos(theta));
		matrix.setValueAt(0,2,-Math.sin(theta));
		matrix.setValueAt(2,0,Math.sin(theta));

		ret = new Transform(matrix);
		return ret;
	}

	public static Transform createRotationAboutZ(double theta) {
		Matrix matrix;
		Transform ret;

		matrix = Matrix.createIdentityMatrix(4,4);

		matrix.setValueAt(0,0,Math.cos(theta));
		matrix.setValueAt(1,1,Math.cos(theta));
		matrix.setValueAt(0,1,Math.sin(theta));
		matrix.setValueAt(1,0,-Math.sin(theta));

		ret = new Transform(matrix);
		return ret;
	}

	public static Transform createTransformToFrame(OrthonormalBase base,
			Vector3 origin) {
		Matrix matrix;
		Transform ret;

		matrix = new Matrix(base.getTransform().getMatrix());
		matrix.setValueAt(3,0,-origin.x);
		matrix.setValueAt(3,1,-origin.y);
		matrix.setValueAt(3,2,-origin.z);

		ret = new Transform(matrix);
		return ret;
	}
}