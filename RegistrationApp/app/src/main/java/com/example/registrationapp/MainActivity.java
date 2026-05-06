package com.example.registrationapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etPhone;
    RadioGroup radioGroupGender;
    CheckBox cbTerms;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        cbTerms = findViewById(R.id.cbTerms);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String phone = etPhone.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!cbTerms.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please agree to Terms", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedGenderId == -1) {
                    Toast.makeText(MainActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton rbGender = findViewById(selectedGenderId);
                String gender = rbGender.getText().toString();

                Toast.makeText(MainActivity.this,
                        "Registered Successfully\nName: " + name +
                                "\nGender: " + gender,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
