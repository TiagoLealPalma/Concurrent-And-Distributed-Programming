package Week_2.PhysicsSimulator;

public class Ball {

    private int x, y;
    private double vx, vy;
    private double radius;

    public Ball(int x, int y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        vx = 0;
        vy = 0;
    }

    // Move based on velocity
    public void move() {
        x += (int)vx;
        y += (int)vy;
    }

    // Move based on a given force (in meter/second)
    public void applyForce(double fx, double fy) {
        vx += fx;
        vy += fy;
    }

    // Getters and Setter
    public double getRadius() { return radius; }
    public double getY() { return y; }
    public double getX() { return x; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
}
