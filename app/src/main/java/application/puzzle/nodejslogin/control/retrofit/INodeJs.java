package application.puzzle.nodejslogin.control.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJs {

    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(
            @Field("user_code") int userCode,
            @Field("first_name") String firstname,
            @Field("last_name") String lastname,
            @Field("age") int age,
            @Field("email")String email,
            @Field("password")String password,
            @Field("education_degree") String education_degree,
            @Field("marital_status") String marital_status,
            @Field("gender") String gender
    );

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(
            @Field("email")String email,
            @Field("password")String password
    );

    @POST("dashboard")
    @FormUrlEncoded
    Observable<String> getUserInfo(
            @Field("email") String email
    );

}
