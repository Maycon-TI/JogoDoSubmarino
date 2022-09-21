package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.game.databinding.ActivityConfBinding;

public class ConfActivity extends AppCompatActivity {

    private ActivityConfBinding binding;
    private final String[] opcoes = {"Easy", "Normal", "Hard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityConfBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.ibVoltar.setImageResource(R.drawable.conf);
        prepararBotao();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, opcoes);
        binding.sSpinner.setAdapter(adapter);
        binding.sSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                binding.tvDificuldade.setText(opcoes[position]);
                game().setDificuldade(opcoes[position]);
                loadView();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
    private void loadView(){
        switch (game().getDificuldade()){
            case "Hard":
                binding.tvDificuldade.setTextColor(Color.RED);
                game().setBombas(6);
                game().setGastoDeOxigenio(9);
                break;
            case "Normal":
                binding.tvDificuldade.setTextColor(Color.YELLOW);
                game().setBombas(4);
                game().setGastoDeOxigenio(5);
                break;
            case "Easy":
                binding.tvDificuldade.setTextColor(Color.GREEN);
                game().setBombas(3);
                game().setGastoDeOxigenio(2);
                break;
        }
        binding.tvGastoDeOxigenio2.setText(getString(R.string.gastoOxigenio, String.valueOf(game().getGastoDeOxigenio())));
        binding.tvQuantidadeBombas2.setText(getString(R.string.quantidadeBombas, String.valueOf(game().getBombas())));
    }
    private Game game() {
        return getIntent().getParcelableExtra(Constantes.GAME.getDescricao());
    }
    private void prepararBotao(){
        ibVoltar();
    }
    private void ibVoltar(){
        binding.ibVoltar.setOnClickListener(view -> {
            Intent i = new Intent();
            i.putExtra(Constantes.GAME.getDescricao(), game());
            setResult(10, i);
            finish();
        });
    }




}