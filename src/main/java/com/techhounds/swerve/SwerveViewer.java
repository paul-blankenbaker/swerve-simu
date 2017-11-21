package com.techhounds.swerve;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.text.NumberFormat;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.techhounds.math.CartesianPoint;
import com.techhounds.math.PolarPoint;

/**
 * A graphical view of all of the {@link SwerveWheel}s making up a robot held in a {@link SwerveWheels} collection.
 */
public class SwerveViewer extends JComponent {
  /** serialVersionUID - Java Swing component version ID. */  
  private static final long serialVersionUID = 1L;

  /**
   * How many pixels per real-space units.
   */
  private double pxPerUnit = 40;

  /**
   * Used for drawing wheels (thick line).
   */
  private Stroke wheelStroke;

  /**
   * Color to use for outline of wheel.
   */
  private Color wheelColor = Color.DARK_GRAY;

  /**
   * Color to use for tire tread on wheel (fill).
   */
  private Color tireColor = Color.BLUE;

  /**
   * Color to use for marker to identify the front of the wheel.
   */
  private Color frontColor = Color.YELLOW;

  /**
   * Color used for wheel velocity vectors originating from center of wheel.
   */
  private Color wheelVector = Color.RED;

  /**
   * Collection of swerve wheels.
   */
  private SwerveWheels wheels;

  /**
   * Center of draw area x value (in pixels).
   */
  private double x0 = 200;

  /**
   * Center of draw area y value (in pixels).
   */
  private double y0 = 300;

  // Bounding box for all wheels (in robot units - not pixels)
  private double minX = -100;
  private double minY = -100;
  private double maxX = +100;
  private double maxY = +100;

  // Center of robot (in robot units - not pixels) - gets mapped to (x0, y0).
  private double cx = 0;
  private double cy = 0;

  /**
   * Used for formatting numbers displayed on widget.
   */
  private NumberFormat nf;

  /**
   * Used to scale velocity vectors.
   */
  private double velocityScale;

  /**
   * Constructs a new instance without any swerve wheels.
   */
  public SwerveViewer() {
    int bw = 10;
    nf = NumberFormat.getNumberInstance();
    nf.setMinimumFractionDigits(3);
    nf.setMaximumFractionDigits(3);
    Dimension size = new Dimension((int) ((x0 + bw) * 2), (int) ((y0 + bw) * 2));
    setMinimumSize(size);
    setPreferredSize(size);
    wheelStroke = new BasicStroke(3);
    wheels = new SwerveWheels();
    setBackground(Color.white);
    setBorder(BorderFactory.createEmptyBorder(bw, bw, bw, bw));
    setOpaque(true);
  }

  /**
   * Adds a {@link SwerveWheel} to display on the widget.
   *
   * @param wheel The wheel to display (must not be null).
   */
  public void addWheel(SwerveWheel wheel) {
    // Find a square that is big enough to draw top view of wheel
    // rotated in any view
    double wd = wheel.getDiameter();
    double ww = wheel.getWidth();
    double bounds = Math.sqrt(wd * wd + ww * ww);
    double bounds2 = bounds / 2;

    double wheelMinX = wheel.getX() - bounds2;
    double wheelMaxX = wheel.getX() + bounds2;
    double wheelMinY = wheel.getY() - bounds2;
    double wheelMaxY = wheel.getY() + bounds2;

    if (wheels.size() == 0) {
      minX = wheelMinX;
      minY = wheelMinY;
      maxX = wheelMaxX;
      maxY = wheelMaxY;
    } else {
      minX = Math.min(wheelMinX, minX);
      minY = Math.min(wheelMinY, minY);
      maxX = Math.max(wheelMaxX, maxX);
      maxY = Math.max(wheelMaxY, maxY);
    }
    // Center of view based off all wheels
    cx = (minX + maxX) / 2;
    cy = (minY + maxY) / 2;
    wheels.add(wheel);
  }

  /**
   * Draws a picture of the labeled wheel positions.
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    // Get position of our origin
    Insets insets = getInsets();
    int width = getWidth();
    int height = getHeight();
    g.setColor(getBackground());
    g.fillRect(0, 0, width, height);
    int wi = width - insets.left - insets.right;
    int hi = height - insets.top - insets.bottom;
    x0 = wi / 2.0 + insets.left;
    y0 = hi / 2.0 + insets.top;
    pxPerUnit = Math.min(wi / (maxX - minX), hi / (maxY - minY));
    Graphics2D g2 = (Graphics2D) g;

    int n = wheels.size();

    if (n > 0) {
      // Set the scale factor for the velocity vectors based on the minimum wheel size
      double minDiam = 1.0;
      Collection<SwerveWheel> allWheels = wheels.getSwerveWheels();
      for (SwerveWheel wheel : allWheels) {
        minDiam = Math.min(minDiam, wheel.getDiameter());
      }
      velocityScale = minDiam * 2;

      // Draw each wheel and sum velocity vectors
      double velX = 0;
      double velY = 0;
      for (SwerveWheel wheel : allWheels) {
        drawWheel(g2, wheel);
        double wheelAng = wheel.getAxleTheta();
        double wheelVel = wheel.getVelocity();

        // Add velocity components together from wheel bearing perspective (0 is due north and positive is clockwise)
        velY += Math.cos(wheelAng) * wheelVel;
        velX -= Math.sin(wheelAng) * wheelVel;
      }

      // Draw summary vector from center of robot
      g2.setColor(Color.BLACK);
      g2.setStroke(wheelStroke);
      velX /= n;
      velY /= n;
      drawVelocityVector(g2, computePixelX(0), computePixelY(0), computePixelX(velX * velocityScale), computePixelY(velY * velocityScale));

      // Label summary vector
      FontMetrics fm = g.getFontMetrics();
      CartesianPoint vv = new CartesianPoint(velX, velY);
      PolarPoint velocity = vv.toPolar();
      String vstr = nf.format(velocity.getR());
      String bstr = nf.format(90 - velocity.getThetaDegrees());
      int vstrw = fm.stringWidth(vstr);
      int bstrw = fm.stringWidth(bstr);
      int fw = Math.max(bstrw, vstrw);
      int wy = (int) computePixelY(0);
      int fxr = -4;
      if (velX < 0) {
        fxr = 4 + fw;
      }
      g.drawString(vstr, (width / 2) + fxr - vstrw, wy - 2);
      g.drawString(bstr, (width / 2) + fxr - bstrw, wy + 2 + fm.getHeight());
    }
  }

  /**
   * Compute the x value of a pixel location given an x value in robot space.
   *
   * @param x The x value relative to the center of the robot.
   * @return The x value this maps to on the pixel output for the widget.
   */
  private double computePixelX(double x) {
    double px = ((x - cx) * pxPerUnit) + x0;
    return px;
  }

  /**
   * Compute the y value of a pixel location given an y value in robot space.
   *
   * @param y The y value relative to the center of the robot.
   * @return The y value this maps to on the pixel output for the widget.
   */
  private double computePixelY(double y) {
    // Swing Y axis is inverted
    double py = y0 - ((y - cy) * pxPerUnit);
    return py;
  }

  /**
   * Draw a single wheel onto the component.
   *
   * @param g Graphics context to use for drawing.
   * @param wheel The wheel that you want an image rendered for.
   */
  private void drawWheel(Graphics2D g, SwerveWheel wheel) {
    double ww = wheel.getWidth();
    double wd = wheel.getDiameter();
    double ww2 = ww / 2;
    double wd2 = wd / 2;


    // Draw wheel out-line and fill with tire color
    GeneralPath wheelOutline = new GeneralPath();
    CartesianPoint pt = new CartesianPoint(-ww2, -wd2);
    wheel.transform(pt, pt);
    wheelOutline.moveTo(computePixelX(pt.getX()), computePixelY(pt.getY()));

    pt.set(-ww2, +wd2);
    wheel.transform(pt, pt);
    wheelOutline.lineTo(computePixelX(pt.getX()), computePixelY(pt.getY()));

    pt.set(+ww2, +wd2);
    wheel.transform(pt, pt);
    wheelOutline.lineTo(computePixelX(pt.getX()), computePixelY(pt.getY()));

    pt.set(+ww2, -wd2);
    wheel.transform(pt, pt);
    wheelOutline.lineTo(computePixelX(pt.getX()), computePixelY(pt.getY()));

    wheelOutline.closePath();

    g.setStroke(wheelStroke);
    g.setPaint(tireColor);
    g.fill(wheelOutline);
    g.setColor(wheelColor);
    g.draw(wheelOutline);

    // Put indicator on front end of wheel
    double fIndDiam = Math.min(ww, wd) / 2;
    int msize = (int) (fIndDiam * pxPerUnit);
    pt.set(0, wd2 - fIndDiam * 0.75);
    wheel.transform(pt, pt);
    int x = (int) (computePixelX(pt.getX()) - msize / 2);
    int y = (int) (computePixelY(pt.getY()) - msize / 2);
    g.setPaint(frontColor);
    Ellipse2D front = new Ellipse2D.Double(x, y, msize, msize);
    g.fill(front);

    // If wheel has non-zero velocity, draw a velocity vector
    double velocity = wheel.getVelocity();
    if (velocity != 0) {
      double velLen = velocity * velocityScale;
      pt.set(0, 0);
      wheel.transform(pt, pt);
      double sx = pt.getX();
      double sy = pt.getY();
      pt.set(0, velLen);
      wheel.transform(pt, pt);
      double ex = pt.getX();
      double ey = pt.getY();
      g.setColor(wheelVector);
      g.setStroke(wheelStroke);
      g.setPaint(wheelVector);
      drawVelocityVector(g, computePixelX(sx), computePixelY(sy), computePixelX(ex), computePixelY(ey));
      
      FontMetrics fm = g.getFontMetrics();
      String vstr = nf.format(velocity);
      String bstr = nf.format(Math.toDegrees(wheel.getWheelBearing()));
      int vstrw = fm.stringWidth(vstr);
      int bstrw = fm.stringWidth(bstr);
      int fw = Math.max(bstrw, vstrw);
      double wx = wheel.getX();
      int wy = (int) computePixelY(wheel.getY());
      int fxr;
      if (wx > 0) {
        fxr = (int) computePixelX(wheel.getX() - wd * 0.75);
      } else {
        fxr = (int) computePixelX(wheel.getX() + wd * 0.75) + fw;
      }
      
      g.setColor(getForeground());
      g.drawString(vstr, fxr - vstrw, wy - 2);
      g.drawString(bstr, fxr - bstrw, wy + 2 + fm.getHeight());
    }

  }

  /**
   * Helper method to draw a velocity vector.
   *
   * @param g Graphics context to draw with.
   * @param sx x pixel value of start of vector.
   * @param sy y pixel value of start of vector.
   * @param ex x pixel value of end point that vector points at.
   * @param ey y pixel value of end point that vector points at.
   */
  private void drawVelocityVector(Graphics2D g, double sx, double sy, double ex, double ey) {
    double dx = ex - sx;
    double dy = ey - sy;
    double slope = Math.atan2(dy, dx);
    double as = slope + Math.PI * .875;
    double ptLen = 10;
    double ax = ex + ptLen * Math.cos(as);
    double ay = ey + ptLen * Math.sin(as);
    GeneralPath arrow = new GeneralPath();
    arrow.moveTo(sx, sy);
    arrow.lineTo(ex, ey);
    arrow.lineTo(ax, ay);
    g.draw(arrow);
  }

  /**
   * Sets all of the wheels to point to a particular direction.
   *
   * @param angRads The angle in radians on the unit circle. Zero corresponds to the axle aligned with the x-axis and the front of the tire on the positive side of the y-axis.
   */
  public void setWheelDirection(double angRads) {
    for (SwerveWheel wheel : wheels.getSwerveWheels()) {
      wheel.setAxleTheta(angRads);
      wheel.setVelocity(1.0);
    }
    invalidate();
  }

  /**
   * Get access to all of the swerve wheels managed by the widget.
   *
   * @return Collection of {@link SwerveWheel} objects.
   */
  public SwerveWheels getWheels() {
    // Hmmm, should we return a defensive copy?
    return wheels;
  }

  /**
   * Sets the direction that you want the robot to move in.
   *
   * @param ux The x portion of the translation velocity vector.
   * @param uy The y portion of the translation velocity vector.
   * @param rot The rotation velocity to add in (positive is counter-clockwise).
   */
  public void setDirection(double ux, double uy, double rot) {
    for (SwerveWheel wheel : wheels.getSwerveWheels()) {
      wheel.setDirection(ux, uy, rot);
    }
    repaint();
  }

}
