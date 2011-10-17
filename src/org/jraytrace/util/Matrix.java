package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Matrix {

	private double[][] data;
	private int h;
	private int w;

	public Matrix() {
		data = new double[1][1];
		data[0][0] = 0;
		h=w=1;
	}

	public Matrix(int h, int w) {
		int min;
		data = new double[h][w];
		min = Math.min(h,w);
		this.h = h;
		this.w = w;
	}

	public Matrix(double[][] data) {
		h = data.length;
		w = data[0].length;
		this.data = new double[h][w];
		for (int i=0; i < h; i++) {
			for (int j=0; j < w; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	public Matrix(Matrix mat) {
		this(mat.data);
	}

	public Matrix inverse() {
		double[][] rot;
		double[][] inverse;
		int i;
		int j;
		int k;
		double t;
		rot = new double[h][2 * w];
		//create rot matrix
		for (i=0; i < h; i++) {
			for (j=0; j < w; j++) {
				rot[i][j] = data[i][j];
			}
			for (j=w; j < w * 2; j++) {
				if (j == i + w) {
					rot[i][j] = 1;
				} else {
					rot[i][j] = 0;
				}
			}
		}
		//rotate to solution
		for (i=0; i < h; i++) {
			t = rot[i][i];
			//Create a one in the identity spot
			for (j=0; j < w * 2; j++) {
				rot[i][j] /= t;
			}
			//Create zeros in the column
			for (j=0; j < h; j++) {
				if (j != i) {
					t = rot[j][i];
					for (k=0; k < w * 2; k++) {
						rot[j][k] = rot[j][k] - (t * rot[i][k]);
					}
				}
			}
		}
		inverse = new double[h][w];
		for (i=0; i < h; i++) {
			for (j=0; j < w; j++) {
				inverse[i][j] = rot[i][j+w];
			}
		}
		return new Matrix(inverse);
	}

	public Matrix transpose() {
		double[][] transpose;
		int i;
		int j;

		transpose = new double[w][h];
		for (i = 0; i < w; i++) {
			for (j = 0; j < h; j++) {
				transpose[i][j] = data[j][i];
			}
		}
		return new Matrix(transpose);
	}

	public Matrix multiply(Matrix b) {
		double[][] product;
		int i;
		int j;
		int k;

		if (b.h != this.w) {
			return null;
		}

		product = new double[this.h][b.w];
		for (i = 0; i < this.h; i++) {
			for (j = 0; j < b.w; j++) {
				for (k = 0; k < this.w; k++) {
					double x = this.data[i][k];
					x *= b.data[k][j];
					product[i][j] += x;
					 //product[i][j] += this.data[i][k] * b.data[k][j];
				}
			}
		}
		return new Matrix(product);
	}

	public double getValueAt(int row, int col) {
		return data[row][col];
	}

	public void setValueAt(int row, int col, double val) {
		data[row][col] = val;
	}

	public int getHeight() {
		return h;
	}

	public int getWidth() {
		return w;
	}

	public void resize(int h, int w) {
		double[][] temp;

		if ((this.h != h) || (this.w != w)) {
			temp = new double[h][w];
			int mh = Math.min(h,this.h);
			int mw = Math.min(w,this.w);
			for (int i = 0; i < mh; i++) {
				for (int j = 0; j < mw; j++) {
					temp[i][j] = this.data[i][j];
				}
			}
			this.data = temp;
			this.h = h;
			this.w = w;
		}
	}

	public static Matrix createIdentityMatrix(int h, int w) {
		double[][] data;
		int min;
		data = new double[h][w];
		min = Math.min(h,w);
		for (int i=0; i < min; i++) {
			data[i][i] = 1;
		}
		return new Matrix(data);
	}
}