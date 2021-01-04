import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class GettingStartedTest {

    @Test
    public void add() {
        assertThat(GettingStarted.add(2, 3), is(5));
    }

    @Test
    public void addNegative() {
        assertThat(GettingStarted.add(-4, -5), is(-9));
    }

    @Test
    public void addDifferentMagnitudes() {
        assertThat(GettingStarted.add(400000, 1), is(400001));
    }
}