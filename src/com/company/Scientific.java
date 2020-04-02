package com.company;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.text.DecimalFormat;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Scientific extends JFrame implements ActionListener, KeyListener {

    JMenuBar menuBar;
    JMenu view, edit, help;
    JCheckBoxMenuItem history,simpleCalc;
    JMenuItem copy, paste, copyHistory, clearHistory, keyboardShortcuts, about;

    JPanel panel;
    JTextArea historyText;
    JScrollPane historyScroller;

    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, comma, plus, minus,
            divide, multiple, cos, sin, tan, sqrt, clear, back, equal,
            parentesesLeft, parentesesRight, power, modulo, exponential, pi;

    JTextField textField;

    DecimalFormat format = new DecimalFormat("0.###############");

    Font font1 = new Font("Arial", Font.BOLD, 17);
    Font font2 = new Font("Arial", Font.BOLD, 9);
    Font font3 = new Font("Arial", Font.BOLD, 12);


    private void autoAddOrRemove(String button) {
        if (!textField.getText().isEmpty()) {
            Character lastCharacter = textField.getText().charAt(textField.getText().length() - 1);

            switch (button) {

                case "symbol":
                    switch (lastCharacter) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case 'e':
                        case 'π':
                            textField.setText(textField.getText() + "×");
                            break;
                        case '.':
                            textField.setText(textField.getText() + "0×");
                            break;
                    }
                    break;

                case "number":
                    switch (lastCharacter) {
                        case 'e':
                        case 'π':
                            textField.setText(textField.getText() + "×");
                            break;
                        case '0':
                            String str = textField.getText();
                            if(
                                    str.equals("0") ||
                                            str.endsWith("+0") ||
                                            str.endsWith("-0") ||
                                            str.endsWith("×0") ||
                                            str.endsWith("÷0") ||
                                            str.endsWith("%0") ||
                                            str.endsWith("^0") ||
                                            str.endsWith("√0") ||
                                            str.endsWith("(0") ||
                                            str.endsWith("cos0") ||
                                            str.endsWith("sin0") ||
                                            str.endsWith("tan0")
                            )
                                textField.setText(textField.getText().substring(0, textField.getText().length()-1));
                            break;
                    }
                    break;

                case "operand":
                    switch (lastCharacter) {
                        case '+':
                        case '-':
                        case '×':
                        case '÷':
                        case '%':
                        case '.':
                            textField.setText(textField.getText().substring(0, textField.getText().length()-1));
                            break;
                    }
                    break;

                case "point":
                    switch(lastCharacter) {
                        case '+':
                        case '-':
                        case '×':
                        case '÷':
                        case '%':
                        case '(':
                        case '√':
                        case 'π':
                        case 's':
                        case 'n':
                        case '^':
                            textField.setText(textField.getText() + "0");
                            break;
                        case ')':
                            textField.setText(textField.getText() + "×0");
                            break;
                        case '.':
                            textField.setText(textField.getText().substring(0, textField.getText().length()-1));
                            break;
                    }
                    break;
            }

        }
    }


    private double calculate(String str) {

        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) {
                        x += parseTerm();
                    }
                    else if (eat('-')) {
                        x -= parseTerm();
                    }
                    else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('×')) {
                        x *= parseFactor();
                    }
                    else if (eat('÷')) {
                        x /= parseFactor();
                    }
                    else if (eat('%')) {
                        x %= parseFactor();
                    }
                    else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                if (eat('+')) {
                    return parseFactor();
                }
                if (eat('-')) {
                    return -parseFactor();
                }
                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                }
                else if (eat('e')) {
                    x = Math.E;
                }
                else if (eat('π')) {
                    x = Math.PI;
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
                else if (ch >= 'a' && ch <= 'z' || ch == '√') {
                    while (ch >= 'a' && ch <= 'z' || ch == '√') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "√":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                }
                else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                if (eat('^')) {
                    x = Math.pow(x, parseFactor());
                }
                return x;

            }

        }.parse();
    }

    private void b0_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "0");
    }

    private void b1_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "1");
    }

    private void b2_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "2");
    }

    private void b3_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "3");
    }

    private void b4_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "4");
    }

    private void b5_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "5");
    }

    private void b6_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "6");
    }

    private void b7_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "7");
    }

    private void b8_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "8");
    }

    private void b9_isClicked(){
        autoAddOrRemove("number");
        textField.setText(textField.getText() + "9");
    }

    private void sin_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "sin");
    }

    private void cos_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "cos");
    }

    private void tan_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "tan");
    }

    private void sqrt_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "√");
    }

    private void exponential_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "e");
    }

    private void pi_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "π");
    }

    private void plus_isClicked(){
        autoAddOrRemove("operand");
        textField.setText(textField.getText() + "+");
    }

    private void minus_isClicked(){
        autoAddOrRemove("operand");
        textField.setText(textField.getText() + "-");
    }

    private void multiple_isClicked(){
        if (!textField.getText().isEmpty()) {
            autoAddOrRemove("operand");
            textField.setText(textField.getText() + "×");
        }
    }

    private void divide_isClicked(){
        if (!textField.getText().isEmpty()) {
            autoAddOrRemove("operand");
            textField.setText(textField.getText() + "÷");
        }
    }

    private void modulo_isClicked(){
        if (!textField.getText().isEmpty()) {
            autoAddOrRemove("operand");
            textField.setText(textField.getText() + "%");
        }
    }

    private void power_isClicked(){
        if(textField.getText().matches(".*[0-9eπ)]$")) {
            textField.setText(textField.getText() + "^");
        }
    }

    private void parentesesRight_isClicked(){
        if(textField.getText().matches(".*[^ns√(]$")) {
            int LparentesesCounter = 0, RparentesesCounter = 0;
            for(char c: textField.getText().toCharArray()) {
                if( c == '(')
                    LparentesesCounter++;
                else if ( c == ')')
                    RparentesesCounter++;
            }
            if(LparentesesCounter > RparentesesCounter)
                textField.setText(textField.getText() + ")");

        }
    }

    private void parentesesLeft_isClicked(){
        autoAddOrRemove("symbol");
        textField.setText(textField.getText() + "(");
    }

    private void comma_isClicked(){
        String str = textField.getText();
        if (textField.getText().isEmpty()) {
            textField.setText("0.");
        }
        else {
            int lastPointIndex = str.lastIndexOf(".");
            int lastPlusIndex = str.lastIndexOf("+");
            int lastMinusIndex = str.lastIndexOf("-");
            int lastMultipleIndex = str.lastIndexOf("×");
            int lastDivideIndex = str.lastIndexOf("÷");
            int lastModuloIndex = str.lastIndexOf("%");

            if(lastPointIndex <= lastPlusIndex ||
                    lastPointIndex <= lastMinusIndex ||
                    lastPointIndex <= lastMultipleIndex ||
                    lastPointIndex <= lastDivideIndex ||
                    lastPointIndex <= lastModuloIndex ) {
                autoAddOrRemove("point");
                textField.setText(textField.getText() + ".");
            }
        }
    }

    private void equal_isClicked(){
        if (!textField.getText().isEmpty()) {
            String historyNewText = historyText.getText()+textField.getText()+"\n= ";

            try {
                Double answer = calculate(textField.getText());
                if (answer.isInfinite()) {
                    textField.setText("cannot divide by 0");
                    historyNewText += "cannot divide by 0";
                }
                else if (answer.isNaN()) {
                    textField.setText("Error");
                    historyNewText += "Error";
                }
                else {
                    textField.setText(format.format(answer));
                    historyNewText += format.format(answer);
                }
            }
            catch (Exception ex) {
                textField.setText("Error");
                historyNewText += "Error";
            }
            historyText.setText(historyNewText+"\n\n");
        }
    }

    private void back_isClicked(){

        String temp = textField.getText();

        if (temp.equals("Error") || temp.equals("cannot divide by 0")) {
            textField.setText("");
        }
        else if (!temp.isEmpty()) {
            temp = textField.getText().substring(0, textField.getText().length() - 1);
            if (temp.length() >= 2) {
                switch (temp.substring(temp.length() - 2)) {
                    case "co":
                    case "si":
                    case "ta":
                        temp = temp.substring(0, temp.length() - 2);
                        break;
                }
            }
            textField.setText(temp);
        }
    }

    private void clear_isClicked(){
        textField.setText("");
    }

    private void createAndShowGUI() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        menuBar = new JMenuBar();
        view = new JMenu(" View ");
        edit = new JMenu(" Edit ");
        help = new JMenu(" Help ");
        history = new JCheckBoxMenuItem("History");
        simpleCalc = new JCheckBoxMenuItem("Simple Calculator");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        copyHistory = new JMenuItem("Copy History");
        clearHistory = new JMenuItem("Clear History");
        keyboardShortcuts = new JMenuItem("Keyboard Shortcuts");
        about = new JMenuItem("About");

        menuBar.add(view);
        menuBar.add(edit);
        menuBar.add(help);
        view.add(history);
        view.add(simpleCalc);
        edit.add(copy);
        edit.add(paste);
        edit.addSeparator();
        edit.add(copyHistory);
        edit.add(clearHistory);
        help.add(keyboardShortcuts);
        help.add(about);

        panel = new JPanel(null);
        historyText = new JTextArea();
        historyScroller = new JScrollPane(historyText);

        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        comma = new JButton(".");
        plus = new JButton("+");
        minus = new JButton("-");
        multiple = new JButton("×");
        divide = new JButton("÷");
        cos = new JButton("cos");
        sin = new JButton("sin");
        tan = new JButton("tan");
        sqrt = new JButton("√");
        power = new JButton("^");
        modulo = new JButton("%");
        exponential = new JButton("e");
        pi = new JButton("π");
        parentesesLeft = new JButton("(");
        parentesesRight = new JButton(")");
        equal = new JButton("=");
        clear = new JButton("C");
        back = new JButton("←");
        textField = new JTextField("");

        panel.setBounds(0, 0, 256, 358);
        historyScroller.setBounds(256, 0, 258, 328);

        textField.setBounds(11, 11, 234, 60);

        cos.setBounds(10, 80, 45, 38);
        sin.setBounds(58, 80, 45, 38);
        tan.setBounds(106, 80, 45, 38);
        back.setBounds(154, 80, 45, 38);
        clear.setBounds(202, 80, 45, 38);

        pi.setBounds(10, 121, 45, 38);
        exponential.setBounds(58, 121, 45, 38);
        modulo.setBounds(106, 121, 45, 38);
        parentesesLeft.setBounds(154, 121, 45, 38);
        parentesesRight.setBounds(202, 121, 45, 38);

        b7.setBounds(10, 161, 45, 38);
        b8.setBounds(58, 161, 45, 38);
        b9.setBounds(106, 161, 45, 38);
        plus.setBounds(154, 161, 45, 38);
        power.setBounds(202, 161, 45, 38);

        b4.setBounds(10, 201, 45, 38);
        b5.setBounds(58, 201, 45, 38);
        b6.setBounds(106, 201, 45, 38);
        minus.setBounds(154, 201, 45, 38);
        sqrt.setBounds(202, 201, 45, 38);

        b1.setBounds(10, 241, 45, 38);
        b2.setBounds(58, 241, 45, 38);
        b3.setBounds(106, 241, 45, 38);
        multiple.setBounds(154, 241, 45, 38);
        equal.setBounds(202, 241, 45, 78);

        b0.setBounds(10, 281, 93, 38);
        comma.setBounds(106, 281, 45, 38);
        divide.setBounds(154, 281, 45, 38);

        view.setFont(font3);
        edit.setFont(font3);
        help.setFont(font3);
        history.setFont(font3);
        simpleCalc.setFont(font3);
        copy.setFont(font3);
        paste.setFont(font3);
        copyHistory.setFont(font3);
        clearHistory.setFont(font3);
        about.setFont(font3);
        keyboardShortcuts.setFont(font3);

        b0.setFont(font1);
        b1.setFont(font1);
        b2.setFont(font1);
        b3.setFont(font1);
        b4.setFont(font1);
        b5.setFont(font1);
        b6.setFont(font1);
        b7.setFont(font1);
        b8.setFont(font1);
        b9.setFont(font1);
        comma.setFont(font1);
        equal.setFont(font1);
        plus.setFont(font1);
        minus.setFont(font1);
        multiple.setFont(font1);
        divide.setFont(font1);
        cos.setFont(font2);
        sin.setFont(font2);
        tan.setFont(font2);
        sqrt.setFont(font1);
        power.setFont(font1);
        modulo.setFont(font1);
        exponential.setFont(font1);
        pi.setFont(font1);
        parentesesLeft.setFont(font1);
        parentesesRight.setFont(font1);
        clear.setFont(font1);
        back.setFont(font1);

        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("Monospaced", Font.BOLD, 19));
        historyText.setFont(new Font("Arial", Font.BOLD, 16));

        b0.setForeground(Color.white);
        b1.setForeground(Color.white);
        b2.setForeground(Color.white);
        b3.setForeground(Color.white);
        b4.setForeground(Color.white);
        b5.setForeground(Color.white);
        b6.setForeground(Color.white);
        b7.setForeground(Color.white);
        b8.setForeground(Color.white);
        b9.setForeground(Color.white);
        comma.setForeground(Color.white);
        equal.setForeground(Color.white);
        plus.setForeground(Color.white);
        minus.setForeground(Color.white);
        multiple.setForeground(Color.white);
        divide.setForeground(Color.white);
        cos.setForeground(Color.white);
        sin.setForeground(Color.white);
        tan.setForeground(Color.white);
        sqrt.setForeground(Color.white);
        power.setForeground(Color.white);
        modulo.setForeground(Color.white);
        exponential.setForeground(Color.white);
        pi.setForeground(Color.white);
        parentesesLeft.setForeground(Color.white);
        parentesesRight.setForeground(Color.white);
        clear.setForeground(Color.white);
        back.setForeground(Color.white);

        textField.setBackground(Color.lightGray);
        b0.setBackground(Color.darkGray);
        b1.setBackground(Color.darkGray);
        b2.setBackground(Color.darkGray);
        b3.setBackground(Color.darkGray);
        b4.setBackground(Color.darkGray);
        b5.setBackground(Color.darkGray);
        b6.setBackground(Color.darkGray);
        b7.setBackground(Color.darkGray);
        b8.setBackground(Color.darkGray);
        b9.setBackground(Color.darkGray);
        comma.setBackground(Color.darkGray);
        plus.setBackground(Color.black);
        minus.setBackground(Color.black);
        multiple.setBackground(Color.black);
        divide.setBackground(Color.black);
        cos.setBackground(Color.black);
        sin.setBackground(Color.black);
        tan.setBackground(Color.black);
        sqrt.setBackground(Color.black);
        power.setBackground(Color.black);
        modulo.setBackground(Color.black);
        exponential.setBackground(Color.black);
        pi.setBackground(Color.black);
        parentesesLeft.setBackground(Color.black);
        parentesesRight.setBackground(Color.black);
        equal.setBackground(Color.black);
        clear.setBackground(new Color(223, 1, 1));
        back.setBackground(new Color(255, 128, 0));
        panel.setBackground(Color.black);
        historyText.setBackground(Color.lightGray);
        getContentPane().setBackground(Color.black);

        textField.setFocusable(false);
        historyText.setFocusable(false);
        b0.setFocusable(false);
        b1.setFocusable(false);
        b2.setFocusable(false);
        b3.setFocusable(false);
        b4.setFocusable(false);
        b5.setFocusable(false);
        b6.setFocusable(false);
        b7.setFocusable(false);
        b8.setFocusable(false);
        b9.setFocusable(false);
        comma.setFocusable(false);
        plus.setFocusable(false);
        minus.setFocusable(false);
        multiple.setFocusable(false);
        divide.setFocusable(false);
        cos.setFocusable(false);
        sin.setFocusable(false);
        tan.setFocusable(false);
        sqrt.setFocusable(false);
        power.setFocusable(false);
        modulo.setFocusable(false);
        exponential.setFocusable(false);
        pi.setFocusable(false);
        parentesesLeft.setFocusable(false);
        parentesesRight.setFocusable(false);
        equal.setFocusable(false);
        clear.setFocusable(false);
        back.setFocusable(false);

        panel.add(b0);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(comma);
        panel.add(equal);
        panel.add(plus);
        panel.add(multiple);
        panel.add(minus);
        panel.add(divide);
        panel.add(cos);
        panel.add(sin);
        panel.add(tan);
        panel.add(sqrt);
        panel.add(power);
        panel.add(modulo);
        panel.add(exponential);
        panel.add(pi);
        panel.add(parentesesRight);
        panel.add(parentesesLeft);
        panel.add(clear);
        panel.add(back);
        panel.add(textField);

        history.addActionListener(this);
        simpleCalc.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        copyHistory.addActionListener(this);
        clearHistory.addActionListener(this);
        keyboardShortcuts.addActionListener(this);
        about.addActionListener(this);
        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        comma.addActionListener(this);
        cos.addActionListener(this);
        sin.addActionListener(this);
        tan.addActionListener(this);
        sqrt.addActionListener(this);
        power.addActionListener(this);
        modulo.addActionListener(this);
        exponential.addActionListener(this);
        pi.addActionListener(this);
        parentesesLeft.addActionListener(this);
        parentesesRight.addActionListener(this);
        plus.addActionListener(this);
        multiple.addActionListener(this);
        divide.addActionListener(this);
        minus.addActionListener(this);
        equal.addActionListener(this);
        clear.addActionListener(this);
        back.addActionListener(this);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        add(panel);
        add(historyScroller);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Scientific Calculator");
        setSize(262, 380);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b0)
            b0_isClicked();

        else if (e.getSource() == b1)
            b1_isClicked();

        else if (e.getSource() == b2)
            b2_isClicked();

        else if (e.getSource() == b3)
            b3_isClicked();

        else if (e.getSource() == b4)
            b4_isClicked();

        else if (e.getSource() == b5)
            b5_isClicked();

        else if (e.getSource() == b6)
            b6_isClicked();

        else if (e.getSource() == b7)
            b7_isClicked();

        else if (e.getSource() == b8)
            b8_isClicked();

        else if (e.getSource() == b9)
            b9_isClicked();

        else if (e.getSource() == sin)
            sin_isClicked();

        else if (e.getSource() == cos)
            cos_isClicked();

        else if (e.getSource() == tan)
            tan_isClicked();

        else if (e.getSource() == sqrt)
            sqrt_isClicked();

        else if (e.getSource() == exponential)
            exponential_isClicked();

        else if (e.getSource() == pi)
            pi_isClicked();

        else if (e.getSource() == plus)
            plus_isClicked();

        else if (e.getSource() == minus)
            minus_isClicked();

        else if (e.getSource() == multiple)
            multiple_isClicked();

        else if (e.getSource() == divide)
            divide_isClicked();

        else if (e.getSource() == modulo)
            modulo_isClicked();

        else if (e.getSource() == power)
            power_isClicked();

        else if (e.getSource() == parentesesRight)
            parentesesRight_isClicked();

        else if (e.getSource() == parentesesLeft)
            parentesesLeft_isClicked();

        else if (e.getSource() == comma)
            comma_isClicked();

        else if (e.getSource() == equal)
            equal_isClicked();

        else if (e.getSource() == back)
            back_isClicked();

        else if (e.getSource() == clear)
            clear_isClicked();

        else if (e.getSource() == history)
        {
            if(history.isSelected())
                this.setSize(520, 380);
            else
                this.setSize(262, 380);
        }

        else if (e.getSource() == simpleCalc)
        {
            if(simpleCalc.isSelected()){
                Main main =new Main();
                main.setVisible(true);
                setVisible(false);

            }

        }

        else if (e.getSource() == copy )
        {
            textField.selectAll();
            textField.copy();
            textField.setCaretPosition(textField.getText().length());
        }

        else if (e.getSource() == paste )
        {
            textField.paste();
        }


        else if (e.getSource() == copyHistory )
        {
            historyText.selectAll();
            historyText.copy();
            textField.setCaretPosition(textField.getText().length());
        }

        else if (e.getSource() == clearHistory )
        {
            historyText.setText("");
        }

        else if (e.getSource() == keyboardShortcuts)
        {
            String str
                    = "<html>"
                    + "<ul>"
                    + "<li>Press <b>v</b> to add <b>√</b>.</li>"
                    + "<li>Press <b>p</b> to add <b>π</b>.</li>"
                    + "<li>Press <b>c</b> to add <b>cos</b>.</li>"
                    + "<li>Press <b>s</b> to add <b>sin</b>.</li>"
                    + "<li>Press <b>t</b> to add <b>tan</b>.</li>"
                    + "<li>Press <b>=</b> or <b>Enter</b> to get the result</li>"
                    + "<li>Press <b>BackSpace</b> to clear last character entered.</li>"
                    + "<li>Press <b>Delete</b> to clear all characters entered.</li>"
                    + "</ul>"
                    + "<html>";

            JOptionPane.showMessageDialog(getContentPane(), str, "Keyboard Shortcuts", JOptionPane.PLAIN_MESSAGE);
        }

        else if (e.getSource() == about)
        {
            String str
                    = "<html>"
                    + "<big>Scientific Calculator</big><br><br>"
                    + "<p>Prepared by a <b>Mahmoud Zakaria</b><br><br>"
                    + "If you have any comments, ideas.. just let know<br><br>"
                    + "email: &nbsp zakaria.forwork@gmail.com<br>"
                    + "Github: &nbsp <a href='https://github.com/ZakariaMahmoud'> https://github.com/ZakariaMahmoud</a><br><br>"
                    + "<html>";

            JOptionPane.showMessageDialog(getContentPane(), str, "About", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()) {
            case '0':
                b0_isClicked();
                break;
            case '1':
                b1_isClicked();
                break;
            case '2':
                b2_isClicked();
                break;
            case '3':
                b3_isClicked();
                break;
            case '4':
                b4_isClicked();
                break;
            case '5':
                b5_isClicked();
                break;
            case '6':
                b6_isClicked();
                break;
            case '7':
                b7_isClicked();
                break;
            case '8':
                b8_isClicked();
                break;
            case '9':
                b9_isClicked();
                break;
            case 's':
                sin_isClicked();
                break;
            case 'c':
                cos_isClicked();
                break;
            case 't':
                tan_isClicked();
                break;
            case 'v':
                sqrt_isClicked();
                break;
            case 'e':
                exponential_isClicked();
                break;
            case 'p':
                pi_isClicked();
                break;
            case '+':
                plus_isClicked();
                break;
            case '-':
                minus_isClicked();
                break;
            case '*':
            case '×':
                multiple_isClicked();
                break;
            case '/':
            case '÷':
                divide_isClicked();
                break;
            case '%':
                modulo_isClicked();
                break;
            case '^':
                power_isClicked();
                break;
            case ')':
                parentesesRight_isClicked();
                break;
            case '(':
                parentesesLeft_isClicked();
                break;
            case '.':
                comma_isClicked();
                break;
            case '=':
            case '\n':
                equal_isClicked();
                break;
        }

        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE )
            back_isClicked();

        if(e.getKeyCode() == KeyEvent.VK_DELETE )
            clear_isClicked();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public Scientific() {
        createAndShowGUI();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

}