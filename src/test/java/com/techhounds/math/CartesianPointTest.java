package com.techhounds.math;

import junit.framework.TestCase;

public class CartesianPointTest extends TestCase {
  private static final double expX = Double.MAX_VALUE;

  private static final double expY = -Double.MIN_VALUE;

  private static final CartesianPoint expPt = new CartesianPoint(-Double.MAX_VALUE, Double.MIN_VALUE);

  private static final double TOLERANCE = 1.0e-6;

  static final CartesianPoint[] points = {new CartesianPoint(1.0, 0.0), new CartesianPoint(10.0, 10.0), new CartesianPoint(0.0, 0.25),
                                          new CartesianPoint(0.0, 0.0)};

  static final double[] rotations = {0.0, 0.25 * Math.PI, 0.5 * Math.PI, 0.75 * Math.PI, 1.0 * Math.PI, -0.75 * Math.PI, -0.5 * Math.PI,
                                     -0.25 * Math.PI};

  public void testCartesianPointDoubleDouble() {
    CartesianPoint p = new CartesianPoint(expX, expY);
    assertEquals(expX, p.getX());
    assertEquals(expY, p.getY());
  }

  public void testCartesianPointCartesianPoint() {
    CartesianPoint p = new CartesianPoint(expPt);
    assertEquals(expPt.getX(), p.getX());
    assertEquals(expPt.getY(), p.getY());
  }

  public void testCartesianPoint() {
    CartesianPoint p = new CartesianPoint();
    assertEquals(0.0, p.getX());
    assertEquals(0.0, p.getY());
  }

  public void testComputeThetaDoubleDouble() {
    double expTheta, gotTheta;

    expTheta = 0;
    gotTheta = CartesianPoint.computeTheta(0.125, 0);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.25;
    gotTheta = CartesianPoint.computeTheta(2.5, 2.5);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.5;
    gotTheta = CartesianPoint.computeTheta(0, 500);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.75;
    gotTheta = CartesianPoint.computeTheta(-250, 250);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 1.0;
    gotTheta = CartesianPoint.computeTheta(-50, 0);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.75;
    gotTheta = CartesianPoint.computeTheta(-5, -5);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.5;
    gotTheta = CartesianPoint.computeTheta(0, -0.5);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.25;
    gotTheta = CartesianPoint.computeTheta(10000, -10000);
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.atan2(0, 0);
    gotTheta = CartesianPoint.computeTheta(0, 0);
    assertEquals(expTheta, gotTheta);
  }

  public void testComputeTheta() {
    double expTheta, gotTheta;

    expTheta = 0;
    gotTheta = new CartesianPoint(0.125, 0).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.25;
    gotTheta = new CartesianPoint(2.5, 2.5).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.5;
    gotTheta = new CartesianPoint(0, 500).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 0.75;
    gotTheta = new CartesianPoint(-250, 250).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * 1.0;
    gotTheta = new CartesianPoint(-50, 0).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.75;
    gotTheta = new CartesianPoint(-5, -5).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.5;
    gotTheta = new CartesianPoint(0, -0.5).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.PI * -0.25;
    gotTheta = new CartesianPoint(10000, -10000).computeTheta();
    assertEquals(expTheta, gotTheta, TOLERANCE);

    expTheta = Math.atan2(0, 0);
    gotTheta = new CartesianPoint().computeTheta();
    assertEquals(expTheta, gotTheta);
  }

  public void testComputeRDoubleDouble() {
    double expR, gotR;

    expR = 5;
    gotR = CartesianPoint.computeR(3, 4);
    assertEquals(expR, gotR, TOLERANCE);

    expR = 13;
    gotR = CartesianPoint.computeR(5, -12);
    assertEquals(expR, gotR, TOLERANCE);

    expR = 5;
    gotR = CartesianPoint.computeR(-3, 4);
    assertEquals(expR, gotR, TOLERANCE);

    expR = 13;
    gotR = CartesianPoint.computeR(-5, -12);
    assertEquals(expR, gotR, TOLERANCE);

    expR = 0;
    gotR = CartesianPoint.computeR(0, 0);
    assertEquals(expR, gotR, TOLERANCE);
  }

  public void testComputeR() {
    double expR, gotR;

    expR = 5;
    gotR = new CartesianPoint(3, 4).computeR();
    assertEquals(expR, gotR, TOLERANCE);

    expR = 13;
    gotR = new CartesianPoint(5, -12).computeR();
    assertEquals(expR, gotR, TOLERANCE);

    expR = 5;
    gotR = new CartesianPoint(-3, 4).computeR();
    assertEquals(expR, gotR, TOLERANCE);

    expR = 13;
    gotR = new CartesianPoint(-5, -12).computeR();
    assertEquals(expR, gotR, TOLERANCE);

    expR = 0;
    gotR = new CartesianPoint().computeR();
    assertEquals(expR, gotR, TOLERANCE);
  }

  public void testComputeR2DoubleDouble() {
    double expR2, gotR2;

    expR2 = 25;
    gotR2 = CartesianPoint.computeR2(3, 4);
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 13 * 13;
    gotR2 = CartesianPoint.computeR2(5, -12);
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 25;
    gotR2 = CartesianPoint.computeR2(-3, 4);
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 13 * 13;
    gotR2 = CartesianPoint.computeR2(-5, -12);
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 0;
    gotR2 = CartesianPoint.computeR2(0, 0);
    assertEquals(expR2, gotR2, TOLERANCE);
  }

  public void testComputeR2() {
    double expR2, gotR2;

    expR2 = 25;
    gotR2 = new CartesianPoint(3, 4).computeR2();
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 13 * 13;
    gotR2 = new CartesianPoint(5, -12).computeR2();
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 25;
    gotR2 = new CartesianPoint(-3, 4).computeR2();
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 13 * 13;
    gotR2 = new CartesianPoint(-5, -12).computeR2();
    assertEquals(expR2, gotR2, TOLERANCE);

    expR2 = 0;
    gotR2 = new CartesianPoint().computeR2();
    assertEquals(expR2, gotR2, TOLERANCE);
  }

  public void testTranslate() {
    CartesianPoint pt = new CartesianPoint();
    pt.translate(1, -2);
    assertEquals(-1.0, pt.getX());
    assertEquals(2.0, pt.getY());
    pt.translate(-4, +6);
    assertEquals(3.0, pt.getX());
    assertEquals(-4.0, pt.getY());
  }

  public void testRotateDoubleDoubleDoublePolarPoint() {
    for (CartesianPoint origin : points) {
      for (CartesianPoint pt : points) {
        for (double rotation : rotations) {
          CartesianPoint translated = new CartesianPoint(pt);
          translated.translate(origin.getX(), origin.getY());
          double expR = translated.computeR();
          double expTheta = translated.computeTheta() + rotation;

          PolarPoint gotPt = pt.rotate(origin.getX(), origin.getY(), rotation, new PolarPoint());
          assertEquals(expR, gotPt.getR(), TOLERANCE);
          assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
        }
      }
    }
  }

  public void testRotateDoubleDoubleDouble() {
    for (CartesianPoint origin : points) {
      for (CartesianPoint pt : points) {
        for (double rotation : rotations) {
          CartesianPoint translated = new CartesianPoint(pt);
          translated.translate(origin.getX(), origin.getY());
          double expR = translated.computeR();
          double expTheta = translated.computeTheta() + rotation;
          double expX = expR * Math.cos(expTheta) + origin.getX();
          double expY = expR * Math.sin(expTheta) + origin.getY();

          CartesianPoint copy = new CartesianPoint(pt);

          CartesianPoint gotPt = copy.rotate(origin.getX(), origin.getY(), rotation);
          assertEquals(true, gotPt == copy);
          assertEquals(expX, gotPt.getX(), TOLERANCE);
          assertEquals(expY, gotPt.getY(), TOLERANCE);
          gotPt.rotate(origin.getX(), origin.getY(), -rotation);
          assertEquals(pt.getX(), gotPt.getX(), TOLERANCE);
          assertEquals(pt.getY(), gotPt.getY(), TOLERANCE);
        }
      }
    }
  }

  public void testToPolarPolarPoint() {
    {
      double expR = 5.0;
      double expTheta = 0.0;
      CartesianPoint pt = new CartesianPoint(expR, 0);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.5;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * 0.25;
      CartesianPoint pt = new CartesianPoint(d, d);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 15.0;
      double expTheta = Math.PI * 0.5;
      CartesianPoint pt = new CartesianPoint(0, expR);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 2.5;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * 0.75;
      CartesianPoint pt = new CartesianPoint(-d, d);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 150.0;
      double expTheta = Math.PI * 1.0;
      CartesianPoint pt = new CartesianPoint(-expR, 0);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.125;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * -0.75;
      CartesianPoint pt = new CartesianPoint(-d, -d);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 10000.0;
      double expTheta = Math.PI * -0.5;
      CartesianPoint pt = new CartesianPoint(0, -expR);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 1.0;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * -0.25;
      CartesianPoint pt = new CartesianPoint(d, -d);
      PolarPoint gotPt = pt.toPolar(new PolarPoint());
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.0;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = 0;
      CartesianPoint pt = new CartesianPoint(d, -d);
      PolarPoint gotPt = pt.toPolar(new PolarPoint(1, 1));
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }
  }

  public void testToPolar() {
    {
      double expR = 5.0;
      double expTheta = 0.0;
      CartesianPoint pt = new CartesianPoint(expR, 0);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.5;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * 0.25;
      CartesianPoint pt = new CartesianPoint(d, d);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 15.0;
      double expTheta = Math.PI * 0.5;
      CartesianPoint pt = new CartesianPoint(0, expR);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 2.5;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * 0.75;
      CartesianPoint pt = new CartesianPoint(-d, d);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 150.0;
      double expTheta = Math.PI * 1.0;
      CartesianPoint pt = new CartesianPoint(-expR, 0);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.125;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * -0.75;
      CartesianPoint pt = new CartesianPoint(-d, -d);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double expR = 10000.0;
      double expTheta = Math.PI * -0.5;
      CartesianPoint pt = new CartesianPoint(0, -expR);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 1.0;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = Math.PI * -0.25;
      CartesianPoint pt = new CartesianPoint(d, -d);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }

    {
      double d = 0.0;
      double expR = Math.sqrt(d * d * 2);
      double expTheta = 0;
      CartesianPoint pt = new CartesianPoint(d, -d);
      PolarPoint gotPt = pt.toPolar();
      assertEquals(expR, gotPt.getR(), TOLERANCE);
      assertEquals(expTheta, gotPt.getTheta(), TOLERANCE);
    }
  }

  public void testGetX() {
    double exp = 4.0;
    CartesianPoint pt = new CartesianPoint(exp, exp * 2);
    double got = pt.getX();
    assertEquals(exp, got);

    pt = new CartesianPoint();
    got = pt.getX();
    assertEquals(0.0, got);
  }

  public void testSetX() {
    CartesianPoint pt = new CartesianPoint();

    double[] expVals = {-Double.MAX_VALUE, -1.0, -Double.MIN_VALUE, 0.0, Double.MIN_VALUE, 1.0, Double.MAX_VALUE};
    for (double exp : expVals) {
      pt.setX(exp);
      double got = pt.getX();
      assertEquals(exp, got);
    }
  }

  public void testGetY() {
    double exp = 4.0;
    CartesianPoint pt = new CartesianPoint(exp / 2, exp);
    double got = pt.getY();
    assertEquals(exp, got);

    pt = new CartesianPoint();
    got = pt.getY();
    assertEquals(0.0, got);
  }

  public void testSetY() {
    CartesianPoint pt = new CartesianPoint();

    double[] expVals = {-Double.MAX_VALUE, -1.0, -Double.MIN_VALUE, 0.0, Double.MIN_VALUE, 1.0, Double.MAX_VALUE};
    for (double exp : expVals) {
      pt.setY(exp);
      double got = pt.getY();
      assertEquals(exp, got);
    }
  }

  public void testToStringStatic() {
    String exp = "(-3.0, 4.5)";
    CartesianPoint pt = new CartesianPoint(-3, 4.5);
    String got = pt.toString();
    assertEquals(exp, got);
  }

  public void testToString() {
    String exp = "(3.0, -4.0)";
    CartesianPoint pt = new CartesianPoint(3, -4);
    String got = pt.toString();
    assertEquals(exp, got);
  }

}
