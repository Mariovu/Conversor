package com.example.conversor;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConversorController {

    @FXML
    private TextField txtAmount;

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private Label resultLabel;

    @FXML
    private Label errorLabel;

    // Matriz de tasas de conversión directa entre diferentes monedas
    // Las tasas representan el valor de 1 unidad de la moneda "from" a la moneda "to"
    private static final double[][] conversionRates = {
            // Mx, USD, Euro, Libra, Yen, Won
            {1.0, 0.059, 0.85, 0.072, 10.938, 118.225}, // Peso Mexicano (Mx)
            {17.0, 1.0, 1.18, 0.85, 128.69, 1388.75},   // Dólar (USD)
            {1.18, 0.85, 1.0, 0.73, 109.38, 1182.25},   // Euro
            {13.89, 0.072, 1.37, 1.0, 151.05, 1631.93}, // Libra Esterlina
            {0.091, 0.0073, 0.0092, 0.0066, 1.0, 10.789},   // Yen Japonés
            {0.0085, 0.00066, 0.00085, 0.00061, 0.0926, 1.0} // Won sur-coreano
    };

    @FXML
    private void onAmountChanged() {
        Conversion();
    }

    @FXML
    private void onClickConvertir(){
        Conversion();
    }
    @FXML
    private void onInvert() {
        String fromValue = fromComboBox.getValue();
        String toValue = toComboBox.getValue();

        // Intercambiar las selecciones de los ComboBox
        fromComboBox.setValue(toValue);
        toComboBox.setValue(fromValue);
        Conversion();
    }

    private int getIndexForCurrency(String currency) {
        String[] currencies = { "Peso Mexicano", "Dolar", "Euros", "Libras Esterlinas", "Yen Japones", "Won sur-coreano" };
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].equals(currency)) {
                return i;
            }
        }
        return -1;
    }

    private void Conversion(){
        String fromCurrency = fromComboBox.getValue();
        String toCurrency = toComboBox.getValue();
        String amountText = txtAmount.getText();

        // Manejo de excepciones en caso de que el texto no sea un número válido
        try {
            double amount = Double.parseDouble(amountText);
            int fromIndex = getIndexForCurrency(fromCurrency);
            int toIndex = getIndexForCurrency(toCurrency);

            if (fromIndex != -1 && toIndex != -1) {
                double conversionRate = conversionRates[fromIndex][toIndex];
                double convertedAmount = amount * conversionRate;

                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));
            } else {
                errorLabel.setText("Error en la selección de monedas.");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Ingrese un número válido.");
        }
    }
}

