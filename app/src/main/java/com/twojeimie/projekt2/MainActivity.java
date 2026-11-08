package com.twojeimie.projekt2;

import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.twojeimie.projekt2.R;
import java.util.ArrayList;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    EditText editTextLiczbaKostek;
    Button buttonLosuj;
    Button buttonRosnaco;
    Button buttonMalejaco;
    GridLayout gridKostki;
    TextView textViewWyniki;
    ArrayList<Integer> wyniki = new ArrayList<>();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);
        buttonLosuj = findViewById(R.id.buttonLosuj);
        buttonRosnaco = findViewById(R.id.buttonRosnaco);
        buttonMalejaco = findViewById(R.id.buttonMalejaco);
        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);

        buttonLosuj.setOnClickListener(v -> losujKostki());

        buttonRosnaco.setOnClickListener(v -> sortujRosnaco());

        buttonMalejaco.setOnClickListener(v -> sortujMalejaco());
    }

    private void losujKostki() {

        String tekst = editTextLiczbaKostek.getText().toString().trim();

        int liczbaKostek;

        try {
            liczbaKostek = Integer.parseInt(tekst);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Podaj prawidlowa liczbe!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (liczbaKostek <= 0) {
            Toast.makeText(this, "Liczba kostek musi byc wieksza od zera!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (liczbaKostek > 100) {
            Toast.makeText(this, "Maksymalna liczba kostek to 100!", Toast.LENGTH_SHORT).show();
            return;
        }

        wyniki.clear();

        for (int i = 0; i < liczbaKostek; i++) {
            int wynik = random.nextInt(6) + 1;
            wyniki.add(wynik);
        }
        odswiezWidok();
    }

    private void sortujRosnaco() {
        if (wyniki.isEmpty()) {
            Toast.makeText(this, "Najpierw wykonaj losowanie!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < wyniki.size() - 1; i++) {
            for (int j = 0; j < wyniki.size() - 1 - i; j++) {
                if (wyniki.get(j) > wyniki.get(j + 1)) {
                    int temp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, temp);
                }
            }
        }

        odswiezWidok();
    }
    private void sortujMalejaco() {

        if (wyniki.isEmpty()) {
            Toast.makeText(this, "Najpierw wykonaj losowanie!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < wyniki.size() - 1; i++) {
            for (int j = 0; j < wyniki.size() - 1 - i; j++) {
                if (wyniki.get(j) < wyniki.get(j + 1)) {
                    int temp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, temp);
                }
            }
        }

        odswiezWidok();
    }
    private void odswiezWidok() {

        StringBuilder tekstWynikow = new StringBuilder();

        for (int i = 0; i < wyniki.size(); i++) {

            tekstWynikow.append(wyniki.get(i));

            if (i < wyniki.size() - 1) {
                tekstWynikow.append(", ");
            }
        }
        textViewWyniki.setText(tekstWynikow.toString());
        gridKostki.removeAllViews();
        gridKostki.setColumnCount(5);

        for (int i = 0; i < wyniki.size(); i++) {
            TextView kostka = new TextView(this);
            kostka.setText(String.valueOf(wyniki.get(i)));
            kostka.setTextSize(24);
            kostka.setTextColor(Color.BLACK);
            kostka.setGravity(Gravity.CENTER);
            kostka.setBackgroundColor(Color.LTGRAY);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = dp(56);
            params.height = dp(56);
            params.setMargins(dp(4), dp(4), dp(4), dp(4));
            gridKostki.addView(kostka, params);
        }
    }
    private int dp(int wartosc) {
        return (int) (wartosc * getResources().getDisplayMetrics().density + 0.5f);
    }
}