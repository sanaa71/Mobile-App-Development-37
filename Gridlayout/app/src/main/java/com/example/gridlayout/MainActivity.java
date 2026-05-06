package com.example.gridlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    GridView gridView;

    String[] buttons = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "C","0","=","+"
    };

    String currentInput = "";
    double firstNumber = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        gridView = findViewById(R.id.gridView);

        gridView.setAdapter(new CalculatorAdapter());
    }

    class CalculatorAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return buttons.length;
        }

        @Override
        public Object getItem(int position) {
            return buttons[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.grid_layout, parent, false);

            Button button = view.findViewById(R.id.btnItem);
            button.setText(buttons[position]);

            button.setOnClickListener(v -> handleInput(buttons[position]));

            return view;
        }
    }

    private void handleInput(String value) {

        if (value.matches("[0-9]")) {
            currentInput += value;
            txtResult.setText(currentInput);
        }
        else if (value.equals("C")) {
            currentInput = "";
            operator = "";
            txtResult.setText("");
        }
        else if (value.equals("=")) {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": result = firstNumber / secondNumber; break;
            }

            txtResult.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
        }
        else {
            operator = value;
            firstNumber = Double.parseDouble(currentInput);
            currentInput = "";
        }
    }
}
