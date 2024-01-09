package ec.com.lemas.guardarusuario.interfaces;

import ec.com.lemas.guardarusuario.entity.UserRequestDTO;
import ec.com.lemas.guardarusuario.entity.UserResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMain {

    @POST("create")
    Call<UserResponseDTO> saveUser(@Body UserRequestDTO request);
}
