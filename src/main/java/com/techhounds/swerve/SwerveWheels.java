package com.techhounds.swerve;

import java.util.ArrayList;
import java.util.Collection;

public class SwerveWheels {

  private ArrayList<SwerveWheel> wheels;

  private boolean dirty = false;

  private boolean applyRotation = false;

  private double cx = 0.0;

  private double cy = 0.0;

  private double translationTheta = 0.0;

  public SwerveWheels() {
    wheels = new ArrayList<SwerveWheel>();
  }

  public int size() {
    return wheels.size();
  }

  public SwerveWheel getSwerveWheel(int idx) {
    apply();
    return wheels.get(idx);
  }

  public Collection<SwerveWheel> getSwerveWheels() {
    apply();
    return wheels;
  }

  public void setTranslation(double translationTheta) {
    if (this.translationTheta != translationTheta) {
      dirty = true;
      this.translationTheta = translationTheta;
    }
  }

  public void setTurn(double cx, double cy) {
    if (this.cx != cx || this.cy != cy) {
      dirty = true;
      applyRotation = (this.cx != 0) || (this.cy != 0);
      this.cx = cx;
      this.cy = cy;
    }
  }

  private void apply() {
    if (dirty) {
      double maxDist = 0;
      for (SwerveWheel wheel : wheels) {
        double angTheta = translationTheta;
        double dist = 1.0;
        if (applyRotation) {
          double rotTheta = wheel.computeForTurnAround(cx, cy);
          angTheta += rotTheta;
          dist = wheel.computeDistance(cx, cy);
        }
        wheel.setAxleTheta(angTheta);
        maxDist = Math.max(maxDist, dist);
        wheel.setVelocity(dist);
      }
      if (maxDist > 0) {
        for (SwerveWheel wheel : wheels) {
          double dist = wheel.getVelocity();
          // How fast the wheel should be moving as a multiplier to the fastest
          // wheel
          double velRatio = dist / maxDist;
          wheel.setVelocity(velRatio);
        }
      }

    }
    dirty = false;
  }

  public SwerveWheel add(SwerveWheel wheel) {
    assert (wheel != null);
    wheels.add(wheel);
    dirty = true;
    return wheel;
  }

}
