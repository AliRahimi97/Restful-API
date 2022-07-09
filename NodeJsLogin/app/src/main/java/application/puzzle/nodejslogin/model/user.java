package application.puzzle.nodejslogin.model;

public class user {

    private int user_code, id, age;
    private String first_name, last_name, password, gender, marital_status, education_degree,
            email, encrypted_key;

    public user(){}

    public user(int user_code,String first_name, String last_name, int age,
                String email, String password, String education_degree,
                String marital_status, String gender) {

        this.id = id;
        this.user_code = user_code;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.encrypted_key = encrypted_key;
        this.education_degree = education_degree;
        this.marital_status = marital_status;
        this.gender = gender;

    }

    public int getUser_code() {
        return user_code;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getEducation_degree() {
        return education_degree;
    }

    public String getEmail() {
        return email;
    }

    public String getEncrypted_key() {
        return encrypted_key;
    }

    public void setUser_code(int user_code) {
        this.user_code = user_code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public void setEducation_degree(String education_degree) {
        this.education_degree = education_degree;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncrypted_key(String encrypted_key) {
        this.encrypted_key = encrypted_key;
    }
}
