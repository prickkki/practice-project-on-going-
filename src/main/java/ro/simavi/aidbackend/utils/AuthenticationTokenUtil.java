package ro.simavi.aidbackend.utils;

import ro.simavi.aidbackend.Model.StandardModelResponse;
import ro.simavi.aidbackend.Model.User;
import ro.simavi.aidbackend.Service.AidBackendServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AuthenticationTokenUtil {

    public String generateToken(String username) {
        // constructing the token
        StringBuilder stringBuilder = new StringBuilder("");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm");
        stringBuilder.append(username);
        stringBuilder.append("-");
        stringBuilder.append("Standard");
        stringBuilder.append("-");
        stringBuilder.append(formatter.format(date));
        //String s = "adb".concat("-").concat("dsfsf").concat("-").concat("sadsada");
        String tokenStr = stringBuilder.toString();
        return tokenStr;
    }

    public StandardModelResponse validateToken(Map<String,String> headers) {
        String token = headers.get("authentication");
        StandardModelResponse standardModelResponse = null;
        // start
        // first step - check if token exists
        if (token == null) {
            standardModelResponse = new StandardModelResponse();
            standardModelResponse.setResponseCode("011");
            standardModelResponse.setResponseMsg("Token authentication is not present!");
            standardModelResponse.setStatus(false);
            return standardModelResponse;
        }
        String[] tokenArray = token.split("-");
        //looking for bearer
        if (!tokenArray[0].equals("Bearer")) {
            StandardModelResponse standardTokenResponse = new StandardModelResponse();
            standardTokenResponse.setResponseCode("012");
            standardTokenResponse.setResponseMsg("Token dose not have Bearer");
            standardTokenResponse.setStatus(false);
            return standardTokenResponse;
        }

        //Getting the userList with Singleton
        AidBackendServiceImpl data = new AidBackendServiceImpl();
        List<User> userList = data.getUserData();

        //Looking to see if the user exist in our list
        for (User user : userList) {
            if (!tokenArray[1].equals(user.getUsername())) {
                standardModelResponse = new StandardModelResponse();
                standardModelResponse.setResponseCode("002");
                standardModelResponse.setResponseMsg("This username does not exist");
                standardModelResponse.setStatus(false);
                return standardModelResponse;
            }
        }
        //checking the role
        if (tokenArray[2].equals("Standard") || (tokenArray[2].equals("Admin"))) {
            //checking the date
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm");
            String tokenDate = formatter.format(date);

            int tokenLoginH = Integer.parseInt(tokenArray[5].substring(3, 5));
            int tokenLoginM = Integer.parseInt(tokenArray[5].substring(6, 8));
            int tokenCurrentH = Integer.parseInt(tokenDate.substring(12, 14));
            int tokenCurrentM = Integer.parseInt(tokenDate.substring(15, 17));
            int tokenCurrentTime = tokenCurrentM + tokenCurrentH * 60;
            int tokenLoginTime = tokenLoginM + tokenLoginH * 60;
                if (tokenCurrentTime - tokenLoginTime > 60){
                    standardModelResponse = new StandardModelResponse();
                    standardModelResponse.setResponseCode("013");
                    standardModelResponse.setResponseMsg("Session has expired");
                    standardModelResponse.setStatus(false);
                    return standardModelResponse;
                    // success case
                        }else{
                             standardModelResponse = new StandardModelResponse();
                             standardModelResponse.setResponseCode("014");
                             standardModelResponse.setResponseMsg("The token verification was good");
                             standardModelResponse.setStatus(true);
                        }
        }else{
                standardModelResponse = new StandardModelResponse();
                standardModelResponse.setResponseCode("015");
                standardModelResponse.setResponseMsg("The role does not exist");
                standardModelResponse.setStatus(false);
 }
         return standardModelResponse;
     }
}