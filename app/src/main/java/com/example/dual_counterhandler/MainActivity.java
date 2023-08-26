package com.example.dual_counterhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements CounterHandler.CounterListener {

    // EditText to display the current number
    private EditText num;
    // Decimal format for formatting the displayed numbers
    DecimalFormat form;

    /**
     * ButtonTextView
     **/
    // TextViews for incrementing and decrementing by different steps
    private TextView add001, add01, minus001, minus01;
    // Initial value for the counter
    double Startnumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the decimal format for number formatting
        form = new DecimalFormat("###.###");

        // Get a reference to the EditText for displaying numbers
        num = (EditText) findViewById(R.id.num);

        /***     Text Button       **/
        // Get references to TextViews used for incrementing and decrementing by different steps
        add001 = (TextView) findViewById(R.id.add0_01);
        add01 = (TextView) findViewById(R.id.add0_1);
        minus001 = (TextView) findViewById(R.id.minus0_01);
        minus01 = (TextView) findViewById(R.id.minus0_1);
        /**          Button      ****/

        // Create a new CounterHandler instance using the builder pattern
        new CounterHandler.Builder()
                // Set different TextViews for incrementing and decrementing by different steps
                .incrementalView2(add001)
                .decrementalView2(minus001)
                .incrementalView(add01)
                .decrementalView(minus01)
                // Set the initial number for the counter
                .startNumber(0.01)
                // Set the minimum and maximum ranges for the counter
                .minRange(0) // Can't go below -50
                .maxRange(10000) // Can't go above 50
                // Set whether the counter cycles (e.g., 49, 50, -50, -49, and so on)
                .isCycle(false)
                // Set the delay between counter updates (speed of the counter)
                .counterDelay(1000)
                // Set the step size for the counter
                .counterStep(0.01) // Steps like 0, 0.01, 0.02, ...
                .counterStep2(0.1) // Steps like 0, 0.1, 0.2, ...
                // Set the listener to receive counter events and update the display
                .listener(this)
                // Build the CounterHandler instance
                .build();
    }

    // CounterListener method for handling increment event
    @Override
    public void onIncrement(View view, double number) {
        // Update the displayed number with the formatted value
        num.setText(form.format(number));
    }

    // CounterListener method for handling decrement event
    @Override
    public void onDecrement(View view, double number) {
        // Update the displayed number with the formatted value
        num.setText(form.format(number));
    }

    // Additional CounterListener methods for a second counter
    @Override
    public void onIncrement2(View view, double number) {
        // Update the displayed number with the formatted value
        num.setText(form.format(number));
    }

    @Override
    public void onDecrement2(View view, double number) {
        // Update the displayed number with the formatted value
        num.setText(form.format(number));
    }
}
