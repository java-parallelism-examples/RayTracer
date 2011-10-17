package org.jraytrace.object;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.MemoryImageSource;
import java.util.Enumeration;

import org.jraytrace.util.Color;
import org.jraytrace.util.CoordinateSystem;
import org.jraytrace.util.OrthonormalBase;
import org.jraytrace.util.Ray;
import org.jraytrace.util.Vector3;

public class Camera {

	final private static double filmHeight = .035; // 'meters'

	private CoordinateSystem ref;

	private Dimension resolution;
	private MemoryImageSource imageSource;
	private Color[][] image;
	private int[] imageBuffer;
	private double maxColor;
	private double focalLength; // 'meters'
	private double filmWidth;

	public Camera(Dimension displaySize) {
		int h = displaySize.height;
		int w = displaySize.width;
		resolution = displaySize;
		ref = new CoordinateSystem(OrthonormalBase.createFromWV(new Vector3(0,
				0, -1), new Vector3(0, 1, 0)), new Vector3(0, 0, 0));
		focalLength = .05;

		image = new Color[h][w];
		imageBuffer = new int[h * w];

		imageSource = new MemoryImageSource(w, h, imageBuffer, 0, w);
		maxColor = 0;
		filmWidth = w * (filmHeight / h);
	}

	public void setResolution(Dimension res) {
		int h = res.height;
		int w = res.width;
		if ((resolution.width != res.width)
				&& (resolution.height != res.height)) {
			image = null;
			imageBuffer = null;
			imageSource = null;
			System.gc();
			image = new Color[h][w];
			imageBuffer = new int[h * w];
			imageSource = new MemoryImageSource(w, h, imageBuffer, 0, w);
			maxColor = 0;
			filmWidth = w * (filmHeight / h);
		}

		imageSource.newPixels();
		resolution = res;
	}

	public Dimension getResolution() {
		return resolution;
	}

	public Vector3 getFilmPos() {
		return ref.getOrigin();
	}

	public void setFilmPos(Vector3 position) {
		ref.setOrigin(position);
	}

	public Vector3 getGazeDirection() {
		Vector3 ret = new Vector3(ref.getBase().getW());
		ret.neg();
		return ret;
	}

	public void setGazeDirection(Vector3 direction) {
		CoordinateSystem newRef;
		newRef = new CoordinateSystem(OrthonormalBase.createFromWV(direction,
				ref.getBase().getV()), ref.getOrigin());
		ref = newRef;
	}

	public Vector3 getCameraUp() {
		return new Vector3(ref.getBase().getV());
	}

	public void setCameraUp(Vector3 up) {
		CoordinateSystem newRef;
		newRef = new CoordinateSystem(OrthonormalBase.createFromWV(ref
				.getBase().getW(), up), ref.getOrigin());
		ref = newRef;
	}

	public CoordinateSystem getCoordinateSystem() {
		return ref;
	}

	public void setCoordinateSystem(CoordinateSystem sys) {
		ref = sys;
	}

	public MemoryImageSource getImageSource() {
		return imageSource;
	}

	public Ray getRayForPoint(Point pt) {
		Vector3 rayPos;
		Vector3 rayDir;
		Vector3 camDir;
		double filmX;
		double filmY;

		filmX = (((double) pt.x / resolution.width) * filmWidth)
				- (filmWidth / 2);
		filmY = (filmHeight / 2)
				- (((double) pt.y / resolution.height) * filmHeight);

		camDir = new Vector3(filmX, filmY, focalLength);

		rayDir = ref.getBase().getTransform().transformVector(camDir);
		rayPos = new Vector3(ref.getOrigin());
		return new Ray(rayPos, rayDir);
	}

	public void setPixelRow(Point[] pts, Color[] ptColors) {
		for (int i = 0; i < pts.length; i++) {
			Point pt = pts[i];
			Color ptColor = ptColors[i];
			image[pt.y][pt.x] = new Color(ptColor);
			imageBuffer[resolution.width * pt.y + pt.x] = ptColor.toInt();
			maxColor = Math.max(maxColor, ptColor.maxComponent());
			if (pt.x == resolution.width - 1) {
				imageSource.newPixels(0, pt.y, resolution.width, 1, false);
			}
		}
	}

	public void setPixelColor(Point pt, Color ptColor) {
		image[pt.y][pt.x] = new Color(ptColor);
		imageBuffer[resolution.width * pt.y + pt.x] = ptColor.toInt();
		maxColor = Math.max(maxColor, ptColor.maxComponent());
		if (pt.x == resolution.width - 1) {
			imageSource.newPixels(0, pt.y, resolution.width, 1, false);
		}
	}

	public void doAutoExposure() {
		if (maxColor > 0) {
			double scaleFactor = 1 / maxColor;
			int w = resolution.width;
			int h = resolution.height;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					image[i][j].scale(scaleFactor);
					imageBuffer[w * i + j] = image[i][j].toInt();
				}
				imageSource.newPixels(0, i, w, 1, false);
			}
		}
		imageSource.newPixels();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}