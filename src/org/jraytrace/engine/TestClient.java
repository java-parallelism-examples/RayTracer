package org.jraytrace.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jraytrace.executor.ExecutorRayTracer;
import org.jraytrace.forkjoin.ForkJoinRayTracer;
import org.jraytrace.object.PointSource;
import org.jraytrace.object.Sphere;
import org.jraytrace.object.UnionSurface;
import org.jraytrace.parallelarray.ParallelArrayRayTracer;
import org.jraytrace.sequential.SequentialRayTracer;
import org.jraytrace.util.Vector3;




public class TestClient extends JFrame {
	
/*  Configuration */
	private int threshold= 100;
	private int numThreads= 20;
	private Dimension displaySize=new Dimension(400,400);
/* ************** */
	
	
	private Image image;
	private TraceThread traceThread;
	private MemoryImageSource src;
	private JLabel label1;
	private JLabel label2;
	private JButton seqBtn;
	private JButton thrBtn;
	private JButton forkBtn;
	private JButton arrayBtn;
	private RenderPanel renderPanel;
	private long seqTime;
	private double speedup;
	public TestClient() {
		super("TestClient");

		
		JPanel info = new JPanel();
		JPanel text= new JPanel();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		seqBtn = new JButton("Sequential");
		thrBtn = new JButton("Executor");
		forkBtn = new JButton("ForkJoin");
		arrayBtn=new JButton("ParallelArray");
		ButtonListener btnListener = new ButtonListener();
		seqBtn.addActionListener(btnListener);
		thrBtn.addActionListener(btnListener);
		forkBtn.addActionListener(btnListener);
		arrayBtn.addActionListener(btnListener);
		
		label1 = new JLabel();
		label2= new JLabel();
		info.add(seqBtn);
		info.add(thrBtn);
		info.add(forkBtn);
		info.add(arrayBtn);
		text.add(label1);
		text.add(label2);
		
		setLayout(new BorderLayout());
		renderPanel = new RenderPanel();
		getContentPane().add(info,BorderLayout.NORTH);
		getContentPane().add(text,BorderLayout.SOUTH);
		getContentPane().add(renderPanel,BorderLayout.CENTER);
		pack();
		setVisible(true);
		
		
	}	
	public void initTracer(int type)
	{
		traceThread = new TraceThread("tracer",type);
		src = traceThread.tracer.getCamera().getImageSource();
		src.setAnimated(true);
		renderPanel.setImage(renderPanel.createImage(src));
	}
	
	private class RenderPanel extends JPanel {
		private static final long serialVersionUID = -6160628506441192086L;
		private Image img;
		

		public RenderPanel() {
			setPreferredSize(new Dimension(400,400));

		}
		public void setImage(Image img) {
			this.img = img;
			repaint();
		}

		public void paintComponent(Graphics g) {
			if(img!=null)
			{
				g.drawImage ( img, 0, 0, this);
			}
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int type=0;
			if (arg0.getSource() == seqBtn) {
				type=0;			
			} else if (arg0.getSource() == thrBtn) {
				type=1;
			}else if (arg0.getSource() == forkBtn) {
				type=2;
			}else if (arg0.getSource() == arrayBtn) {
				type=3;
			}
		
			initTracer(type);
			traceThread.start();
		}
	}

	protected class TraceThread extends Thread {
		public RayTracer tracer;
		public int type;
		public TraceThread(String name,int type) {
			super(name);
			this.type=type;
			
			switch(type)
			{
			case 0:
				tracer = new SequentialRayTracer(displaySize);
				break;
			case 1:
				tracer = new ExecutorRayTracer(displaySize,numThreads);
				break;
			case 2:
				tracer= new ForkJoinRayTracer(displaySize,threshold);
				break;
			case 3:
				tracer= new ParallelArrayRayTracer(displaySize);
				break;
			}
			
		}

		private void createDefaultScene(int i) {
			UnionSurface scene;
			UnionSurface scene2;
			Sphere sph;
			Sphere sph2;
			PointSource lightSource;
			PointSource lightSource2;

			sph = new Sphere(new Vector3(0,(double) i/10 -1, -10), 1, new org.jraytrace.util.Color(1,1,1));
			lightSource = new PointSource(new Vector3(-5, 3, 4), new org.jraytrace.util.Color(1,1,1));

			lightSource2 = new PointSource(new Vector3(5, 3, 4), new org.jraytrace.util.Color(1,0,0));
			sph2 = new Sphere(new Vector3(1, 1, -8), 1, new org.jraytrace.util.Color(1, 0, 0));
			
			scene = new UnionSurface(sph, sph2);
			scene2 = new UnionSurface(lightSource2, lightSource);

			scene = new UnionSurface(scene, scene2);

			tracer.setScene(scene);

		}

		public void run() {
			
			Stopwatch timer=new Stopwatch();
			timer.start();

			for(int i=0; i<34; i++)
			{
				createDefaultScene(i);
				tracer.trace();
			}
			for(int i=35; -16<i; i--)
			{
				createDefaultScene(i);
				tracer.trace();
			}
			for(int i=-16; i<0; i++)
			{
				createDefaultScene(i);
				tracer.trace();
			}
			timer.stop();
			
			final long elapsedTime= timer.getElapsedTime();
			if(type==0)
			{
				seqTime=elapsedTime;
			}
			else if(seqTime!=0 && type!=0)
			{
				speedup=(double)seqTime/elapsedTime;
			}
			Runnable r = new Runnable() {
				public void run() {

					
					label1.setText(elapsedTime + "ms");
					if(speedup!=0)
						label2.setText("Speedup:"+speedup);
				}
			};
			EventQueue.invokeLater(r);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new TestClient();
		//frame.show();
	}
}