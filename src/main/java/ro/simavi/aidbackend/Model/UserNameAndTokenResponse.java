package ro.simavi.aidbackend.Model;

import java.util.Map;

public class UserNameAndTokenResponse {
    String userFirstName;
    String token;

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

