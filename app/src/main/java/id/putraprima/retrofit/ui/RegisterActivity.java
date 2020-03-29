package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword, edtPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
    }

    private void moveLogin() {
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void handleRegister(View view) {
        String name = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String password_confirmation = edtPasswordConfirm.getText().toString();

//        boolean validate = name != null && email !=null
//                && password != null && password_confirmation != null
//                && password == password_confirmation;
        if(name.length() == 0){
            edtUsername.setError("name must be filled!");
        }else if(email.length() == 0){
            edtEmail.setError("email must be filled!");
        }else if(password.length() == 0){
            edtPassword.setError("password must be filled");
        }else if(password.length() == 0){
            edtPasswordConfirm.setError("password confirmation must be filled");
        }else if(!password_confirmation.equals(password)){
//            edtPasswordConfirm.setError("password confirmation must be filled");
            Toast.makeText(RegisterActivity.this, "password and confirmation must be same", Toast.LENGTH_SHORT).show();
        }else {
            RegisterRequest register = new RegisterRequest(name, email, password, password_confirmation);
            ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
            Call<RegisterResponse> call =  service.doRegister(register);
            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    RegisterResponse respon = response.body();
                    Snackbar.make(view, "succesfully register " + respon.getEmail(), Snackbar.LENGTH_SHORT).show();
                    moveLogin();
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Snackbar.make(view, "failed register account", Snackbar.LENGTH_SHORT).show();
                }
            });
        }

//        if(validate == true){
//
//        }else{
//            Toast.makeText(RegisterActivity.this, "fill all data!", Toast.LENGTH_SHORT).show();
//        }

    }

}
