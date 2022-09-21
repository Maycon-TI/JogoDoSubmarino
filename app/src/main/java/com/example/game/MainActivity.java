package com.example.game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.game.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Game game = new Game();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10){
            game = data.getExtras().getParcelable(Constantes.GAME.getDescricao());
            loadView();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.ibConf.setImageResource(R.drawable.conf);
        binding.ibNewGame.setImageResource(R.drawable.bt_new_game1);
        prepararBotao();
        loadView();
    }

    private void loadView(){
        switch (game.getDificuldade()){
            case "Hard":
                binding.tvDifficult.setTextColor(Color.RED);
                break;
            case "Normal":
                binding.tvDifficult.setTextColor(Color.YELLOW);
                break;
            case "Easy":
                binding.tvDifficult.setTextColor(Color.GREEN);
                break;
        }
        binding.tvDifficult.setText(String.valueOf(game.getDificuldade()));
        binding.tvGastoDeOxigenio.setText(getString(R.string.gastoOxigenio, String.valueOf(game.getGastoDeOxigenio())));
        binding.tvQuantidadeBombas.setText(getString(R.string.quantidadeBombas, String.valueOf(game.getBombas())));
    }

    private void prepararBotao(){
        btConf();
        btNewGame();
    }

    private void btConf(){
        binding.ibConf.setOnClickListener(view -> {
            Intent i = new Intent(this, ConfActivity.class);
            i.putExtra(Constantes.GAME.getDescricao(), game);
            startActivityForResult(i, 10);
        });
    }

    private void btNewGame(){
        binding.ibNewGame.setOnClickListener(view -> {
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra(Constantes.GAME.getDescricao(), game);
            startActivityForResult(i, 10);
        });
    }
}