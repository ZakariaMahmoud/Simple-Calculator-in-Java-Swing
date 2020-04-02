package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.text.DecimalFormat;

public class Main extends JFrame {

    JButton scientific, bin, oct, hex, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, comma, plus, equal, minus, divide, multiple, clear, back;

    JTextField textField;
    JLabel oldValueLabel, operandLabel;

    double num;
    double answer;
    double oldAnswer;
    int operation;
    boolean isTextChanged = false;
    boolean noClickedOperator = true;
    boolean isEqualClicked = false;
    DecimalFormat format = new DecimalFormat("0.###############");
    Font font = new Font("Arial", Font.BOLD, 17);

    private void calculateOldAnswer() {
        switch (operation) {
            case 1:
                oldAnswer += num;
                break;
            case 2:
                if (!oldValueLabel.getText().isEmpty())
                    oldAnswer -= num;
                else
                    oldAnswer = num - oldAnswer;
                break;
            case 3:
                oldAnswer *= num;
                break;
            case 4:
                oldAnswer /= num;
                break;
        }
    }


    private void createAndShowGUI() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        scientific = new JButton("Scientific");
        bin = new JButton("BIN");
        oct = new JButton("OCT");
        hex = new JButton("HEX");

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
        equal = new JButton("=");
        clear = new JButton("C");
        back = new JButton("←");
        textField = new JTextField("0");
        oldValueLabel = new JLabel("", JLabel.RIGHT);
        operandLabel = new JLabel("", JLabel.RIGHT);

        oldValueLabel.setBounds(11, 5, 214, 20);
        operandLabel.setBounds(225, 5, 20, 20);
        textField.setBounds(11, 25, 334, 47);
        b7.setBounds(10, 80, 45, 42);
        b8.setBounds(58, 80, 45, 42);
        b9.setBounds(106, 80, 45, 42);
        plus.setBounds(154, 80, 45, 42);
        clear.setBounds(202, 80, 45, 42);
        b4.setBounds(10, 125, 45, 42);
        b5.setBounds(58, 125, 45, 42);
        b6.setBounds(106, 125, 45, 42);
        minus.setBounds(154, 125, 45, 42);
        back.setBounds(202, 125, 45, 42);
        b1.setBounds(10, 170, 45, 42);
        b2.setBounds(58, 170, 45, 42);
        b3.setBounds(106, 170, 45, 42);
        multiple.setBounds(154, 170, 45, 42);
        equal.setBounds(202, 170, 45, 87);
        b0.setBounds(10, 215, 93, 42);

        scientific.setBounds(250, 80, 93, 42);
        bin.setBounds(250, 125, 93, 42);
        oct.setBounds(250, 170, 93, 42);
        hex.setBounds(250, 215, 93, 42);


        comma.setBounds(106, 215, 45, 42);
        divide.setBounds(154, 215, 45, 42);

        b0.setFont(font);
        b1.setFont(font);
        b2.setFont(font);
        b3.setFont(font);
        b4.setFont(font);
        b5.setFont(font);
        b6.setFont(font);
        b7.setFont(font);
        b8.setFont(font);
        b9.setFont(font);
        comma.setFont(font);
        equal.setFont(font);
        plus.setFont(font);
        minus.setFont(font);
        multiple.setFont(font);
        divide.setFont(font);
        clear.setFont(font);
        back.setFont(font);
        oldValueLabel.setFont(font);
        operandLabel.setFont(font);

        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setFont(new Font("Monospaced", Font.BOLD, 19));

        oldValueLabel.setForeground(Color.cyan);
        operandLabel.setForeground(Color.yellow);
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
        clear.setForeground(Color.white);
        back.setForeground(Color.white);

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
        equal.setBackground(Color.black);
        clear.setBackground(new Color(223, 1, 1));
        back.setBackground(new Color(255, 128, 0));
        getContentPane().setBackground(Color.black);



        add(scientific);
        add(bin);
        add(hex);
        add(oct);
        add(b0);
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
        add(b8);
        add(b9);
        add(comma);
        add(equal);
        add(plus);
        add(multiple);
        add(minus);
        add(divide);
        add(clear);
        add(back);
        add(textField);
        add(oldValueLabel);
        add(operandLabel);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == b0) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("0");

                    else
                        textField.setText(textField.getText() + "0");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b1) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("1");

                    else
                        textField.setText(textField.getText() + "1");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b2) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("2");

                    else
                        textField.setText(textField.getText() + "2");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b3) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("3");

                    else
                        textField.setText(textField.getText() + "3");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b4) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("4");

                    else
                        textField.setText(textField.getText() + "4");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b5) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("5");

                    else
                        textField.setText(textField.getText() + "5");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b6) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("6");

                    else
                        textField.setText(textField.getText() + "6");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b7) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("7");

                    else
                        textField.setText(textField.getText() + "7");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b8) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("8");

                    else
                        textField.setText(textField.getText() + "8");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == b9) {
                    if (isEqualClicked || textField.getText().equals("0"))
                        textField.setText("9");

                    else
                        textField.setText(textField.getText() + "9");

                    isEqualClicked = false;
                    isTextChanged = true;
                    noClickedOperator = true;
                } else if (e.getSource() == comma) {
                    if (isEqualClicked || textField.getText().isEmpty())
                        textField.setText("0.");

                    else if (!textField.getText().contains("."))
                        textField.setText(textField.getText() + ".");

                    isEqualClicked = false;
                    isTextChanged = true;
                } else if (e.getSource() == plus && noClickedOperator) {
                    if (textField.getText().equals("0") && operandLabel.getText().equals("÷"))
                        textField.setText("cannot divide by 0");

                    else if (isTextChanged || oldValueLabel.getText().isEmpty()) {
                        try {

                            if (textField.getText().isEmpty())
                                num = 0;

                            else
                                num = Double.parseDouble(textField.getText());

                            if (oldValueLabel.getText().isEmpty()) {
                                operation = 1;
                                oldAnswer = 0;
                            } else
                                oldAnswer = Double.parseDouble(oldValueLabel.getText());

                            calculateOldAnswer();
                            operandLabel.setText("+");
                            oldValueLabel.setText(format.format(oldAnswer));
                            textField.setText("");

                            operation = 1;
                        } catch (Exception ex) {
                            textField.setText("Error");
                        }
                    }
                    isTextChanged = false;
                    noClickedOperator = false;
                } else if (e.getSource() == minus && noClickedOperator) {
                    if (textField.getText().equals("0") && operandLabel.getText().equals("÷"))
                        textField.setText("cannot divide by 0");

                    else if (isTextChanged || oldValueLabel.getText().isEmpty()) {
                        try {

                            if (textField.getText().isEmpty())
                                num = 0;

                            else
                                num = Double.parseDouble(textField.getText());

                            if (oldValueLabel.getText().isEmpty()) {
                                operation = 2;
                                oldAnswer = 0;
                            } else
                                oldAnswer = Double.parseDouble(oldValueLabel.getText());

                            calculateOldAnswer();
                            operandLabel.setText("-");
                            oldValueLabel.setText(format.format(oldAnswer));
                            textField.setText("");

                            operation = 2;
                        } catch (Exception ex) {
                            textField.setText("Error");
                        }
                    }
                    isTextChanged = false;
                    noClickedOperator = false;
                } else if (e.getSource() == multiple && noClickedOperator) {
                    if (textField.getText().equals("0") && operandLabel.getText().equals("÷"))
                        textField.setText("cannot divide by 0");

                    else if (isTextChanged || oldValueLabel.getText().isEmpty()) {
                        try {

                            if (textField.getText().isEmpty())
                                num = 1;

                            else
                                num = Double.parseDouble(textField.getText());

                            if (oldValueLabel.getText().isEmpty()) {
                                operation = 3;
                                oldAnswer = 1;
                            } else
                                oldAnswer = Double.parseDouble(oldValueLabel.getText());

                            calculateOldAnswer();
                            operandLabel.setText("×");
                            oldValueLabel.setText(format.format(oldAnswer));
                            textField.setText("");

                            operation = 3;
                        } catch (Exception ex) {
                            textField.setText("Error");
                        }
                    }
                    isTextChanged = false;
                    noClickedOperator = false;
                } else if (e.getSource() == divide && noClickedOperator) {
                    if (textField.getText().equals("0") && operandLabel.getText().equals("÷"))
                        textField.setText("cannot divide by 0");

                    else if (isTextChanged || oldValueLabel.getText().isEmpty()) {
                        try {

                            if (textField.getText().isEmpty())
                                num = 1;

                            else
                                num = Double.parseDouble(textField.getText());

                            if (oldValueLabel.getText().isEmpty())
                                oldAnswer = num;

                            else {
                                oldAnswer = Double.parseDouble(oldValueLabel.getText());
                                calculateOldAnswer();
                            }

                            operandLabel.setText("÷");
                            oldValueLabel.setText(format.format(oldAnswer));
                            textField.setText("");

                            operation = 4;
                        } catch (Exception ex) {
                            textField.setText("Error");
                        }
                    }
                    isTextChanged = false;
                    noClickedOperator = false;
                } else if (e.getSource() == equal) {
                    if (!textField.getText().isEmpty() && !oldValueLabel.getText().isEmpty() && !isEqualClicked) {

                        double a = Double.parseDouble(oldValueLabel.getText());
                        double b = Double.parseDouble(textField.getText());
                        char operand = operandLabel.getText().charAt(0);

                        switch (operand) {
                            case '+':
                                answer = a + b;
                                textField.setText(format.format(answer));
                                break;

                            case '-':
                                answer = a - b;
                                textField.setText(format.format(answer));
                                break;

                            case '×':
                                answer = a * b;
                                textField.setText(format.format(answer));
                                break;

                            case '÷':
                                if (b == 0)
                                    textField.setText("cannot divide by 0");
                                else {
                                    answer = a / b;
                                    textField.setText(format.format(answer));
                                }
                                break;
                        }
                    } else if (textField.getText().isEmpty() && !oldValueLabel.getText().isEmpty())
                        textField.setText(oldValueLabel.getText());

                    oldValueLabel.setText("");
                    operandLabel.setText("");

                    isEqualClicked = true;
                    noClickedOperator = true;
                } else if (e.getSource() == back) {
                    if (textField.getText().equals("Error") || textField.getText().equals("cannot divide by 0"))
                        textField.setText("");
                    else if (!textField.getText().isEmpty())
                        textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                } else if (e.getSource() == clear) {
                    System.out.println("Clear");
                    oldValueLabel.setText("");
                    textField.setText("");
                    operandLabel.setText("");
                    oldAnswer = 0;
                    num = 0;
                    noClickedOperator = true;
                } else if (e.getSource() ==scientific ) {
                    setVisible(false);

                    Scientific calc= new Scientific();
                    calc.setVisible(true);

                } else if (e.getSource() == bin) {
                    if (!textField.getText().equals("")) {
                        int value = Integer.parseInt(textField.getText());
                        String txt = Integer.toBinaryString(value);
                        textField.setText(txt);
                    }
                } else if (e.getSource() == oct) {
                    if (!textField.getText().equals("")) {
                        int value = Integer.parseInt(textField.getText());
                        String txt = Integer.toOctalString(value);
                        textField.setText(txt);
                    }

                }
                else if (e.getSource() == hex) {
                    if (!textField.getText().equals("")) {
                        int value = Integer.parseInt(textField.getText());
                        String txt = Integer.toHexString(value);
                        textField.setText(txt);
                    }
                }

            }
        };


        b0.addActionListener(actionListener);
        b1.addActionListener(actionListener);
        b2.addActionListener(actionListener);
        b3.addActionListener(actionListener);
        b4.addActionListener(actionListener);
        b5.addActionListener(actionListener);
        b6.addActionListener(actionListener);
        b7.addActionListener(actionListener);
        b8.addActionListener(actionListener);
        b9.addActionListener(actionListener);
        scientific.addActionListener(actionListener);
        bin.addActionListener(actionListener);
        oct.addActionListener(actionListener);
        hex.addActionListener(actionListener);
        comma.addActionListener(actionListener);
        plus.addActionListener(actionListener);
        multiple.addActionListener(actionListener);
        divide.addActionListener(actionListener);
        minus.addActionListener(actionListener);
        equal.addActionListener(actionListener);
        clear.addActionListener(actionListener);
        back.addActionListener(actionListener);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Simple Calculator");
        setSize(352, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

    }

    public Main() {
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main =new Main();

                main.setVisible(true);


            }
        });
    }

}