package org.jraytrace.executor;

import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jraytrace.engine.RayTracer;
import org.jraytrace.util.Color;

public class ExecutorRayTracer extends RayTracer {

	private final int numThreads;


	public ExecutorRayTracer(Dimension displaySize,int numThreads) {
		super(displaySize);
		// TODO Auto-generated constructor stub
		this.numThreads = numThreads;
	}

	
	@Override
	public void trace() {
		
		final ExecutorService exec = Executors
				.newFixedThreadPool(numThreads);

		
		for (int i = 0; i < numThreads; i++) {
			exec.execute(new Task(i));
		}
		
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.out.print(e.toString());
		}
		
		viewer.doAutoExposure();
		
	}
	
	

	
	private class Task implements Runnable {
		int tid;
		public Task(int tid) {
			this.tid=tid;
		}
		@Override
		public void run() {
			
			for(int i=tid; i<height ;i+=numThreads)
			{
				for(int j=0; j< width; j++)
				{
					Point pt=new Point(j,i);
					Color ptColor = recursiveTrace(viewer.getRayForPoint(pt),maxRecursion);
					viewer.setPixelColor(pt, ptColor);	
				}
			}
			
		}
		
		
	}

}
