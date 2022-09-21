package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.game.databinding.ActivityGameBinding;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    Random r = new Random();
    private int p, y, x;
    private boolean bomb = false;
    private final int tempoMax = 30;
    private int tempo = tempoMax;
    private int bp;
    private int by;
    private int bx;
    private final int[] rp = {360, 0, 45, 90, 135, 180, 225, 270, 315};
    private int bombas;
    private int oxigenio = 1000;
    private boolean avanca = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        loadImagesGame();
        prepararBotao();
        loadView();
        loadView2();
    }

    private void loadImagesGame(){
        binding.tvNegativeY.setTextColor(Color.RED);
        binding.tvNegativeX.setTextColor(Color.RED);
        binding.tvPositiveY.setTextColor(Color.GREEN);
        binding.tvPositiveX.setTextColor(Color.GREEN);
        binding.ivSubmarine.setImageResource(R.drawable.submarino);
        binding.ivSeta.setImageResource(R.drawable.ponteiro);
        binding.ivAlavanca.setImageResource(R.drawable.alavanca2);
        binding.ivPegar.setImageResource(R.drawable.coletar_bomb);
        binding.ivDireita.setImageResource(R.drawable.botao_seta1);
        binding.ivEsquerda.setImageResource(R.drawable.botao_seta2);
        binding.pbQuantidadeOxigenio.setMax(1000);
    }

    private Game game() {
        return getIntent().getParcelableExtra(Constantes.GAME.getDescricao());
    }

    private void girarSubmarino(){
        if (p > 360)
            p = 0;
        else if (p < 0)
            p = 360;
    }

    private void loadBomba(){
        if (by == y && bx == x && bp == p)
            binding.ivBomba.setImageResource(R.drawable.bomb);
        else
            binding.ivBomba.setImageResource(R.drawable.none);
    }

    private void tempo() throws MyExcption{
        if (tempo <= 0){
            bomb = false;
            throw new MyExcption(getString(R.string.explodiu));
        }
    }

    private void loadView (){
        try {
            tempo();
        }
        catch (MyExcption e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        girarSubmarino();
        loadBomba();
        geradorDeBombas();
        botarSimbulos();

        binding.tvTempo.setText(getString(R.string.tempo, String.valueOf(tempo)));
        binding.tvY.setText(getString(R.string.y, String.valueOf(y)));
        binding.tvX.setText(getString(R.string.x, String.valueOf(x)));
        binding.tvP.setText(getString(R.string.p, String.valueOf(p)));

        binding.pbQuantidadeOxigenio.setProgress(oxigenio);
        String textBomb1 = String.valueOf(bombas);
        String textBomb2 = String.valueOf(game().getBombas());
        binding.tvBombas.setText(getString(R.string.bombs, textBomb1, textBomb2));
        String pbp = String.valueOf(bp);
        String pby = String.valueOf(by);
        String pbx = String.valueOf(bx);
        binding.tvPosBomb.setText(getString(R.string.posBombs, pby, pbx, pbp));
        binding.ivSeta.setRotation(p);
        voltar();
    }

    private void geradorDeBombas(){
        if (!bomb){
            bomb = true;
            by = 10 * r.nextInt(15);
            bx = 10 * r.nextInt(15);
            bp = rp[r.nextInt(9)];
            tempo = tempoMax;
        }
    }

    private void prepararBotao(){
        ivAlavanca();
        ivDireita();
        ivEsquerda();
        ivPegarBomb();
    }
    private void ivPegarBomb(){
        binding.ivPegar.setOnClickListener(view -> {
            if (by == y && bx == x && bp == p) {
                tempo = tempoMax;
                bombas = bombas + 1;
                bomb = false;
                Toast.makeText(this, getString(R.string.pego), Toast.LENGTH_SHORT).show();
            }
            loadView();
        });
    }


    private void ivDireita(){
        binding.ivDireita.setOnClickListener(view -> {
            p = p - 45;
            loadView();
        });
    }

    private void ivEsquerda(){
        binding.ivEsquerda.setOnClickListener(view -> {
            p = p + 45;
            loadView();
        });
    }

    private void ivAlavanca(){
        binding.ivAlavanca.setOnClickListener(view -> {
            if (avanca) {
                avanca = false;
                binding.ivAlavanca.setImageResource(R.drawable.alavanca2);
            }
            else {
                avanca = true;
                binding.ivAlavanca.setImageResource(R.drawable.alavanca1);
            }
        });
    }

    private void tirarSimbulos(){
        binding.tvNegativeY.setText("");
        binding.tvPositiveY.setText("");
        binding.tvNegativeX.setText("");
        binding.tvPositiveX.setText("");
    }

    private void botarSimbulos(){
        tirarSimbulos();
        if (p == 0 || p == 360) {
            binding.tvNegativeY.setText("Y");
        }
        else if (p == 45) {
            binding.tvNegativeY.setText("Y");
            binding.tvPositiveX.setText("X");
        }
        else if (p == 90) {
            binding.tvPositiveX.setText("X");
        }
        else if (p == 135) {
            binding.tvPositiveY.setText("Y");
            binding.tvPositiveX.setText("X");
        }
        else if (p == 180) {
            binding.tvPositiveY.setText("Y");
        }
        else if (p == 225) {
            binding.tvPositiveY.setText("Y");
            binding.tvNegativeX.setText("X");
        }
        else if (p == 270) {
            binding.tvNegativeX.setText("X");
        }
        else if (p == 315) {
            binding.tvNegativeY.setText("Y");
            binding.tvNegativeX.setText("X");
        }
    }

    private void movimento(){
        int velo = 10;
        if (p == 0 || p == 360) {
            y = y - velo;
        }
        else if (p == 45) {
            y = y - velo;
            x = x + velo;
        }
        else if (p == 90) {
            x = x + velo;
        }
        else if (p == 135) {
            y = y + velo;
            x = x + velo;
        }
        else if (p == 180) {
            y = y + velo;
        }
        else if (p == 225) {
            y = y + velo;
            x = x - velo;
        }
        else if (p == 270) {
            x = x - velo;
        }
        else if (p == 315) {
            y = y - velo;
            x = x - velo;
        }
    }

    private void voltar(){
        if (bombas >= game().getBombas() || oxigenio < 0) {
            Intent i = new Intent();
            i.putExtra(Constantes.GAME.getDescricao(), game());
            setResult(10, i);
            finish();
        }
    }

    private void loadView2() {
        new Thread(() -> {
            while(true) {
                runOnUiThread(() -> {
                    oxigenio = oxigenio - game().getGastoDeOxigenio();
                    tempo = tempo - 1;

                    if (avanca){
                        movimento();
                    }
                    loadView();
                });

                SystemClock.sleep(1000);
            }

        }).start();

    }
}