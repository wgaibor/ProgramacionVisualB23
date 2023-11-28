package ec.com.lemas.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import ec.com.lemas.calculadora.interfaces.IAcciones;

public class MainActivity extends AppCompatActivity implements IAcciones, View.OnClickListener {

    TextView txtPantalla;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnAdd;
    Button btnMinus;
    Button btnMultiply;
    Button btnDivide;
    Button btnEquals;
    Button btnDots;
    ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //----------------------------------------------------
        txtPantalla = (TextView) findViewById(R.id.txtPantalla);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnAdd = (Button) findViewById(R.id.btnSuma);
        btnMinus = (Button) findViewById(R.id.btnResta);
        btnMultiply = (Button) findViewById(R.id.btnMultiplicacion);
        btnDivide = (Button) findViewById(R.id.btnDividir);
        btnEquals = (Button) findViewById(R.id.btnIgual);
        btnDots = (Button) findViewById(R.id.btnComma);
        imgPicture = (ImageView) findViewById(R.id.imgCalculadora);
        //----------------------------------------------------
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnDots.setOnClickListener(this);
        imgPicture.setOnClickListener(this);
        //---------------------------------------------------
    }

    @Override
    public void mostrarLoDigitado(String caracter) {
        txtPantalla.setText(txtPantalla.getText().toString()+""+caracter);
    }

    @Override
    public void equalsOperation(String value) {
        Double result = eval(value);
        txtPantalla.setText(String.valueOf(result));
        habilitarTodosLosBotones();
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn0){
            mostrarLoDigitado(btn0.getText().toString());
        } else if(view.getId() == R.id.btn1){
            mostrarLoDigitado(btn1.getText().toString());
        } else if(view.getId() == R.id.btn2){
            mostrarLoDigitado(btn2.getText().toString());
        } else if(view.getId() == R.id.btn3){
            mostrarLoDigitado(btn3.getText().toString());
        } else if(view.getId() == R.id.btn4){
            mostrarLoDigitado(btn4.getText().toString());
        } else if(view.getId() == R.id.btn5){
            mostrarLoDigitado(btn5.getText().toString());
        } else if(view.getId() == R.id.btn6){
            mostrarLoDigitado(btn6.getText().toString());
        } else if(view.getId() == R.id.btn7){
            mostrarLoDigitado(btn7.getText().toString());
        } else if(view.getId() == R.id.btn8){
            mostrarLoDigitado(btn8.getText().toString());
        } else if(view.getId() == R.id.btn9){
            mostrarLoDigitado(btn9.getText().toString());
        } else if(view.getId() == R.id.btnSuma){
            caracterYaMarcado(R.id.btnSuma);
            mostrarLoDigitado(btnAdd.getText().toString());
        } else if(view.getId() == R.id.btnResta){
            caracterYaMarcado(R.id.btnResta);
            mostrarLoDigitado(btnMinus.getText().toString());
        } else if(view.getId() == R.id.btnMultiplicacion){
            caracterYaMarcado(R.id.btnMultiplicacion);
            mostrarLoDigitado(btnMultiply.getText().toString());
        } else if(view.getId() == R.id.btnDividir){
            caracterYaMarcado(R.id.btnDividir);
            mostrarLoDigitado(btnDivide.getText().toString());
        }  else if(view.getId() == R.id.btnComma){
            caracterYaMarcado(R.id.btnComma);
            mostrarLoDigitado(btnDots.getText().toString());
        } else if(view.getId() == R.id.btnIgual){
            equalsOperation(txtPantalla.getText().toString());
        } else if (view.getId() == R.id.imgCalculadora) {
            limpiarPantalla();
        }
    }

    private void caracterYaMarcado(int view) {
        habilitarTodosLosBotones();
        ((Button) findViewById(view)).setEnabled(false);
    }

    void habilitarTodosLosBotones(){
        btnAdd.setEnabled(true);
        btnMinus.setEnabled(true);
        btnMultiply.setEnabled(true);
        btnDivide.setEnabled(true);
        btnDots.setEnabled(true);
    }

    void limpiarPantalla(){
        txtPantalla.setText("");
        habilitarTodosLosBotones();
    }
}