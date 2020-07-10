/**
 * Custom number class for the calculator. Either contains an Integer or a Float. Implements arithmetic operations
 * taking into account the type of number. For example, 10/2 should produce an Integer, whereas 10/3 would produce a
 * Float.
 */
public class CalcNumber {
    private final Number n;

    public CalcNumber(Number n) {
        if (!(n instanceof Integer || n instanceof Float)) {
            throw new IllegalArgumentException("Argument to CalcNumber must either be Integer or Float");
        }
        this.n = n;
    }

    public CalcNumber add(CalcNumber other) {
        if (this.n instanceof Float || other.n instanceof Float) {
            return new CalcNumber(this.n.floatValue() + other.n.floatValue());
        }
        return new CalcNumber(this.n.intValue() + other.n.intValue());
    }

    public CalcNumber subtract(CalcNumber other) {
        if (this.n instanceof Float || other.n instanceof Float) {
            return new CalcNumber(this.n.floatValue() - other.n.floatValue());
        }
        return new CalcNumber(this.n.intValue() - other.n.intValue());
    }

    public CalcNumber multiply(CalcNumber other) {
        if (this.n instanceof Float || other.n instanceof Float) {
            return new CalcNumber(this.n.floatValue() * other.n.floatValue());
        }
        return new CalcNumber(this.n.intValue() * other.n.intValue());
    }

    public CalcNumber divide(CalcNumber other) {
        if (this.n instanceof Float || other.n instanceof Float) {
            return new CalcNumber(this.n.floatValue() / other.n.floatValue());
        }

        if (this.n.intValue() % other.n.intValue() == 0) {
            return new CalcNumber(this.n.intValue() / other.n.intValue());
        }

        return new CalcNumber(this.n.floatValue() / other.n.floatValue());
    }

    public Number getN() {
        return n;
    }
}
