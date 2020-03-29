package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    TextView lblAppName, lblAppTittle, lblAppVersion;
    SharedPreferences sharedPreferences;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        setupLayout();
        if (checkInternetConnection()) {
            checkAppVersion();
        }
        setAppInfo();
    }

    private void setupLayout() {
        lblAppName = findViewById(R.id.lblAppName);
        lblAppTittle = findViewById(R.id.lblAppTittle);
        lblAppVersion = findViewById(R.id.lblAppVersion);
        //Sembunyikan lblAppName dan lblAppVersion pada saat awal dibuka//
        lblAppVersion.setVisibility(View.INVISIBLE);
        lblAppName.setVisibility(View.INVISIBLE);
    }

    private boolean checkInternetConnection() {
        //TODO : 1. Implementasikan proses pengecekan koneksi internet, berikan informasi ke user jika tidak terdapat koneksi internet
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            activeNetwork = ConnectionManager.getActiveNetwork();
            if(activeNetwork != null){
                Toast.makeText(this,"Terkoneksi Internet",Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                Toast.makeText(this,"Tidak Terkoneksi Internet",Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return  true;

    }


    private void setAppInfo() {
        //TODO : 5. Implementasikan proses setting app info, app info pada fungsi ini diambil dari shared preferences

        //lblAppVersion dan lblAppName dimunculkan kembali dengan data dari shared preferences


        if(sharedPreferences.edit() != null){
            lblAppName.setVisibility(View.VISIBLE);
            lblAppVersion.setVisibility(View.VISIBLE);
            lblAppName.setText(sharedPreferences.getString("nameApp",null));
            lblAppVersion.setText(sharedPreferences.getString("nameVersion",null));
        }
    }

    private void checkAppVersion() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<AppVersion> call = service.getAppVersion();
        call.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                Toast.makeText(SplashActivity.this, response.body().getApp(), Toast.LENGTH_SHORT).show();

                      SharedPreferences.Editor myEdit = sharedPreferences.edit();
                      myEdit.putString("nameApp", response.body().getApp());
                      myEdit.putString("nameVersion", response.body().getVersion());
                      myEdit.apply();

                      Intent i = new Intent(getApplicationContext(),MainActivity.class);
                      startActivity(i);
                      finish();

                //Todo : 2. Implementasikan Proses Simpan Data Yang didapat dari Server ke SharedPreferences
                //Todo : 3. Implementasikan Proses Pindah Ke MainActivity Jika Proses getAppVersion() sukses
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
                //Todo : 4. Implementasikan Cara Notifikasi Ke user jika terjadi kegagalan koneksi ke server silahkan googling cara yang lain selain menggunakan TOAST
                Snackbar.make(view,"Gagal Koneksi Ke Server",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
