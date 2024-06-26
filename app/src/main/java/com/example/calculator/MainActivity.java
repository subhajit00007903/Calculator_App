package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;
import com.google.android.material.button.MaterialButton;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView sol, resultTv;
    MaterialButton buttonc, buttonBrackOpen, ButtonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonadd, buttonsub, buttoneq;
    MaterialButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven,
            buttonEight, buttonNine, buttonZero;
    MaterialButton buttonAc, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sol = findViewById(R.id.solution_textview);
        resultTv = findViewById(R.id.result_tv);


        buttonc = findViewById(R.id.btn_c);
        buttonBrackOpen = findViewById(R.id.btn_ob);
        ButtonBrackClose = findViewById(R.id.btn_cb);
        buttonDivide = findViewById(R.id.btn_div);
        buttonMultiply = findViewById(R.id.btn_mul);
        buttonadd = findViewById(R.id.btn_plus);
        buttonsub = findViewById(R.id.btn_sub);
        buttoneq = findViewById(R.id.btn_eq);
        buttonOne = findViewById(R.id.btn_1);
        buttonTwo = findViewById(R.id.btn_2);
        buttonThree = findViewById(R.id.btn_3);
        buttonFour = findViewById(R.id.btn_4);
        buttonFive = findViewById(R.id.btn_5);
        buttonSix = findViewById(R.id.btn_6);
        buttonSeven = findViewById(R.id.btn_7);
        buttonEight = findViewById(R.id.btn_8);
        buttonNine = findViewById(R.id.btn_9);
        buttonZero = findViewById(R.id.btn_0);
        buttonAc = findViewById(R.id.btn_AC);
        buttonDot = findViewById(R.id.btn_dot);


        buttonc.setOnClickListener(this);
        buttonBrackOpen.setOnClickListener(this);
        ButtonBrackClose.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonadd.setOnClickListener(this);
        buttonsub.setOnClickListener(this);
        buttoneq.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        buttonZero.setOnClickListener(this);
        buttonAc.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton btn = (MaterialButton) v;
        String buttonText = btn.getText().toString();
        String dataTocal = sol.getText().toString();

        switch (buttonText) {
            case "AC":
                sol.setText("");
                resultTv.setText("0");
                break;
            case "C":
                if (!dataTocal.isEmpty()) {
                    dataTocal = dataTocal.substring(0, dataTocal.length() - 1);
                    sol.setText(dataTocal);
                }
                break;
            case "=":
                String result = calculateResult(dataTocal);
                resultTv.setText(result);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "(":
            case ")":
                dataTocal += buttonText;
                sol.setText(dataTocal);
                break;
            default:
                dataTocal += buttonText;
                sol.setText(dataTocal);
                break;
        }
    }

    private String calculateResult(String expression) {
        try {
            return String.valueOf(evaluate(expression));
        } catch (Exception e) {
            return "Error";
        }
    }

    private double evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            if (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.') {
                StringBuilder sbuf = new StringBuilder();

                while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.'))
                    sbuf.append(tokens[i++]);
                values.push(Double.parseDouble(sbuf.toString()));
                i--;
            } else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
