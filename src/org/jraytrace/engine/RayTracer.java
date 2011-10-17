package org.jraytrace.engine;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import org.jraytrace.object.CSGSurface;
import org.jraytrace.object.Camera;
import org.jraytrace.object.Intersection;
import org.jraytrace.object.LightSource;
import org.jraytrace.object.Surface;
import org.jraytrace.util.Color;
import org.jraytrace.util.Interval;
import org.jraytrace.util.Ray;


public abstract class RayTracer {

	private CSGSurface objects;
	private Vector lightSources;
	protected Camera viewer;
	
	protected int width;
	protected int height;
	protected static final int maxRecursion = 10;
	
	public abstract void trace();
	
	public RayTracer(Dimension displaySize) {
		objects = null;
		width= displaySize.width;
		height= displaySize.height;
		Camera cam = new Camera(displaySize);
		setCamera(cam);
	}

	public void setScene(CSGSurface object) {
		objects = object;
	}

	public CSGSurface getScene() {
		return objects;
	}

	public void setCamera(Camera cam) {
		viewer = cam;
	}

	public Camera getCamera() {
		return viewer;
	}
	protected Color recursiveTrace(Ray ray, int depth) {
		Color ret;

		if (objects.intersects(ray)) { //hit
			Intersection hitObj = objects.hit(ray,Interval.positiveReals);
			if (null != hitObj) {
				ret = illuminate(hitObj);
				ret.mult(hitObj.getMatteColor());
			} else {
				ret = new Color(0,1,0);
			}
		} else {
			ret = new Color(0,0,0);
		}
		return ret;
	}

	protected Color illuminate(Intersection intersect) {
		Enumeration<LightSource> sources = objects.getLightSources().elements();
		LightSource source;
		Ray rayToLight;
		Intersection lightData = null;
		Color lightColor = new Color(0,0,0);

		while (sources.hasMoreElements()) {
			Color tempColor;
			Surface tempSurf;
			source = (LightSource) sources.nextElement();
			rayToLight = objects.getRayToLight(source, intersect.getIntersection());
			lightData = objects.hit(rayToLight, new Interval(Surface.EPSILON, Double.POSITIVE_INFINITY));
			if (lightData != null) {
				tempSurf = lightData.getSurface();
				if (tempSurf instanceof LightSource) {
					if (((LightSource) tempSurf).equals(source)) {
						tempColor = lightData.getMatteColor();
						double thetaScale =
							intersect.getNormal().dot(rayToLight.direction);
						if (thetaScale > 0) {
							tempColor.scale(thetaScale);
							lightColor.add(tempColor);
						} else {
							tempColor = null;
						}
					} else {
						tempColor = null;
					}
				} else {
					tempColor = null;
				}
			} else {
				tempColor = null;
			}
		}
		return lightColor;
	}

}