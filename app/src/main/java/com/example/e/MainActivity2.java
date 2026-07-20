package com.example.e;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private TextView tvBalance, tvIncome, tvExpense;
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;

    private ArrayList<Transaction> transactionList;
    private TransactionAdapter adapter;

    private double income = 0;
    private double expense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvBalance = findViewById(R.id.tvBalance);
        tvIncome = findViewById(R.id.tvIncome);
        tvExpense = findViewById(R.id.tvExpense);

        fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.recyclerTransactions);

        transactionList = new ArrayList<>();

        adapter = new TransactionAdapter(transactionList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateBalance();

        fabAdd.setOnClickListener(v -> showAddDialog());

        getOnBackPressedDispatcher().addCallback(this,new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                getSharedPreferences("User", MODE_PRIVATE)
                        .edit()
                        .putBoolean("logged", false)
                        .apply();

                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showAddDialog() {

        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_transaction, null);

        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etAmount = dialogView.findViewById(R.id.etAmount);

        RadioButton rbIncome = dialogView.findViewById(R.id.rbIncome);
        RadioButton rbExpense = dialogView.findViewById(R.id.rbExpense);

        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnBack = dialogView.findViewById(R.id.btnBack);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        btnBack.setOnClickListener(v -> dialog.dismiss());

        btnOk.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String amountText = etAmount.getText().toString().trim();

            if(title.isEmpty() || amountText.isEmpty())
                return;

            double amount = Double.parseDouble(amountText);

            boolean isIncome = rbIncome.isChecked();

            if(isIncome){
                income += amount;
            }else{
                expense += amount;
            }

            transactionList.add(
                    new Transaction(title, amount, isIncome)
            );

            adapter.notifyDataSetChanged();

            updateBalance();

            dialog.dismiss();

        });

        dialog.show();

    }

    private void updateBalance(){

        tvIncome.setText("$ " + income);

        tvExpense.setText("$ " + expense);

        tvBalance.setText("$ " + (income-expense));

    }

}