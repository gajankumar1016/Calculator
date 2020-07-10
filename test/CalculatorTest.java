import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void testSingleInteger() {
        assertEquals(4, Calculator.calculate("4"));
    }

    @Test
    void testSingleNegativeInteger() {
        assertEquals(-1004, Calculator.calculate("-1004"));
    }

    @Test
    void testStartNegativeOutsideParens() {
        assertEquals(-7, Calculator.calculate("-(3+4)"));
    }

    @Test
    void testStartNegativeInsideParens() {
        assertEquals(-7, Calculator.calculate("(-3+4)"));
    }

    @Test
    void testTwoAdditions() {
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
        //assertEquals(1, Calculator.calculate("5 - 4 + 3 - 2 -   1 + 4  *0"));
        assertEquals(1, Calculator.calculate(" 5 - 4"));
    }

    @Test
    void testPlusMinus() {
        assertEquals(-2, Calculator.calculate("5-4+3"));
    }

    @Test
    void testDivideMultiply() {
        assertEquals(10, Calculator.calculate("2*10/2"));
    }

    @Test
    void testDivideProduceInt() {
        assertEquals(2, Calculator.calculate("10/5"));
    }

    @Test
    void testDivideProduceFloat() {
        assertEquals(1.6f, Calculator.calculate("8/5"));
    }

    @Test
    void testNestingMedium() {
        assertEquals(81, Calculator.calculate("(8*(2+4*(7-5))) + 1"));
    }

    @Test
    void testFloat() {
        assertEquals(1.0f, (Float) Calculator.calculate("5.3-4.3"));
    }

    @Test
    void testReadmeExample() {
        assertEquals(583, Calculator.calculate("4 + ((5 + 9) - 3) - (9 * 2) + 4*2 - 10/5 + 592"));
    }

    //******************
    // ERROR TESTS
    //******************

    @Test
    void testEmptyString() {
        Assertions.assertThrows(InvalidCalculatorExpressionException.class, () -> {
            Calculator.calculate("");
        });
    }

    @Test
    void testDanglingRightPlus() {
        Assertions.assertThrows(InvalidCalculatorExpressionException.class, () -> {
            Calculator.calculate("8/4+");
        });
    }

    @Test
    void testDanglingLeftTimes() {
        Assertions.assertThrows(InvalidCalculatorExpressionException.class, () -> {
            Calculator.calculate("*8/4");
        });
    }

    @Test
    void testInvalidCharacters() {
        Assertions.assertThrows(InvalidCalculatorCharacterException.class, () -> {
            Calculator.calculate("67+a");
        });
    }

}