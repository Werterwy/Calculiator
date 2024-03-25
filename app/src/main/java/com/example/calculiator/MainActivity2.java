package com.example.calculiator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private EditText editText;
    private TextView text_result;

    private Calculator calculator;

    // Переменные для хранения операции и первого операнда

    String operator = "";

    private int clickCount = 0;

    private static final int MAX_CLICKS = 7;

    double num1 = 0, num2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = findViewById(R.id.edit_input);
        text_result = findViewById(R.id.text_result);

        // Находим все кнопки калькулятора
        Button button0 = findViewById(R.id.button_0);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDivide = findViewById(R.id.button_divide);
        Button buttonEquals = findViewById(R.id.button_equals);
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonSin = findViewById(R.id.button_sin);
        Button buttonCos = findViewById(R.id.button_cos);
        Button buttonTan = findViewById(R.id.button_tan);
        Button buttonSqrt = findViewById(R.id.button_sqrt);
        Button buttonCot = findViewById(R.id.button_cot);
        Button buttonLn = findViewById(R.id.button_ln);
        Button buttonLog = findViewById(R.id.button_log);
        Button buttonPower = findViewById(R.id.button_power);
        Button buttonAbs = findViewById(R.id.button_abs);
        Button buttonFactorial = findViewById(R.id.button_factorial);
        Button buttonPi = findViewById(R.id.button_pi);
        Button buttonE = findViewById(R.id.button_e);
        Button buttonBrackets = findViewById(R.id.button_brackets);
        Button buttonBracketsClose = findViewById(R.id.button_bracketsClose);
        Button buttonPoint = findViewById(R.id.button_point);
        Button buttonExp = findViewById(R.id.button_EXP);

        // Устанавливаем обработчик событий для каждой кнопки
        button0.setOnClickListener(new CalculatorButtonClickListener("0"));
        button1.setOnClickListener(new CalculatorButtonClickListener("1"));
        button2.setOnClickListener(new CalculatorButtonClickListener("2"));
        button3.setOnClickListener(new CalculatorButtonClickListener("3"));
        button4.setOnClickListener(new CalculatorButtonClickListener("4"));
        button5.setOnClickListener(new CalculatorButtonClickListener("5"));
        button6.setOnClickListener(new CalculatorButtonClickListener("6"));
        button7.setOnClickListener(new CalculatorButtonClickListener("7"));
        button8.setOnClickListener(new CalculatorButtonClickListener("8"));
        button9.setOnClickListener(new CalculatorButtonClickListener("9"));
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                performOperation("+");
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                performOperation("-");
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                performOperation("*");
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                performOperation("/");
            }
        });
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                calculate();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator = "";
                text_result.setText("");
                clearInput();
            }
        });
        buttonSin.setOnClickListener(new CalculatorFunctionClickListener("sin"));
        buttonCos.setOnClickListener(new CalculatorFunctionClickListener("cos"));
        buttonTan.setOnClickListener(new CalculatorFunctionClickListener("tan"));
        buttonSqrt.setOnClickListener(new CalculatorFunctionClickListener("sqrt"));
        buttonCot.setOnClickListener(new CalculatorFunctionClickListener("cot"));
        buttonLn.setOnClickListener(new CalculatorFunctionClickListener("ln"));
        buttonLog.setOnClickListener(new CalculatorFunctionClickListener("log"));
        buttonPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = 0;
                performOperation("^");
            }
        });
        buttonAbs.setOnClickListener(new CalculatorFunctionClickListener("abs"));
        buttonFactorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double angle = Double.parseDouble(editText.getText().toString());
                factorial(angle);
            }
        });
        buttonPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcConst("π");
            }
        });
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcConst("e");
            }
        });
        buttonBrackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInput("(");
            }
        });
        buttonBracketsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInput(")");
            }
        });
        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToInput(".");
            }
        });
        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcConst("EXP");
            }
        });
    }

    private void appendToInput(String text) {
        if (clickCount >= MAX_CLICKS) {
            Toast.makeText(this, "Вы превысили ограничение на количество нажатий", Toast.LENGTH_SHORT).show();
            return;
        }
        if("+" != text && "-" != text && "*" != text && "/" != text && "^" != text && "(" != text && ")" != text) {
            editText.getText().append(text);
        }
        String input = text_result.getText().toString();
        text_result.setText(input + text);
        clickCount++;
    }
    private void funcConst(String con){
        String input = text_result.getText().toString();
        text_result.setText(input + con);
        con = con.replaceAll("EXP", String.valueOf(EXP))
                .replaceAll("e", String.valueOf(E))
                .replaceAll("π", String.valueOf(PI));
        editText.getText().append(con);

    }
    private void calculate() {
        clickCount = 0;
        String currentText = editText.getText().toString();
        num2 = Double.parseDouble(currentText);
        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    editText.setText("Ошибка: деление на ноль");
                    return;
                }
                break;
            case "^":
                result = Math.pow(num1, num2);
                break;
        }
        String res = String.valueOf(result);
        editText.setText(res);
    }

    private void clearInput() {
        clickCount = 0;
        editText.setText("");
    }

    private class CalculatorButtonClickListener implements View.OnClickListener {
        private String buttonText;

        public CalculatorButtonClickListener(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public void onClick(View v) {
            appendToInput(buttonText);
        }
    }

    private void performOperation(String op) {
        clickCount = 0;
        operator = op;
        String currentText = editText.getText().toString();
        num1 = Double.parseDouble(currentText);
        clearInput();
        String input = text_result.getText().toString();
        text_result.setText(input + op);
    }

    private class CalculatorFunctionClickListener implements View.OnClickListener {
        private String functionName;

        public CalculatorFunctionClickListener(String functionName) {
            this.functionName = functionName;
        }

        @Override
        public void onClick(View v) {
            String input = text_result.getText().toString();
            String angle = editText.getText().toString();
            text_result.setText(input + functionName +"(" + angle + ")" );
            calculateFunction(functionName);
        }
    }
    // Метод для вычисления функций
    public void calculateFunction(String functionName) {
        double angle = Double.parseDouble(editText.getText().toString());
        double result = 0;
        switch (functionName) {
            case "sin":
                 result = Math.sin(angle);
                break;
            case "cos":
                result = Math.cos(angle);
                break;
            case "tan":
                result = Math.tan(angle);
                break;
            case "cot":
                result = 1 / Math.tan(angle); // котангенс равен 1/tan(x)
                break;
            case "sqrt":
                result = Math.sqrt(angle);
                break;
            case "ln":
                result = Math.log(angle);
                break;
            case "log":
                result = Math.log10(angle);
                break;
            case "abs":
                result = Math.abs(angle);
                break;
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }
        editText.setText(String.valueOf(result));
    }
    // Метод для вычисления факториала
    public void factorial(double n) {
        double result = 1;
        if (n < 0) throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        if (n == 0 || n == 1) {
            result = 1;
        }else {
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
        }
        String input = text_result.getText().toString();
        text_result.setText(input + String.valueOf(n) + "!");
        editText.setText(String.valueOf(result));
    }

    // Константы
    public static final double PI = Math.PI;
    public static final double E = Math.E;

    public static final double EXP = Math.exp(1);
}
