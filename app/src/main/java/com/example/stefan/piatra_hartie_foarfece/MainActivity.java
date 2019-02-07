package com.example.stefan.piatra_hartie_foarfece;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private class ImageOnClickListener implements View.OnClickListener {
        private MainActivity.PosibilitateAlegere m_alegere;

        ImageOnClickListener(MainActivity.PosibilitateAlegere t_alegere) {
            m_alegere = t_alegere;
        }

        @Override
        public void onClick(View v) {
            alegereAdversar(m_alegere);
        }
    }

    public enum PosibilitateAlegere {
        PIATRA(0),
        HARTIE(1),
        FOARFECE(2),
        COUNT(3);

        private int m_value;

        PosibilitateAlegere(int t_value)
        { m_value = t_value; }

        public int value()
        { return m_value; }

        public static PosibilitateAlegere laNimereala() {
            Random n = new Random();

            return PosibilitateAlegere.valueOf(String.valueOf(
                        n.nextInt(COUNT.value())
                    )
            );
        }
    }

    public enum PosibilitateRezultat {
        CASTIGATOR(0),
        PIERZATOR(1),
        EGALITATE(2);

        private int m_value;

        PosibilitateRezultat(int t_value)
        { m_value = t_value; }

        public int value()
        { return m_value; }
    }

    public PosibilitateRezultat tipRezultat(PosibilitateAlegere t_alegereJucator, PosibilitateAlegere t_alegereAdversar) {
        if(t_alegereJucator == t_alegereAdversar) {
            return PosibilitateRezultat.EGALITATE;
        }

        switch(t_alegereJucator) {
            case PIATRA:
                if(t_alegereAdversar == PosibilitateAlegere.HARTIE) {
                    return PosibilitateRezultat.PIERZATOR;
                }
                else {
                    return PosibilitateRezultat.CASTIGATOR;
                }
            case HARTIE:
                if(t_alegereAdversar == PosibilitateAlegere.FOARFECE) {
                    return PosibilitateRezultat.PIERZATOR;
                }
                else {
                    return PosibilitateRezultat.CASTIGATOR;
                }
            case FOARFECE:
                if(t_alegereAdversar == PosibilitateAlegere.PIATRA) {
                    return PosibilitateRezultat.PIERZATOR;
                }
                else {
                    return PosibilitateRezultat.CASTIGATOR;
                }
            default:
                throw new RuntimeException();
        }
    }

    ImageButton piatra_image_button;
    ImageButton hartie_image_button;
    ImageButton foarfece_image_button;

    ImageButton[] imageButtons = new ImageButton[PosibilitateAlegere.COUNT.value()];

    Intent intent_jucator = new Intent(this,Rezultat.class);

    public PosibilitateAlegere jucator_input = PosibilitateAlegere.PIATRA;

    public void alegereAdversar(PosibilitateAlegere t_alegereJucator) {
        PosibilitateAlegere alegereAdversar = PosibilitateAlegere.laNimereala();
        batalie(alegereAdversar);
        startActivity(jucator_output(t_alegereJucator, alegereAdversar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        piatra_image_button = (ImageButton) findViewById(R.id.piatra);
        hartie_image_button = (ImageButton)findViewById(R.id.hartie);
        foarfece_image_button = (ImageButton)findViewById(R.id.foarfece);

        this.imageButtons[PosibilitateAlegere.PIATRA.value()] = (ImageButton)findViewById(R.id.piatra);
        this.imageButtons[PosibilitateAlegere.HARTIE.value()] = (ImageButton)findViewById(R.id.hartie);
        this.imageButtons[PosibilitateAlegere.FOARFECE.value()] = (ImageButton)findViewById(R.id.foarfece);

        for(int i = 0; i < PosibilitateAlegere.COUNT.value(); i++) {
            this.imageButtons[i].setOnClickListener(
                    new ImageOnClickListener(
                            PosibilitateAlegere.valueOf(String.valueOf(i))
                    )
            );
        }
    }

    public void batalie(PosibilitateAlegere t_alegereAdversar) {
        try {
            deschide_rezultat(tipRezultat(jucator_input, t_alegereAdversar));
        } catch(RuntimeException e) {
            System.out.printf("jucatorul luat cumva alegerea COUNT: %s\n", e.getCause());
        }
    }

    public void deschide_rezultat(PosibilitateRezultat t_rezultat) {
        Intent intent_text = new Intent(this,Rezultat.class);

        String mesaj = "";

        if(t_rezultat == PosibilitateRezultat.PIERZATOR) {
            mesaj = "Ai pierdut";
        }
        else if (t_rezultat == PosibilitateRezultat.CASTIGATOR) {
            mesaj = "Ai castigat!";
        }
        else if (t_rezultat == PosibilitateRezultat.EGALITATE) {
            mesaj = "Egalitate!";
        }

        intent_text.putExtra("raspuns", mesaj);
    }

    public Intent jucator_output(PosibilitateAlegere t_alegereJucator, PosibilitateAlegere t_alegereAdversar) {
        Intent intentImagine = new Intent(this, Rezultat.class);

        ImageView[] imagini = new ImageView[PosibilitateAlegere.COUNT.value()];

        imagini[PosibilitateAlegere.PIATRA.value()] = (ImageView)findViewById(R.id.piatra);
        imagini[PosibilitateAlegere.HARTIE.value()] = (ImageView)findViewById(R.id.hartie);
        imagini[PosibilitateAlegere.FOARFECE.value()] = (ImageView)findViewById(R.id.foarfece);

        Bundle extrasJucator = new Bundle();
        Bundle extrasAdversar = new Bundle();

        Bitmap bitmapJucator = ((BitmapDrawable)imagini[t_alegereJucator.value()].getDrawable()).getBitmap();
        Bitmap bitmapAdversar = ((BitmapDrawable)imagini[t_alegereAdversar.value()].getDrawable()).getBitmap();

        extrasJucator.putParcelable("imagine_jucator", bitmapJucator);
        extrasJucator.putParcelable("imagine_adversar", bitmapAdversar);

        intentImagine.putExtras(extrasJucator);
        intentImagine.putExtras(extrasAdversar);

        return intentImagine;
    }

}


