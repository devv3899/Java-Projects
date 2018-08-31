package app;

import java.io.*;

import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";

	 /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
	
	public static void 
	makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		
		Stack<String> symbols = new Stack<String>();
		
		String characters;
		StringTokenizer DeliCharacter = new StringTokenizer(expr, delims, true);

		while (DeliCharacter.hasMoreTokens()) {

			characters = DeliCharacter.nextToken();

			if (Pattern.matches("[a-z A-Z]+", characters) 
					|| characters.equals("[")) {
				
				symbols.push(characters);
			}
		}
		while (symbols.isEmpty() == false) {
			
			characters = symbols.pop();
			
			if (characters.equals("[")) {
				
				characters = symbols.pop();
				Array A = new Array(characters);
				
				if(arrays.indexOf(A) == -1) {
					
					arrays.add(A);
				}
				
			} else {

				Variable S = new Variable(characters);
				
				if (vars.indexOf(S) == -1) {
					
					vars.add(S);
				}
			}
		}
	}

	/**
	 * Loads values for variables and arrays in the expression
	 * 
	 * @param sc Scanner for values input
	 * @throws IOException If there is a problem with the input 
	 * @param vars The variables array list, previously populated by makeVariableLists
	 * @param arrays The arrays array list - previously populated by makeVariableLists
	 */
	public static void 
	loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
			throws IOException {
		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String tok = st.nextToken();
			Variable var = new Variable(tok);
			Array arr = new Array(tok);
			int vari = vars.indexOf(var);
			int arri = arrays.indexOf(arr);
			if (vari == -1 && arri == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				vars.get(vari).value = num;
			} else { // array symbol
				arr = arrays.get(arri);
				arr.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok," (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					arr.values[index] = val;              
				}
			}
		}
	}
	
	  /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */

	public static float 
	evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		
		expr.replaceAll("\\s",""); // ignores all white spaces.
		
		float A = evaluator(expr, 0, expr.length()-1, vars, arrays); // recursive method to evaluate using other private methods.
		
		if (A == -0.0) {
			
			return 0;
		}
		return A;
	}


	/**
	 * Below are all methods helpful to solve and find answer for evaluation of expression.
	 */
	private static float evaluator(String expr, int begin, int finish, 
			ArrayList<Variable> vars, ArrayList<Array> arrays) {
		
		expr.replaceAll("\\s",""); // ignores all white spaces.
		
		Stack<Float> Floats = new Stack<Float>();
		Stack<Character> Characters = new Stack<Character>();
		
		int i;
		for(i = begin; i <= finish; i++) {

			if(Character.isLetter(expr.charAt(i))) {

				int A; 
				A = i+1;
				String B = expr.charAt(i)+""; // to convert output to string a null string is added.

				while(A <= finish && Character.isLetter(expr.charAt(A))) { 

					B = B + expr.charAt(A);
					A = A + 1;
				}
				if(containsArray(arrays,B)) {

					int[] temporary = findArrays(arrays, B).values;
					int Closing = ClosingIndex(expr, i+B.length());
					
					Floats.push((float)temporary[(int)evaluator(expr,i+B.length(),Closing-1, vars, arrays)]);
					i = Closing;
				}
				else if(containVariable(vars,B)) {

					Floats.push((float)findvariable(vars,B).value);
					i = A - 1; //index.
				}
			}
			else if(Float(expr.charAt(i))) {

				int A = i+1;
				String B = expr.charAt(i) + ""; // to convert output to string a null string is added.

				while(A <= finish && Float(expr.charAt(A))) {

					B = B + expr.charAt(A);
					A = A + 1;
				}
				Floats.push(Float.parseFloat(B));
				i = A-1; 

			}
			else if(expr.charAt(i) == '(') {

				int Closing = ClosingIndex(expr, i);
				Floats.push(evaluator(expr, i+1, Closing-1,vars, arrays));
				i = Closing;
			}
			if(!Characters.isEmpty() && (Characters.peek() == '/' || Characters.peek() == '*')) {

				char Cases = Characters.pop();
				float B = Floats.pop();
				float A = Floats.pop();

				switch(Cases) {
				
				case '/' : Floats.push(A/B); {
					
					break;
				}
				case '*' : Floats.push(A*B); {
					
					break;
				}
				
				}
			}
			else if(expr.charAt(i) == '*' || expr.charAt(i) == '/') {
				
				Characters.push(expr.charAt(i));
			}
			else if(expr.charAt(i) == '+' || expr.charAt(i) == '-') {
				
				Characters.push(expr.charAt(i));
			}

		}

		Stack<Float> Floats1 = new Stack<Float>();
		Stack<Character> Characters1 = new Stack<Character>();

		while(!Floats.isEmpty()) {

			Floats1.push(Floats.pop());
		}
		while(!Characters.isEmpty()) {

			Characters1.push(Characters.pop());
		}
		while(Floats1.size() > 1) {

			float B = Floats1.pop();
			float A = Floats1.pop();
			char Cases = Characters1.pop();

			switch(Cases){
			
			case '-' : Floats1.push(B-A); {

				break;
			}
			case '+' : Floats1.push(B+A); {

				break;
			}
			
			}
		}
		
		Float C = Floats1.pop();
		return C;
	}

	/**
	 * Using ASCII value of digits to check for digits.
	 */
	private static boolean Float(char Value) {

		boolean B;

		if ((Value >= 48 && Value <= 57) || Value == 46) { //46 for a period to count decimal values.
			B = true;

		} else {

			B = false;
		}
		return B;
	}

	/**
	 * finding the index of the last parentheses or square bracket.
	 */
	private static int ClosingIndex(String expr, int open) {

		Stack<Character> opening = new Stack<Character>();
		opening.push(expr.charAt(open));

		int temp = open + 1;
		int B = 0;

		while(!opening.isEmpty()) {

			if(expr.charAt(temp) == ']' || expr.charAt(temp) == ')') {

				opening.pop();
			}

			else if(expr.charAt(temp) == '[' || expr.charAt(temp) == '(') {

				opening.push(expr.charAt(temp));
			}

			temp = temp + 1;
		}

		B = temp-1;
		return B;
	}

	/**
	 * Finds the Variable with that name in given string from array list.
	 */
	private static Variable findvariable(ArrayList<Variable> arraylist, String expr) {

		Variable A = null;

		for(Variable temp : arraylist){

			if(temp.name.equals(expr)) {

				A = temp;
			}
		}
		return A;
	}

	/**
	 * Find the Array with that name in given string from array list.
	 */
	private static Array findArrays(ArrayList<Array> arraylist, String expr) {

		Array A = null;

		for(Array temp : arraylist){

			if(temp.name.equals(expr)) {

				A = temp;
			}
		}
		return A;
	}

	/**
	 * Checks if the name of the Array is already in the ArrayList
	 */
	private static boolean containsArray(ArrayList<Array> arraylist, String expr) {

		for(Array A : arraylist) {

			if(A.name.equals(expr)) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the name of the Variable is already in the ArrayList
	 */
	private static boolean containVariable(ArrayList<Variable> arraylist, String expr) {
		
		for(Variable A : arraylist) {

			if(A.name.equals(expr)) {

				return true;
			}
		}
		return false;
	}
}
