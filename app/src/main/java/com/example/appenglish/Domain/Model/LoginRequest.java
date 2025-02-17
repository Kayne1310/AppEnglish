package com.example.appenglish.Domain.Model;

import com.example.appenglish.R;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public class LoginResponse  extends ReturnData{
        private String token;  // Giả sử API trả về token sau khi đăng nhập thành công

        public String getToken() {
            return token;
        }
    }
}


