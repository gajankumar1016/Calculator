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
