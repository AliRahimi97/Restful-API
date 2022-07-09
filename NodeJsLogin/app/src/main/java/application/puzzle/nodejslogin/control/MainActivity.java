package application.puzzle.nodejslogin.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import application.puzzle.nodejslogin.R;
import application.puzzle.nodejslogin.control.retrofit.INodeJs;
import application.puzzle.nodejslogin.control.retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText edt_email, edt_password;
    Button btn_register, btn_login;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);

        btn_login = findViewById(R.id.login_button);
        btn_register = findViewById(R.id.register_button);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    loginUser(edt_email.getText().toString(), edt_password.getText().toString());
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser(String email, String password) {

        compositeDisposable.add(myAPI.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        if (s.contains("user not exist ... !")) {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }else {

                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }
                })

        );

    }

    private boolean validate() {
        if (edt_email.length() == 0) {
            edt_email.setError("email is required");
            return false;
        }

        if (edt_password.length() == 0) {
            edt_password.setError("password is required");
            return false;
        }

        return true;
    }


}