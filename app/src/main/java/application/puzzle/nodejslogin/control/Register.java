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
import application.puzzle.nodejslogin.model.user;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {

    EditText firstname, lastname, userCode, age, email, password, education_degree, marital_status, gender;
    Button register;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    INodeJs myAPI;
    user user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);
        //get data from view
        firstname = findViewById(R.id.edt_firstname);
        lastname = findViewById(R.id.edt_lastname);
        userCode = findViewById(R.id.edt_usercode);
        age = findViewById(R.id.edt_age);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        education_degree = findViewById(R.id.edt_educationDegree);
        marital_status = findViewById(R.id.edt_maritalStatus);
        gender = findViewById(R.id.edt_gender);

        register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if validate is true
                if (validate()) {
                    //get data from fields
                    String firstname_value = firstname.getText().toString().trim();
                    String lastname_value = lastname.getText().toString().trim();
                    int userCode_value = Integer.parseInt(userCode.getText().toString().trim());
                    int age_value = Integer.parseInt(age.getText().toString().trim());
                    String email_value = email.getText().toString().trim();
                    String password_value = password.getText().toString().trim();
                    String educationDegree_value = education_degree.getText().toString().trim();
                    String maritalStatus_value = marital_status.getText().toString().trim();
                    String gender_value = gender.getText().toString().trim();
                    //set data to user model
                    user = new user(userCode_value, firstname_value, lastname_value, age_value, email_value,
                            password_value, educationDegree_value, maritalStatus_value, gender_value);
                    registerData(user);
                }
            }
        });


    }

    //validation fields
    private boolean validate() {
        if (firstname.length() == 0) {
            firstname.setError("first name is required");
            return false;
        }

        if (lastname.length() == 0) {
            lastname.setError("last name is required");
            return false;
        }

        if (userCode.length() == 0) {
            userCode.setError("user code is required");
            return false;
        }

        if (age.length() == 0) {
            age.setError("age is required");
            return false;
        }

        if (email.length() == 0) {
            email.setError("email is required");
            return false;
        }

        if (password.length() == 0) {
            password.setError("Password is required");
            return false;
        }

        if (education_degree.length() == 0) {
            education_degree.setError("education degree is required");
            return false;
        }

        if (marital_status.length() == 0) {
            marital_status.setError("marital status is required");
            return false;
        }
        if (gender.length() == 0) {
            gender.setError("gender is required");
            return false;
        }

        // after all validation return true.
        return true;
    }

    private void registerData(user user_info){
        compositeDisposable.add(myAPI.registerUser(user_info.getUser_code(), user_info.getFirst_name(), user_info.getLast_name(),
                        user_info.getAge(), user_info.getEmail(), user_info.getPassword(), user_info.getEducation_degree(),
                        user_info.getMarital_status(), user_info.getGender())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(Register.this, s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
        );
    }


}