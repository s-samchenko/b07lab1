import java.io.*;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws IOException {
        Polynomial p = new Polynomial();  // Zero polynomial
        System.out.println(p.evaluate(3));  // Should print 0.0

        double[] c1 = {6, -2, 5};  // Coefficients for 6 - 2x + 5x^3
        int[] e1 = {0, 1, 3};      // Exponents for the same polynomial
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {3, 7};      // Coefficients for 3x^2 + 7x^8
        int[] e2 = {2, 8};         // Exponents for the same polynomial
        Polynomial p2 = new Polynomial(c2, e2);

        double[] c3 = {-6, 4, -4};
        int[] e3 = {0, 2, 3};
        Polynomial p3 = new Polynomial(c3, e3);

        //Polynomial sum = p1.add(p3);  // Adding two polynomials
        //System.out.println("sum(1) = " + sum.evaluate(1));  // Evaluate sum at x = 1

        Polynomial sum = p1.add(p2);  // Adding two polynomials
        System.out.println("sum(1) = " + sum.evaluate(1));  // Evaluate sum at x = 1

        Polynomial product = p1.multiply(p2);  // Multiplying two polynomials
        System.out.println("product(1) = " + product.evaluate(1));  // Evaluate product at x = 1

        Polynomial product2 = p1.multiply(p3);  // Multiplying two polynomials
        System.out.println("product(2) = " + product2.evaluate(2));  // Evaluate product at x = 2

        // Save polynomial to file
        product.saveToFile("product.txt");

        // Load polynomial from file
        File file = new File("product.txt");
        Polynomial pFromFile = new Polynomial(file);
        System.out.println("Loaded polynomial from file: " + pFromFile.evaluate(1));
    }
}