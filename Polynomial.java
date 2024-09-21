public class Polynomial {
    //coefficients of a polynomial
    private double[] coefficients;

    // implementing a no-argument constructor
    public Polynomial() {
        this.coefficients = new double[]{0}; // Initialize with zeroes
    }

    // Constructor that takes an array of doubles as coefficients
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;  // Initialize with given coefficients
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = (i < this.coefficients.length) ? this.coefficients[i] : 0;
            double b = (i < other.coefficients.length) ? other.coefficients[i] : 0;
            result[i] = a + b;
        }

        return new Polynomial(result);
    }

    // Method to evaluate the polynomial at a given value of x
    public double evaluate(double x) {
        double result = 0;
        double term = 1;  // Start with x^0

        for (double coeff : coefficients) {
            result += coeff * term;
            term *= x;  // Increment the power of x
        }

        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}