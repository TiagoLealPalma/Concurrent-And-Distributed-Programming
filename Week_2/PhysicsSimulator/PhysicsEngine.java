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

    // Suavizar perdas de energia com função y = -log x + 1

    private boolean magnetic = false;
    private double magneticForce = 0.1;
    private Dimension cursorCoords;

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


        if(magnetic) {
            double distance = 1 - Math.sqrt(Math.pow(cursorCoords.getWidth()- ball.getX(),2) + Math.pow(cursorCoords.getHeight()- ball.getY(),2))/1500;

            ball.applyForce((distance*magneticForce * (cursorCoords.getWidth() - ball.getX())) * timeStep,
                    distance*magneticForce * (cursorCoords.getHeight() - ball.getY()) * timeStep);

        }

        // Gravity force
        ball.applyForce(0, gravity * timeStep);

        ball.move();

        handleCollisions(ball);
    }

    private void handleCollisions(Ball ball) {
        double energyLossValue = 0.8; // For impact
        double frictionValue = 0.0005;

        if(ball.getX() - ball.getRadius() < 0){
            ball.setVx(Math.abs(ball.getVx()) * energyLossValue); // Reverse and dampen velocity
            ball.setX((int)ball.getRadius());
        }

        if(ball.getX() + ball.getRadius() > panel.getWidth()){
            ball.setVx(-Math.abs(ball.getVx()) * energyLossValue);
            ball.setX((int)(panel.getWidth()- ball.getRadius()));
        }

        if(ball.getY() - ball.getRadius() < 0 ){
            ball.setVy(Math.abs(ball.getVy()) * energyLossValue); // Reverse and dampen velocity
            ball.setY((int)ball.getRadius());
        }

        if(ball.getY() + ball.getRadius() > panel.getHeight()){
            // Calculate vertical movement
            ball.setVy(-Math.abs(ball.getVy()) * energyLossValue);
            ball.setY((int)(panel.getHeight()- ball.getRadius()));

            // Calculate Friction
            ball.setVx(ball.getVx()*0.95);
        }

        double minVelocity = 0.5; // Adjust this threshold as needed
        if (Math.abs(ball.getVy()) < minVelocity) {
            ball.setVy(ball.getVy() < 0 ? minVelocity : -minVelocity);
        }

    }

    public void setMagnetic(boolean magnetic, Dimension cursorCoords) {
        this.magnetic = magnetic;
        this.cursorCoords = cursorCoords;
    }
}
