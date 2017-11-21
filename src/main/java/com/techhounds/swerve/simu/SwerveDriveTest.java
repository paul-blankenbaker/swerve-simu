package com.techhounds.swerve.simu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import com.techhounds.gui.AxisListener;
import com.techhounds.gui.AxisWidget;
import com.techhounds.math.CartesianPoint;
import com.techhounds.swerve.SwerveViewer;
import com.techhounds.swerve.SwerveWheel;
import com.techhounds.swerve.SwerveWheels;

/**
 * Sample GUI tool that lets you visually experiment with a swerve simulated swerve drive.
 */
public final class SwerveDriveTest extends JFrame implements AxisListener {

  /** Swing component ID. */
  private static final long serialVersionUID = 1L;

  /**
   * Swing component used to render the robot.
   */
  private SwerveViewer swerveViewer;

  /**
   * Swing component for the translation UI (simulates joystick axis).
   */
  private AxisWidget translation;

  /**
   * Swing component for the rotation UI (simulates joystick axis - we only use x-axis).
   */
  private AxisWidget rotation;

  /**
   * Constructs a new instance of the GUI widget (does not display it).
   *
   * @param args Command line arguments (currently ignored).
   */
  public SwerveDriveTest(String[] args) {
    super("Swerve Simu");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    swerveViewer = new SwerveViewer();
    swerveViewer.setFocusable(true);
    InputMap imap = swerveViewer.getInputMap(JComponent.WHEN_FOCUSED);
    ActionMap amap = swerveViewer.getActionMap();

    imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "zero");
    amap.put("zero", new AbstractAction() {
      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent e) {
        translation.getAxisX().setPosition(0);
        translation.getAxisY().setPosition(0);
        rotation.getAxisX().setPosition(0.0);
        rotation.getAxisY().setPosition(0.0);
      }
    });
    swerveViewer.addMouseListener(new MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        swerveViewer.requestFocusInWindow();
      }
    });
    getContentPane().add(swerveViewer, BorderLayout.CENTER);

    JPanel controls = new JPanel();
    controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
    getContentPane().add(controls, BorderLayout.WEST);

    translation = new AxisWidget();
    addWidget(controls, "Translation", translation);

    controls.add(Box.createVerticalStrut(10));

    rotation = new AxisWidget();
    addWidget(controls, "Rotation (x)", rotation);
    
    pack();

    translation.getAxisX().addAxisListener(this);
    translation.getAxisY().addAxisListener(this);
    rotation.getAxisX().addAxisListener(this);
  }

  /**
   * Helper method to add a labeled widget to the control area.
   *
   * @param controls Control area to add to.
   * @param label Label for widget.
   * @param widget The widget to add.
   */
  private void addWidget(JPanel controls, String label, JComponent widget) {
    if (label != null) {
      controls.add(new JLabel(label));
    }
    controls.add(widget);
  }

  /**
   * Adds a wheel to manage as a part of the robot.
   *
   * @param wheel The wheel to add.
   */
  public void addWheel(SwerveWheel wheel) {
    swerveViewer.addWheel(wheel);
  }

  //@Override
  public void valueUpdated(double position, double oldPosition) {
    if (position != oldPosition) {
      CartesianPoint transIn = new CartesianPoint();
      translation.getNormalized(transIn);
      double ux = transIn.getX();
      double uy = transIn.getY();
      double transR = transIn.computeR();
      double rot = -rotation.getAxisX().getPosition();
      // Scale rotation down if user is translating (so we can't exceed 1.0)
      rot *= (1.0 - transR);

      // Convert user inputs from polar space to wheel bearing space
      double wx = uy;
      double wy = -ux;
      swerveViewer.setDirection(wx, wy, rot);
    }
  }

  /**
   * Entry point into the application.
   *
   * @param args Set of command line arguments.
   */
  public static void main(String[] args) {
    SwerveDriveTest main = new SwerveDriveTest(args);
    double wheelDiameter = 4;
    double wheelWidth = 1;
    double robotWidth2 = 20 / 2.0;
    double robotLength2 = 30 / 2.0;
    main.addWheel(new SwerveWheel(robotWidth2, robotLength2, wheelDiameter, wheelWidth));
    main.addWheel(new SwerveWheel(-robotWidth2, -robotLength2, wheelDiameter, wheelWidth));
    main.addWheel(new SwerveWheel(robotWidth2, -robotLength2, wheelDiameter, wheelWidth));
    main.addWheel(new SwerveWheel(-robotWidth2, robotLength2, wheelDiameter, wheelWidth));
    main.setVisible(true);
  }

  //@Override
  public void valueUpdated2(double position, double oldPosition) {
    if (position != oldPosition) {
      SwerveWheels wheels = swerveViewer.getWheels();
      SwerveWheel wheel0 = wheels.getSwerveWheel(0);
      double vx = translation.getAxisX().getPosition();
      double vy = -translation.getAxisY().getPosition();
      double omega = -rotation.getAxisX().getPosition();
      int uiQuad = getQuadrant(vx, vy, omega);
      
      for (SwerveWheel w : wheels.getSwerveWheels()) {
        double x = w.getX();
        double y = w.getY();
        if (getQuadrant(x, y, 1.0) == uiQuad) {
          wheel0 = w;
          break;
        }
      }
      
      double vmag2 = vx * vx + vy * vy;
      double vtheta = (vmag2 == 0) ? 0 : Math.atan2(vy, vx) - Math.PI / 2;
      double vcosTheta = Math.cos(vtheta);
      double vcosTheta2 = vcosTheta * vcosTheta;
      double vsinTheta = Math.sin(vtheta);
      double vsinTheta2 = vsinTheta * vsinTheta;
      double omega2 = omega * omega;
      double vmax = 20.0;
      double vmax2 = vmax * vmax;
      double vmag = Math.sqrt(vmag2) * Math.abs((Math.abs(vx) > Math.abs(vy)) ? vsinTheta : vcosTheta);
      
      // Position of FR wheel from center of robot (assumes symmetrical placement)
      double rx = Math.abs(wheel0.getX());
      double rx2 = rx * rx;
      double ry = Math.abs(wheel0.getY());
      double ry2 = ry * ry;
      double r2 = rx2 + ry2;
      //double r = Math.sqrt(r2);
      
      double mess = omega2 * (ry2 * vcosTheta2 + rx2 * vsinTheta2 + 2 * rx * ry * vsinTheta * vcosTheta) - omega2 * r2 + vmax2;
      double vabs = (ry * vcosTheta + rx * vsinTheta) * omega + Math.sqrt(mess);
      
      for (SwerveWheel wheel : wheels.getSwerveWheels()) {
        double wx = vabs * vcosTheta + wheel.getY() * omega;
        double wy = vabs * vsinTheta + wheel.getX() * omega;
        wheel.setDirection(wx * vmag, wy * vmag, 0);
      }
      swerveViewer.repaint();
    }
  }

  private int getQuadrant(double vx, double vy, double omega) {
    if (omega < 0) {
      vx = -vx;
      vy = -vy;
    }
    int quad = 0;
    if (vx < 0) {
      quad++;
    }
    if (vy < 0) {
      quad += 2;
    }
    return quad;
  }
  
}
