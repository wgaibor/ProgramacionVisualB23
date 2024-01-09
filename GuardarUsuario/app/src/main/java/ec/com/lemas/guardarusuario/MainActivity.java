package ec.com.lemas.guardarusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ec.com.lemas.guardarusuario.entity.UserRequestDTO;
import ec.com.lemas.guardarusuario.entity.UserResponseDTO;
import ec.com.lemas.guardarusuario.interfaces.IMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombre;
    EditText salario;
    EditText edad;
    Button guardar;

    Call<UserResponseDTO> callMetodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.edtNombre);
        salario = (EditText) findViewById(R.id.edtSalario);
        edad = (EditText) findViewById(R.id.edtEdad);
        guardar = (Button) findViewById(R.id.btnGuardar);
        guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnGuardar){
            consumirWSGuardar();
        }
    }

    private void consumirWSGuardar() {
        UserRequestDTO usuario = new UserRequestDTO();
        String valorNombre = nombre.getText().toString();
        usuario.setName(valorNombre);
        usuario.setAge(edad.getText().toString());
        usuario.setSalary(salario.getText().toString());
        IMain iMain = MainApplication.getmInstance().getRestAdapter().create(IMain.class);
        callMetodo = iMain.saveUser(usuario);

        callMetodo.enqueue(new Callback<UserResponseDTO>() {
            @Override
            public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                if(response.isSuccessful()){
                    UserResponseDTO objRespuesta = response.body();
                    mostrarMensaje(objRespuesta);
                }
            }

            @Override
            public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                Log.e("*******", t.getMessage());
            }
        });
    }

    private void mostrarMensaje(UserResponseDTO objRespuesta) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(objRespuesta.getStatus());
        alertBuilder.setMessage(objRespuesta.getMessage());
        alertBuilder.setPositiveButton("OK", (dialogInterface, i) -> finish());
        alertBuilder.create().show();
    }
}