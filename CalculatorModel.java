import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

/**
 * CalculatorModel.java
 *
 * CS 204 Final Project- Graphing Calculator
 *
 * @author  Abby Lewis, Jamaica Lammi, Danny Hanson, Sam Keyes
 *
 * Class for implementing the correct button actions for the
 * calculator.  This class is the model, so it contains
 * everything the calculator needs to know about calculations.
 *
 */

public class CalculatorModel {
    private ArrayList<String> currentEq;
    private ScriptEngine solver;
    private int size;

    /**
     * Constructor creates a script engine to solve equations,
     * then initializes an ArrayList and sets the size to zero.
     */
    public CalculatorModel() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        solver = mgr.getEngineByName("JavaScript");
        currentEq = new ArrayList<String>();
        size = 0;
    }

    /**
     * Adds the correct symbol to the equation based on the String
     * parameter action and returns an Array of Strings.
     */
    public String[] performAction(String action) {
        // Initializes an Array of Strings of size 2
        String[] finalEquation = new String[2];

        // If the input is "Enter" evaluate the equation
        switch (action) {
            case "Enter":
                return this.evaluate();
            // main basic operators
            //addition
            case "+":
                this.plus();
                break;

            //subtraction
            case "-":
                this.minus();
                break;

            //multiplication
            case "*":
                this.times();
                break;
            //division
            case "/":
                this.divide();
                break;

            //subtraction
            case "(-)":
                this.negative();
                break;

            // parentheses
            //opening parentheses
            case "(":
                this.openParen();
                break;

            //closing parentheses
            case ")":
                this.closeParen();
                break;


            // trig functions
            //sine
            case "sin()":
                this.sin();
                break;

            // cosine
            case "cos()":
                this.cos();
                break;

            //tangent
            case "tan()":
                this.tan();
                break;

            //pi
            case "pi":
                this.pi();
                break;

            // logarithms
            //natural log
            case "ln()":
                this.ln();
                break;

            //euler's number
            case "e":
                this.e();
                break;

            // powers
            //squared shortcut
            case "x^2":
                this.square();
                break;

            //square root shortcut
            case "sqrt":
                this.sqrt();
                break;

            //power general
            case "^":
                this.power();
                break;


            // delete
            case "Delete":
                this.delete();
                break;

            // clear
            case "Clear":
                this.clear();
                break;

            // clear all
            case ("<html>" + "Clear" + "<br>" + "All" + "<html>"):
                this.clear();
                break;

            // clear graph
            case ("<html>" + "Clear" + "<br>" + "Graph" + "<html>"):
                this.clear();
                break;

            // decimal point
            case ".":
                this.decimal();
                break;

            // digits
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                this.number(action);
                break;
            // variable x
            case "x":
                this.number(action);
                break;

        }
        //puts the equation in a more formatted form
        finalEquation[0] = equationToNiceForm(this.copyEquation());
        //returns the formatted form of the equation
        return finalEquation;
    }

    // clear current equation
    public void clear() {
        //make new array of strings
        currentEq = new ArrayList<String>();
        size = 0;
    }

    // essentially a backspace
    public void delete() {
        if (currentEq.size() > 0) {
            String temp = currentEq.get(size - 1);
            // Ensures only one digit is deleted
            if (isNum(temp)) {
                if (temp.length() > 1) {
                    currentEq.set(size - 1, temp.substring(0, temp.length() - 1));
                } else {
                    currentEq.remove(size - 1);
                    size--;
                }
            }
            // deals with the power function being deleted
            else if (temp.charAt(0) == ',') {
                currentEq.remove(size - 1);
                int i = 2;
                while (!currentEq.get(size - i).equals("Math.pow(")) {
                    i++;
                }
                currentEq.remove(size - i);
                size -= 2;
            }
            // else most recent is deleted
            else {
                currentEq.remove(size - 1);
                size--;
            }
        }
    }

    //deals with new digits being added to the equation
    public void number(String num) {
        //route if the equation has numbers with more than one digit
        if (size > 0) {
            String temp = currentEq.get(size - 1);
            if (isNum(temp)) {
                currentEq.set(size - 1, temp + num);
            } else {
                currentEq.add(num);
                size++;
            }
            //if the term only has one digit
        } else {
            currentEq.add(num);
            size++;
        }
    }

    //handles decimal points
    public void decimal() {
        if (size > 0) {
            String temp = currentEq.get(size - 1);
            if (isNum(temp)) {
                currentEq.set(size - 1, temp + ".");
            } else {
                currentEq.add(".");
                size++;
            }
        } else {
            currentEq.add(".");
            size++;
        }
    }


    /**
     * The next few functions add the basic operators
     */

    public void plus() {
        currentEq.add("+");
        size++;
    }

    public void minus() {
        currentEq.add("-");
        size++;
    }

    public void times() {
        currentEq.add("*");
        size++;
    }

    public void divide() {
        currentEq.add("/");
        size++;
    }

    public void negative() {
        currentEq.add("(-1)*");
        size++;
    }

    /**
     * The next few functions add the parentheses
     */
    public void openParen() {
        currentEq.add("(");
        size++;
    }

    public void closeParen() {
        currentEq.add(")");
        size++;
    }


    /**
     * The next few functions add the trigonometric operations
     */

    public void sin() {
        currentEq.add("Math.sin(");
        size++;
    }

    public void cos() {
        currentEq.add("Math.cos(");
        size++;
    }

    public void tan() {
        currentEq.add("Math.tan(");
        size++;
    }

    public void pi() {
        currentEq.add("Math.PI");
        size++;
    }


    /**
     * The next few functions add the logarithms
     */
    public void ln() {
        currentEq.add("Math.log(");
        size++;
    }

    public void e() {
        currentEq.add("Math.E");
        size++;
    }


    /**
     * Adds the "x" variable
     */
    public void x() {
        currentEq.add("x");
        size++;
    }

    /**
     * Adds the sqrt function
     */
    public void sqrt() {
        currentEq.add("Math.sqrt(");
        size++;
    }

    /**
     * Adds the "square" function
     */
    public void square() {
        // Checks for different cases to figure out where to add "Math.pow(" and ",2)"
        if (size > 0) {
            String temp = currentEq.get(size - 1);
            Stack<String> tempStack = new Stack<String>();
            if (isNum(temp) || temp.equals("x")) {
                currentEq.add(size - 1, "Math.pow(");
                currentEq.add(",2)");
                size += 2;
            } else if (temp.equals(")")) {
                tempStack.push(")");
                int i = 2;
                while (!tempStack.empty()) {
                    String temp2 = currentEq.get(size - i);
                    if (temp2.equals(")")) {
                        tempStack.push(")");
                    } else if (temp2.equals("(")) {
                        tempStack.pop();
                    } else if (temp2.matches("Math.+[(]")) {
                        tempStack.pop();
                    }
                    i++;
                }
                currentEq.add(size - i, "Math.pow(");
                currentEq.add(",2)");
                size += 2;
            } else if (temp.matches("Math.(E|(PI))")) {
                currentEq.add(size - 1, "Math.pow(");
                currentEq.add(",2)");
                size += 2;
            }
        }
    }

    /**
     * Adds the power function
     */
    public void power() {
        // Checks for different cases to figure out where to add "Math.pow(" and ","
        if (size > 0) {
            String temp = currentEq.get(size - 1);
            Stack<String> tempStack = new Stack<String>();
            if (isNum(temp) || temp.equals("x")) {
                currentEq.add(size - 1, "Math.pow(");
                currentEq.add(",");
                size += 2;
            } else if (temp.equals(")")) {
                tempStack.push(")");
                int i = 2;
                while (!tempStack.empty()) {
                    String temp2 = currentEq.get(size - i);
                    if (temp2.equals(")")) tempStack.push(")");
                    else if (temp2.equals("(")) tempStack.pop();
                    else if (temp2.matches("Math.+[(]")) tempStack.pop();
                    i++;
                }
                i--;
                currentEq.add(size - i, "Math.pow(");
                currentEq.add(",");
                size += 2;
            } else if (temp.matches("Math.(E|(PI))")) {
                currentEq.add(size - 1, "Math.pow(");
                currentEq.add(",");
                size += 2;
            }
        }
    }

    /**
     * Used so we can get a copy of the current equation,
     * it returns an ArrayList of Strings which represent
     * the equation.
     */
    public ArrayList<String> copyEquation() {
        return (ArrayList<String>) currentEq.clone();
    }

    /**
     * Evaluates the equation and returns an Array of Strings
     * where the first entry is the displayable form of the equation,
     * and the second entry is the answer.
     */
    public String[] evaluate() {
        String[] evaluatedEquation = new String[2];

        // Creates a displayable form of the equation
        String displayableEquation = equationToNiceForm(copyEquation());
        evaluatedEquation[0] = displayableEquation;

        // Makes the equation readable by a ScriptEngine
        String readableEquation = javascriptEquation();
        String fixedParen = parenthesesChecker(readableEquation);

        // Rounds to 6 decimal points
        String withRounding = "Math.round(1000000*(" + fixedParen + "))/1000000";
        String tempSolution = "";

        // Equation is evaluated here and catches any errors
        try {
            tempSolution = solver.eval(withRounding).toString();
        } catch (Exception e) {
            tempSolution = "Error";
        }
        evaluatedEquation[1] = tempSolution;

        // Resets the equation to size 0
        currentEq = new ArrayList<String>();
        size = 0;
        System.out.println(evaluatedEquation[1] + evaluatedEquation[2]);
        return evaluatedEquation;
    }

    /************************************
     * SECTION CHANGE: GRAPH MODEL HERE *
     ***********************************/

    /**
     * Evaluates the equation over the range x = -10 to x = 10,
     * and returns an Array of Strings which contain the y-values
     * for the graph.
     */
    public String[] evaluateGraph() {
        String[] solutionArray = new String[600];

        // Gets the equation to a form that the ScriptEngine can read
        String readableEquation = javascriptEquation();
        String fixedParen = parenthesesChecker(readableEquation);
        String scaledEquation = "30*(" + fixedParen + ")";

        // Loops through possible x values
        for (int i = -300; i < 300; i++) {
            // Scales x value so it fits with 20 by 20 grid
            double scaleFactor = i * 1 / 30.0;

            // Solves equations at a given x value
            String graphedEq = replaceX(scaledEquation, Double.toString(scaleFactor));
            String tempSolution = "";
            try {
                tempSolution = solver.eval(graphedEq).toString();
            } catch (Exception e) {
                tempSolution = null;
            }
            solutionArray[i + 300] = tempSolution;
        }

        // Resets current equation
        currentEq = new ArrayList<String>();
        size = 0;
        return solutionArray;
    }

    /******************
     * HELPER METHODS *
     *****************/

    /**
     * Returns a String with all x's in the parameter equation with a number
     */
    private String replaceX(String equation, String num) {
        String output = new String(equation);

        // Loops through the output equation and if an "x" is found replace it with the correct number
        for (int i = 0; i < output.length(); i++) {
            if (output.charAt(i) == 'x') {
                String firstPart = output.substring(0, i);
                String secondPart = output.substring(i + 1);
                output = "";
                output = output.concat(firstPart);
                output = output.concat(num);
                output = output.concat(secondPart);
            }
        }
        return output;
    }

    /**
     * Checks if a value is a number and returns a boolean
     */
    private boolean isNum(String nm) {
        if (nm.matches("[0-9]+.?[0-9]*")) {
            return true;
        }
        return false;
    }

    /**
     * Converts the current equation to readable form for a ScriptEngine
     */
    private String javascriptEquation() {
        String currentEquation = "";
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                if (isNum(currentEq.get(i)) && currentEq.get(i + 1).matches("Math.+")) {
                    currentEq.add(i + 1, "*");
                    size++;
                }
            }
            currentEquation += currentEq.get(i);
        }
        System.out.println(currentEquation);
        return currentEquation;
    }

    /**
     * Converts the equation to a displayable form for text areas
     */
    private String equationToNiceForm(ArrayList<String> eq) {
        String currentEquation = "";
        System.out.println("over here: " + eq.toString());
        for (int i = 0; i < eq.size(); i++) {
            if (i < eq.size() - 1) {
                if (isNum(eq.get(i)) && eq.get(i + 1).matches("Math.+")) {
                    eq.add(i + 1, "*");
                }
            }
            if (eq.get(i).equals("Math.pow(")) {
                eq.remove(i);
            }
            if (eq.get(i).matches("Math.+")) {
                String replace = eq.get(i).substring(5);
                eq.set(i, replace);
            }
            if (eq.get(i).equals(",")) {
                eq.set(i, "^(");
            }
            if (eq.get(i).equals(",2)")) {
                eq.set(i, "^2");
            }
            if (eq.get(i).equals("(-1)*")) {
                eq.set(i, "(-)");
            }


            currentEquation += eq.get(i);
        }
            return currentEquation;
        }

        /**
         * Adds in all missing parentheses
         */
        private String parenthesesChecker(String checkedEq){
            String withParens = new String(checkedEq);
            Stack<String> parenStack = new Stack<String>();
            for (int i = 0; i < checkedEq.length(); i++) {
                if (withParens.charAt(i) == '(') {
                    parenStack.push("off cliff");
                }
                if (withParens.charAt(i) == ')' && !parenStack.empty()) {
                    parenStack.pop();
                }
            }
            while (!parenStack.empty()) {
                withParens += ")";
                parenStack.pop();
            }
            return withParens;
        }
    }

