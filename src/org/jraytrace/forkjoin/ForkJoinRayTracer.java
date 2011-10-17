package org.jraytrace.forkjoin;

import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jsr166y.ForkJoinPool;
import jsr166y.ForkJoinTask;
import jsr166y.RecursiveAction;

import org.jraytrace.engine.RayTracer;
import org.jraytrace.util.Color;

public class ForkJoinRayTracer extends RayTracer {

	private final int threshold;

	public ForkJoinRayTracer(Dimension displaySize,int threshold) {
		super(displaySize);
		this.threshold = threshold;

	}

	
	@Override
	public void trace() {
		
		ForkJoinPool pool= new ForkJoinPool();
		ForkJoinTask recursiveTask= new Task(0, height);
		
		pool.invoke(recursiveTask);
		
		viewer.doAutoExposure();
		
	}
	


	
	private class Task extends RecursiveAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3382700411285400128L;
		int start;
		int end;

		public Task( int start, int end) {
			this.start=start;
			this.end=end;
		}

		@Override
		protected void compute() {
			if(end-start <threshold)
			{
				for(int y=start; y<end ;y++)
				{
					
					for(int x=0; x<viewer.getResolution().width; x++)
					{
						Point pt=new Point(x,y);
						Color ptColor = recursiveTrace(viewer.getRayForPoint(pt),maxRecursion);
						viewer.setPixelColor(pt, ptColor);
						
					}
				}
				return;
			}
			
			int split = (end-start) / 2;
			
			Task task1= new Task(start,  start+split);
			task1.invoke();
			
			Task task2= new Task( start+split, end);
			task2.compute();
			
		}
		
		
		
		
	}
}
