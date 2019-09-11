package com.example.carrosretrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Carro> carros;
    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSalvar;
    private RetrofitConfig retrofitConfig;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        recyclerView = (RecyclerView) findViewById(R.id.carrosList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.25:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitConfig = retrofit.create(RetrofitConfig.class);

        getCarros();
        salvarCarro();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarros();
    }

    private void salvarCarro() {
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carro newCarro = new Carro(editTextTitle.getText().toString(), editTextContent.getText().toString());
                Call<Carro> call = retrofitConfig.salvarCarro(newCarro);
                call.enqueue(new Callback<Carro>() {
                    @Override
                    public void onResponse(Call<Carro> call, Response<Carro> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                        Carro carro = response.body();
                        carros.add(carros.size(), carro);
                        mAdapter.notifyItemInserted(carros.size());
                    }

                    @Override
                    public void onFailure(Call<Carro> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void getCarros() {
        Call<List<Carro>> call = retrofitConfig.getAll();
        call.enqueue(new Callback<List<Carro>>() {
            @Override
            public void onResponse(Call<List<Carro>> call, Response<List<Carro>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                carros = response.body();
                mAdapter = new CarroAdapter(carros);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Carro>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
