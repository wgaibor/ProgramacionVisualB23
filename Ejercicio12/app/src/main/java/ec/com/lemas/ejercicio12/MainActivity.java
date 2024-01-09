package ec.com.lemas.ejercicio12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSaludar;
    EditText edtMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSaludar = (Button) findViewById(R.id.btnSaludo);
        edtMensaje = (EditText) findViewById(R.id.edtMensaje);
        btnSaludar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSaludo){
            llamarSiguienteActividad();
        }
    }

    private void llamarSiguienteActividad() {
        Intent intent = new Intent(this, SaludoActivity.class);
        String textoEscrito = edtMensaje.getText().toString();
        edtMensaje.setText("");
        intent.putExtra("mensaje", textoEscrito);
        startActivity(intent);
    }
}