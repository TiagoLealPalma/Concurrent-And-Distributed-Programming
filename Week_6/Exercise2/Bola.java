package Week_6.Exercise2;

import java.awt.Color;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

public class Bola extends Observable implements DrawableBall, Runnable {
	private float estado=0;
	private Color color=new Color((int)(Math.random()*256), 
			(int)(Math.random()*256), (int)(Math.random()*256));
	private boolean running = true;
	private CyclicBarrier barrier;
	private int number;

	public Bola(int number, CyclicBarrier barrier){
		this.barrier=barrier;
		this.number=number;
	}

	@Override
	public void run() {
		while(running){
			estado+=0.01 + Math.random()*0.09;
			setChanged();
			notifyObservers();
			if(bolaAtingiuLimite()) {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
	}

	public int getNumber() {
		return number;
	}

	public boolean bolaAtingiuLimite(){
		return estado>=1;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public float getX() {
		return estado;
	}

	@Override
	public int getSize() {
		return 10;
	}

	public void shutdown(){
		running=false;
		Thread.currentThread().interrupt();
	}

}
