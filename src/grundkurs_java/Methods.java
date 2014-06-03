package grundkurs_java;

public class Methods {

	public static void main(String[] args) {
		// System.out.println(fxn(4.0, 8.0, 4));
		// System.out.println(max(2, 4.0));
		// variableArgs(1, 2, 3, 4, 5);
		// visibility();
		// System.out.println(tangens(9.48));
		int[] a = { 1, 2, 3, 4, 5, 6 };
		// reverseArray(a);
		// reverseArrayInPlace(a);
		// System.out.println(factor(5));
		// System.out.println(recurseFactor(5));
		// System.out.println(pow(5.4, 2));
		// System.out.println(recurseFib(15));
		// System.out.println(fib(16));
		// System.out.println(palindrome("racecaR"));
		// System.out.println(recursePalindrome("Racecar"));
		System.out.println(sum(a));
		double[] dd = { -3.0, -2.0, 0.0, 1.5, 3.0 };
		double[] dr = enter(-3.0, 1, dd);
		for (double d : dr)
			System.out.println(d);
	}

	public static int position(double d, double[] dField) {
		int index = 0;
		for (int i = 0; i < dField.length; i++) {
			if (i == d)
				return i;
		}
		if (d < 0)
			return dField.length;
		return index;
	}

	public static double[] enter(double d, int c, double[] dField) {
		// 6.11 s = s + z[i++];
		if (c < 0) {
			for (int i = dField.length - 1; i >= 0; i--) {
				dField[i + 1] = dField[i];
			}
			dField[0] = d;
		} else if (c >= dField.length) {
			dField[dField.length] = d;
		}

		return dField;
	}

	public static int sum(int[] z) {
		// berechnet die Summe der Komponenten des Feldes z
		int s = 0, i = 0;
		while (i < z.length)
			s = s + z[i++];
		// s = s + z[++i]; = arrayindexoutofbounds
		return s;
	}

	public static void stringStuff() {
		String s1 = "Christmas";
		String s2 = "ChristmAs";
		System.out.println(s1.compareTo(s2));
		System.out.println(s1.equalsIgnoreCase(s2));
		System.out.println(s1.replace('s', 'S'));
		System.out.println(s1.substring(0, 4));
		System.out.println(String.valueOf(1.5e3));
	}

	public static boolean recursePalindrome(String string) {
		string = string.toLowerCase();
		if (string.length() < 2)
			return true;
		if (string.charAt(0) == string.charAt(string.length() - 1))
			return recursePalindrome(string.substring(1, string.length() - 1));
		else
			return false;
	}

	private static boolean palindrome(String string) {
		string = string.toLowerCase();
		for (int i = 0; i < string.length() / 2; i++) {
			if (string.charAt(i) != string.charAt(string.length() - 1 - i))
				return false;
		}
		return true;
	}

	private static int fib(int n) {
		if (n < 2)
			return 1;
		int a = 0, b = 1, temp;
		for (int i = 0; i < n; i++) {
			temp = a;
			a = b;
			b = a + temp;
		}
		return a;
	}

	static int recurseFib(int n) {
		if (n < 2)
			return 1;
		return recurseFib(n - 1) + recurseFib(n - 2);
	}

	static double pow(double x, int k) {
		if (k == 0) {
			return 1;
		} else if (k > 0) {
			return x * pow(x, k - 1);
		} else
			return 1 / pow(x, -k);
	}

	private static int factor(int n) {
		if (n == 0)
			return 1;
		for (int i = n - 1; i > 0; i--)
			n *= i;
		return n;
	}

	private static int recurseFactor(int n) {
		if (n == 0)
			return 1;
		return n * recurseFactor(n - 1);
	}

	private static void reverseArrayInPlace(int[] a) {
		int temp;
		for (int i = 0; i < a.length / 2; i++) {
			temp = a[i];
			a[i] = a[a.length - 1 - i];
			a[a.length - 1 - i] = temp;
		}
		for (int i : a)
			System.out.println(i);
	}

	private static void reverseArray(int[] a) {
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = a[a.length - 1 - i];
		}
		for (int i : b)
			System.out.println(i);

	}

	private static double tangens(double d) {
		return Math.sin(d) / Math.cos(d);
	}

	// class variables
	static int a = 1, b = 2, c = 3;

	static int m(int a) {
		// local variable
		int b = 20;
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("c = " + c);
		return 100;
	}

	public static void visibility() {
		// formal variable
		int a = 1000;
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("m(c)= " + m(c));
	}

	public static void variableArgs(int... args) {
		int sum = 0;
		System.out.println(args.getClass().getName());
		for (int arg : args) {
			sum += arg;
		}
		System.out.println(sum);
	}

	public static int max(int x, int y) {
		return (x > y) ? x : y;
	}

	public static double max(double x, double y) {
		// overloading max
		System.out.println("double");
		return (x > y) ? x : y;
	}

	public static double f(double x, int n) {
		double result, product = 1.0;
		for (int i = 0; i < 2 * n; i++) {
			product *= x;
		}
		result = product + n * n - n * x;
		return result;
	}

	private static double fxn(double l, double r, int n) {
		double c, left, right, center, mean;
		left = f(l, n);
		right = f(r, n);
		c = (left + right) / 2.0;
		center = f(c, n);
		mean = (left + right + center) / 3.0;
		return mean;
	}

}
