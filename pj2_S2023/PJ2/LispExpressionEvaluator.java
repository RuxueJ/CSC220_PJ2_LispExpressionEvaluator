/************************************************************************************
 *
 *  		CSC220 Programming Project#2
 *
 * Specification: 
 *
 * Taken from Project 7, Chapter 5, Page 178
 * I have modified specification and requirements of this project
 *
 * Ref: http://www.gigamonkeys.com/book/        (see chap. 10)
 *
 * In the language Lisp, each of the four basic arithmetic operators appears 
 * before an arbitrary number of operands, which are separated by spaces. 
 * The resulting expression is enclosed in parentheses. The operators behave 
 * as follows:
 *
 * (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
 *
 * (- a b c ...) returns a - b - c - ..., and (- a) returns -a. 
 *
 * (* a b c ...) returns the product of all the operands, and (* a) returns a.
 *
 * (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a. 
 *
 * Note: + * - / must have at least one operand
 *
 * You can form larger arithmetic expressions by combining these basic 
 * expressions using a fully parenthesized prefix notation. 
 * For example, the following is a valid Lisp expression:
 *
 * 	(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))
 *
 * This expression is evaluated successively as follows:
 *
 *	(+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 1))
 *	(+ -6 24 -1.5 1)
 *	17.5
 *
 * Requirements:
 *
 * - Design and implement an algorithm that uses Java API stacks to evaluate a 
 *   valid Lisp expression composed of the four basic operators and integer values. 
 * - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
 * - Display result as floating point number with at 2 decimal places
 * - Negative number is not a valid "input" operand, e.g. (+ -2 3) 
 *   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
 * - There may be any number of blank spaces, >= 0, in between tokens
 *   Thus, the following expressions are valid:
 *   	(+   (-6)3)
 *   	(/(+20 30))
 *
 * - Must use Java API Stack class in this project.
 *   Ref: http://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
 * - Must throw LispExpressionEvaluatorException to indicate errors
 * - Must not add new or modify existing data fields
 * - Must implement these methods : 
 *
 *   	public LispExpressionEvaluator()
 *   	public LispExpressionEvaluator(String inputExpression) 
 *      public void reset(String inputExpression) 
 *      public double evaluate()
 *      private void evaluateCurrentOperation()
 *
 * - You may add new private methods
 *
 *************************************************************************************/
package PJ2;
import java.util.*;

public class LispExpressionEvaluator extends LispExpressionEvaluatorException
{
	// Current input Lisp expression
	private String currentExpression;

	// Main expression stack, see algorithm in evaluate()
	private Stack<Object> inputExprStack;
	private Stack<Double> evaluationStack;

	// default constructor
	// set currentExpression to ""
	// create stack objects
	public LispExpressionEvaluator()
	{
		// add statements
		currentExpression="";
		inputExprStack = new Stack<>();
		evaluationStack = new Stack<>();
	}

	// constructor with an input expression
	// set currentExpression to inputExpression
	// create stack objects
	public LispExpressionEvaluator(String inputExpression)
	{
		// add statements
		currentExpression = inputExpression;
		inputExprStack = new Stack<>();
		evaluationStack = new Stack<>();
	}

	// set currentExpression to inputExpression
	// clear stack objects
	public void reset(String inputExpression)
	{
		// add statements
		currentExpression = inputExpression;
		inputExprStack.clear();
		evaluationStack.clear();
	}

	// This function evaluates current operator with its operands
	// See complete algorithm in evaluate()
	//
	// Main Steps:
	// Pop operands from allTokensStack and push them onto
	// currentOperandsStack until you find an operator
	// Apply the operator to the operands on currentOperandsStack
	// Push the result into allTokensStack
	//
	private boolean isOperator(Object token){
		boolean isPerator = false;
		if((token == (Object)'+') ||
			(token == (Object)'-') ||
			(token == (Object)'*') ||
			(token == (Object)'/')){
			isPerator = true;
		}
		return isPerator;
	}
	private void solveCurrentParenthesisOperation()
	{
		// add statements
		Object popToken = inputExprStack.pop();
		boolean flag = isOperator(popToken);
		if(flag){
			throw new LispExpressionEvaluatorException();
		}
		while (!flag){
			evaluationStack.push((double)popToken);
			popToken = inputExprStack.pop();
			flag = isOperator(popToken);
		}
		switch((Character)popToken){
			case '+':
				double sum = 0.0;
				while(!evaluationStack.isEmpty()){
					double temp = evaluationStack.pop();
					sum += temp;
				}
				inputExprStack.push(sum);
				break;

			case '*':
				double mul = 1.0;
				while(!evaluationStack.isEmpty()){
					double temp = evaluationStack.pop();
					mul *= temp;
				}
				inputExprStack.push(mul);
				break;

			case '-':
				double sub = 0.0;
				double tempSub = evaluationStack.pop();
				if(evaluationStack.size() == 0){
					sub = 0.0 - tempSub;
					inputExprStack.push(sub);
				}else{
					sub = tempSub;
					while(!evaluationStack.isEmpty()){
						sub -= evaluationStack.pop();
					}
					inputExprStack.push(sub);
				}
				break;

			case '/':
				double div = 0.0;
				double tempDiv = (double)evaluationStack.pop();
				if(tempDiv ==0){
					throw new LispExpressionEvaluatorException();
				}
				if(evaluationStack.size() == 0){
					div = 1.0 / tempDiv;
					inputExprStack.push(div);
				}else{
					div = tempDiv;
					while(!evaluationStack.isEmpty()){
						double tempDiv2 = evaluationStack.pop();
						if(tempDiv2 == 0){
							throw new LispExpressionEvaluatorException();
						}
						div /= tempDiv2;
					}
					inputExprStack.push(div);
				}
				break;
		}

	}


	/**
	 * This function evaluates current Lisp expression in currentExpression
	 * It return result of the expression
	 *
	 * The algorithm:
	 *
	 * Step 1 Scan the tokens in the string.
	 * Step 2 If you see an operand, push operand object onto the allTokensStack
	 * Step 3 If you see "(", next token should be an operator
	 * Step 4 If you see an operator, push operator object onto the allTokensStack
	 * Step 5 If you see ")" // steps in solveCurrentParenthesisOperation() :
	 * Step 6 Pop operands and push them onto currentOperandsStack
	 * until you find an operator
	 * Step 7 Apply the operator to the operands on currentOperandsStack
	 * Step 8 Push the result into allTokensStack
	 * Step 9 If you run out of tokens, the value on the top of allTokensStack
	 * is the result of the expression.
	 */
	public double evaluate()
	{
		// only outline is given...
		// you need to add statements/local variables
		// you may delete or modify any statements in this method

		// use scanner to tokenize currentExpression
		Scanner currentExpressionScanner = new Scanner(currentExpression);
		double result = 0.0;

		// Use zero or more white space as delimiter,
		// which breaks the string into single character tokens
		currentExpressionScanner = currentExpressionScanner.useDelimiter("\\s*");

		// Step 1: Scan the tokens in the string.
		while (currentExpressionScanner.hasNext())
		{

			// Step 2: If you see an operand, push operand object onto the allTokensStack
			if (currentExpressionScanner.hasNextInt())
			{

				// This force scanner to grab all of the digits
				// Otherwise, it will just get one char
				String dataString = currentExpressionScanner.findInLine("\\d+");
				double number = Double.parseDouble(dataString);
				inputExprStack.push(number);

				// more ...
			}
			else
			{
				// Get next token, only one char in string token
				String aToken = currentExpressionScanner.next();
				//System.out.println("Other: " + aToken);
				char item = aToken.charAt(0);

				switch (item)
				{

					// Step 3: If you see "(", next token should be an operator
					case '(':
//						String nextAToken = currentExpressionScanner.next();
//						if ((nextAToken != "+") &&
//							(nextAToken != "-") &&
//								(nextAToken != "*") &&
//								(nextAToken!= "/")){
//							throw new LispExpressionEvaluatorException();
//						}
						break;

					// Step 4: If you see an operator, push operator object onto the allTokensStack
					case '+': case '*': case '/': case '-':
						inputExprStack.push(item);
						break;
					// Step 5: If you see ")" // steps in solveCurrentParenthesisOperation() :
					case ')':
						if(inputExprStack.isEmpty()){
							throw new LispExpressionEvaluatorException();
						}
						solveCurrentParenthesisOperation();
						break;
					default: // error
						throw new LispExpressionEvaluator(item + " is not a legal expression operator");
				} // end switch
			} // end else
		} // end while

		while (!currentExpressionScanner.hasNext() && inputExprStack.size()!=1){
			solveCurrentParenthesisOperation();
		}
		while (!currentExpressionScanner.hasNext() && inputExprStack.size()==1){
			result = (double)inputExprStack.pop();
			 // return the correct result
		}
		return result;
		// Step 9: If you run out of tokens, the value on the top of allTokensStack is
		// is the result of the expression.
		//
		// return result
	}

    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    //=====================================================================

    
    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
		expr.reset(s);
        try {
           result = expr.evaluate();
           System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionEvaluatorException e) {
            System.out.println("Evaluated result :"+"Infinity or LispExpressionEvaluatorException");
        }
		catch (EmptyStackException e){
			System.out.println("Evaluated result :"+"LispExpressionEvaluatorException");
		}
        
        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 0))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 )) (/ 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";

	evaluateExprTest(test1, expr, "16.50");
	evaluateExprTest(test2, expr, "-378.12");
	evaluateExprTest(test3, expr, "4.50");
	evaluateExprTest(test4, expr, "1.50");
	evaluateExprTest(test5, expr, "Infinity or LispExpressionEvaluatorException");
	evaluateExprTest(test6, expr, "LispExpressionEvaluatorException");
	evaluateExprTest(test7, expr, "LispExpressionException");
	evaluateExprTest(test8, expr, "LispExpressionException");
    }
}


