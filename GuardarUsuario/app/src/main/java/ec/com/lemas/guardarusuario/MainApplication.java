package ec.com.lemas.guardarusuario;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApplication extends Application {

    private static MainApplication mInstance;
    private Retrofit restAdapter;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        restAdapter = retrofitMain();
    }

    private Retrofit retrofitMain() {

        Retrofit client = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return client;
    }

    public Retrofit getRestAdapter() {
        return restAdapter;
    }

    public static MainApplication getmInstance() {
        return mInstance;
    }
}
