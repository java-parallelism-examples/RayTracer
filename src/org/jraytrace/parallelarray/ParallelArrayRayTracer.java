package org.jraytrace.parallelarray;

import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.TimeUnit;

import org.jraytrace.engine.RayTracer;
import org.jraytrace.util.Color;

import extra166y.Ops;
import extra166y.ParallelArray;

public class ParallelArrayRayTracer extends RayTracer {



	public ParallelArrayRayTracer(Dimension displaySize) {
		super(displaySize);
		// TODO Auto-generated constructor stub

	}

	
	@Override
	public void trace() {
		
		

		ParallelArray<Integer> pixels;			
		pixels=ParallelArray.create(height, Integer.class, ParallelArray.defaultExecutor());
		
	
		pixels.replaceWithMappedIndex(new Ops.IntAndObjectToObject<Integer,Integer>()
		{
			@Override
			public Integer op(int i, Integer arg1) {
				Point[] pts = new Point[width];
				Color[] colors = new Color[width];
					for(int j=0; j< width; j++)
					{
						pts[j]=new Point(j,i);
						colors[j] = recursiveTrace(viewer.getRayForPoint(pts[j]),maxRecursion);
						
					}
					viewer.setPixelRow(pts, colors);
				return 0;
			}
		});
		viewer.doAutoExposure();
		
	}

}
