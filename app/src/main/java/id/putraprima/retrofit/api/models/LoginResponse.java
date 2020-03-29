package id.putraprima.retrofit.api.models;

public class LoginResponse {
    public boolean error;
    public String email, password;
    public String token, token_type, expires_in;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginResponse(String token, String token_type, String expires_in) {
        this.token = token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken() {
        return token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public boolean isError() {
        return error;
    }
}
