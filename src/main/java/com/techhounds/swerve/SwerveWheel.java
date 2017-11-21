package com.techhounds.swerve;

import com.techhounds.math.CartesianPoint;
import com.techhounds.math.PolarPoint;

/**
 * Class to help with the math involved in computing the direction and motor
 * output for a single swerve wheel on a robot.
 * <p>
 * Much of the math used in this code is based off diagrams and formulas found
 * in the "Crash Course in Swerve Drive" document by Jaci R Brunning, Feb 22nd
 * 2017, Rev 1.1.
 * </p>
 */
public class SwerveWheel {

  /**
   * The y-offset of the center of the wheel contact point to the center of the
   * robot.
   */
  private final CartesianPoint position;

  /**
   * The velocity vector for contribution to robot movement from wheel.
   */
  private final PolarPoint vector;

  /**
   * The diameter of the wheel.
   */
  private double diameter;

  /**
   * The width of the wheel.
   */
  private double width;

  /**
   * Pre-computed cosine value for the rotation angle.
   */
  private double cosRotAng;

  /**
   * Pre-computed sine value for the rotation angle.
   */
  private double sinRotAng;

  /**
   * Fully construct a new instance of a swerve wheel.
   *
   * @param x
   *          Horizontal offset of center of wheel contact point from center of
   *          robot (along x-axis in robot space).
   * @param y
   *          Vertical offset of center of wheel contact point from center of
   *          robot (along y-axis in robot space).
   * @param diameter
   *          Diameter of wheel (we don't care about units - but you must be
   *          consistent in your code).
   * @param width
   *          Width of wheel (we don't care about units - but you must be
   *          consistent in your code).
   */
  public SwerveWheel(double x, double y, double diameter, double width) {
    this.position = new CartesianPoint(x, y);
    this.vector = new PolarPoint(1.0, 0);
    this.diameter = diameter;
    this.width = width;
    // update internal pre-computed values
    setPosition(x, y);
  }

  /**
   * Constructs a new instance located at the center of the robot.
   * <p>
   * You should only use this constructor if you plan on calling
   * {@link #setPosition(double, double)} later. It creates a swerve wheel with
   * a diameter of 10 and width of 4 attached to the center point of your robot.
   * </p>
   */
  public SwerveWheel() {
    this(0, 0, 10, 4);
  }

  /**
   * Transforms a point relative to the wheel's center point origin to
   * real-world coordinates after applying current rotaion.
   * 
   * @param ptSrc
   *          Source point to be transformed relative to center of wheel.
   * @param ptDst
   *          Result of rotating point about wheel center and then offseting
   *          from center of robot.
   * @return The destination point
   */
  public CartesianPoint transform(CartesianPoint relToWheel, CartesianPoint dstRelToOrigin) {
    // Add swerve rotation to current polar angle of coordinate
    double theta = relToWheel.computeTheta() + vector.getTheta();

    double r = relToWheel.computeR();
    // Compute new (x, y) relative to wheel origin
    double x = Math.cos(theta) * r;
    double y = Math.sin(theta) * r;
    // Translate back to wheel space
    x += getX();
    y += getY();
    dstRelToOrigin.set(x, y);
    return dstRelToOrigin;
  }

  /**
   * The y-offset of the center of the wheel contact point to the center of the
   * robot.
   *
   * @return y portion of Cartesian coordinate.
   */
  public final double getY() {
    return position.getY();
  }

  /**
   * The x-offset of the center of the wheel contact point to the center of the
   * robot.
   *
   * @return x portion of Cartesian coordinate.
   */
  public final double getX() {
    return position.getX();
  }

  /**
   * Set the position of the center of the wheel contact relative to the center
   * point of the robot.
   *
   * @param x
   *          New x value indicating horizontal offset from center of robot.
   * @param y
   *          New y value indicating vertical offset from center of robot.
   */
  public final void setPosition(double x, double y) {
    position.setX(x);
    position.setY(y);
    if (x == 0 && y == 0) {
      // Wheel located at center or robot, rotational force vector
      // will always be 0 (can never contribute to robot rotation)
      cosRotAng = 0;
      sinRotAng = 0;
    } else {
      double rotAng = Math.atan2(y, x);
      cosRotAng = Math.cos(rotAng);
      sinRotAng = Math.sin(rotAng);
    }
  }

  /**
   * The diameter of the wheel.
   *
   * @return The wheel diameter in the same units as it was set in (we don't
   *         care what units you use).
   */
  public double getDiameter() {
    return diameter;
  }

  /**
   * The diameter of the wheel.
   *
   * @param diameter
   *          The wheel diameter to associate with this swerve wheel.
   */
  public void setDiameter(double diameter) {
    this.diameter = diameter;
  }

  /**
   * Get the width of the wheel (we use this and the diameter to compute center
   * contact point of wheel).
   *
   * @return Width of the tire tread.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Set the width of the wheel (we use this and the diameter to compute center
   * contact point of wheel).
   *
   * @param width
   *          The width of the tire tread.
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * Set the axle angle on unit circle.
   *
   * @param theta
   *          The angle of the axle on the unit circle (0 is due east and
   *          rotation is counter clockwise).
   */
  public void setAxleTheta(double theta) {
    vector.setTheta(theta);
    // transform = null;
  }

  public double getAxleTheta() {
    return vector.getTheta();
  }

  /**
   * Set the bearing of the wheel clockwise from due north.
   *
   * @param bearing
   *          The bearing in radians relative to due north (clockwise heads from
   *          North to East).
   */
  public void setWheelBearing(double bearing) {
    vector.setTheta(-bearing);
  }

  /**
   * Get the bearing of the wheel clockwise from due north.
   *
   * @return Angle in radians relative to due north (clockwise heads from North
   *         to East).
   */
  public double getWheelBearing() {
    // Wheel bearing is aligned to direction of vector
    return -vector.getTheta();
  }

  /**
   * Get the linear velocity set on the wheel.
   *
   * @return Current velocity on wheel (negative value indicates wheel should go
   *         in reverse).
   */
  public double getVelocity() {
    return vector.getR();
  }

  /**
   * Set the velocity for the wheel.
   *
   * @param velocity
   *          New velocity to assign (if you pass a negative value it means
   *          wheel should go in reverse).
   */
  public void setVelocity(double velocity) {
    vector.setR(velocity);
  }

  /**
   * Computes the angle theta that a wheel should be positioned at to rotate
   * around a point in robot space.
   *
   * @param cx
   *          The x portion of the coordinate to rotate about (relative to
   *          center of robot).
   * @param cy
   *          The y portion of the coordinate to rotate about (relative to
   *          center of robot).
   * @return The angle in radians for the axle.
   */
  public double computeForTurnAround(double cx, double cy) {
    double dx = getX() - cx;
    double dy = getY() - cy;
    double theta = CartesianPoint.computeTheta(dy, dx);
    return theta;
  }

  /**
   * Computes the distance of a point in robot space to the center point of the
   * wheel.
   *
   * @param cx
   *          The x portion of the coordinate to rotate about (relative to
   *          center of robot).
   * @param cy
   *          The y portion of the coordinate to rotate about (relative to
   *          center of robot).
   * @return The distance to the center point of the wheel.
   */
  public double computeDistance(double cx, double cy) {
    double dx = getX() - cx;
    double dy = getY() - cy;
    return CartesianPoint.computeR(dx, dy);
  }

  /**
   * Compute vector heading given translation components and rotation component.
   * 
   * @param xTranslation
   *          - The east/west component of the translation velocity vector.
   * @param yTranslation
   *          - The north/south component of the translation velocity vector.
   * @param rotational
   *          - Rotational velocity to factor in.
   */
  public void setDirection(double xTranslation, double yTranslation, double rotation) {
    double ux = xTranslation;
    double uy = yTranslation;
    if (rotation != 0) {
      ux += rotation * cosRotAng;
      uy += rotation * sinRotAng;
    }
    // Compute velocity
    double r = CartesianPoint.computeR(ux, uy);

    // If velocity on wheel is basically zero, treat as zero and leave wheel
    // position
    // alone
    if (Math.abs(r) < 1e-9) {
      vector.setR(0);
      return;
    }

    // Compute new wheel angle and shortest path to get there from current
    // direction
    double newTheta = Math.atan2(uy, ux);
    double oldTheta = vector.getTheta();
    double shortestPath = PolarPoint.computeShortestPath(oldTheta, newTheta);
    if (Math.abs(shortestPath) > (Math.PI / 2)) {
      // Shortest path is more than 90 degrees, we can reduce this to
      // something less than 90 degrees if we reverse motor direction
      if (newTheta < 0) {
        newTheta += Math.PI;
      } else {
        newTheta -= Math.PI;
      }
      // Invert velocity direction
      r = -r;
    }
    vector.setTheta(newTheta);
    vector.setR(r);
  }

}
