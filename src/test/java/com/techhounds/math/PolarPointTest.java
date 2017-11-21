package com.techhounds.math;

import junit.framework.TestCase;

public class PolarPointTest extends TestCase {
  private static final double TOLERANCE = 1.0e-6;

  private static final double[] TEST_THETAS = {0.0, 0.25 * Math.PI, 0.5 * Math.PI, 0.75 * Math.PI, 1.0 * Math.PI, -0.75 * Math.PI, -0.5 * Math.PI,
                                               -0.25 * Math.PI

  };

  private static final double[] TEST_RADII = {-Double.MAX_VALUE, -1000, -1, -0.125, 0.0, 0.125, +1, +1000, Double.MAX_VALUE};

  public void testPolarPointDoubleDouble() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint pt = new PolarPoint(expR, expTheta);
        assertEquals(expR, pt.getR());
        assertEquals(expTheta, pt.getTheta());
      }
    }
  }

  public void testPolarPointPolarPoint() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint orig = new PolarPoint(expR, expTheta);
        PolarPoint pt = new PolarPoint(orig);
        assertEquals(expR, pt.getR());
        assertEquals(expTheta, pt.getTheta());
      }
    }
  }

  public void testPolarPoint() {
    PolarPoint pt = new PolarPoint();
    assertEquals(0.0, pt.getR());
    assertEquals(0.0, pt.getTheta());
  }

  public void testGetR() {
    for (double expR : TEST_RADII) {
      PolarPoint pt = new PolarPoint(expR, 0);
      assertEquals(expR, pt.getR());
    }
  }

  public void testSetR() {
    PolarPoint pt = new PolarPoint();
    for (double expR : TEST_RADII) {
      pt.setR(expR);
      assertEquals(expR, pt.getR());
    }
  }

  public void testGetTheta() {
    for (double expTheta : TEST_THETAS) {
      PolarPoint pt = new PolarPoint(0, expTheta);
      assertEquals(expTheta, pt.getTheta());
    }
  }

  public void testSetTheta() {
    PolarPoint pt = new PolarPoint();
    for (double expTheta : TEST_THETAS) {
      pt.setTheta(expTheta);
      assertEquals(expTheta, pt.getTheta());
    }
  }

  public void testGetThetaDegrees() {
    for (double expTheta : TEST_THETAS) {
      PolarPoint pt = new PolarPoint(0, expTheta);
      assertEquals(Math.toDegrees(expTheta), pt.getThetaDegrees(), TOLERANCE);
    }
  }

  public void testSetThetaDegrees() {
    PolarPoint pt = new PolarPoint();
    for (double expTheta : TEST_THETAS) {
      pt.setThetaDegrees(Math.toDegrees(expTheta));
      assertEquals(expTheta, pt.getTheta(), TOLERANCE);
    }
  }

  public void testGetBearing() {
    for (double expTheta : TEST_THETAS) {
      PolarPoint pt = new PolarPoint(0, expTheta);
      assertEquals(Math.PI / 2 - expTheta, pt.getBearing(), TOLERANCE);
    }
  }

  public void testSetBearing() {
    PolarPoint pt = new PolarPoint();
    for (double expTheta : TEST_THETAS) {
      pt.setBearing(Math.PI / 2 - expTheta);
      assertEquals(expTheta, pt.getTheta(), TOLERANCE);
    }
  }

  public void testGetBearingDegrees() {
    for (double expTheta : TEST_THETAS) {
      PolarPoint pt = new PolarPoint(0, expTheta);
      assertEquals(90 - Math.toDegrees(expTheta), pt.getBearingDegrees(), TOLERANCE);
    }
  }

  public void testSetBearingDegrees() {
    PolarPoint pt = new PolarPoint();
    for (double expTheta : TEST_THETAS) {
      pt.setBearingDegrees(Math.toDegrees(Math.PI / 2 - expTheta));
      assertEquals(expTheta, pt.getTheta(), TOLERANCE);
    }
  }

  public void testToString() {
    PolarPoint pt = new PolarPoint(-3.5, Math.PI / 4);
    String expStr = "(-3.5, \u222045.0)";
    assertEquals(expStr, pt.toString());
  }

  public void testToCartesianCartesianPoint() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint pt = new PolarPoint(expR, expTheta);
        double expX = expR * Math.cos(expTheta);
        double expY = expR * Math.sin(expTheta);
        CartesianPoint got = pt.toCartesian(new CartesianPoint());
        assertEquals(expX, got.getX(), TOLERANCE);
        assertEquals(expY, got.getY(), TOLERANCE);
      }
    }
  }

  public void testToCartesian() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint pt = new PolarPoint(expR, expTheta);
        double expX = expR * Math.cos(expTheta);
        double expY = expR * Math.sin(expTheta);
        CartesianPoint got = pt.toCartesian();
        assertEquals(expX, got.getX(), TOLERANCE);
        assertEquals(expY, got.getY(), TOLERANCE);
      }
    }
  }

  public void testComputeX() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint pt = new PolarPoint(expR, expTheta);
        double expX = expR * Math.cos(expTheta);
        double gotX = pt.computeX();
        assertEquals(expX, gotX, TOLERANCE);
      }
    }
  }

  public void testComputeY() {
    for (double expTheta : TEST_THETAS) {
      for (double expR : TEST_RADII) {
        PolarPoint pt = new PolarPoint(expR, expTheta);
        double expY = expR * Math.sin(expTheta);
        double gotY = pt.computeY();
        assertEquals(expY, gotY, TOLERANCE);
      }
    }
  }
  
  public void testComputeShortestPath() {
    double[][] vals = {
      { +0.50, +0.25, -0.25 },
      { +0.50, -0.25, -0.75 },
      { -0.25, +1.00, -0.75 },
      { -0.50, -0.625, -0.125 },
      { -0.25, +0.25, +0.500 },
      { +0.125, -0.250, -0.375 },
      { +0.75, -0.50, +0.75 },
      { -0.875, +0.750, -0.375 },
    };
    for (double[] check : vals) {
      double oldTheta = check[0] * Math.PI;
      double newTheta = check[1] * Math.PI;
      double exp = check[2] * Math.PI;
      double got = PolarPoint.computeShortestPath(oldTheta, newTheta);
      assertEquals(exp, got, TOLERANCE);
      double got2 = PolarPoint.computeShortestPath(-newTheta, -oldTheta);
      assertEquals(exp, got2, TOLERANCE);
    }
  }

}
