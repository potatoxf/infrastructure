package potatoxf.infrastructure.global;

import java.io.BufferedInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p/>
 * Create Time:2024-03-23
 *
 * @author potatoxf
 */
public class Std {
    // send output here
    private static final PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);

    /**
     * Terminates the current line by printing the line-separator string.
     */
    public static void println() {
        out.println();
        out.flush();
    }

    /**
     * Prints an object to this output stream and then terminates the line.
     *
     * @param x the object to print
     */
    public static void println(Object x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a boolean to standard output and then terminates the line.
     *
     * @param x the boolean to print
     */
    public static void println(boolean x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a character to standard output and then terminates the line.
     *
     * @param x the character to print
     */
    public static void println(char x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a double to standard output and then terminates the line.
     *
     * @param x the double to print
     */
    public static void println(double x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints an integer to standard output and then terminates the line.
     *
     * @param x the integer to print
     */
    public static void println(float x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints an integer to standard output and then terminates the line.
     *
     * @param x the integer to print
     */
    public static void println(int x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a long to standard output and then terminates the line.
     *
     * @param x the long to print
     */
    public static void println(long x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a short integer to standard output and then terminates the line.
     *
     * @param x the short to print
     */
    public static void println(short x) {
        out.println(x);
        out.flush();
    }

    /**
     * Prints a byte to standard output and then terminates the line.
     * <p>
     * To write binary data, see {@link BinaryStdOut}.
     *
     * @param x the byte to print
     */
    public static void println(byte x) {
        out.println(x);
        out.flush();
    }

    /**
     * Flushes standard output.
     */
    public static void print() {
        out.flush();
    }

    /**
     * Prints an object to standard output and flushes standard output.
     *
     * @param x the object to print
     */
    public static void print(Object x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a boolean to standard output and flushes standard output.
     *
     * @param x the boolean to print
     */
    public static void print(boolean x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a character to standard output and flushes standard output.
     *
     * @param x the character to print
     */
    public static void print(char x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a double to standard output and flushes standard output.
     *
     * @param x the double to print
     */
    public static void print(double x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a float to standard output and flushes standard output.
     *
     * @param x the float to print
     */
    public static void print(float x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints an integer to standard output and flushes standard output.
     *
     * @param x the integer to print
     */
    public static void print(int x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a long integer to standard output and flushes standard output.
     *
     * @param x the long integer to print
     */
    public static void print(long x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a short integer to standard output and flushes standard output.
     *
     * @param x the short integer to print
     */
    public static void print(short x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a byte to standard output and flushes standard output.
     *
     * @param x the byte to print
     */
    public static void print(byte x) {
        out.print(x);
        out.flush();
    }

    /**
     * Prints a formatted string to standard output, using the specified format
     * string and arguments, and then flushes standard output.
     *
     * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
     * @param args   the arguments accompanying the format string
     */
    public static void printf(String format, Object... args) {
        out.printf(Locale.getDefault(), format, args);
        out.flush();
    }

    /**
     * Prints a formatted string to standard output, using the locale and
     * the specified format string and arguments; then flushes standard output.
     *
     * @param locale the locale
     * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
     * @param args   the arguments accompanying the format string
     */
    public static void printf(Locale locale, String format, Object... args) {
        out.printf(locale, format, args);
        out.flush();
    }

    // the default token separator; we maintain the invariant that this value
    // is held by the scanner's delimiter between calls
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

    // makes whitespace significant
    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    /*** end: section (1 of 2) of code duplicated from In to Std. */
    private static final Scanner scanner = new Scanner(new BufferedInputStream(System.in), StandardCharsets.UTF_8.name());

    //// begin: section (2 of 2) of code duplicated from In to Std,
    //// with all methods changed from "public" to "public static"

    /**
     * Returns true if standard input is empty (except possibly for whitespace).
     * Use this method to know whether the next call to {@link #readString()},
     * {@link #readDouble()}, etc will succeed.
     *
     * @return {@code true} if standard input is empty (except possibly
     * for whitespace); {@code false} otherwise
     */
    public static boolean isEmpty() {
        return !scanner.hasNext();
    }

    /**
     * Returns true if standard input has a next line.
     * Use this method to know whether the
     * next call to {@link #readLine()} will succeed.
     * This method is functionally equivalent to {@link #hasNextChar()}.
     *
     * @return {@code true} if standard input has more input (including whitespace);
     * {@code false} otherwise
     */
    public static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Returns true if standard input has more input (including whitespace).
     * Use this method to know whether the next call to {@link #readChar()} will succeed.
     * This method is functionally equivalent to {@link #hasNextLine()}.
     *
     * @return {@code true} if standard input has more input (including whitespace);
     * {@code false} otherwise
     */
    public static boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    /**
     * Reads and returns the next line, excluding the line separator if present.
     *
     * @return the next line, excluding the line separator if present;
     * {@code null} if no such line
     */
    public static String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    /**
     * Reads and returns the next character.
     *
     * @return the next {@code char}
     * @throws NoSuchElementException if standard input is empty
     */
    public static char readChar() {
        try {
            scanner.useDelimiter(EMPTY_PATTERN);
            String ch = scanner.next();
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'char' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token  and returns the {@code String}.
     *
     * @return the next {@code String}
     * @throws NoSuchElementException if standard input is empty
     */
    public static String readString() {
        try {
            return scanner.next();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'String' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as a boolean,
     * and returns the boolean.
     *
     * @return the next boolean on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code boolean}:
     *                                {@code true} or {@code 1} for true, and {@code false} or {@code 0} for false,
     *                                ignoring case
     */
    public static boolean readBoolean() {
        try {
            return scanner.nextBoolean();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            if ("true".equalsIgnoreCase(token)) return true;
            if ("false".equalsIgnoreCase(token)) return false;
            if ("1".equals(token)) return true;
            if ("0".equals(token)) return false;
            throw new InputMismatchException("attempts to read a 'boolean' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as a byte, and returns the byte.
     *
     * @return the next byte on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code byte}
     */
    public static byte readByte() {
        try {
            return scanner.nextByte();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'byte' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as a short integer, and returns the short integer.
     *
     * @return the next short integer on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code short}
     */
    public static short readShort() {
        try {
            return scanner.nextShort();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'short' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as an integer, and returns the integer.
     *
     * @return the next integer on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as an {@code int}
     */
    public static int readInt() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read an 'int' value from standard input, but there are no more tokens available");
        }

    }

    /**
     * Reads the next token from standard input, parses it as a long integer, and returns the long integer.
     *
     * @return the next long integer on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code long}
     */
    public static long readLong() {
        try {
            return scanner.nextLong();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'long' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as a float, and returns the float.
     *
     * @return the next float on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code float}
     */
    public static float readFloat() {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'float' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads the next token from standard input, parses it as a double, and returns the double.
     *
     * @return the next double on standard input
     * @throws NoSuchElementException if standard input is empty
     * @throws InputMismatchException if the next token cannot be parsed as a {@code double}
     */
    public static double readDouble() {
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from standard input, but the next token is \"" + token + "\"");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'double' value from standard input, but there are no more tokens available");
        }
    }

    /**
     * Reads all remaining tokens from standard input and returns them as an array of strings.
     *
     * @return all remaining tokens on standard input, as an array of strings
     */
    public static String[] readAllStrings() {
        // we could use readAll.trim().split(), but that's not consistent
        // because trim() uses characters 0x00..0x20 as whitespace
        String[] tokens = WHITESPACE_PATTERN.split(readLine());
        if (tokens.length == 0 || !tokens[0].isEmpty())
            return tokens;

        // don't include first token if it is leading whitespace
        String[] decapitokens = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, decapitokens, 0, tokens.length - 1);
        return decapitokens;
    }

    /**
     * Reads all remaining lines from standard input and returns them as an array of strings.
     *
     * @return all remaining lines on standard input, as an array of strings
     */
    public static String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[0]);
    }

    /**
     * Reads all remaining tokens from standard input, parses them as integers, and returns
     * them as an array of integers.
     *
     * @return all remaining integers on standard input, as an array
     * @throws InputMismatchException if any token cannot be parsed as an {@code int}
     */
    public static int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    /**
     * Reads all remaining tokens from standard input, parses them as longs, and returns
     * them as an array of longs.
     *
     * @return all remaining longs on standard input, as an array
     * @throws InputMismatchException if any token cannot be parsed as a {@code long}
     */
    public static long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Long.parseLong(fields[i]);
        return vals;
    }

    //// end: section (2 of 2) of code duplicated from In to Std

    /**
     * Reads all remaining tokens from standard input, parses them as doubles, and returns
     * them as an array of doubles.
     *
     * @return all remaining doubles on standard input, as an array
     * @throws InputMismatchException if any token cannot be parsed as a {@code double}
     */
    public static double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    /******************************************************************************
     *  Compilation:  javac StdArrayIO.java
     *  Execution:    java StdArrayIO < input.txt
     *  Dependencies: StdOut.java
     *  Data files:    https://introcs.cs.princeton.edu/java/22library/tinyDouble1D.txt
     *                 https://introcs.cs.princeton.edu/java/22library/tinyDouble2D.txt
     *                 https://introcs.cs.princeton.edu/java/22library/tinyBoolean2D.txt
     *
     *  A library for reading in 1D and 2D arrays of integers, doubles,
     *  and booleans from standard input and printing them out to
     *  standard output.
     *
     *  % more tinyDouble1D.txt
     *  4
     *    .000  .246  .222  -.032
     *
     *  % more tinyDouble2D.txt
     *  4 3
     *    .000  .270  .000
     *    .246  .224 -.036
     *    .222  .176  .0893
     *   -.032  .739  .270
     *
     *  % more tinyBoolean2D.txt
     *  4 3
     *    1 1 0
     *    0 0 0
     *    0 1 1
     *    1 1 1
     *
     *  % cat tinyDouble1D.txt tinyDouble2D.txt tinyBoolean2D.txt | java StdArrayIO
     *  4
     *    0.00000   0.24600   0.22200  -0.03200
     *
     *  4 3
     *    0.00000   0.27000   0.00000
     *    0.24600   0.22400  -0.03600
     *    0.22200   0.17600   0.08930
     *    0.03200   0.73900   0.27000
     *
     *  4 3
     *  1 1 0
     *  0 0 0
     *  0 1 1
     *  1 1 1
     *
     ******************************************************************************/

    /**
     * <i>Standard array IO</i>. This class provides methods for reading
     * in 1D and 2D arrays from standard input and printing out to
     * standard output.
     * <p>
     * For additional documentation, see
     * <a href="https://introcs.cs.princeton.edu/22libary">Section 2.2</a> of
     * <i>Computer Science: An Interdisciplinary Approach</i>
     * by Robert Sedgewick and Kevin Wayne.
     *
     * @author Robert Sedgewick
     * @author Kevin Wayne
     */

    /**
     * Reads a 1D array of doubles from standard input and returns it.
     *
     * @return the 1D array of doubles
     */
    public static double[] readDouble1D() {
        int n = Std.readInt();
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = Std.readDouble();
        }
        return a;
    }

    /**
     * Prints an array of doubles to standard output.
     *
     * @param a the 1D array of doubles
     */
    public static void print(double[] a) {
        int n = a.length;
        Std.println(n);
        for (double v : a) {
            Std.printf("%9.5f ", v);
        }
        Std.println();
    }


    /**
     * Reads a 2D array of doubles from standard input and returns it.
     *
     * @return the 2D array of doubles
     */
    public static double[][] readDouble2D() {
        int m = Std.readInt();
        int n = Std.readInt();
        double[][] a = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Std.readDouble();
            }
        }
        return a;
    }

    /**
     * Prints the 2D array of doubles to standard output.
     *
     * @param a the 2D array of doubles
     */
    public static void print(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        Std.println(m + " " + n);
        for (double[] vs : a) {
            for (double v : vs) {
                Std.printf("%9.5f ", v);
            }
            Std.println();
        }
    }


    /**
     * Reads a 1D array of integers from standard input and returns it.
     *
     * @return the 1D array of integers
     */
    public static int[] readInt1D() {
        int n = Std.readInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Std.readInt();
        }
        return a;
    }

    /**
     * Prints an array of integers to standard output.
     *
     * @param a the 1D array of integers
     */
    public static void print(int[] a) {
        int n = a.length;
        Std.println(n);
        for (int i = 0; i < n; i++) {
            Std.printf("%9d ", a[i]);
        }
        Std.println();
    }


    /**
     * Reads a 2D array of integers from standard input and returns it.
     *
     * @return the 2D array of integers
     */
    public static int[][] readInt2D() {
        int m = Std.readInt();
        int n = Std.readInt();
        int[][] a = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Std.readInt();
            }
        }
        return a;
    }

    /**
     * Print a 2D array of integers to standard output.
     *
     * @param a the 2D array of integers
     */
    public static void print(int[][] a) {
        int m = a.length;
        int n = a[0].length;
        Std.println(m + " " + n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Std.printf("%9d ", a[i][j]);
            }
            Std.println();
        }
    }


    /**
     * Reads a 1D array of booleans from standard input and returns it.
     *
     * @return the 1D array of booleans
     */
    public static boolean[] readBoolean1D() {
        int n = Std.readInt();
        boolean[] a = new boolean[n];
        for (int i = 0; i < n; i++) {
            a[i] = Std.readBoolean();
        }
        return a;
    }

    /**
     * Prints a 1D array of booleans to standard output.
     *
     * @param a the 1D array of booleans
     */
    public static void print(boolean[] a) {
        int n = a.length;
        Std.println(n);
        for (int i = 0; i < n; i++) {
            if (a[i]) Std.print("1 ");
            else Std.print("0 ");
        }
        Std.println();
    }

    /**
     * Reads a 2D array of booleans from standard input and returns it.
     *
     * @return the 2D array of booleans
     */
    public static boolean[][] readBoolean2D() {
        int m = Std.readInt();
        int n = Std.readInt();
        boolean[][] a = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Std.readBoolean();
            }
        }
        return a;
    }

    /**
     * Prints a 2D array of booleans to standard output.
     *
     * @param a the 2D array of booleans
     */
    public static void print(boolean[][] a) {
        int m = a.length;
        int n = a[0].length;
        Std.println(m + " " + n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j]) Std.print("1 ");
                else Std.print("0 ");
            }
            Std.println();
        }
    }


    /**
     * Unit tests {@code StdArrayIO}.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // read and print an array of doubles
        double[] a = Std.readDouble1D();
        Std.print(a);
        Std.println();

        // read and print a matrix of doubles
        double[][] b = Std.readDouble2D();
        Std.print(b);
        Std.println();

        // read and print a matrix of doubles
        boolean[][] d = Std.readBoolean2D();
        Std.print(d);
        Std.println();
    }


}
