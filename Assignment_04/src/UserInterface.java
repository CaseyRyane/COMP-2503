import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * The UserInterface class is responsible for taking in input from the user and
 * displaying results to the screen. The user is prompted to choose to enter
 * either an infix or postfix boolean expression, if infix is chosen the given
 * expression is passed through the shunting yard algorithm to convert it into a
 * post fix expression. The postfix expression is used to create an expression
 * tree
 * 
 * @author Casey Ryane
 *
 */
public class UserInterface {

	private static UserInterface UI;
	private Scanner keyboard = new Scanner(System.in);
	private ExpressionTree expTree;

	/**
	 * Main access point for the program to run, initializes the UI class and runs
	 * it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UI = new UserInterface();
		UI.run();
	}

	/**
	 * The main 'run' method that is the driver for the program as a whole. The user
	 * is prompted for the type of expression that the want to enter, validates the
	 * given expression, builds an expression tree, displays the contents of the
	 * expression tree in postfix, prefix and infix format and evaluates the
	 * expression.
	 */
	private void run() {
		String userInput = "";

		char expressionType = askUserIfPost();
		if (expressionType == 'p') {
			userInput = getPostfixExpression();
		} else if (expressionType == 'i') {
			userInput = getInfix();
		}
		if (userInput.equals("")) {

			System.out.println("Invalid expression type indicator, Program Terminated");
		} else {
			if (!validateExpression(userInput)) {
				System.out.println("Invalid expression. Program terminated");
			} else {
				buildExpressionTree(userInput);
				printExpressions();
				evaluateExp();
			}
		}
	}

	/**
	 * Asks the user if the expression to be given is in postfix or infix format.
	 * Returns a char value that represents the response: 'p' for postfix 'i' for
	 * infix.
	 * 
	 * @return char value.
	 */
	private char askUserIfPost() {
		System.out.println("Enter \"P\" if you want to enter a postfix expression, \"I\" to enter Infix expression");
		String response = keyboard.nextLine();
		char expressionType = ' ';
		if (response.equalsIgnoreCase("P")) {
			expressionType = 'p';
		} else if (response.equalsIgnoreCase("I")) {
			expressionType = 'i';
		}
		return expressionType;
	}

	/**
	 * Prompts the user for an infix expression, translates the given expression
	 * into postfix and returns the translated expression.
	 * 
	 * @return String repreenting postifx expression
	 */
	private String getInfix() {
		System.out.println("Enter an Infix boolean expression");
		String userInput = keyboard.nextLine();
		String postExp = "";
		try {
			postExp = translateToPostfix(userInput);
		} catch (NullPointerException e) {
			System.out.println("Invalid infix expression");
		}
		return postExp;
	}

	/**
	 * Evaluates and displays the result of the given expression.
	 */
	private void evaluateExp() {
		System.out.println("Evaluated Result: " + expTree.evaluate());
	}

	/**
	 * Builds an expression tree based off of a given postfix expression.
	 * 
	 * @param expression
	 *            String representing expression.
	 */
	private void buildExpressionTree(String expression) {
		expTree = new ExpressionTree(expression);
	}

	/**
	 * Displays the expression on the screen in three formats: prefix, infix and
	 * postfix.
	 */
	private void printExpressions() {
		System.out.print("Prefix: ");
		System.out.println(expTree.getPreOrderExp());
		System.out.print("Infix: ");
		System.out.println(expTree.getInOrderExp());
		System.out.print("Postfix: ");
		System.out.println(expTree.getPostOrderExp());

	}

	/**
	 * Prompts the user to enter a postfix expression and returns the given value as
	 * a string
	 *
	 * @return the string of the given postfix expression.
	 */
	private String getPostfixExpression() {
		System.out.println("Enter a postfix boolean expression");
		String userInput = keyboard.nextLine();
		return userInput;
	}

	/**
	 * Validates the expression given by the users, determines if the number of
	 * operators, brackets, variables and 0/1's presented in the expression given by
	 * the user make up a valid boolean expression.
	 * 
	 * @param expression
	 *            String given by user
	 * @return boolean based on validity of expression
	 */
	private boolean validateExpression(String expression) {
		boolean valid = true;
		boolean belowZero = false;
		int counter = 0;
		for (int i = 0; i <= expression.length() - 1; i++) {
			char nextChar = expression.charAt(i);
			valid = checkForParentheses(nextChar);

			if (isLiteral(nextChar)) {
				counter++;
			} else if (isBinary(nextChar)) {
				counter -= 2;
				belowZero = checkBelowZero(counter);
				counter++;
			} else if (isUnary(nextChar)) {
				counter--;
				belowZero = checkBelowZero(counter);
				counter++;
			}
		}
		if (valid && !belowZero && counter == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the given char is a parentheses
	 * 
	 * @param nextChar
	 *            given Char
	 * @return Boolean value
	 */
	private boolean checkForParentheses(char nextChar) {
		boolean isParentheses = true;
		if (nextChar == '(' || nextChar == ')') {
			isParentheses = false;
		}
		return isParentheses;
	}

	/**
	 * Checks if the given number is below a value of 0
	 * 
	 * @param counter int
	 * @return boolean value
	 */
	private boolean checkBelowZero(int counter) {
		boolean belowZero = false;
		if (counter < 0) {
			belowZero = true;
		}
		return belowZero;
	}
	
	/**
	 * Checks if the given char is a literal, meaning 
	 * a variable value or 0 or 1
	 * 
	 * @param newChar char
	 * @return boolean value
	 */
	private boolean isLiteral(char newChar) {
		boolean isLiteral = false;
		if (newChar == '0' || newChar == '1' || Character.isLetter(newChar)) {
			isLiteral = true;
		}
		return isLiteral;
	}

	/**
	 * Checks if the given char is a a binary operator,
	 * representing an AND, OR or XOR operator.
	 * 
	 * @param newChar char
	 * @return boolean value
	 */
	private boolean isBinary(char newChar) {
		boolean isBinary = false;
		if (newChar == '&' || newChar == '|' || newChar == '^') {
			isBinary = true;
		}
		return isBinary;
	}

	/**
	 * Checks if the given char is a unary operator,
	 * representing a NOT operator.
	 * @param newChar char
	 * @return boolean value
	 */
	private boolean isUnary(char newChar) {
		boolean isUnary = false;
		if (newChar == '!') {
			isUnary = true;
		}
		return isUnary;
	}

	/**
	 * Utilizes the Shunting Yard algorithm to translate an infix expression in to a postfix expression,
	 * When given an infix expression the algorithm utilizes stacks and queues to parse the values
	 * from the infix expression, remove brackets and re format as a postfix expression that can then be 
	 * used to create an expression tree and be evaluated.
	 * 
	 * 
	 * @param infixExp String value of infix expression
	 * @return String value of postfix expression
	 * @throws NullPointerException
	 */
	private String translateToPostfix(String infixExp) throws NullPointerException {
		String postfix = "";
		Deque<Character> operatorStack = new ArrayDeque<>();
		Deque<Character> outputQueue = new ArrayDeque<>();

		int count = 0;
		while (count < infixExp.length()) {
			char currChar = infixExp.charAt(count);
			if (Character.isLetter(currChar) || currChar == '1' || currChar == '0') {
				outputQueue.push(currChar);
			} else if (isOperator(currChar)) {
				while (!operatorStack.isEmpty()
						&& operatorPrecedence(currChar) < operatorPrecedence(operatorStack.peek())) {
					outputQueue.push(operatorStack.pop());
				}
				operatorStack.push(currChar);
			} else if (currChar == '(') {
				operatorStack.push(currChar);
			} else if (currChar == ')') {
				while (operatorStack.peek() != '(') {
					outputQueue.push(operatorStack.pop());
				}
				operatorStack.pop();
			}
			count++;
		}
		while (!operatorStack.isEmpty()) {
			outputQueue.push(operatorStack.pop());
		}
		while (!outputQueue.isEmpty()) {
			postfix += outputQueue.removeLast();
		}
		return postfix;

	}

	/**
	 * Evaluates the given char value and determines its "weight" in terms 
	 * of operator precedence.
	 * 
	 * @param value char
	 * @return int value representing operator weight
	 */
	private int operatorPrecedence(char value) {
		// !highest to lowers !, &, ^, |
		int weight = 0;
		if (value == '!') {
			weight = 4;
		} else if (value == '&') {
			weight = 3;
		} else if (value == '^') {
			weight = 2;
		} else if (value == '|') {
			weight = 1;
		}
		return weight;
	}

	/**
	 * Determines if a given char is a unary or binary operator. 
	 * returns tru if operator is OR, NOT, XOR, or AND operator.
	 * 
	 * @param newChar char
	 * @return Boolean value
	 */
	private boolean isOperator(char newChar) {
		boolean isOperator = false;
		if (newChar == '|' || newChar == '^' || newChar == '&' || newChar == '!') {
			isOperator = true;
		}
		return isOperator;
	}

}
