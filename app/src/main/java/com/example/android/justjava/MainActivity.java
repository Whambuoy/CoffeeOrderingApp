/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 3;
    int price = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox );
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        EditText name = (EditText) findViewById(R.id.name_text);
        String userName= name.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent (Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + userName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }
    /**
     * Calculates the price of the order.
     * @return total price
     * @param hasChocolate is whether or not the user wants chocolate toppings
     * @param hasWhippedCream is whether or not the user wants whipped cream toppings
     */
    private int calculatePrice( boolean hasWhippedCream, boolean hasChocolate) {;
        int basePrice = 5;
        if (hasChocolate){
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream){
            basePrice = basePrice + 1;
        }
        return basePrice * quantity;
    }

    /**
     * Create summary of the order
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String userName, int price, boolean addWhippedCream, boolean hasChocolate){
        String priceMessage = "Name: " + userName;
        priceMessage += "\nAdd whipped Cream?\t" + addWhippedCream;
        priceMessage += "\nAdd Chocolate Topping ?\t" + hasChocolate;
        priceMessage += "\nQuantity: " + quantity + "\nTotal: $" + price +" \nThank You!";
        displayMessage(priceMessage);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}