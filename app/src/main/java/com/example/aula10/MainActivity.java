package com.example.aula10;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btnResete;
    private String[] cores = new String[4];
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private ImageButton btnImg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Random r;

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btnResete = findViewById(R.id.btnResete);
        btnImg = findViewById(R.id.imageButton2);

        r = new Random();
        int aux = r.nextInt(3) + 1;
        String[] corRand = {"azul", "verde", "vermelho"};
        for (int i = 0; i < 4; i++) {
            if (aux == 1)
                cores[i] = corRand[0];
            else if (aux == 2)
                cores[i] = corRand[1];
            else if (aux == 3)
                cores[i] = corRand[2];
            aux = r.nextInt(3) + 1;
        }
        ajustarCor(btn1, cores[0]);
        ajustarCor(btn2, cores[1]);
        ajustarCor(btn3, cores[2]);
        ajustarCor(btn4, cores[3]);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cores[0] = trocarCor(cores[0]);
                ajustarCor(btn1, cores[0]);
                cores[1] = trocarCor(cores[1]);
                ajustarCor(btn2, cores[1]);
                if (verificarCor()) {

                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cores[0] = trocarCor(cores[0]);
                ajustarCor(btn1, cores[0]);
                cores[1] = trocarCor(cores[1]);
                ajustarCor(btn2, cores[1]);
                cores[2] = trocarCor(cores[2]);
                ajustarCor(btn3, cores[2]);
                if (verificarCor()) {

                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cores[1] = trocarCor(cores[1]);
                ajustarCor(btn2, cores[1]);
                cores[2] = trocarCor(cores[2]);
                ajustarCor(btn3, cores[2]);
                cores[3] = trocarCor(cores[3]);
                ajustarCor(btn4, cores[3]);
                if (verificarCor()) {

                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cores[2] = trocarCor(cores[2]);
                ajustarCor(btn3, cores[2]);
                cores[3] = trocarCor(cores[3]);
                ajustarCor(btn4, cores[3]);
                if (verificarCor()) {

                }
            }
        });

        btnResete.setOnClickListener(new View.OnClickListener() {
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
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest1 = new AdRequest.Builder().build();
        InterstitialAd.load(this, "", adRequest1, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "Anuncio carregado!");
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SegundaTela.class);
                startActivity(intent);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "O Anúncio interstitial nçao está pronto ainda.");
                }
                finish();
            }
        });
    }

    private void ajustarCor(Button btn, String cor) {
        if (cor.equals("azul")) {
            btn.setBackgroundColor(Color.BLUE);
        } else if (cor.equals("verde")) {
            btn.setBackgroundColor(Color.GREEN);
        } else if (cor.equals("vermelho")) {
            btn.setBackgroundColor(Color.RED);
        }
    }

    private String trocarCor (String cor) {
        if (cor.equals("azul"))
            cor = "vermelho";
        else if (cor.equals("vermelho"))
            cor = "verde";
        else if (cor.equals("verde"))
            cor = "azul";

        return cor;
    }

    private boolean verificarCor() {
        if (cores[0].equals("verde") && cores[1].equals("verde") &&
                    cores[2].equals("verde") && cores[3].equals("verde")) {
            Toast.makeText(this, "Vitória!", Toast.LENGTH_SHORT).show();
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            return true;
        }
        return false;
    }
}