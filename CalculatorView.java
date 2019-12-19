import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * CalculatorDisplay.java
 *
 * CS 204 Final Project- Graphing Calculator
 *
 * @author  Abby Lewis, Jamaica Lammi, Danny Hanson, Sam Keyes
 *
 * Class for how the calculator is displayed within the window.
 * It creates the appropriate frame and panels, then draws from
 * other classes and puts the necessary buttons and functions
 * into them.
 *
 */

public class CalculatorView implements ActionListener {
    protected JFrame frame;

    protected JTabbedPane tabs;

    protected JPanel displayPanel;
    protected JPanel buttonPanel;
    protected JPanel graphPanel;
    protected JPanel graphDisplayPanel;

    protected JTextArea inputEquation;
    protected JTextArea equationDisplay;
    protected JTextArea graphEquation;

    protected CalculatorController calcControl;

    protected boolean canPOI = true;

    protected Graphics2D g;

    protected Font displayFont;
    Color colors[] = {new Color(144, 0, 58),
            new Color(255, 132, 0),
            new Color(255, 242, 0),
            new Color(50, 163, 71),
            new Color(0, 255, 224),
            new Color(0, 173, 255),
            new Color(184, 12, 227, 238)};

    Color integColor = new Color(247, 0, 0);


    /**
     * Sets up the overall frame for the Calculator, the two
     * panels where everything is displayed.
     */
    public CalculatorView() {

        // Creates a new font to be used within the frame
        displayFont = new Font("Dialogue", Font.PLAIN, 18);

        // Creates the frame and correct panels and tabs so it displays properly
        createFrame();
        createGraphPanel();
        createDisplayPanel();
        createButtonPanel();
        createTabs();

        // Creates a new Calculator Controller for the Calculator
        calcControl = new CalculatorController();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Creates the graphics to draw the grid on the graph panel
        g = (Graphics2D) graphDisplayPanel.getGraphics();
    }

    /**
     * Sets up the whole frame to be used in the Calculator, sets it
     * to be visible and of a set size with a grid layout of two
     * columns and one row.
     */
    protected void createFrame() {
        frame = new JFrame("Graphing Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2));
        frame.setSize(1250, 720);
    }

    /**
     * Sets up the display panel, which is half the size of the frame,
     * and has a null layout so we can place the text areas appropriately.
     * <p>
     * The display panel is where equations are entered in, evaluated, and graphed.
     */
    protected void createDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(null);
        frame.add(displayPanel, BorderLayout.WEST);
        addToDisplayPanel();
    }

    /**
     * Sets up the button panel, which is half the size of the frame,
     * and has a grid layout to place all the buttons in a uniform way.
     * <p>
     * The button panel contains the keyboard for the Calculator.
     */
    protected void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 6));
        frame.add(buttonPanel, BorderLayout.EAST);
        addButtonsToButtonPanel();
    }

    /**
     * Sets up the graph panel, which is half the size of the frame,
     * and has a null layout so we can add the graph to it in a
     * specified location.
     * <p>
     * The graph panel contains equation to be graphed and the graph itself.
     */
    protected void createGraphPanel() {
        graphPanel = new JPanel();
        graphPanel.setLayout(null);
        graphPanel.setVisible(true);
        frame.add(graphPanel, BorderLayout.WEST);
        addToGraphPanel();

    }

    /**
     * First creates the display panel tab, which allows the user to switch
     * between the display and graph panels, then creates the button panel
     * tab, which informs the user of the location of the keyboard.
     */
    protected void createTabs() {
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.addTab("Equations", displayPanel);
        tabs.addTab("Graph", graphPanel);
        tabs.setVisible(true);
        frame.add(tabs);

        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.addTab("Keyboard", buttonPanel);
        tabs.setVisible(true);
        frame.add(tabs);
    }

    /**
     * Adds everything necessary for the display panel, including two
     * text areas to display the current equation and previous equations
     */
    protected void addToDisplayPanel() {
        // Creates the text area for the input equation and adds it to the displayPanel
        inputEquation = new JTextArea("Enter equation here: ", 3, 5);
        inputEquation.setLineWrap(true);
        inputEquation.setEditable(false);
        inputEquation.setFont(displayFont);
        inputEquation.setBounds(0, 0, 600, 50);
        displayPanel.add(inputEquation);

        // Creates the text area for the previous equations and adds it to the displayPanel
        equationDisplay = new JTextArea("Previous Equations: \n", 3, 5);
        equationDisplay.setLineWrap(true);
        equationDisplay.setEditable(false);
        equationDisplay.setFont(displayFont);
        equationDisplay.setBounds(0, 60, 600, 600);
        displayPanel.add(equationDisplay);
    }

    /**
     * Adds all necessary buttons to the keyboard by creating each
     * button and adding it to an ArrayList to store them all,
     * then looping through that list and adding them to the panel
     * while also setting the font to a correct size and creating
     * action listeners for all buttons.
     */
    protected void addButtonsToButtonPanel() {
        // Creates the new ArrayList to store the buttons
        ArrayList<JButton> buttonList = new ArrayList<JButton>();

        // Row one of buttons on the keyboard
        JButton plus = new JButton("+"); buttonList.add(plus);
        JButton minus = new JButton("-"); buttonList.add(minus);
        JButton multiply = new JButton("*"); buttonList.add(multiply);
        JButton divide = new JButton("/"); buttonList.add(divide);
        JButton openParen = new JButton("("); buttonList.add(openParen);
        JButton closeParen = new JButton(")"); buttonList.add(closeParen);

        // Row two of buttons on the keyboard
        JButton seven = new JButton(Integer.toString(7)); buttonList.add(seven);
        JButton eight = new JButton(Integer.toString(8)); buttonList.add(eight);
        JButton nine = new JButton(Integer.toString(9)); buttonList.add(nine);
        JButton power = new JButton("^"); buttonList.add(power);
        JButton squared = new JButton("x^2"); buttonList.add(squared);
        JButton sqrt = new JButton("sqrt"); buttonList.add(sqrt);

        // Row three of buttons on the keyboard
        JButton four = new JButton(Integer.toString(4)); buttonList.add(four);
        JButton five = new JButton(Integer.toString(5)); buttonList.add(five);
        JButton six = new JButton(Integer.toString(6)); buttonList.add(six);
        JButton sine = new JButton("sin()"); buttonList.add(sine);
        JButton cosine = new JButton("cos()"); buttonList.add(cosine);
        JButton tan = new JButton("tan()"); buttonList.add(tan);

        // Row four of buttons on the keyboard
        JButton one = new JButton(Integer.toString(1)); buttonList.add(one);
        JButton two = new JButton(Integer.toString(2)); buttonList.add(two);
        JButton three = new JButton(Integer.toString(3)); buttonList.add(three);
        JButton pi = new JButton("pi"); buttonList.add(pi);
        JButton ln = new JButton("ln()"); buttonList.add(ln);
        JButton e = new JButton("e"); buttonList.add(e);

        // Row five of buttons on the keyboard
        JButton period = new JButton("."); buttonList.add(period);
        JButton zero = new JButton(Integer.toString(0)); buttonList.add(zero);
        JButton negative = new JButton("(-)"); buttonList.add(negative);
        JButton x = new JButton("x"); buttonList.add(x);
        //JButton enter = new JButton("Enter"); buttonList.add(enter);
        JButton graph = new JButton("Graph"); buttonList.add(graph);

        // Row six of buttons on the keyboard
        JButton delete = new JButton("Delete"); buttonList.add(delete);
        //JButton clear = new JButton("Clear"); buttonList.add(clear);
        JButton clearAll = new JButton("<html>" + "Clear" + "<br>" + "All" + "<html>"); buttonList.add(clearAll);
        JButton clearGraph = new JButton("<html>" + "Clear" + "<br>" + "Graph" + "<html>"); buttonList.add(clearGraph);

        JButton derivative = new JButton("d/dx"); buttonList.add(derivative);
        JButton integral = new JButton("∫"); buttonList.add(integral);
        JButton sndderivative = new JButton("d^2/dx"); buttonList.add(sndderivative);
        JButton defintegral = new JButton("∮"); buttonList.add(defintegral);
        //JButton inflectionPoints = new JButton("P.O.I"); buttonList.add(inflectionPoints);

        // Creates a new font so the buttons have larger text than everything else
        Font f = new Font("Dialogue", Font.PLAIN, 22);

        // Loops through the button list, sets the font, adds listeners, then adds them to the panel
        for (int j = 0; j < buttonList.size(); j++) {
            JButton temp = buttonList.get(j);
            temp.setFont(f);
            temp.setActionCommand(temp.getText());
            temp.addActionListener(this);
            buttonPanel.add(temp);
        }
    }

    /**
     * Adds everything necessary to the graphPanel, including a text area
     * to display the current equation, and a panel to draw the graph in.
     */
    protected void addToGraphPanel() {
        // Creates a text area for the input equations and adds it to the graphPanel
        graphEquation = new JTextArea("Graph: Y = ", 600, 50);
        graphEquation.setEditable(false);
        graphEquation.setFont(displayFont);
        graphEquation.setBounds(0, 0, 600, 50);
        graphPanel.add(graphEquation);

        // Creates a new panel to draw the graph in and adds it to the graphPanel
        graphDisplayPanel = new JPanel();
        graphDisplayPanel.setVisible(true);
        graphDisplayPanel.setLayout(null);
        graphDisplayPanel.setBounds(0, 50, 650, 650);
        graphPanel.add(graphDisplayPanel);
    }


    /**
     * Determine which button was pressed and evaluates it once Enter is pushed, Graphs
     * it once graph is pushed, Clears everything when Clear All is pushed, and clears
     * the graph when Clear Graph is pushed, otherwise it just adds the text from the
     * button to each text area.
     */
    public void actionPerformed(ActionEvent arg0) {
        // Gets the button text and instantiates an array of Strings to store the answer
        String result = arg0.getActionCommand();
        String[] fullEquation;
        String[] newText = null;

        // If the user pushes "Enter", evaluate the equation and display it in previous equations
        switch (result) {
            case "Enter":

                fullEquation = calcControl.update("Enter");
                String eq = fullEquation[0];
                String sol = fullEquation[1];

                // Inserts the equation and solution to the text area and adds newlines for readability
                equationDisplay.insert("\n", 22);
                equationDisplay.insert(sol, 22);
                equationDisplay.insert(" = ", 22);
                equationDisplay.insert(eq, 22);
                equationDisplay.insert("\n", 22);
                equationDisplay.insert("\n", 22);
                inputEquation.setText("");

                // If the list of equations gets longer than the given screen size clear the
                // screen of previous equations and start over
                if (equationDisplay.getLineCount() > 24) {
                    equationDisplay.setText(eq + " = " + sol);
                    equationDisplay.append("\n");
                }
                break;
            // If the user pushes the "Graph" button graph the equation if they are on the graphPanel
            case "Graph":
                graph();
                break;
            // If the user pushes "Clear All" reset all text areas to their original state
            case "d/dx":
                drawPoints(indefiniteDerivative());
                break;

            case "d^2/dx":
                drawPoints(secondDerivative());
                break;

            case "∫":
                drawPoints(indefiniteIntegral());
                break;

            case "∮":
                Scanner s = new Scanner(System.in);
                System.out.println("What is the b value");
                int b = s.nextInt();
                System.out.println("What is the a value");
                int a = s.nextInt();

                String coordinates[] = calcControl.update("Graph");
                System.out.println(definiteIntegral(a, b, 1, coordinates));
            case "P.O.I":
                //inflectionPoints();
                break;

            case "<html>" + "Clear" + "<br>" + "All" + "<html>":
                clearAll(newText, result);
                break;
            // If the user pushes "Clear Graph" reset the graph if it is showing
            case "<html>" + "Clear" + "<br>" + "Graph" + "<html>":
                if (graphDisplayPanel.isShowing()) {
                    clearGraph();
                    drawGrid();
                }
                break;
            // Otherwise add the input to the equation stored in the model
            default:
                newText = calcControl.update(result);
                inputEquation.setText(newText[0]);
                graphEquation.setText(newText[0]);
                break;
        }
    }

    /**
     * Draws the grid of the graph using a set size that we
     * determined based on the size of the panel.
     */
    public void drawGrid() {
        g.setColor(Color.gray);
        int boxSize = 30;

        // Loops and draws horizontal and vertical lines for every 30 units
        for (int i = 0; i <= 20; i++) {
            if (i % 10 == 0) g.setStroke(new BasicStroke(3));
            g.drawLine(boxSize * i, 0, boxSize * i, 600);
            g.drawLine(0, boxSize * i, 600, boxSize * i);
            g.setStroke(new BasicStroke(1));
        }
    }

    /**
     * Draws the grid for the graph and then, given the array of coordinates, plots
     * the points in the correct place in the graph after setting the color to red
     * so it shows up on the graph better.
     */
    public void drawPoints(String[] coordinates) {
        drawGrid();
        Random rand = new Random();
        int n = rand.nextInt(colors.length);
        for (int j = 0; j < coordinates.length - 1; j++) {
            System.out.println(coordinates[j]);
            if (coordinates[j].equals("NaN") && j > 20 && j < coordinates.length - 20 && !coordinates[j + 5].equals("NaN") && !coordinates[j - 5].equals("NaN")) {
                g.setColor(new Color(0, 148, 170));
                g.drawOval(j, 300 - Double.valueOf(coordinates[j + 5]).intValue() - 3, 10, 10);
                continue;
            }
            else if (Double.valueOf(coordinates[j]) < 0 & Double.valueOf(coordinates[j + 1]) > 0) {
                continue;
            } else if (Double.valueOf(coordinates[j]) > 0 & Double.valueOf(coordinates[j + 1]) < 0) {
                continue;
            }
            String[] hold = definiteDerivative(coordinates);
            double[] tempFirstDer = new double[hold.length];
            for (int i = 0; i < hold.length; i++) {
                tempFirstDer[i] = Double.valueOf(hold[i]);
            }
            for (int i = 0; i < tempFirstDer.length-1; i++) {
                if((tempFirstDer[i] > 0 && tempFirstDer[i+1] < 0)){
                    g.setColor(Color.MAGENTA);
                    g.fillOval(i, 300 - Double.valueOf(coordinates[i]).intValue(), 7, 7);
                    g.setFont(new Font("Serif",1,8));
                    g.drawString("MAX",i+10, 290 - Double.valueOf(coordinates[i]).intValue());
                    g.setColor(colors[n]);
                }
                else if((tempFirstDer[i] < 0 && tempFirstDer[i+1] > 0)){
                    g.setColor(Color.BLUE);
                    g.fillOval(i, 300 - Double.valueOf(coordinates[i]).intValue(), 7, 7);
                    g.setFont(new Font("Serif",1,8));
                    g.drawString("MIN",i+10, 290 - Double.valueOf(coordinates[i]).intValue());
                    g.setColor(colors[n]);
                }
            }
            g.setColor(colors[n]);
            g.setStroke(new BasicStroke(3));
            //double[] tempIndef = indefiniteIntegral();

            String[] holder = secondDerivative(coordinates);
            double[] tempSecondDer = new double[holder.length];
            for (int i = 0; i < holder.length; i++) {
                tempSecondDer[i] = Double.valueOf(holder[i]);
            }
            for (int i = 0; i < tempSecondDer.length-1; i++) {

                if(canPOI && ((Math.abs(tempSecondDer[i] - tempSecondDer[i+1]) > 0.0000001) &&
                        ((tempSecondDer[i] >= 0 && tempSecondDer[i+1] <= 0)||(tempSecondDer[i] <= 0 && tempSecondDer[i+1] >= 0)))){
                    System.out.println("meep");
                    g.setColor(integColor);
                    g.fillOval(i, 300 - Double.valueOf(coordinates[i]).intValue(), 7, 7);
                    g.setFont(new Font("Serif",1,8));
                    g.drawString("P.O.I",i+10, 290 - Double.valueOf(coordinates[i]).intValue());

                    g.setColor(colors[n]);
                }
            }


            if(j > 2 && !coordinates[j].equals("NaN") && !coordinates[j+1].equals("NaN") && !coordinates[j-1].equals("NaN") && !coordinates[j-2].equals("NaN"))
                g.drawLine(j, 300 - Double.valueOf(coordinates[j]).intValue(), j + 1, 300 - Double.valueOf(coordinates[j + 1]).intValue());
        }

    }

    public double slope(double y2, double y1, double x2, double x1) {
        return (double) ((y2 - y1) / (x2 - x1));
    }


    public void clearGraph() {
        g.clearRect(0, 0, 600, 600);
        canPOI = true;
    }



    public double definiteIntegral(int a, int b, int N, String[] coordinates) {
        canPOI = false;

        int h = (b - a) / N;              // step size
        double sum = 0.5 * (Double.parseDouble(coordinates[a + 300]) + Double.parseDouble(coordinates[b + 300]));    // area
        for (int i = 1; i < N; i++) {
            int x = a + h * i;
            sum += Double.parseDouble(coordinates[x + 300]);
        }

        return sum * h;
    }

    public void graph() {
        if (graphDisplayPanel.isShowing()) {
            String[] coordinates = calcControl.update("Graph");
            drawPoints(coordinates);
        }
    }

    public String[] indefiniteIntegral() {
        canPOI = false;
        String[] coordinates = calcControl.update("Graph");
        double[] coordinatesDouble = new double[coordinates.length];
        double[] coordinatesIntegral = new double[coordinates.length];

        if (graphDisplayPanel.isShowing()) {
            for (int i = 0; i < coordinates.length; i++) {
                coordinatesDouble[i] = Double.parseDouble(coordinates[i]);
            }
            for (int negIterator = -300; negIterator < 300; negIterator++) {
                coordinatesIntegral[negIterator + 300] = (definiteIntegral(0, negIterator, 1, coordinates)) / 30;
            }

            String[] coordIntegString = new String[coordinatesIntegral.length - 1];
            for (int i = 0; i < coordIntegString.length; i++) {
                coordIntegString[i] = ("" + coordinatesIntegral[i]);
            }
            return coordIntegString;
        }
        return null;


    }

    public String[] secondDerivative() {
        canPOI = false;
        String[] coordinates = calcControl.update("Graph");
        String[] derivative = definiteDerivative(coordinates);

        double coordinatesDouble[] = new double[derivative.length];

        for (int i = 0; i < derivative.length; i++) {
            coordinatesDouble[i] = Double.parseDouble(derivative[i]);
            System.out.println(coordinatesDouble[i]);
        }
        double[] coordinatesDerivative = new double[coordinates.length - 1];
        for (int i = 0; i < coordinatesDerivative.length-1; i++) {
            coordinatesDerivative[i] =  30.0 * (coordinatesDouble[i + 1] - coordinatesDouble[i]) / (double) (i + 1 - i);
        }


        String[] derCoordinates = new String[coordinatesDerivative.length];
        for (int i = 0; i < derCoordinates.length; i++) {
            derCoordinates[i] = coordinatesDerivative[i] + "";
        }
        return derCoordinates;

    }
    public String[] secondDerivative(String[] coordinates) {
        //canPOI = false;
        String[] derivative = definiteDerivative(coordinates);


        String[] secondDerivative = definiteDerivative(derivative);

        return secondDerivative;


    }

    public String[] indefiniteDerivative () {
        canPOI = false;
            String[] coordinates = calcControl.update("Graph");
            double[] coordinatesDouble = new double[coordinates.length];

            if (graphDisplayPanel.isShowing()) {

                for (int i = 0; i < coordinates.length; i++) {
                    coordinatesDouble[i] = Double.parseDouble(coordinates[i]);
                }
                double[] coordinatesDerivative = new double[coordinates.length - 1];
                for (int i = 0; i < coordinatesDerivative.length; i++) {
                    coordinatesDerivative[i] = Math.round(30 * (coordinatesDouble[i + 1] - coordinatesDouble[i]) / (i + 1 - i));
                }


                String[] derCoordinates = new String[coordinatesDerivative.length];
                for (int i = 0; i < derCoordinates.length; i++) {
                    derCoordinates[i] = coordinatesDerivative[i] + "";
                }
                return derCoordinates;
            }
            return null;
        }
    public String[] definiteDerivative (String[] coordinates) {
        //canPOI = false;
        double[] coordinatesDouble = new double[coordinates.length];

        if (graphDisplayPanel.isShowing()) {

            for (int i = 0; i < coordinates.length; i++) {
                coordinatesDouble[i] = Double.parseDouble(coordinates[i]);
            }
            double[] coordinatesDerivative = new double[coordinates.length - 1];
            for (int i = 0; i < coordinatesDerivative.length; i++) {
                coordinatesDerivative[i] = (30 * (coordinatesDouble[i + 1] - coordinatesDouble[i]) / (i + 1 - i));
            }


            String[] derCoordinates = new String[coordinatesDerivative.length];
            for (int i = 0; i < derCoordinates.length; i++) {
                derCoordinates[i] = coordinatesDerivative[i] + "";
            }
            return derCoordinates;
        }
        return null;
    }


        public void clearAll (String[]newText, String result) {
        canPOI = true;
            newText = calcControl.update(result);
            inputEquation.setText(newText[0]);
            graphEquation.setText(newText[0]);
            equationDisplay.setText("Previous equations: ");
        }

}
