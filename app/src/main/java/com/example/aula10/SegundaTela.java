package com.example.aula10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class SegundaTela extends AppCompatActivity {

    ImageButton btnImg2;
    ImageView flexa;
    AdView mAdview;
    Random r;
    int anguloAtual = 0, anguloFinal = 45;
    int countCima = 0, countBaixo = 0, countEsquerda = 0, countDireita = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_tela);
        btnImg2 = findViewById(R.id.btnImg2);
        flexa = findViewById(R.id.imageView2);


        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        flexa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r = new Random();
                anguloFinal = anguloAtual % 360;
                anguloAtual = r.nextInt(3600) + 360;

                RotateAnimation imagem = new RotateAnimation(anguloFinal, anguloAtual,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                imagem.setFillAfter(true);
                imagem.setDuration(3600);
                imagem.setInterpolator(new AccelerateInterpolator());
                imagem.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        anguloAtual = anguloAtual % 360;
                        calcularDirecao(anguloAtual);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                flexa.startAnimation(imagem);
            }
        });
    }
    private void calcularDirecao(int angulo) {
        if (angulo >= 45 && angulo < 135) {
            countCima++;
        } else if (angulo >= 135 && angulo < 225) {
            countEsquerda++;
        } else if (angulo >= 225 && angulo < 315) {
            countBaixo++;
        } else {
            countDireita++;
        }
        System.out.println("Cima: " + countCima + ", Baixo: " + countBaixo + ", Esquerda: " + countEsquerda + ", Direita: " + countDireita);
    }
}
