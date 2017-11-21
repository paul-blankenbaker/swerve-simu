package com.techhounds.gui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.NumberFormat;

import javax.swing.JComponent;

import com.techhounds.math.CartesianPoint;

/**
 * Swing widget that can be used as a two axis joystick input.
 * <p>
 * Default settings (you can adjust):
 * </p>
 * <ul>
 * <li>Background color is black.</li>
 * <li>Foreground color is yellow.</li>
 * <li>Preferred size is 200x200 (does not need to be square - but looks
 * better).</li>
 * </ul>
 */
public class AxisWidget extends JComponent implements AxisListener, MouseListener, MouseMotionListener {

  /** Swing requirement to indicate version of widget. */
  private static final long serialVersionUID = 1L;

  /** Used to track x-axis value. */
  private final Axis xaxis;

  /** Used to track y-axis value. */
  private final Axis yaxis;

  /** How big the marker showing the current position should be. */
  private int markerSize;

  /** Used to format values. */
  private final NumberFormat format;

  /** Whether we are currently tracking mouse movement events. */
  private boolean track;

  /** The size of the square to draw the widget on. */
  private int sideLen;

  /**
   * Construct a new instance of the widget and associate with pre-existing
   * x-axis and y-axis trackers.
   *
   * @param xaxis
   *          The x-axis position tracker to associate with widget (must not be
   *          null).
   * @param yaxis
   *          The y-axis position tracker to associate with widget (must not be
   *          null).
   */
  public AxisWidget(Axis xaxis, Axis yaxis) {
    super();
    this.xaxis = xaxis;
    this.yaxis = yaxis;
    markerSize = 10;
    sideLen = 200;
    format = NumberFormat.getNumberInstance();
    format.setMinimumFractionDigits(3);
    format.setMaximumFractionDigits(3);
    Dimension widgetSize = new Dimension(sideLen, sideLen);
    setPreferredSize(widgetSize);
    setBackground(Color.BLACK);
    setForeground(Color.YELLOW);
    xaxis.addAxisListener(this);
    yaxis.addAxisListener(this);
    enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    addMouseListener(this);
    setFocusable(true);
  }

  /**
   * Construct a new instance (we will create our own internal x-axis and y-axis
   * trackers).
   */
  public AxisWidget() {
    this(new Axis(), new Axis());
  }

  /**
   * Get access to the x-axis tracker associated with the widget.
   *
   * @return The x-axis tracker (never null).
   */
  public final Axis getAxisX() {
    return xaxis;
  }

  /**
   * Get access to the y-axis tracker associated with the widget.
   *
   * @return The y-axis tracker (never null).
   */
  public final Axis getAxisY() {
    return yaxis;
  }

  /**
   * Draws the component (small circle marker on square with x and y values
   * displayed).
   *
   * @param g
   *          The graphics context used to render the drawing (must not be
   *          null).
   */
  @Override
  protected void paintComponent(Graphics g) {
    //int vw = getWidth();
    //int vh = getHeight();
    sideLen = Math.min(getWidth(), getHeight());
    int vw = sideLen;
    int vh = sideLen;
    g.setColor(getBackground());
    g.fillRect(0, 0, vw, vh);

    double xpos = xaxis.getPosition();
    double ypos = yaxis.getPosition();

    int cx = (int) (vw * (xpos + 1.0) / 2.0);
    int cy = (int) (vh * (ypos + 1.0) / 2.0);

    g.setColor(getForeground());
    g.drawOval(cx - markerSize / 2, cy - markerSize / 2, markerSize, markerSize);
    FontMetrics fm = g.getFontMetrics();

    int fx, fy;
    int fh = fm.getHeight();
    fy = cy + fh / 2;
    String ylabel = format.format(ypos);
    int textWidth = fm.stringWidth(ylabel);
    if (xpos < 0) {
      fx = vw - 2 - textWidth;
    } else {
      fx = 2;
    }
    fy = Math.min(vh - 2, Math.max(fy, fh + 2));
    g.drawString(ylabel, fx, fy);

    String xlabel = format.format(xpos);
    textWidth = fm.stringWidth(xlabel);
    if (ypos < 0) {
      fy = vh - 2;
    } else {
      fy = 2 + fh;
    }
    if (xpos < 0) {
      fx = cx;
    } else {
      fx = cx - textWidth;
    }
    g.drawString(xlabel, fx, fy);

  }

  /**
   * Make sure we repaint ourselves whenever the underlying axis values change.
   * 
   * @see com.techhounds.gui.AxisListener#valueUpdated(double, boolean)
   */
  @Override
  public void valueUpdated(double position, double oldPos) {
    if (position != oldPos) {
      repaint(20);
    }
  }

  /**
   * Clicking on the widget with a mouse causes things to happen.
   * <ul>
   * <li>The tracking state is toggled (we will either start/stop updating mouse
   * position).</li>
   * <li>The position of the x and y axis are updated based on where the user
   * clicks if tracking was enabled.</li>
   * <li>If the Control Key is held down, we will "snap" the position to a
   * grid.</li>
   * <li>We will start processing mouse motion events if tracking is
   * enabled.</li>
   * <li>If the Shift Key is held down, we will always turn tracking back off
   * (if you just want to "jump").</li>
   * </ul>
   * 
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    track = !track;
    if (track) {
      requestFocusInWindow();
      mouseMoved(e);
      if (!e.isShiftDown()) {
        addMouseMotionListener(this);
      } else {
        track = false;
      }
    } else {
      removeMouseMotionListener(this);
    }

  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  /**
   * When tracking has been turned on by clicking on the widget, we will update
   * x and y axis based on mouse movements.
   * <ul>
   * <li>The position of the x and y axis are updated based on where the user
   * moves the mouse.</li>
   * <li>If the Control Key is held down, we will "snap" the position to a
   * grid.</li>
   * </ul>
   * 
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    if (track) {
      double x = 2 * e.getX();
      x /= (sideLen - 1);
      double y = 2 * e.getY();
      y /= (sideLen - 1);
      if (e.isControlDown()) {
        x = snap(x);
        y = snap(y);
      }
      x -= 1.0;
      y -= 1.0;
      xaxis.setPosition(x);
      yaxis.setPosition(y);
    } else {
      removeMouseMotionListener(this);
    }
  }

  /**
   * Takes a value and snaps it to a grid having a step size of 0.125.
   *
   * @param val
   *          Value to snap to nice range.
   * @return Nice snapped value (2.223 becomes 2.250).
   */
  private double snap(double val) {
    double snapVal = Math.round(val * 8.0) / 8.0;
    return snapVal;
  }

  /**
   * Returns a "normalized" version of the current input axis (adjusts so radius
   * is never more than 1.0).
   * <p>
   * On a joystick axis, both the x and y values can range from [-1.0, +1.0].
   * This means that if the user is pressing due north or due south, the
   * magnitude of the radius is 1.0 where as if they point all the way to the NE
   * the magnitude is sqrt(2). This method normalizes the user inputs such that
   * the the radius will never exceed 1.0 (pointing all the way to the top right
   * results in the same magnitude as pointing straight up).
   * </p>
   *
   * @param dst
   *          Where to store the resulting (x, y) values (must not be null).
   * @return The Cartesian point with (x,y) values such that (x*x + y*y) is less
   *         then or equal to 1.0.
   */
  public CartesianPoint getNormalized(CartesianPoint dst) {
    double x = xaxis.getPosition();
    double y = -yaxis.getPosition();
    if (x == 0 && y == 0) {
      dst.set(x, y);
      return dst;
    }
    double theta = Math.atan2(y, x);
    x = Math.abs(x) * Math.cos(theta);
    y = Math.abs(y) * Math.sin(theta);
    dst.set(x, y);
    return dst;
  }
}
