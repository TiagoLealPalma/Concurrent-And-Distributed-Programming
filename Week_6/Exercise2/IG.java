package Week_6.Exercise2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.JButton;
import javax.swing.JFrame;

public class IG {
	ArrayList<Bola> bolas= new ArrayList<>();
	
	public void addContent(){
		JFrame janela= new JFrame("hh");
		janela.setLayout(new BorderLayout());
		BallPainter painter=new BallPainter();
		janela.add(painter, BorderLayout.CENTER);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ExecutorService threadPool = Executors.newFixedThreadPool(6);

		CyclicBarrier finishBarrier = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				threadPool.shutdownNow();
				float max = -1;
				Bola firstPlace = null;
				for(Bola bola:bolas){
					bola.shutdown();
					if(firstPlace == null)
						firstPlace = bola;
					else if(firstPlace.getX()< bola.getX()) {
						firstPlace = bola;
					}
				}
				System.err.println("Bola " + firstPlace.getNumber() + " ficou em primeiro lugar.");
			}
		});

		for(int i=0;i<25;i++){
			Bola bola=new Bola(i+1, finishBarrier);
			bola.addObserver(painter);
			bolas.add(bola);
			painter.addBall(bola);
		}
		
		JButton start=new JButton("Start");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				for(Bola bola:bolas)
					threadPool.submit(bola);
			}
		});
		janela.add(start, BorderLayout.SOUTH);
		janela.setSize(800, 600);
		janela.setVisible(true);
	}
	public static void main(String[] args) {
		new IG().addContent();

	}

}
