package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;

	/**
	 * Degree of term.
	 */
	public int degree;

	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
				other instanceof Term &&
				coeff == ((Term)other).coeff &&
				degree == ((Term)other).degree;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {

	/**
	 * Term instance. 
	 */
	Term term;

	/**
	 * Next node in linked list. 
	 */
	Node next;

	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;

	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;

		poly = null;

		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}


	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */

	public Polynomial add(Polynomial p) {

		Node pol = this.poly;
		Node pol1 = p.poly;

		if (pol == null && pol1 != null) {

			return p;

		} else if (pol1 == null && pol != null) {

			return this;
		}

		Node a = null;
		Node b = null;
		Node temporary = null;
		Node temporary1 = null;

		while (pol != null) {

			while(pol1 != null) {

				if (pol1.term.degree < pol.term.degree) {

					temporary = new Node (pol1.term.coeff, pol1.term.degree, a);
					a = temporary;
					pol1 = pol1.next;

				} else if (pol1.term.degree > pol.term.degree) {

					temporary = new Node (pol.term.coeff, pol.term.degree, a);
					a = temporary;
					pol = pol.next;

				} else {

					if(pol.term.coeff + pol1.term.coeff == 0) {

						pol = pol.next;
						pol1 = pol1.next;

					} else {

						temporary = new Node (pol.term.coeff + pol1.term.coeff, pol.term.degree, a);
						a = temporary;
						pol = pol.next;
						pol1 = pol1.next;
					}
				}

				if (pol1 != null && pol == null) {

						temporary = new Node (pol1.term.coeff, pol1.term.degree, a);
						a = temporary;
						pol1 = pol1.next;
						
					} else if (pol != null && pol1 == null) {

						temporary = new Node (pol.term.coeff, pol.term.degree, a);
						a = temporary;
						pol = pol.next;
					}
				}
			}
		

		for(Node i = temporary; i !=null; i = i.next) {

			temporary1 = new Node (i.term.coeff, i.term.degree, b);
			b = temporary1;
		}

		Polynomial polynomial_p = new Polynomial();
		polynomial_p.poly = temporary1;
		return polynomial_p;

	}

	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {

		Polynomial polynomial_p = new Polynomial();
		Node pol;

		for (Node poly1 = this.poly; poly != null; poly = poly.next) {

			Node LastTerm = null;
			Node firstTerm = null;

			Polynomial temporary = new Polynomial();

			for (Node poly2 = p.poly; poly2 != null; poly2 = poly2.next) {

				int a = (poly1.term.degree + poly2.term.degree);
				float b = (poly1.term.coeff * poly2.term.coeff);


				pol = new Node(b, a, null);

				if (firstTerm != null){

					LastTerm.next = pol;
					LastTerm = LastTerm.next;

				} else {

					firstTerm = pol;
					LastTerm = firstTerm;

				}
			}

			temporary.poly = firstTerm;
			polynomial_p = polynomial_p.add(temporary);

		}

		return polynomial_p;
	}

	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {

		float answer = 0;
		Node temporary = this.poly;

		do {

			answer =  (float) (answer + (temporary.term.coeff*(Math.pow(x,temporary.term.degree))));
			temporary = temporary.next;

		} 

		while (temporary != null);	

		return answer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;

		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
					current != null ;
					current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
