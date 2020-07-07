import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testTwoPlus() {
        assertEquals(19, Calculator.calculate("5+6+8"));
    }

    @Test
    void testPlusMultiplyPrecedence() {
        assertEquals(46, Calculator.calculate("10+4*9"));
    }

    @Test
    void testMinusDividePrecedence() {
        assertEquals(86, Calculator.calculate("88-10/5"));
    }

    @Test
    void testParen() {
        assertEquals(40, Calculator.calculate("10*(20/5)"));
    }

    @Test
    void testWhiteSpace() {
        assertEquals(1, Calculator.calculate("5 - 4 + 3 - 2 -   1 + 4  *0"));
    }
}