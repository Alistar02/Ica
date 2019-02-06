package com.example.stefan.piatra_hartie_foarfece;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ImageButton piatra_image_button;
    ImageButton hartie_image_button;
    ImageButton foarfece_image_button;

    Intent intent_jucator = new Intent(this,Rezultat.class);
     public int jucator_input = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        piatra_image_button = (ImageButton) findViewById(R.id.piatra);
        hartie_image_button = (ImageButton)findViewById(R.id.hartie);
        foarfece_image_button = (ImageButton)findViewById(R.id.foarfece);

            piatra_image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = v.getId();
                        switch (id) {
                            case R.id.piatra:
                                jucator_input = 0;

                                adversar();


                                startActivity(jucator_output(0));
                            break;
                        }


                }
            });

            hartie_image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = v.getId();
                    switch (id) {
                        case R.id.hartie:
                            jucator_input = 1;

                            adversar();
                            startActivity(jucator_output(1));

                            break;
                    }
                }
            });

            foarfece_image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = v.getId();
                    switch (id) {
                        case R.id.foarfece:
                            jucator_input = 2;

                            adversar();

                            startActivity(jucator_output(2));
                            break;

                    }
                }
            });


    }

    public void batalie(int adversar_input)
    {

        if(jucator_input == 0 && adversar_input == 0){
            deschide_rezultat(2);
                              }
        else if (jucator_input == 0 && adversar_input == 1){
            deschide_rezultat(0);

        }
        else if (jucator_input == 0 && adversar_input == 2) {
            deschide_rezultat(1);

        }                                                    //pt piatra

        else if (jucator_input == 1 && adversar_input == 1){
            deschide_rezultat(2);

        }
        else if (jucator_input == 1 && adversar_input == 2){
            deschide_rezultat(0);

        }
        else if (jucator_input == 1 && adversar_input == 0) {
            deschide_rezultat(1);
                                              //pt hartie
        }
        else if (jucator_input == 2 && adversar_input == 2){
            deschide_rezultat(2);

        }
        else if (jucator_input == 2 && adversar_input == 1) {
            deschide_rezultat(1);

        }
        else if (jucator_input == 2 && adversar_input == 0) {
            deschide_rezultat(0);

        }                                                       //pt ff

    }





    public void adversar()
    {
        Random n = new Random();
        int adversar_input = n.nextInt(3);
        batalie(adversar_input);


    }

    public void deschide_rezultat(int i)
    {
        Intent intent_text = new Intent(this,Rezultat.class);


            if(i == 0)
                intent_text.putExtra("raspuns","Ai pierdut!");
            else if (i == 1)
                intent_text.putExtra("raspuns","Ai castigat!");
            else if (i == 2)
                intent_text.putExtra("raspuns","Egalitate");
        //startActivity(intent_text);


    }

    public Intent  jucator_output(int j)
    {   Intent intent_imagine = new Intent(this,Rezultat.class);
        ImageView piatra = (ImageView)findViewById(R.id.piatra);
        ImageView hartie = (ImageView)findViewById(R.id.hartie);
        ImageView foarfece = (ImageView)findViewById(R.id.foarfece);

        piatra.buildDrawingCache();
        hartie.buildDrawingCache();
        foarfece.buildDrawingCache();

        Bitmap image_p = piatra.getDrawingCache();
        Bitmap image_h = hartie.getDrawingCache();
        Bitmap image_f = foarfece.getDrawingCache();

        Bundle extras_jucator = new Bundle();

        if (j == 0)
           {
                extras_jucator.putParcelable("imagine",image_p);
                intent_imagine.putExtras(extras_jucator);
           }
        else if (j == 1)
            {
                extras_jucator.putParcelable("imagine",image_h);
                intent_imagine.putExtras(extras_jucator);
            }

        else if (j == 2)
            {
                extras_jucator.putParcelable("imagine",image_f);
                intent_imagine.putExtras(extras_jucator);
            }


        return intent_imagine;
    }

public enum Jucator {




}


}


