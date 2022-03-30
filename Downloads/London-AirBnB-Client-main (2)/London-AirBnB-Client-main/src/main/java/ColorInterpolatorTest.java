import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorInterpolatorTest {

    ColorInterpolator test = new ColorInterpolator(0, 1000);
    @Test
    void snapValueOver() {
        assertEquals(254, test.snapValue(1000000));
    }

    @Test
    void snapValueUnder() {
        assertEquals(1, test.snapValue(-1000000));
    }

    @Test
    void snapValueJustOver() {
        assertEquals(254, test.snapValue(256));
    }

    @Test
    void snapValueBoundary() {
        assertEquals(255, test.snapValue(255));
    }

    @Test
    void snapValueJustUnder() {
        assertEquals(1, test.snapValue(-1));
    }

    @Test
    void snapValueUnderBoundary() {
        assertEquals(0, test.snapValue(0));
    }

    @Test
    void snapValueValidPass(){
        assertEquals(137, test.snapValue(137));
    }

}