package com.thomasbreydo.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import java.awt.*;

public class BasketballSimulation extends AbstractSimulation {
  static final Color BALL_COLOR = Color.decode("#ee6730");
  /*
                                --|
                                  |
                                  |
  ---------------------------------
   */
  // TODO play with values below, and make ball starting velocity lower and angle ≈75
  static final int DIST_TO_HOOP = 30;
  static final int HOOP_HEIGHT = 5;
  static final int BACKBOARD_HEIGHT = 3;
  static final int RIM_LENGTH = 1;
  static final double EPS = 1;
  static final double DELTA_TIME = 0.001; // (s)
  static final double AIR_PRESSURE = 1.225; // at sea level, 15ºC (kg/m³)
  static boolean bounced;
  PlotFrame plotFrame = new PlotFrame("x", "y", "PlotGraph Simulation");
  Trail rim, hoopBase, backboard;
  Particle2D ball;

  public static void main(String[] args) {
    SimulationControl.createApp(new BasketballSimulation());
  }

  boolean shouldBounce() {
    if (bounced) return false;
    if (Math.abs(ball.getX() - DIST_TO_HOOP) > EPS) return false;
    if (ball.getY() - HOOP_HEIGHT > EPS) return false;
    if (HOOP_HEIGHT - BACKBOARD_HEIGHT - ball.getY() > EPS) return false;
    return true;
  }

  @Override
  protected void doStep() {
    if (shouldBounce()) {
      ball.bounceX();
      bounced = true;
    }
    ball.step(DELTA_TIME, AIR_PRESSURE);
  }

  @Override
  public void initialize() {
    plotFrame.setPreferredMinMax(0, (DIST_TO_HOOP * 3) >> 1, 0, 35);
    plotFrame.setAutoscaleX(false);
    plotFrame.setAutoscaleY(false);
    plotFrame.setSize(775, 328);
    plotFrame.setVisible(true);
    plotFrame.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    plotFrame.clearDrawables();

    double radius = control.getDouble("Radius of ball (m)");
    double mass = control.getDouble("Mass of ball (kg)");
    double angle = Math.toRadians(control.getDouble("Angle (degrees)"));
    double speed = control.getDouble("Speed (m/s)");
    ball = new Particle2D(radius, mass, 0, 1, angle, speed);
    ball.color = BALL_COLOR;
    plotFrame.addDrawable(ball);

    hoopBase = new Trail();
    rim = new Trail();
    backboard = new Trail();

    for (Trail drawable : new Trail[] {rim, hoopBase, backboard}) plotFrame.addDrawable(drawable);

    hoopBase.setStroke(new BasicStroke(2));
    hoopBase.color = Color.BLACK;
    hoopBase.addPoint(DIST_TO_HOOP, 0);
    hoopBase.addPoint(DIST_TO_HOOP, HOOP_HEIGHT);

    rim.setStroke(new BasicStroke(2));
    rim.color = Color.RED;
    rim.addPoint(DIST_TO_HOOP - RIM_LENGTH, HOOP_HEIGHT);
    rim.addPoint(DIST_TO_HOOP, HOOP_HEIGHT);

    backboard.setStroke(new BasicStroke(4));
    backboard.color = Color.BLACK;
    backboard.addPoint(DIST_TO_HOOP, HOOP_HEIGHT);
    backboard.addPoint(DIST_TO_HOOP, HOOP_HEIGHT + BACKBOARD_HEIGHT);
  }

  @Override
  public void reset() {
    control.setValue("Radius of ball (m)", 0.035);
    control.setValue("Mass of ball (kg)", 0.14);
    control.setValue("Angle (degrees)", 75);
    control.setValue("Speed (m/s)", 24.5);
  }
}
