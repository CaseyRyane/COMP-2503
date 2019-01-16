import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * The ExpressionTree class builds an expression tree from a given postfix
 * expression, Evaluates the given expression and utilizes recursion to build
 * postfix, infix and prefix expression from the values stored in the
 * ExpressionTree nodes.
 * 
 * @author Casey Ryane
 *
 */
public class ExpressionTree {

	private Deque<TreeNode> stack = new ArrayDeque<>();
	private Hashtable<Character, Character> varValues = new Hashtable<>();
	private Scanner keyboard = new Scanner(System.in);
	private TreeNode root;

	public ExpressionTree(String postfixExp) {
		root = null;
		createExpressionTree(postfixExp);
	}

	public ExpressionTree(TreeNode root) {
		this.root = root;
	}

	/**
	 * Constructs expression tree, from the postfix expression, assigns operators to
	 * 'root' nodes and literals to the left and right nodes of operator nodes.
	 */
	public void createExpressionTree(String expression) {

		for (int i = 0; i <= expression.length() - 1; i++) {
			TreeNode rootNode, rightNode, leftNode = null;
			char data = expression.charAt(i);
			if (!isOperator(data)) {
				rootNode = new TreeNode(data);
				stack.push(rootNode);
			} else {
				rootNode = new TreeNode(data);
				rightNode = stack.pop();
				rootNode.setRight(rightNode);
				if (data != '!') {
					leftNode = stack.pop();
					rootNode.setLeft(leftNode);
				}

				stack.push(rootNode);
			}
		}
		this.root = stack.pop();

	}

	/**
	 * Checks if a given char is a NOT, XOR, OR or AND operator
	 * 
	 * @param newChar
	 *            char
	 * @return boolean value
	 */
	private boolean isOperator(char newChar) {
		boolean isOpeartor = false;
		if (newChar == '|' || newChar == '^' || newChar == '&' || newChar == '!') {
			isOpeartor = true;
		}
		return isOpeartor;
	}

	/**
	 * Returns the post Order Expression based on the contents of the expression
	 * tree.
	 * 
	 * @return String post order expression.
	 */
	public String getPostOrderExp() {
		StringBuilder postOrderExp = new StringBuilder();
		return compilePostOrderExp(root, postOrderExp);
	}

	/**
	 * Utilizes recursion to compile a formatted string representing the expression
	 * contained in the expression tree in the post order format.
	 * 
	 * @param node current node to traversed to child nodes and compile data
	 * @param postOrderExp the post order expression as it is compiled.
	 * @return string post order expression
	 */
	private String compilePostOrderExp(TreeNode node, StringBuilder postOrderExp) {

		if (node != null) {
			compilePostOrderExp(node.getLeft(), postOrderExp);
			compilePostOrderExp(node.getRight(), postOrderExp);
			postOrderExp.append(node.getData() + "");
			return postOrderExp.toString();
		}
		return postOrderExp.toString();
	}

	/**
	 * Returns an in order expression based on the contents of the expression tree
	 * 
	 * @return formatted string representing in order expression
	 */
	public String getInOrderExp() {
		StringBuilder inOrderExp = new StringBuilder();
		String inFix = compileInOrderExp(root, inOrderExp);
		return inFix;
	}

	/**
	 * * Utilizes recursion to compile a formatted string representing the expression
	 * contained in the expression tree in in order format,
	 * 
	 * @param node current node to traversed to child nodes and compile data
	 * @param inOrderExp the post order expression as it is compiled.
	 * @return string post order expression
	 */
	private String compileInOrderExp(TreeNode node, StringBuilder inOrderExp) {
		if (node != null) {
			char data = node.getData();
			if (data == '&' || data == '^' || data == '|') {

				inOrderExp.append("(");
				compileInOrderExp(node.getLeft(), inOrderExp);
				inOrderExp.append(node.getData() + "");
				compileInOrderExp(node.getRight(), inOrderExp);
				inOrderExp.append(")");
			}

			else if (data == '!') {
				inOrderExp.append("(");
				inOrderExp.append(node.getData() + "");
				compileInOrderExp(node.getRight(), inOrderExp);
				inOrderExp.append(")");
			} else {
				inOrderExp.append(node.getData() + "");
			}

			return inOrderExp.toString();
		}
		return inOrderExp.toString();
	}

	/**
	 * Returns a pre order expression based on the contents of the expression tree
	 * 
	 * @return formatted string representing in order expression
	 */
	public String getPreOrderExp() {
		StringBuilder preOrderExp = new StringBuilder();
		return compilePreOrderExp(root, preOrderExp);
	}

	/**
	 * * Utilizes recursion to compile a formatted string representing the expression
	 * contained in the expression tree in pre order format,
	 * 
	 * @param node current node to traversed to child nodes and compile data
	 * @param preOrderExp the post order expression as it is compiled.
	 * @return string post order expression
	 */
	private String compilePreOrderExp(TreeNode node, StringBuilder preOrderExp) {
		if (node != null) {

			preOrderExp.append(node.getData() + "");
			compilePreOrderExp(node.getLeft(), preOrderExp);
			compilePreOrderExp(node.getRight(), preOrderExp);
			return preOrderExp.toString();
		}
		return preOrderExp.toString();

	}

	/**
	 * Evaluates the expression tree and returns the boolean value for
	 * Whether the expression equals true or false.
	 * @return
	 */
	public boolean evaluate() {
		char result = evaluate(root);
		if (result == '0') {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Utilizes recursion to evaluate the expression tree. Each
	 * nodes is evaluated based on its parent node and the sibling node.
	 * Operators contained in parent nodes determine result of acting on child nodes 
	 * boolean values.
	 * 
	 * For variable values the user is prompted to enter the value (0 or 1) that each
	 * varaible holds.
	 * 
	 * @param node current node being evaluated
	 * @return char value of 0 or 1 indicating result.
	 */
	public char evaluate(TreeNode node) {

		if (node.getLeft() == null && node.getRight() == null) {
			return node.getData();
		} else {
			char result = ' ';
			char left = ' ';
			char right = evaluate(node.getRight());
			if (node.getLeft() != null) {
				left = evaluate(node.getLeft());
			}
			if (Character.isLetter(right)) {
				right = getVariableValue(right);
			}
			if (Character.isLetter(left)) {
				left = getVariableValue(left);
			}
			char operator = node.getData();
			return evaluateSingleExpression(operator, left, right);

		}
	}

	/**
	 * Retrieves the values the user intends for a variable to hold.
	 * Ensures values is 0 or 1 in order for the expression to 
	 * be properly evaluated.
	 * 
	 * @param variable
	 * @return
	 */
	private char getVariableValue(char variable) {
		char variableValue = ' ';
		if (varValues.containsKey(variable)) {
			variableValue = varValues.get(variable);
		} else {

			System.out.println("Enter a value ('0' or '1') for variable: " + variable);
			boolean valid = false;

			variableValue = keyboard.nextLine().charAt(0);
			if (variableValue == '0' || variableValue == '1') {
				valid = true;
				varValues.put(variable, variableValue);
			} else {
				System.out.println("Invalid value");
			}
		}
		return variableValue;
	}

	/**
	 * Evaluates the results of a single expression, given an operator and two
	 * values held in the child nodes of the acting operator node.
	 * 
	 * @param operator char value for opera type, XOR, OR, NOT, ANd
	 * @param left char value of left node (0 or 1)
	 * @param right  char value of right node (0 or 1)
	 * @return char value of 0 or 1 representign result of single evaluation
	 */
	private char evaluateSingleExpression(char operator, char left, char right) {
		char result = ' ';
		if (operator == '&') {
			result = verifyAND(left, right);
		} else if (operator == '|') {
			result = verifyOR(left, right);
		} else if (operator == '^') {
			result = verifyXOR(left, right);
		} else if (operator == '!') {
			result = verifyNOT(right);
		}
		return result;
	}

	/**
	 * Returns the result of two values when an AND operator is used on them.
	 * returns 1 if both the left and right ndoes are equal to 1.
	 * 
	 * @param left char (0 or 1) value of left node
	 * @param right char (0 or 1) value of right node
	 * @return char (0 or 1) value of result
	 */
	private char verifyAND(char left, char right) {
		char result = ' ';
		if (left == '1' && right == '1') {
			result = '1';
		} else {
			result = '0';
		}
		return result;
	}

	/**
	 * Returns the result of two values when an OR operator is used on them.
	 * Returns 1 if eithe the left or right node is equal to 1
	 * 
	 * @param left char (0 or 1) value of left node
	 * @param right char (0 or 1) value of right node
	 * @return char (0 or 1) value of result
	 */
	private char verifyOR(char left, char right) {
		char result = ' ';
		if (left == '1' || right == '1') {
			result = '1';
		} else {
			result = '0';
		}
		return result;
	}

	/**
	 * Returns the result of two values when an XOR operator is used on them
	 * returns 1 if only the left is equal 1 one
	 * or only theright is equal to 1;
	 * 
	 * @param left char (0 or 1) value of left node
	 * @param right char (0 or 1) value of right node
	 * @return char (0 or 1) value of result
	 */
	private char verifyXOR(char left, char right) {
		char result = ' ';
		if (left == '1' && right == '0') {
			result = '1';
		} else if (left == '0' && right == '1') {
			result = '1';
		} else {
			result = '0';
		}
		return result;
	}

	/**
	 * Returns the result of a single value when a NOT operator is used on it
	 * when given a value of 1 returns 0
	 * when given a value of 0 returns 1;
	 * 
	 * @param right char (0 or 1) value of right node
	 * @return char (0 or 1) value of result
	 */
	private char verifyNOT(char right) {
		char result = ' ';
		if (right == '1') {
			result = '0';
		} else {
			result = '1';
		}
		return result;
	}

}
