package com.techhounds.gui;

import java.util.ArrayList;

/**
 * An abstract representation of a jostick axis that has a range of [-1.0,
 * +1.0].
 */
public final class Axis {

  /**
   * Current position on the axis.
   */
  private double position;

  /**
   * Listeners to notify if position is updated.
   */
  private final ArrayList<AxisListener> listeners;

  /**
   * Construct a new instance with an initial position of 0.0.
   */
  public Axis() {
    position = 0.0;
    listeners = new ArrayList<AxisListener>();
  }

  /**
   * Get the current position.
   *
   * @return A value in the range of [-1.0, +1.0].
   */
  public double getPosition() {
    return position;
  }

  /**
   * Set the current position.
   *
   * @param newPosition
   *          A value in the range of [-1.0, +1.0] (we will force to this range
   *          if you pass something outside of it).
   */
  public void setPosition(double newPosition) {
    double oldPos = position;
    position = Math.max(-1.0, Math.min(+1.0, newPosition));

    // Not sure if lambda expressions are growing on me yet or not
    listeners.forEach(name -> name.valueUpdated(this.position, oldPos));
  }

  /**
   * Add listener to notify whenever the position is set on the axis.
   *
   * @param listener
   *          Must not be null. If listener is already registered, the request
   *          is ignored (we won't add the same listener twice).
   */
  public void addAxisListener(AxisListener listener) {
    if (listener == null) {
      throw new NullPointerException();
    }
    if (!listeners.contains(listener)) {
      listeners.add(listener);
    }
  }

  /**
   * Removes previously registered listener that was added via
   * {@link #addAxisListener(AxisListener)}.
   *
   * @param listener
   *          Reference to listener to remove (it is OK to pass null or a
   *          listener that is not currently registered - we just don't do
   *          anything).
   */
  public void removeAxisListener(AxisListener listener) {
    listeners.remove(listener);
  }

}
