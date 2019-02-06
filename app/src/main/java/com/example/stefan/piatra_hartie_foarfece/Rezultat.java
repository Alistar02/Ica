package com.example.stefan.piatra_hartie_foarfece;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Rezultat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);

        ImageView jucator_output = (ImageView)findViewById(R.id.jucator_output) ;

        ImageView adversar_output = (ImageView)findViewById(R.id.adversar_output);

                    Intent second_intent_text = getIntent();

                     TextView text_output;
 
                     String mesaj = second_intent_text.getStringExtra("raspuns");

                     text_output = (TextView)findViewById(R.id.output);

                         text_output.setText(mesaj);

        Bundle extras_jucator = getIntent().getExtras();

        Bitmap imagine_jucator = (Bitmap) extras_jucator.getParcelable("imagine");

        Bitmap imagine_adversar = (Bitmap) extras_jucator.getParcelable("imagine1");

        jucator_output.setImageBitmap(imagine_jucator);
        adversar_output.setImageBitmap(imagine_adversar);

    }
}
