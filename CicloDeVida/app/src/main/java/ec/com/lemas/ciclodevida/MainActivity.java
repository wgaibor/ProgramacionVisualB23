package ec.com.lemas.ciclodevida;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import ec.com.lemas.ciclodevida.activities.LearnActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int i = 0;
    CoordinatorLayout coordinatorLayout;
    Button btnSiguienteActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        btnSiguienteActividad = (Button) findViewById(R.id.btnCambiarActividad);
        btnSiguienteActividad.setOnClickListener(this);
        Log.i("  >>>>>>  ", "Ingreso por el método onCreate()   ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(" >>>>>>  ", getString(R.string.mensaje_on_resume) );
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.w(" >>>>> ", "Ingreso por el método onStop()  ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(" >>>>> ", "Ingreso por el método onDestroy()  ");
    }

    @Override
    public void onBackPressed() {
        i++;
        Toast.makeText(this,getString(R.string.mensaje_salida),Toast.LENGTH_LONG).show();
        Snackbar.make(coordinatorLayout, getString(R.string.mensaje_salida), Snackbar.LENGTH_SHORT).show();
        AlertDialog mensajeAlerta = new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.mensaje_alert_dialog))
                .setMessage(getString(R.string.mensaje_salida))
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, getString(R.string.ya_voy_a_salir), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancelar), /* listener = */ null)
                .show();
        if (i >= 2){
            i = 0;
            mensajeAlerta.dismiss();
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnCambiarActividad){
            cambiarActividad();
        }
    }

    private void cambiarActividad() {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }
}