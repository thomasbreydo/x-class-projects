import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QuadraticTest {
  @Test
  public void getA() {
    Quadratic q = new Quadratic(1, 2, 3);

    assertThat(q.getA(), is(1.0));
  }

  @Test
  public void getB() {
    Quadratic q = new Quadratic(1, 2, 3);

    assertThat(q.getB(), is(2.0));
  }

  @Test
  public void getC() {
    Quadratic q = new Quadratic(1, 2, 3);

    assertThat(q.getC(), is(3.0));
  }

  @Test
  public void hasRealRoots() {
    Quadratic q1 = new Quadratic(1, 2, 3);
    Quadratic q2 = new Quadratic(1, 0, -4);
    assertThat(q1.hasRealRoots(), is(false));
    assertThat(q2.hasRealRoots(), is(true));
  }

  @Test
  public void numberOfRoots() {
    Quadratic q0 = new Quadratic(4, 0, 1);
    Quadratic q1 = new Quadratic(4, 0, 0);
    Quadratic q2 = new Quadratic(4, 0, -1);
    assertThat(q0.numberOfRoots(), is(0));
    assertThat(q1.numberOfRoots(), is(1));
    assertThat(q2.numberOfRoots(), is(2));
  }
}
