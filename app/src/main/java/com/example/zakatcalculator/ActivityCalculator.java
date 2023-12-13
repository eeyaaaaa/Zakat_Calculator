package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ActivityCalculator extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText gram, value;
    Button calcBTN, resetBTN, backBTN;
    TextView output, outputt, outputtt;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float gweight, gvalue;

    SharedPreferences weightSharedPref;
    SharedPreferences valueSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        gram = findViewById(R.id.amount);
        value = findViewById(R.id.cgold);
        output = findViewById(R.id.totalGold);
        outputt = findViewById(R.id.zakatPay);
        outputtt = findViewById(R.id.totalZakat);
        calcBTN = findViewById(R.id.btncal);
        resetBTN = findViewById(R.id.btnreset);
        backBTN = findViewById(R.id.btncal);

        calcBTN.setOnClickListener(this);
        resetBTN.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        weightSharedPref = getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = weightSharedPref.getFloat("weight", 0.0F);

        valueSharedPref = getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = valueSharedPref.getFloat("value", 0.0F);

        gram.setText(String.valueOf(gweight));
        value.setText(String.valueOf(gvalue));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "About", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.btncal) {
                performCalculation();
            } else if (v.getId() == R.id.btnreset) {
                resetFields();
            } else if (v.getId() == R.id.btncal) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        } catch (Exception exp) {
            Toast.makeText(this, "Unknown Exception: " + exp.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Exception", exp.getMessage(), exp);
        }
    }

    private void performCalculation() {
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(gram.getText().toString());
        float gvalue = Float.parseFloat(value.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double tValue, uruf, zakatpay, tZakat;

        SharedPreferences.Editor editorWeight = weightSharedPref.edit();
        editorWeight.putFloat("weight", gweight);
        editorWeight.apply();

        SharedPreferences.Editor editorValue = valueSharedPref.edit();
        editorValue.putFloat("value", gvalue);
        editorValue.apply();

        if (stat.equals("Keep")) {
            tValue = gweight * gvalue;
            uruf = gweight - 85;

            if (uruf >= 0.0) {
                zakatpay = uruf * gvalue;
                tZakat = zakatpay * 0.025;
            } else {
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output.setText("Total Gold Value: RM" + df.format(tValue));
            outputt.setText("Zakat Payable    : RM" + df.format(zakatpay));
            outputtt.setText("Total Zakat         : RM" + df.format(tZakat));
        } else {
            tValue = gweight * gvalue;
            uruf = gweight - 200;

            if (uruf >= 0.0) {
                zakatpay = uruf * gvalue;
                tZakat = zakatpay * 0.025;
            } else {
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output.setText("Total Gold Value: RM" + df.format(tValue));
            outputt.setText("Zakat Payable    : RM" + df.format(zakatpay));
            outputtt.setText("Total Zakat         : RM" + df.format(tZakat));
        }
    }

    private void resetFields() {
        gram.setText("");
        value.setText("");
        output.setText("Total Gold Value: RM");
        outputt.setText("Zakat Payable    : RM");
        outputtt.setText("Total Zakat         : RM");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Handle spinner item selection if needed
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle case where nothing is selected in the spinner
    }
}
