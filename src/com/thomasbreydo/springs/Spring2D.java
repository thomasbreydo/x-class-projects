package com.thomasbreydo.springs;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Spring;

import java.awt.*;

class Spring2D implements Drawable {
  // n points where each point is springMass/n and experiences
  // gravity, spring above, and spring below.
  private static final double DELTA_TIME = 0.1; // (s)
  private static final Vector2D GRAVITY = new Vector2D(0, -9.81);
  Spring[] springDrawables;
  Circle weightDrawable;
  FreeBody[] springs;
  int nSprings;
  FreeBody weight;
  double sliceSpringConstant;
  double sliceMass;
  double naturalSliceNorm;

  public Spring2D(
      double springConstant,
      double springMass,
      double attachedMass,
      int nSprings,
      Vector2D start,
      Vector2D end) {
    super();

    this.nSprings = nSprings;
    sliceSpringConstant = springConstant * nSprings;
    sliceMass = springMass / nSprings;
    naturalSliceNorm = Vector2D.distanceBetween(start, end) / nSprings;
    springs = new FreeBody[nSprings];
    springDrawables = new Spring[nSprings];

    Vector2D naturalSlice = end.minus(start).scaledToNorm(naturalSliceNorm);
    for (int i = 0; i < nSprings; ++i) {
      springs[i] = new FreeBody(start.plus(naturalSlice.scaledBy(i)), sliceMass);
      springDrawables[i] = new Spring(0.3);
      springDrawables[i].setEdgeStroke(new BasicStroke((float) .5));
      springDrawables[i].setEdgeColor(
          new Color(255 * i / nSprings, 0, 255 * (nSprings - i) / nSprings));
      springDrawables[i].setThinExtremes(false);
      springDrawables[i].setSolenoid(1);
      springDrawables[i].setResolution(4, 100);
    }

    weight = new FreeBody(end, attachedMass);
    weightDrawable = new Circle();
    weightDrawable.color = Color.BLACK;
  }

  private Vector2D springForceAtEnd(FreeBody start, FreeBody end) {
    Vector2D currentSpring = end.getPosition().minus(start.getPosition());
    Vector2D naturalSpring = currentSpring.scaledToNorm(naturalSliceNorm);
    Vector2D dx = currentSpring.minus(naturalSpring); // down vector
    return dx.scaledBy(-sliceSpringConstant); // -k * dx; up vector
  }

  private Vector2D springForceAtStart(FreeBody start, FreeBody end) {
    return springForceAtEnd(start, end).scaledBy(-1);
  }

  public void step(double deltaTime) {
    //    clear(); // trail

    for (FreeBody jointBody : springs) {
      System.out.println(jointBody.getY());
    }

    // 1. Calculate net force for each body

    springs[0].setNetForce(new Vector2D(0, 0));

    for (int i = 1; i < nSprings; ++i) {
      Vector2D netForce = new Vector2D(0, 0);

      FreeBody joint = springs[i];
      netForce.add(GRAVITY.scaledBy(joint.getMass()));

      FreeBody prev = springs[i - 1];
      netForce.add(springForceAtEnd(prev, joint));

      FreeBody next; // either next joint or attached body
      if (i + 1 < nSprings) {
        next = springs[i + 1];
      } else {
        next = weight;
      }
      netForce.add(springForceAtStart(joint, next));

      joint.setNetForce(netForce);
    }

    Vector2D netForce = new Vector2D(0, 0); // attached body net force
    netForce.add(GRAVITY.scaledBy(weight.getMass()));
    Vector2D upForce = springForceAtEnd(springs[nSprings - 1], weight);
    netForce.add(upForce);
    weight.setNetForce(netForce);

    // 2. Step everything

    for (FreeBody joint : springs) {
      joint.step(DELTA_TIME);
    }
    weight.step(DELTA_TIME);

    // 3. Draw everything

    //    for (FreeBody joint : springs) {
    //      addPoint(joint.getX(), joint.getY());
    //    }
    //    addPoint(weight.getX(), weight.getY());
  }

  @Override
  public void draw(DrawingPanel drawingPanel, Graphics graphics) {
    for (int i = 0; i < nSprings; ++i) {
      FreeBody cur = springs[i];
      FreeBody next = i + 1 < nSprings ? springs[i + 1] : weight;
      Vector2D slice = next.getPosition().minus(cur.getPosition());
      springDrawables[i].setXY(cur.getX(), cur.getY());
      springDrawables[i].setSizeXY(slice.getX(), slice.getY());
      springDrawables[i].draw(drawingPanel, graphics);
    }

    weightDrawable.setXY(weight.getX(), weight.getY());
    weightDrawable.draw(drawingPanel, graphics);
  }
}
