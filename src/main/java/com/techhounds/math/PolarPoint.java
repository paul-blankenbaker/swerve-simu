package com.techhounds.math;

/**
 * Representation of a single point or 2D vector in a
 * <a href="https://en.wikipedia.org/wiki/Polar_coordinate_system">Polar
 * coordinate system</a> (r, theta).
 * <p>
 * This class is used to contain a single
 * <a href="https://en.wikipedia.org/wiki/Polar_coordinate_system">Polar
 * point</a> as a (r, theta) pair of values.
 * </p>
 * <dl>
 * <dt>r</dt>
 * <dd>The radius (magnitude of the the coordinate). There are no units
 * associated with this value (use whatever units you want - just be
 * consistent).</dd>
 * <dt>theta</dt>
 * <dd>The angle in radians measured counter-clock wise from due east (east is
 * 0, north is PI/2, west is PI and south is 1.5 * PI).</dd>
 * </dl>
 */
public final class PolarPoint {

  /** The radius (magnitude) of the point. */
  private double r;

  /** The counter clockwise direction from due east in radians to rotate. */
  private double theta;

  /**
   * Constructs a new instance and initializes the (r, theta) values.
   * PolarPoint.
   *
   * @param r
   *          The radius (magnitude of the the coordinate). There are no units
   *          associated with this value (use whatever units you want - just be
   *          consistent).
   * @param theta
   *          The angle in radians measured counter-clock wise from due east
   *          (east is 0, north is PI/2, west is PI and south is 1.5 * PI).
   */
  public PolarPoint(double r, double theta) {
    this.r = r;
    this.theta = theta;
  }

  /**
   * The copy constructor for a new PolarPoint.
   *
   * @param src
   *          The polar point to copy values from (must not be null).
   */
  public PolarPoint(final PolarPoint src) {
    this(src.r, src.theta);
  }

  /**
   * Constructs a new instance initialized to the origin of the coordinate
   * system (0, 0).
   */
  public PolarPoint() {
    this(0, 0);
  }

  /**
   * Get the radius (magnitude) of the point.
   * 
   * @return The radius (magnitude of the the coordinate). There are no units
   *         associated with this value (be consistent).
   */
  public final double getR() {
    return r;
  }

  /**
   * Set the radius (magnitude) of the point.
   * 
   * @param r
   *          The radius (magnitude of the the coordinate). There are no units
   *          associated with this value (be consistent).
   */
  public final void setR(double r) {
    this.r = r;
  }

  /**
   * Get the angle associated with the point (radians).
   *
   * @return The angle in radians measured counter-clock wise from due east
   *         (east is 0, north is PI/2, west is PI and south is 1.5 * PI).
   */
  public final double getTheta() {
    return theta;
  }

  /**
   * Set the angle associated with the point (radians).
   *
   * @param theta
   *          The angle in radians measured counter-clock wise from due east
   *          (east is 0, north is PI/2, west is PI and south is 1.5 * PI).
   */
  public final void setTheta(double theta) {
    this.theta = theta;
  }

  /**
   * Get the angle associated with the point (degrees).
   *
   * @return The angle in degrees measured counter-clock wise from due east
   *         (east is 0, north is 90, west is 180 and south is 270).
   */
  public double getThetaDegrees() {
    return Math.toDegrees(getTheta());
  }

  /**
   * Set the angle associated with the point (degrees).
   *
   * @param theta
   *          The angle in degrees measured counter-clock wise from due east
   *          (east is 0, north is 90, west is 180 and south is 270).
   */
  public void setThetaDegrees(double theta) {
    setTheta(Math.toRadians(theta));
  }

  /**
   * Get the bearing associated with the point (radians).
   *
   * @return The bearing in radians measured clock wise from due north (north is
   *         0, east is PI/2, south is PI and west is 1.5 * PI).
   */
  public double getBearing() {
    return Math.PI / 2 - getTheta();
  }

  /**
   * Set the bearing associated with the point (radians).
   *
   * @param bearing
   *          The bearing in radians measured clock wise from due north (north
   *          is 0, east is PI/2, south is PI and west is 1.5 * PI).
   */
  public void setBearing(double bearing) {
    setTheta(Math.PI / 2 - bearing);
  }

  /**
   * Get the bearing associated with the point (degrees).
   *
   * @return The bearing in degrees measured clock wise from due north (north is
   *         0, east is 90, south is 180 and west is 270).
   */
  public double getBearingDegrees() {
    return Math.toDegrees(getBearing());
  }

  /**
   * Set the bearing associated with the point (degrees).
   *
   * @param bearing
   *          The bearing in degrees measured clock wise from due north (north
   *          is 0, east is 90, south is 180 and west is 270).
   */
  public void setBearingDegrees(double bearing) {
    setBearing(Math.toRadians(bearing));
  }

  /**
   * Get the string representation of the polar coordinate "(r, <Degs)".
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(128);
    sb.append('(');
    sb.append(getR());
    sb.append(", \u2220");
    sb.append(getThetaDegrees());
    sb.append(')');
    return sb.toString();
  }

  /**
   * Set a Cartesian point so that it (x, y) values represent the same location
   * as our (r, theta) values.
   *
   * @param dst
   *          Where to store the computed (x, y) values - must not be null.
   * @return Reference to the modified Cartesian point.
   */
  public CartesianPoint toCartesian(CartesianPoint cartesianPoint) {
    cartesianPoint.setX(computeX());
    cartesianPoint.setY(computeY());
    return cartesianPoint;
  }

  /**
   * Create an equivalent point in the Cartesian coordinate system.
   * 
   * @return Equivalent point in the Cartesian coordinate system.
   */
  public CartesianPoint toCartesian() {
    return new CartesianPoint(computeX(), computeY());
  }

  /**
   * Computes the x value of the (x, y) pair of the point in the Cartesian
   * coordinate system.
   *
   * @return The x (abscissca) value associated with the point (unit of length).
   */
  public final double computeX() {
    return Math.cos(theta) * r;
  }

  /**
   * Computes the y value of the (x, y) pair of the point in the Cartesian
   * coordinate system.
   *
   * @return The y (ordinate) value associated with the point (unit of length).
   */
  public final double computeY() {
    return Math.sin(theta) * r;
  }

  /**
   * Computes the shortest rotation between two angles (oldTheta - newTheta).
   *
   * @param oldTheta The angle to move from in the range [-Math.PI, +Math.PI].
   * @param newTheta The angle to move to in the range of [-Math.PI, +Math.PI].
   * @return The smallest amount that can be added to oldTheta to end up at newTheta on the unit circle.
   */
  public static double computeShortestPath(double oldTheta, double newTheta) {
    double changeA = newTheta - oldTheta;
    if (changeA > Math.PI) {
      changeA -= Math.PI * 2;
    } else if (changeA < -Math.PI) {
      changeA += Math.PI * 2;
    }

    return changeA;
  }

}
