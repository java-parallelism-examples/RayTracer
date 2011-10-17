package org.jraytrace.sequential;


import java.awt.Dimension;
import java.awt.Point;

import org.jraytrace.engine.RayTracer;
import org.jraytrace.util.Color;

public class SequentialRayTracer extends RayTracer {

	public SequentialRayTracer(Dimension displaySize) {
		super(displaySize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void trace() {

		for(int i=0; i<height; i++)
		{
			for(int j=0; j<width; j++)
			{
				Point pt = new Point(j,i);
				Color ptColor = recursiveTrace(viewer.getRayForPoint(pt),maxRecursion);
				viewer.setPixelColor(pt, ptColor);	
			}	
		}
		viewer.doAutoExposure();
	}

}
