package Week_2.Extra2;

public class Vector2 {
    private int x;
    private int y;

    // Constructor
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Zero vector constructor
    public Vector2() {
        this(0, 0);
    }

    // Getters and Setters
    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Vector Addition
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    // Vector Subtraction
    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    // Scalar Multiplication
    public Vector2 scale(int scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    // Dot Product
    public double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Magnitude (Length)
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    // Normalize the vector
    public Vector2 normalize() {
        double length = magnitude();
        if (length != 0) {
            return new Vector2(this.x / length, this.y / length);
        }
        return new Vector2(0, 0);
    }

    // Distance between two vectors
    public double distance(Vector2 other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    // Override toString for easy display
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}


