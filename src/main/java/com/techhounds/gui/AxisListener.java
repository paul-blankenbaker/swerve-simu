package com.techhounds.gui;

/**
 * Methods that must be implemented by objects that want to monitor {@link Axis}
 * updates.
 */
public interface AxisListener {

  /**
   * Method that is invoked whenever the position is updated on a {@link Axis}.
   *
   * @param position
   *          The new position that was set.
   * @param change
   *          The prior position (if you want to determine if and how much of a
   *          change occurred).
   */
  void valueUpdated(double position, double oldPosition);

}
