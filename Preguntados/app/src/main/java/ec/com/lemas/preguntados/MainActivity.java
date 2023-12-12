package ec.com.lemas.preguntados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ec.com.lemas.preguntados.activity.GameActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSiguiente = (Button) findViewById(R.id.sgtActividad);
        btnSiguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sgtActividad){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }
}