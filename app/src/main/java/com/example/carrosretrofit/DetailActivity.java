package com.example.carrosretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private RetrofitConfig retrofitConfig;
    private EditText editTextTitleDetail;
    private EditText editTextContentDetail;
    private TextView textViewCreatedAtDetail;
    private TextView textViewUpdatedAtDetail;
    private Button buttonSalvarCarro;
    private Button buttonDeletarCarro;
    private String _id;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        extras = getIntent().getExtras();

        editTextTitleDetail = (EditText) findViewById(R.id.editTextTitleDetail);
        editTextContentDetail = (EditText) findViewById(R.id.editTextContentDetail);
        textViewCreatedAtDetail = (TextView) findViewById(R.id.textViewCreatedAtDetail);
        textViewUpdatedAtDetail = (TextView) findViewById(R.id.textViewUpdatedAtDetail);

        buttonSalvarCarro = (Button) findViewById(R.id.buttonDetailSalvar);
        buttonDeletarCarro = (Button) findViewById(R.id.buttonDetailDeletar);
        _id = extras.get("id").toString();
        textViewCreatedAtDetail.setText(extras.get("createdAt").toString());
        textViewUpdatedAtDetail.setText(extras.get("updatedAt").toString());
        editTextTitleDetail.setText(extras.get("title").toString());
        editTextContentDetail.setText(extras.get("content").toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.25:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitConfig = retrofit.create(RetrofitConfig.class);

        atualizar();
        deletar();
    }

    private void deletar() {
        buttonDeletarCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Carro> call = retrofitConfig.deleteCarro(_id);
                call.enqueue(new Callback<Carro>() {
                    @Override
                    public void onResponse(Call<Carro> call, Response<Carro> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error:Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Carro> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void atualizar() {
        buttonSalvarCarro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String title = editTextTitleDetail.getText().toString();
                String content = editTextContentDetail.getText().toString();
                Carro newCarro = new Carro(title, content);
                Call<Carro> call = retrofitConfig.updateCarro(_id, newCarro);

                call.enqueue(new Callback<Carro>() {
                    @Override
                    public void onResponse(Call<Carro> call, Response<Carro> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error:Code:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Carro> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error:Code:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


}
