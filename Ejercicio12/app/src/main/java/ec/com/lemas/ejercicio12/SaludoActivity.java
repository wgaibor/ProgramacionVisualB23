package ec.com.lemas.ejercicio12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SaludoActivity extends AppCompatActivity {

    TextView txtMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);
        txtMensaje = (TextView) findViewById(R.id.txtRecibido);
        Bundle bundle = getIntent().getExtras();
        String texto = bundle.getString("mensaje", "No vino nada");
        txtMensaje.setText(texto);
    }
}