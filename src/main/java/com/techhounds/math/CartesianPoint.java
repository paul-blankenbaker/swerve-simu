package com.techhounds.math;

/**
 * Representation of a single point in a
 * <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian
 * coordinate system</a> (x, y).
 * <p>
 * This class is used to contain a single
 * <a href="https://en.wikipedia.org/wiki/Cartesian_coordinate_system">Cartesian
 * point</a> as a (x, y) pair of values. While both x and y values are length
 * measurements, there are no units associated with the length values (you may
 * "think" in any units you want as long as you are consistent).
 * </p>
 */
public final class CartesianPoint {

  /**
   * The x (abscissca) value associated with the point (unit of length -
   * distance of point from y-axis).
   */
  private double x;

  /**
   * The y (ordinate) value associated with the point (unit of length - distance
   * of point from x-axis).
   */
  private double y;

  /**
   * Construct a new instance and initialize with (x, y) values.
   *
   * @param x
   *          The x (abscissca) value associated with the point (unit of
   *          length).
   * @param y
   *          The y (ordinate) value associated with the point (unit of length).
   */
  public CartesianPoint(double x, double y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * The copy constructor for a new point.
   *
   * @param src
   *          The point to copy values from (must not be null).
   */
  public CartesianPoint(final CartesianPoint src) {
    this(src.x, src.y);
  }

  /**
   * Constructs a new instance initialized to the origin of the coordinate
   * system (0, 0).
   */
  public CartesianPoint() {
    this(0, 0);
  }

  /**
   * Compute the angle component for a polar coordinate representation of the
   * point.
   *
   * @param x
   *          The x (abscissca) value associated with the point (unit of
   *          length).
   * @param y
   *          The y (ordinate) value associated with the point (unit of length).
   * @return The angle (theta) in radians measured counter-clock wise from due
   *         east (east is 0, north is PI/2, west is PI and south is 1.5 * PI).
   */
  public static final double computeTheta(double x, double y) {
    return Math.atan2(y, x);
  }

  /**
   * Compute the angle component for a polar coordinate representation of the
   * point.
   * 
   * @return The angle (theta) in radians measured counter-clock wise from due
   *         east (east is 0, north is PI/2, west is PI and south is 1.5 * PI).
   */
  public double computeTheta() {
    return computeTheta(x, y);
  }

  /**
   * Compute the radius (distance from origin) for a polar coordinate
   * representation of the point.
   *
   * @param x
   *          The x (abscissca) value associated with the point (unit of
   *          length).
   * @param y
   *          The y (ordinate) value associated with the point (unit of length).
   * @return The radius (magnitude of the the coordinate). There are no units
   *         associated with this value (be consistent).
   */
  public static final double computeR(double x, double y) {
    return Math.sqrt(computeR2(x, y));
  }

  /**
   * Compute the radius (distance from origin) for a polar coordinate
   * representation of the point.
   * 
   * @return The radius (magnitude of the the coordinate). There are no units
   *         associated with this value (be consistent).
   */
  public double computeR() {
    return computeR(x, y);
  }

  /**
   * Compute the radius squared (distance from origin) for a polar coordinate
   * representation of the point.
   * <p>
   * This method is more efficient when you need to compare magnitudes or
   * distances as it avoids the expensive square root call required for a
   * distance calculation.
   * </p>
   *
   * @param x
   *          The x (abscissca) value associated with the point (unit of
   *          length).
   * @param y
   *          The y (ordinate) value associated with the point (unit of length).
   * @return The radius (magnitude of the the coordinate). There are no units
   *         associated with this value (be consistent).
   */
  public static final double computeR2(double x, double y) {
    return x * x + y * y;
  }

  /**
   * Compute the radius squared (distance from origin) for a polar coordinate
   * representation of the point.
   * <p>
   * This method is more efficient when you need to compare magnitudes or
   * distances as it avoids the expensive square root call required for a
   * distance calculation.
   * </p>
   * 
   * @return The radius (magnitude of the the coordinate). There are no units
   *         associated with this value (be consistent).
   */
  public double computeR2() {
    return computeR2(x, y);
  }

  /**
   * Translates (shifts x, y values) to new origin.
   *
   * @param x0
   *          The x value of the point that should be considered the new origin
   *          (we subtract this from our current x value).
   * @param y0
   *          The y value of the point that should be considered the new origin
   *          (we subtract this from our current y value).
   * @return Reference to self.
   */
  public CartesianPoint translate(double x0, double y0) {
    x -= x0;
    y -= y0;
    return this;
  }

  /**
   * Rotates the (x, y) coordinate around a point and returns resulting polar
   * coordinate.
   *
   * @param x0
   *          The x value of the point that we should rotate around.
   * @param y0
   *          The y value of the point that we should rotate around.
   * @param rotation
   *          The angle of rotation in radians measured counter-clock wise from
   *          due east (east is 0, north is PI/2, west is PI and south is 1.5 *
   *          PI).
   * @param dst
   *          Where to store the resulting polar coordinates (must not be null).
   * @return The resulting polar coordinate.
   */
  public PolarPoint rotate(double x0, double y0, double rotation, PolarPoint dst) {
    double dx = x - x0;
    double dy = y - y0;
    dst.setR(computeR(dx, dy));
    dst.setTheta(computeTheta(dx, dy) + rotation);
    return dst;
  }

  /**
   * Rotates the (x, y) coordinate around a point (x0, y0).
   * <p>
   * This value changes the internal (x, y) values associated with the object to
   * the newly calculated position.
   * </p>
   *
   * @param x0
   *          The x value of the point that we should rotate around.
   * @param y0
   *          The y value of the point that we should rotate around.
   * @param rotation
   *          The angle of rotation in radians measured counter-clock wise from
   *          due east (east is 0, north is PI/2, west is PI and south is 1.5 *
   *          PI).
   * @return Reference to this object with the updated (x, y) values resulting from the rotation.
   */
  public CartesianPoint rotate(double x0, double y0, double rotation) {
    PolarPoint dst = new PolarPoint();
    return rotate(x0, y0, rotation, dst).toCartesian(this).translate(-x0, -y0);
  }

  /**
   * Set a polar point to its (r, theta) values represent the same location as
   * our (x, y) values.
   *
   * @param dst
   *          Where to store the computed (r, theta) values - must not be null.
   * @return The (r, theta) values in the polar coordinate system that map to
   *         the same location as (x, y).
   */
  public PolarPoint toPolar(PolarPoint dst) {
    dst.setR(computeR());
    dst.setTheta(computeTheta());
    return dst;
  }

  /**
   * Create an equivalent polar point representation.
   *
   * @return The (r, theta) values in the polar coordinate system that map to
   *         the same location as (x, y).
   */
  public PolarPoint toPolar() {
    return new PolarPoint(computeR(), computeTheta());
  }

  /**
   * Get the x value associated with the point.
   *
   * @return The x (abscissca) value associated with the point (unit of length -
   *         distance of point from y-axis).
   */
  public final double getX() {
    return x;
  }

  /**
   * Set the x value associated with the point.
   *
   * @param x
   *          The x (abscissca) value to associate with the point (unit of
   *          length - distance of point from y-axis).
   */
  public final void setX(double x) {
    this.x = x;
  }

  /**
   * Get the y value associated with the point.
   *
   * @return The y (ordinate) value associated with the point (unit of length -
   *         distance of point from x-axis).
   */
  public final double getY() {
    return y;
  }

  /**
   * Set the y value associated with the point.
   *
   * @param y
   *          The y (ordinate) value to associate with the point (unit of length
   *          - distance of point from x-axis).
   */
  public final void setY(double y) {
    this.y = y;
  }

  /**
   * Set the (x, y) coordinates associated with the point.
   *
   * @param x
   *          The x (abscissca) value to associate with the point (unit of
   *          length - distance of point from y-axis).
   * @param y
   *          The y (ordinate) value to associate with the point (unit of length
   *          - distance of point from x-axis).
   */
  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the string representation of the Cartesian coordinate "(x, y)".
   *
   * @param x
   *          The x (abscissca) value to associate with the point (unit of
   *          length - distance of point from y-axis).
   * @param y
   *          The y (ordinate) value to associate with the point (unit of length
   *          - distance of point from x-axis).
   */
  public static String toString(double x, double y) {
    StringBuilder sb = new StringBuilder(128);
    sb.append('(');
    sb.append(x);
    sb.append(", ");
    sb.append(y);
    sb.append(')');
    return sb.toString();
  }

  /**
   * Get the string representation of the Cartesian coordinate "(x, y)".
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return toString(x, y);
  }

}
