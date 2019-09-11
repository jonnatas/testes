package com.example.carrosretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarroViewHolder> {

    private List<Carro> carros;

    public CarroAdapter(List<Carro> carros) {
        this.carros = carros;
    }

    @NonNull
    @Override
    public CarroViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.carro_item, parent, false);
        CarroViewHolder holder = new CarroViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarroViewHolder holder, final int position) {
        final Carro carro = carros.get(position);
        holder.textViewTitle.setText(carro.getTitle());
        holder.textViewContent.setText(carro.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", carros.get(position).getTitle());
                bundle.putString("content", carros.get(position).getContent());
                bundle.putString("createdAt", carros.get(position).getCreatedAt());
                bundle.putString("updatedAt", carros.get(position).getUpdatedAt());
                bundle.putString("id", carros.get(position).getId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carros.size();
    }

    public class CarroViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;

        public CarroViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewContent = (TextView) itemView.findViewById(R.id.textViewContent);
        }
    }
}
