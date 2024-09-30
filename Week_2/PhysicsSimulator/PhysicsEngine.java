package Week_2.PhysicsSimulator;

import javax.swing.*;
import java.awt.*;

public class PhysicsEngine {
    private double gravity = 9.8;
    private JPanel panel;
    private double updatesPerFrame;
    private double fixedTimeStep;
    private double timeAccumulator = 0.0;
    private double time = System.currentTimeMillis();

    public PhysicsEngine(JPanel panel, double fps) {
        this.panel = panel;
        this.updatesPerFrame = updatesPerFrame;
        this.fixedTimeStep = 1.0 / fps;
    }

    public void update(Ball ball, double deltaTime){
        // Accumulate the real time passed
        timeAccumulator += deltaTime;

        while(timeAccumulator >= fixedTimeStep){
            applyPhysics(ball, fixedTimeStep);
            timeAccumulator -= fixedTimeStep;
        }

    }

    public void applyPhysics(Ball ball, double timeStep){


        // Gravity force
        ball.applyForce(0, gravity * timeStep);

        ball.move();

        handleCollisions(ball);
    }

    private void handleCollisions(Ball ball) {
        if(ball.getY() > 800){
            System.out.println(ball.getX());
        }
        if(ball.getX() - ball.getRadius()/2 < 0 || ball.getX() + ball.getRadius() > panel.getWidth()){
            ball.setVx(0);
        }

        if(ball.getY() - ball.getRadius()/2 < 0 ){
            ball.setVy(Math.abs(ball.getVy()) * 0.9); // Reverse and dampen velocity
            ball.setY((int)ball.getRadius());
        }

        if(ball.getY() + ball.getRadius() > panel.getHeight()){
            ball.setVy(-Math.abs(ball.getVy()) * 0.8);
            ball.setY((int)(panel.getHeight()- ball.getRadius()));
        }

        double minVelocity = 0.5; // Adjust this threshold as needed
        if (Math.abs(ball.getVy()) < minVelocity) {
            ball.setVy(ball.getVy() < 0 ? minVelocity : -minVelocity);
        }
    }
}
