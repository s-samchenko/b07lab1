import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;

public class Polynomial {
    private double[] coefficients; // Array for non-zero coefficients
    private int[] exponents;       // Array for corresponding exponents

    // Constructor that takes coefficients and exponents
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // No-argument constructor initializing to zero polynomial
    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0}; // Zero polynomial
    }

    // Constructor that initializes polynomial from ArrayList
    public Polynomial(ArrayList<Double> coefficients, ArrayList<Integer> exponents){
        this.coefficients = new double[coefficients.size()];
        this.exponents = new int[exponents.size()];
        for (int i = 0; i < coefficients.size(); i++) {
            this.coefficients[i] = coefficients.get(i);
            this.exponents[i] = exponents.get(i);
        }
    }

    // Constructor that initializes polynomial from map
    public Polynomial(SortedMap<Integer, Double> map){
        this.coefficients = new double[map.size()];
        this.exponents = new int[map.size()];
        int i = 0;
        for (Integer key : map.keySet()) {
            exponents[i] = key;
            coefficients[i] = map.get(key);
            ++i;
        }
    }

    // Constructor that initializes polynomial from file
    public Polynomial(File file) throws IOException {
        Scanner sc = new Scanner(file);
        if (sc.hasNextLine()) {
            String line = sc.nextLine().replaceAll("\\s+", "");
            String[] terms = line.split("\\+");

            this.coefficients = new double[terms.length];
            this.exponents = new int[terms.length];

            for (int i = 0; i < terms.length; i++) {
                String[] parts = terms[i].split("x");
                coefficients[i] = Double.parseDouble(parts[0]);
                exponents[i] = Integer.parseInt(parts[1]);
            }
        }
        sc.close();
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        ArrayList<Double> newCoefficients = new ArrayList<Double> ();
        ArrayList<Integer> newExponents = new ArrayList<Integer> ();

        //add polynomials using two-pointers method
        int l = 0, r = 0;
        while (l < this.coefficients.length || r < other.exponents.length) {

            // update == 1 means we add exponents from this array
            // update == 2 means we add exponents from other array
            // update == 3 means we add exponents from both arrays
            int update = -1;
            int min_exp = Integer.MAX_VALUE;
            if (l < this.coefficients.length) {
                if (this.exponents[l] < min_exp) {
                    min_exp = this.exponents[l];
                    update = 1;
                }
            }
            if (r < other.exponents.length) {
                if (other.exponents[r] < min_exp) {
                    min_exp = other.exponents[r];
                    update = 2;
                }
                else if (min_exp == other.exponents[r]) {
                    update = 3;
                }
            }

            if (update == 1){
                newCoefficients.add(this.coefficients[l]);
                newExponents.add(this.exponents[l]);
                ++l;
            }
            else if (update == 2){
                newCoefficients.add(other.coefficients[r]);
                newExponents.add(other.exponents[r]);
                ++r;
            }
            else if (update == 3){
                if (other.coefficients[r] + this.coefficients[l] != 0) {
                    newCoefficients.add(other.coefficients[r] + this.coefficients[l]);
                    newExponents.add(this.exponents[l]);
                }
                ++r;
                ++l;
            }
        }

        Polynomial result = new Polynomial(newCoefficients, newExponents);
        return result;

        //System.out.println("Initial arrays");
        //System.out.println(Arrays.toString(this.coefficients));
        //System.out.println(Arrays.toString(this.exponents));
        //System.out.println(Arrays.toString(other.coefficients));
        //System.out.println(Arrays.toString(other.exponents));
        //System.out.println("Final arrays");
        //System.out.println("Array coeff:" + newCoefficients);
        //System.out.println("Array exp:" + newExponents);
    }


    // Method to multiply two polynomials
    public Polynomial multiply(Polynomial other) {

        //exponent is a key, coefficient is a value
        SortedMap<Integer, Double> map = new TreeMap<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int exponent = this.exponents[i] + other.exponents[j];
                if (map.get(exponent) == null){
                    map.put(exponent, this.coefficients[i] * other.coefficients[j]);
                }
                else{
                    double value = map.get(exponent);
                    map.remove(exponent);
                    if (this.coefficients[i] * other.coefficients[j] + value != 0) {
                        map.put(exponent, this.coefficients[i] * other.coefficients[j] + value);
                    }
                }
            }
        }

        Polynomial result = new Polynomial(map);
        return result;

        //System.out.println(Arrays.toString(this.coefficients));
        //System.out.println(Arrays.toString(this.exponents));
        //System.out.println();
        //System.out.println(Arrays.toString(other.coefficients));
        //System.out.println(Arrays.toString(other.exponents));
        //System.out.println();
        //System.out.println(Arrays.toString(result.coefficients));
        //System.out.println(Arrays.toString(result.exponents));
    }

    // Method to evaluate the polynomial at a given value of x
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Method to save polynomial to file
    public void saveToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < coefficients.length; i++) {
            writer.write(coefficients[i] + "x" + exponents[i]);
            if (i < coefficients.length - 1) {
                writer.write(" + ");
            }
        }
        writer.close();
    }
}