    package com.example.bsapp;

    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.ListView;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {

        private RadioGroup radioGroupPizzaSize;
        private RadioButton radioButtonSmall, radioButtonMedium, radioButtonLarge;
        private Spinner spinnerFlavors;
        private TextView textViewOrder;

        private ListView listViewFlavors;
        private ArrayAdapter<String> flavorsAdapter;
        private ArrayList<String> selectedFlavors = new ArrayList<>();
        private CheckBox checkBoxBorda, checkBoxRefrigerante;
        private Button buttonRemoveFlavor, buttonSubmit, buttonClear;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            radioGroupPizzaSize = findViewById(R.id.radioGroupPizzaSize);
            radioButtonSmall = findViewById(R.id.radioButtonSmall);
            radioButtonMedium = findViewById(R.id.radioButtonMedium);
            radioButtonLarge = findViewById(R.id.radioButtonLarge);
            spinnerFlavors = findViewById(R.id.spinnerFlavors);
            listViewFlavors = findViewById(R.id.listViewFlavors);
            checkBoxBorda = findViewById(R.id.checkBoxBorda);
            checkBoxRefrigerante = findViewById(R.id.checkBoxRefrigerante);
            buttonRemoveFlavor = findViewById(R.id.buttonRemoveFlavor);
            buttonSubmit = findViewById(R.id.buttonSubmit);
            buttonClear = findViewById(R.id.buttonClear);
            textViewOrder = findViewById(R.id.textViewOrder);


            // Criar e configurar o ArrayAdapter para o Spinner
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.flavors_array, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFlavors.setAdapter(spinnerAdapter);

            // Criar e configurar o ArrayAdapter para o ListView
            flavorsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedFlavors);
            listViewFlavors.setAdapter(flavorsAdapter);

            spinnerFlavors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    addFlavor(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No action needed here
                }
            });

            buttonRemoveFlavor.setOnClickListener(v -> removeFlavor());

            // Add listeners to the Submit and Clear buttons
            buttonSubmit.setOnClickListener(v -> submitOrder());
            buttonClear.setOnClickListener(v -> clearOrder());

        }

        private void addFlavor(String flavor) {
            int checkedId = radioGroupPizzaSize.getCheckedRadioButtonId();
            int flavorLimit = 0;

            if (checkedId == R.id.radioButtonSmall) {
                flavorLimit = 1;
            } else if (checkedId == R.id.radioButtonMedium) {
                flavorLimit = 2;
            } else if (checkedId == R.id.radioButtonLarge) {
                flavorLimit = 4;
            }

            if (selectedFlavors.size() < flavorLimit) {
                selectedFlavors.add(flavor);
                flavorsAdapter.notifyDataSetChanged();
                updateOrderInfo();
            } else {
                Toast.makeText(MainActivity.this, "Não é permitido adicionar mais outro sabor.", Toast.LENGTH_LONG).show();
            }
        }

        private void removeFlavor() {
            if (!selectedFlavors.isEmpty()) {
                selectedFlavors.remove(selectedFlavors.size() - 1);
                flavorsAdapter.notifyDataSetChanged();
                updateOrderInfo();
            }
        }

        private void updateOrderInfo() {
            String pizzaSize = "";
            int pizzaPrice = 0;

            int checkedId = radioGroupPizzaSize.getCheckedRadioButtonId();

            if (checkedId == R.id.radioButtonSmall) {
                pizzaSize = "Pequena";
                pizzaPrice = 20;
            } else if (checkedId == R.id.radioButtonMedium) {
                pizzaSize = "Média";
                pizzaPrice = 30;
            } else if (checkedId == R.id.radioButtonLarge) {
                pizzaSize = "Grande";
                pizzaPrice = 40;
            }

            if (checkBoxBorda.isChecked()) {
                pizzaPrice += 10;
            }

            if (checkBoxRefrigerante.isChecked()) {
                pizzaPrice += 5;
            }

            StringBuilder pizzaFlavors = new StringBuilder();
            for (String flavor : selectedFlavors) {
                pizzaFlavors.append(flavor).append(", ");
            }

            if (pizzaFlavors.length() > 2)
                pizzaFlavors.setLength(pizzaFlavors.length() - 2);

            textViewOrder.setText("");
            textViewOrder.append("Pedido: \n");
            textViewOrder.append("Tamanho da Pizza: " + pizzaSize + "\n");
            textViewOrder.append("Sabores da Pizza: " + pizzaFlavors + "\n");
            textViewOrder.append("Total: R$" + pizzaPrice);
        }

        private void submitOrder() {
            String pizzaSize = "";
            int pizzaPrice = 0;

            int checkedId = radioGroupPizzaSize.getCheckedRadioButtonId();

            if (checkedId == R.id.radioButtonSmall) {
                pizzaSize = "Pequena";
                pizzaPrice = 20;
            } else if (checkedId == R.id.radioButtonMedium) {
                pizzaSize = "Média";
                pizzaPrice = 30;
            } else if (checkedId == R.id.radioButtonLarge) {
                pizzaSize = "Grande";
                pizzaPrice = 40;
            }

            if (checkBoxBorda.isChecked()) {
                pizzaPrice += 10;
            }

            if (checkBoxRefrigerante.isChecked()) {
                pizzaPrice += 5;
            }

            StringBuilder pizzaFlavors = new StringBuilder();
            for (String flavor : selectedFlavors) {
                pizzaFlavors.append(flavor).append(", ");
            }

            if (pizzaFlavors.length() > 2)
                pizzaFlavors.setLength(pizzaFlavors.length() - 2);

            String message = "Pedido: \n";
            message += "Tamanho da Pizza: " + pizzaSize + "\n";
            message += "Sabores da Pizza: " + pizzaFlavors + "\n";
            message += "Total: R$" + pizzaPrice;
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        private void clearOrder() {
            radioGroupPizzaSize.clearCheck();
            selectedFlavors.clear();
            flavorsAdapter.notifyDataSetChanged();
            checkBoxBorda.setChecked(false);
            checkBoxRefrigerante.setChecked(false);
            textViewOrder.setText("");
        }

    }