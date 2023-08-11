import java.util.Scanner;
import java.util.Stack;

public class Demo {
    public static void main(String[] args) {
        String currentExpression = "(+ (- 6996) (* 2 3 4) )";
        Stack stack1 = new Stack();
        Scanner currentExpressionScanner = new Scanner(currentExpression);
        currentExpressionScanner = currentExpressionScanner.useDelimiter("\\s*");
        while(currentExpressionScanner.hasNext()){
            System.out.println(currentExpressionScanner.next());
        }


//        while (currentExpressionScanner.hasNext()) {
//            if (currentExpressionScanner.hasNextInt()) {
//                String dataString = currentExpressionScanner.findInLine("\\d+");
//                int number = Integer.parseInt(dataString);
//                stack1.push(number);
//
////                System.out.println(dataString);
//            }else{
//                System.out.println(currentExpressionScanner.next());
//            }
//        }
//        while(!stack1.isEmpty()){
//            System.out.println(stack1.pop());
//        }

//        for(int i=0; i<7; i++){
//            String aToken = currentExpressionScanner.next();
//            System.out.println(aToken);
//        } // ( + ( - 6 9 9
//        boolean flag = currentExpressionScanner.hasNextInt();
//        System.out.println(flag);

    }
}
////            System.out.print(currentExpressionScanner.next().getClass());
//
//            System.out.println(currentExpressionScanner.next());
//        }
//////        while (currentExpressionScanner.hasNext()) {
////            // Step 2: If you see an operand, push operand object onto the allTokensStack
////            if (currentExpressionScanner.hasNextInt()) {
////                // This force scanner to grab all of the digits
////                // Otherwise, it will just get one char
////                String dataString = currentExpressionScanner.findInLine("\\d+");
//////                for (int i = 0; i < dataString.length(); i++) {
//////                    double number = Character.getNumericValue(dataString.charAt(i));
//////                }
////                System.out.println(dataString);
////            }
//////        }
//        String sentence = "(+ (- 6996) (* 2 3 4) )";
//        Scanner scanner = new Scanner(sentence);
//
//        while (scanner.hasNext()) {
//            if(scanner.hasNextInt()){
//
//                String dataString = scanner.findInLine("\\d");
//                System.out.println(dataString);
//
//            }
//
//        }



//         Create a scanner object with a custom delimiter that matches non-digit characters
//        Scanner scanner = new Scanner(str).useDelimiter("[^\\d]+");
//
////         Loop through all tokens and check for the number 6996
//        while (scanner.hasNext()) {
//            if (scanner.hasNextInt()) {
//                int num = scanner.nextInt();
//                System.out.println(num);
//            } else {
//                System.out.println(scanner.next());
//            }
//
//        }


