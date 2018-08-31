package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {

	/**
	 * Root node
	 */
	TagNode root=null;

	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;

	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {

		root = Builder(); // use this if you remove your arguments

	}

	private TagNode Builder() {

		boolean LinesDOM = sc.hasNextLine();
		String str = null;

		// Give a check through all the lines.
		if (LinesDOM == true) {

			str = sc.nextLine();

		} else {

			return null; 
		}

		int length = str.length();
		int A = length;
		boolean B = false;

		// Give a check to open and close.
		if (str.charAt(0) == '<') {  // < Denotes starting

			int end = A - 1;
			String C = str.substring(1,end);
			str = C;

			if (str.charAt(0) == '/') {	// / denotes closing.

				return null;

			} else {

				B = true; 
			}
		}

		// A Node for tree.
		TagNode Node = new TagNode (str, null, null);

		if(B == true) {

			Node.firstChild = Builder();
		}

		Node.sibling = Builder();		

		return Node;
	}

	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */

	public void replaceTag(String oldTag, String newTag) {

		if (oldTag == null || root == null || newTag == null) {

			return;

		} else {

			this.Replacer(root.firstChild, oldTag, newTag);
		}
	}

	private void Replacer(TagNode Node, String OLD, String NEW) {

		if (Node == null) {

			return;
		}

		else if (Node.tag.compareTo(OLD) == 0) {

			Node.tag = NEW;
		}

		// INORDER TRAVERSAL
		this.Replacer(Node.firstChild, OLD, NEW);
		this.Replacer(Node.sibling, OLD, NEW);
	}

	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) { 

		TagNode Present = new TagNode(null, null, null);										// Create a TagNode to allow traversal
		TagNode A;

		//Helper Method
		Present = Bolder(root);

		if (Present == null) {

			System.out.println("No such Table exists");
			return;
		}

		//Move through rows
		Present = Present.firstChild;

		// iterating rows
		int i;
		for(i = 1; i < row; i= i + 1 ) {

			Present = Present.sibling;
		} 

		// iterating columns 
		for (A = Present.firstChild; A != null; A = A.sibling) {

			A.firstChild = new TagNode("b", A.firstChild, null);
		}
	} 

	private TagNode Bolder(TagNode Present1) { 

		// Basic
		if (Present1 == null) {

			return null; 
		}

		TagNode Node = null;
		String A = Present1.tag;

		if(A.equals("table")) { 

			Node = Present1; 
			return Node;
		} 

		// Traversing
		if(Node == null) { 

			Node = Bolder(Present1.sibling);
		} 

		if(Node == null) {

			Node = Bolder(Present1.firstChild);
		}

		return Node;
	}

	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {

		Remover(null, tag, root);
	}

	private void Remover(TagNode Before, String Tagg, TagNode Present) {

		// Basic
		if (Present == null) {

			return;
		}

		String ABC = Present.tag;

		//  p, em and b tags
		if(ABC.equals("b") || ABC.equals("p") || 
				ABC.equals("em")) {

			if (ABC.equals(Tagg)) {

				if (Before.firstChild != null && 
						Before.firstChild.tag.equals(ABC)) {	

					// If Sibling
					if(Present.sibling != null) {

						if (Present.firstChild.sibling != null) {	

							TagNode A = Present.firstChild;
							TagNode B = A.sibling;

							Before.sibling = A;

							while (B != null) {

								A = B;
							}

							B = Present.sibling;
							Present.firstChild = null;
							Present.sibling = null;

						} else {	

							Present.firstChild.sibling = Present.sibling;
							Before.firstChild = Present.firstChild;
						}
					}

					// No sibling
					else {

						Before.firstChild = Present.firstChild;						
					}
				}

				else if (Before.sibling != null) {

					// If Sibling
					if(Present.sibling != null) {

						if (Present.firstChild.sibling != null) {

							TagNode A = Present.firstChild;

							Before.sibling = A;

							while (A.sibling != null) {

								TagNode B = A.sibling;
								A = B;
							}

							A.sibling = Present.sibling;
							Present.firstChild = null;
							Present.sibling = null;

						} else {

							Present.firstChild.sibling = Present.sibling;						
							Before.sibling = Present.firstChild;
						}
					}

					// NO sibling
					else {

						Before.sibling = Present.firstChild;
					}
				}
			}
		}

		// ol or ul tags

		else if(ABC.equals("ol") ||
				ABC.equals("ul")) {

			// Matcher
			if (ABC.equals(Tagg)) {

				if (Before.firstChild != null 
						&& Before.firstChild.tag.equals(ABC)) {

					if(Present.sibling != null) {

						if (Present.firstChild.sibling != null) {

							TagNode A = Present.firstChild;

							while (A.sibling != null) {

								if (A.tag.equals("li"))

									A.tag = "p";
								A = A.sibling;
							}

							if (A.tag.equals("li"))

								A.tag = "p";
							A.sibling = Present.sibling;
							Before.firstChild = Present.firstChild;

						} else {

							if (Present.firstChild.tag.equals("li")) 

								Present.firstChild.tag = "p";
							Present.firstChild.sibling = Present.sibling;
							Before.firstChild = Present.firstChild;
						}
					}
					// No sibling
					else {

						if (Present.firstChild.sibling != null) {

							TagNode A = Present.firstChild;

							while(A.sibling != null) {

								if (A.tag.equals("li"))

									A.tag = "p";
								A = A.sibling;
							}

							if (A.tag.equals("li"))

								A.tag = "p";
							Before.firstChild = Present.firstChild;

						} else {

							if (Present.firstChild.tag.equals("li")) 

								Present.firstChild.tag = "p";	
							Before.firstChild = Present.firstChild;						
						}
					}
				}
				else if (Before.sibling != null) {

					//  a sibling
					if(Present.sibling != null) {

						if (Present.firstChild.tag.equals("li"))
							Present.firstChild.tag = "p";

						if (Present.firstChild.sibling != null) {	

							TagNode A = Present.firstChild;

							Before.sibling = A;

							while (A.sibling != null) {

								if (A.tag.equals("li"))

									A.tag = "p";
								A = A.sibling;
							}

							if (A.tag.equals("li"))

								A.tag = "p";

							A.sibling = Present.sibling;
							Present.firstChild = null;
							Present.sibling = null;

						} else {

							Present.firstChild.sibling = Present.sibling;
							Before.sibling = Present.firstChild;
						}
					}
					// NO sibling
					else {

						if (Present.firstChild.sibling != null) {

							TagNode A = Present.firstChild;

							while(A.sibling != null) {

								if (A.tag.equals("li"))

									A.tag = "p";
								A = A.sibling;
							}

							if (A.tag.equals("li"))

								A.tag = "p";
							Before.sibling = Present.firstChild;

						} else {

							if (Present.firstChild.tag.equals("li"))

								Present.firstChild.tag = "p";
							Before.sibling = Present.firstChild;
						}
					}
				}
			}
		}

		// INORDER TRAVERSAL
		Remover(Present, Tagg, Present.firstChild);
		Remover(Present, Tagg, Present.sibling);
	}


	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {

		Adder(null, root, word, tag);
	}

	private void Adder(TagNode Before, 
			TagNode Present, String STR, String TAGG) {

		// Base Case
		if (Present == null) {

			return;
		}

		if(Before != null && Before.tag == TAGG){

			return;
		}

		if (TAGG.equals("html") || TAGG.equals("body") 
				|| TAGG.equals("p") || TAGG.equals("em") 
				|| TAGG.equals("tr") || TAGG.equals("td") 
				|| TAGG.equals("ol") || TAGG.equals("ul") 
				|| TAGG.equals("b") || TAGG.equals("table") 
				|| TAGG.equals("li")) {

			if(Present.tag.equals("html") || Present.tag.equals("body") 
					|| Present.tag.equals("b") || Present.tag.equals("table") 
					|| Present.tag.equals("tr") || Present.tag.equals("td")
					|| Present.tag.equals("p") || Present.tag.equals("em") 
					|| Present.tag.equals("ol") || Present.tag.equals("ul") 
					|| Present.tag.equals("li")) {

			} else {

				String[] A = Present.tag.split(" ");

				int O = A.length;
				int Length = O;

				String PREVIOUS;
				String TARGET;
				String AFTER;

				TagNode B = new TagNode(TAGG, null, null);

				//One word
				if (Length == 1) {

					int i;
					for (i = 0; i < Length; i = i + 1) {

						if (HelperforAdder(A[i], STR, null)) {

							if (Before.firstChild == Present) {

								if (Present.sibling != null) {

									Before.firstChild = B;
									B.firstChild = Present;
									B.sibling = Present.sibling;
									Present.sibling = null;

								} else {

									Before.firstChild = B;
									B.firstChild = Present;
								}
							}

							if (Before.sibling == Present) {

								if (Present.sibling != null) {

									Before.sibling = B;
									B.firstChild = Present;
									B.sibling = Present.sibling;
									Present.sibling = null;

								} else {

									Before.sibling = B;
									B.firstChild = Present;
								}
							}
						}
					}	

				} else {
					
					boolean CheckB = true;
					boolean CheckA = true;
					boolean CheckT = true;

					TagNode Top = null;
					TagNode Bottom = null;

					while (CheckT == true) {

						//helping objects
						TagNode P = new TagNode(null, null, null);
						TagNode Q = new TagNode(null, null, null);
						TagNode R = new TagNode(null, null, null);

						//Initializing strings
						PREVIOUS = "";
						TARGET = "";
						AFTER = "";

						for (int n = 0; n < Length && (CheckA == true); n = n + 1) {

							if (HelperforAdder(A[n], STR, null)) {

								CheckB = false;
								CheckA = false;

								String T = A[n];
								TARGET = T;

								Q.tag = TARGET;

								int M = Length - 1;
								
								if (n != M) {

									int m;
									for (m = n + 1; m < Length; m++) {

										AFTER = AFTER + A[m] + " ";

									}

									R.tag = AFTER;

								}
							}
							else if (CheckB == true) {

								PREVIOUS = PREVIOUS + A[n] + " ";
								P.tag = PREVIOUS;

							}
						}

						//NO MATCH
						if (CheckA == true){

							if (Before.firstChild == Present) {

								Before.firstChild = P;
							}

							if (Before.sibling == Present) {

								Before.sibling = P;
							}

							break;
						}

						if (P.tag != null 
								&& Q.tag != null 
								&& R.tag != null) {

							P.sibling = B;
							B.firstChild = Q;
							B.sibling = R;
						}

						else if (P.tag != null && Q.tag != null) {

							P.sibling = B;
							B.firstChild = Q;

						}

						else if (R.tag != null) {
							B.firstChild = Q;
							B.sibling = R;

						}

						if (Top == null && P.tag != null) {

							Top = P;
						}

						else if (Top == null && P.tag == null) {

							B.firstChild = Q;
							Top = B;

						} else {

							Bottom = Q;
						}

						if (R.tag != null) {

							CheckT = true;

							A = R.tag.split(" ");

							Length = A.length;

							if (Top == null && P.tag != null) {

								Top = P;
							}

							else if (Top == null && P.tag == null) {

								B.firstChild = Q;
								Top = B;

							} else {

								Bottom = R;
							}

						} else {

							CheckT = false;
						}
					}

					if (Before.firstChild == Present) {

						Before.firstChild = Top;
					}

					else if (Before.sibling == Present) {

						Before.sibling = Top;
					}

					if(Present.sibling == null) {

					} else {

						Bottom.sibling = Present.sibling;
						Present.sibling = null;
					}
				}
			}

			//INORDER TRAVERSAL
			Adder(Present, Present.firstChild, STR, TAGG);
			Adder(Present, Present.sibling,  STR, TAGG);
		}
	} 



	// A HELPER FOR ADDER METHOD TO COMPARE AND CHECK.
	private boolean HelperforAdder(String Present, 
			String Target, TagNode X) {

		String A = Present.toLowerCase();
		String B = Target.toLowerCase();

		int L = Present.length();
		char C = Present.charAt(L - 1);

		String STR = A;
		String ABC = B;

		// Check to match
		if(STR.equals(ABC)) {

			return true;
		}

		// Find last character.
		char End = C;

		// check for letters
		String SUB = STR.substring(0, Present.length() - 1);

		if (Character.isLetter(End)) {

			return false;
		}

		else if (ABC.equals(SUB)) {
			return true;

		} else {

			return false;
		}
	}

	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {

		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}

	private void getHTML(TagNode root, StringBuilder sb) {

		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {

			if (ptr.firstChild == null) {

				sb.append(ptr.tag);
				sb.append("\n");

			} else {

				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}

	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {

		print(root, 1);
	}

	private void print(TagNode root, int level) {

		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {

			for (int i=0; i < level-1; i++) {

				System.out.print("      ");
			}

			if (root != this.root) {

				System.out.print("|---- ");

			} else {

				System.out.print("      ");
			}

			System.out.println(ptr.tag);

			if (ptr.firstChild != null) {

				print(ptr.firstChild, level+1);
			}
		}
	}
}
