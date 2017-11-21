package com.techhounds.swerve;

import com.techhounds.math.CartesianPoint;
import junit.framework.TestCase;

public class SwerveWheelTest extends TestCase {

  private static final double EXP_DEFAULT_R = 1.0;

  private static final double EXP_DEFAULT_THETA = 0.0;

  private static final double[] numList = {-1e9, -1e6, -1e3, -1.0, -0.125, 0.0, 0.125, 1.0, 1e3, 1e6, 1e9};

  private static final double EXP_DEFAULT_DIAMETER = 10.0;

  private static final double EXP_DEFAULT_WIDTH = 4.0;

  private static final double TOLERANCE = 1e-6;

  public void testSwerveWheelDoubleDoubleDoubleDouble() {
    for (double expX : numList) {
      for (double expY : numList) {
        for (double expDiameter : numList) {
          for (double expWidth : numList) {
            SwerveWheel sw = new SwerveWheel(expX, expY, expDiameter, expWidth);
            assertEquals(expX, sw.getX());
            assertEquals(expY, sw.getY());
            assertEquals(expDiameter, sw.getDiameter());
            assertEquals(expWidth, sw.getWidth());
            assertEquals(EXP_DEFAULT_R, sw.getVelocity());
            assertEquals(EXP_DEFAULT_THETA, sw.getAxleTheta());
          }
        }
      }
    }
  }

  public void testSwerveWheel() {
    SwerveWheel sw = new SwerveWheel();
    assertEquals(0.0, sw.getX());
    assertEquals(0.0, sw.getY());
    assertEquals(EXP_DEFAULT_DIAMETER, sw.getDiameter());
    assertEquals(EXP_DEFAULT_WIDTH, sw.getWidth());
    assertEquals(EXP_DEFAULT_R, sw.getVelocity());
    assertEquals(EXP_DEFAULT_THETA, sw.getAxleTheta());
  }

  public void testGetY() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(notExp, exp, notExp, notExp);
      assertEquals(exp, sw.getY());
    }
  }

  public void testGetX() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(exp, notExp, notExp, notExp);
      assertEquals(exp, sw.getX());
    }
  }

  public void testSetPosition() {
    SwerveWheel sw = new SwerveWheel();
    for (double expX : numList) {
      for (double expY : numList) {
        sw.setPosition(expX, expY);
        assertEquals(expX, sw.getX());
        assertEquals(expY, sw.getY());
      }
    }
  }

  public void testGetDiameter() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(notExp, notExp, exp, notExp);
      assertEquals(exp, sw.getDiameter());
    }
  }

  public void testSetDiameter() {
    SwerveWheel sw = new SwerveWheel();
    for (double exp : numList) {
      sw.setDiameter(exp);
      assertEquals(exp, sw.getDiameter());
    }
  }

  public void testGetWidth() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(notExp, notExp, notExp, exp);
      assertEquals(exp, sw.getWidth());
    }
  }

  public void testSetWidth() {
    SwerveWheel sw = new SwerveWheel();
    for (double exp : numList) {
      sw.setWidth(exp);
      assertEquals(exp, sw.getWidth());
    }
  }

  public void testGetAxleTheta() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(notExp, notExp, notExp, exp);
      assertEquals(EXP_DEFAULT_THETA, sw.getAxleTheta());
    }
  }

  public void testSetAxleTheta() {
    SwerveWheel sw = new SwerveWheel();
    for (double exp : numList) {
      sw.setAxleTheta(exp);
      assertEquals(exp, sw.getAxleTheta());
    }
  }

  public void testGetWheelBearing() {
    for (double exp : numList) {
      double notExp = exp + 10;
      SwerveWheel sw = new SwerveWheel(notExp, notExp, notExp, exp);
      assertEquals(-EXP_DEFAULT_THETA, sw.getWheelBearing());
    }
  }

  public void testSetWheelBearing() {
    SwerveWheel sw = new SwerveWheel();
    for (double exp : numList) {
      sw.setWheelBearing(exp);
      assertEquals(exp, sw.getWheelBearing());
    }
  }

  public void testGetVelocity() {
    SwerveWheel sw = new SwerveWheel();
    assertEquals(1.0, sw.getVelocity());
  }

  public void testSetVelocity() {
    SwerveWheel sw = new SwerveWheel();
    for (double exp : numList) {
      sw.setVelocity(exp);
      assertEquals(exp, sw.getVelocity());
    }
  }

  public void testComputeForTurnAround() {
    SwerveWheel wheel = new SwerveWheel(10.0, 10.0, 5.0, 2.0);
    double got, exp;

    exp = 0.0;
    got = wheel.computeForTurnAround(10.0, 0.0);
    assertEquals(exp, got, TOLERANCE);

    exp = Math.PI / 4;
    got = wheel.computeForTurnAround(0.0, 0.0);
    assertEquals(exp, got, TOLERANCE);

  }

  public void testComputeDistance() {
    SwerveWheel sw = new SwerveWheel(3, 4, 10, 2.5);

    assertEquals(5.0, sw.computeDistance(0, 0), TOLERANCE);
    assertEquals(5.0, sw.computeDistance(6, 8), TOLERANCE);
    assertEquals(5.0, sw.computeDistance(6, 0), TOLERANCE);
    assertEquals(5.0, sw.computeDistance(0, 8), TOLERANCE);
  }

  // public void testIsInverted() {
  // SwerveWheel sw = new SwerveWheel(3, 4, 10, 2.5);
  //
  // assertEquals(false, sw.isMotorInverted());
  // }
  //
  // public void testSetInverted() {
  // SwerveWheel sw = new SwerveWheel(3, 4, 10, 2.5);
  //
  // sw.setMotorInverted(true);
  // assertEquals(true, sw.isMotorInverted());
  //
  // // Second time should not change
  // sw.setMotorInverted(true);
  // assertEquals(true, sw.isMotorInverted());
  //
  // sw.setMotorInverted(false);
  // assertEquals(false, sw.isMotorInverted());
  //
  // // Second time should not change
  // sw.setMotorInverted(false);
  // assertEquals(false, sw.isMotorInverted());
  //
  // // Verify we can set back to true
  // sw.setMotorInverted(true);
  // assertEquals(true, sw.isMotorInverted());
  // }

  public void testSetDirectionTranslate() {
    double r = 100.0;
    double wx = r * 0.5;
    double wy = r * Math.sqrt(3.0) * 0.5;

    double[][] wheelPositions = {{wx, wy}, {-wx, wy}, {-wx, -wy}, {wx, -wy}, {wy, wx}};

    // Translation should not care about position of wheel on robot!
    for (double[] wp : wheelPositions) {
      double ux45 = Math.sqrt(2.0) / 2;
      double uy45 = ux45;
      double ux30 = Math.sqrt(3.0) / 2;
      double uy30 = 0.5;
      SwerveWheel sw = new SwerveWheel(wp[0], wp[1], 5, 2);

      sw.setDirection(ux45, uy45, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(45.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      sw.setDirection(ux30, uy30, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(30.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from +30 to -30 (60 degree turn crossing 0 axis)
      sw.setDirection(ux30, -uy30, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-30.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from -30 to +90 is quicker to goto -90 and invert motors
      sw.setDirection(0, 1.0, 0.0);
      assertEquals(-1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-90.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from +90 to -90 is quicker to just invert the motors and leave
      // wheel
      // at -90
      sw.setDirection(0, -1.0, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-90.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from -90 to +150 is quicker to invert motors and turn to -30
      sw.setDirection(-ux30, uy30, 0.0);
      assertEquals(-1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-30.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from +150 to -90 is 60 degree rotation no motor inversion
      sw.setDirection(0, -1, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-90.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from -90 to -150 (60 degree change to -150 no motor inversion)
      sw.setDirection(-ux30, -uy30, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-150.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from -150 to +150 is 60 degree rotation across 180 no motor
      // inversion
      sw.setDirection(-ux30, uy30, 0.0);
      assertEquals(1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(150.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Turn from +150 to +30 is 60 degree rotation across 180 with inversion
      sw.setDirection(ux30, uy30, 0.0);
      assertEquals(-1.0, sw.getVelocity(), TOLERANCE);
      assertEquals(-150.0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);
    }
  }

  public void testSetDirectionRotation() {
    double r = 100.0;
    double wx = r * 0.5;
    double wy = r * Math.sqrt(3.0) * 0.5;
    double tanDegs = 60.0;

    double[][] wheelPositions = {{wx, wy, 1.0, tanDegs}, {-wx, wy, -1.0, -tanDegs}, {-wx, -wy, -1.0, tanDegs}, {wx, -wy, 1.0, -tanDegs}};

    for (double[] wp : wheelPositions) {
      double expVelDir = wp[2];
      double expAng = wp[3];
      SwerveWheel sw = new SwerveWheel(wp[0], wp[1], 5, 2);

      sw.setDirection(0, 0, 1.0);
      assertEquals(1.0 * expVelDir, sw.getVelocity(), TOLERANCE);
      assertEquals(expAng, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      sw.setDirection(0, 0, 0.5);
      assertEquals(0.5 * expVelDir, sw.getVelocity(), TOLERANCE);
      assertEquals(expAng, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      sw.setDirection(0, 0, -1.0);
      assertEquals(-1.0 * expVelDir, sw.getVelocity(), TOLERANCE);
      assertEquals(expAng, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      // Put back to starting position and try other direction
      sw.setAxleTheta(0.0);

      sw.setDirection(0, 0, -0.5);
      assertEquals(-0.5 * expVelDir, sw.getVelocity(), TOLERANCE);
      assertEquals(expAng, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

      sw.setDirection(0, 0, 1.0);
      assertEquals(1.0 * expVelDir, sw.getVelocity(), TOLERANCE);
      assertEquals(expAng, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);
    }
  }

  public void testSetDirection() {
    double r = 100.0;
    double wy = 0.5;
    double wx = Math.sqrt(3.0) * 0.5;
    double tanDegs = 30.0;
    SwerveWheel sw = new SwerveWheel(r * wx, r * wy, 5, 2);

    // Translation vector lines up with rotation vector
    sw.setDirection(wx, wy, 1.0);
    assertEquals(2.0, sw.getVelocity(), TOLERANCE);
    assertEquals(tanDegs, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector opposite of rotation vector
    sw.setDirection(-wx, -wy, 1.0);
    assertEquals(0.0, sw.getVelocity(), TOLERANCE);
    assertEquals(tanDegs, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector same x magnitude, opposite y magnitude
    sw.setDirection(wx, -wy, 1.0);
    assertEquals(2 * wx, sw.getVelocity(), TOLERANCE);
    assertEquals(0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector same y magnitude, opposite x magnitude
    sw.setDirection(-wx, wy, 1.0);
    assertEquals(2 * wy, sw.getVelocity(), TOLERANCE);
    assertEquals(90, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Repeat of above four but with opposite rotation
    
    // Translation vector lines up with rotation vector
    sw.setDirection(-wx, -wy, -1.0);
    assertEquals(-2.0, sw.getVelocity(), TOLERANCE);
    assertEquals(tanDegs, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector opposite of rotation vector
    sw.setDirection(wx, wy, -1.0);
    assertEquals(0.0, sw.getVelocity(), TOLERANCE);
    assertEquals(tanDegs, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector same x magnitude, opposite y magnitude
    sw.setDirection(-wx, wy, -1.0);
    assertEquals(-2 * wx, sw.getVelocity(), TOLERANCE);
    assertEquals(0, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);

    // Translation vector same y magnitude, opposite x magnitude
    sw.setDirection(wx, -wy, -1.0);
    assertEquals(-2 * wy, sw.getVelocity(), TOLERANCE);
    assertEquals(90, Math.toDegrees(sw.getAxleTheta()), TOLERANCE);
  }

  public void testTransform() {
    double xofs = 10.0;
    double yofs = 15.0;
    double ww = 2;
    double ww2 = ww / 2;
    double wd = 4;
    double wd2 = wd / 2;
    
    SwerveWheel sw = new SwerveWheel(xofs, yofs, wd, ww);
    
    // Top right corner of wheel
    CartesianPoint wspace = new CartesianPoint(ww2, wd2);
    CartesianPoint rspace = new CartesianPoint();
    
    assertEquals(rspace, sw.transform(wspace, rspace));
    assertEquals(xofs + ww2, rspace.getX());
    assertEquals(yofs + wd2, rspace.getY());
    
    // Rotate point in wheel space on 45 degree line by 45 degrees (line up on y axis)
    wspace.set(1.0, 1.0);
    double r = Math.sqrt(2.0);
    double axleTheta = Math.toRadians(45);

    sw.setAxleTheta(axleTheta);
    assertEquals(rspace, sw.transform(wspace, rspace));
    assertEquals(xofs, rspace.getX());
    assertEquals(yofs + r, rspace.getY());
    
    // Drive motor vector flip does not change space transformation
    sw.setDirection(-1.0, -1.0, 0);
    assertEquals(rspace, sw.transform(wspace, rspace));
    assertEquals(xofs, rspace.getX());
    assertEquals(yofs + r, rspace.getY());
  }

}
