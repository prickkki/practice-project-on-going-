package ro.simavi.aidbackend.Service;

import ro.simavi.aidbackend.Model.Image;
import ro.simavi.aidbackend.Model.StandardModelResponse;
import ro.simavi.aidbackend.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ro.simavi.aidbackend.Model.UserNameAndTokenResponse;
import ro.simavi.aidbackend.Singleton.UserAndDataSingleton;
import ro.simavi.aidbackend.utils.AuthenticationTokenUtil;
import ro.simavi.aidbackend.utils.ImageDataUtil;
import ro.simavi.aidbackend.utils.UserUtil;

import java.util.List;

@Service
public class AidBackendServiceImpl implements AidBackendService{

    @Autowired
    private Environment env;

    @Override
    public Object login(String username, String password){
        UserUtil userUtil = new UserUtil();
        AuthenticationTokenUtil authenticationTokenUtil = new AuthenticationTokenUtil();
        boolean verifyUser = userUtil.checkUsername(username);
        List<User> userList = getUserData();
        StandardModelResponse standardLoginResponse = null;
        UserNameAndTokenResponse usernameToken = null;
        String token = null;
        if(userList.size() == 0){
            standardLoginResponse = new StandardModelResponse();
            standardLoginResponse.setResponseCode("000");
            standardLoginResponse.setResponseMsg("There are no users in the list");
            standardLoginResponse.setStatus(false);
        }
        if(verifyUser){
            for (User user: userList){
                if (username.equals(user.getUsername())) {
                    if (password.equals(user.getPassword())) {
                        token = authenticationTokenUtil.generateToken(username);
                        usernameToken = new UserNameAndTokenResponse();
                        usernameToken.setToken(token);
                        usernameToken.setUserFirstName(username);
                        return usernameToken;
                    } else {
                        standardLoginResponse = new StandardModelResponse();
                        standardLoginResponse.setResponseCode("001");
                        standardLoginResponse.setResponseMsg("The password is bad");
                        standardLoginResponse.setStatus(false);
                    }
                    }else{
                    standardLoginResponse = new StandardModelResponse();
                    standardLoginResponse.setResponseCode("002");
                    standardLoginResponse.setResponseMsg("This username does not exist");
                    standardLoginResponse.setStatus(false);
                    }

                }
        }else{
            standardLoginResponse = new StandardModelResponse();
            standardLoginResponse.setResponseCode("003");
            standardLoginResponse.setResponseMsg("Username format invalid");
            standardLoginResponse.setStatus(false);
        }
        return standardLoginResponse;
    }

    @Override
    public StandardModelResponse uploadImageData(String imageName, double size, int dimensionX, int dimensionY){
        //TODO: to be up dated by email info
        // initialization
        ImageDataUtil imageDataUtil = new ImageDataUtil();
        boolean validationSize = imageDataUtil.sizeImageVerification(size);
        boolean validationDimensions = imageDataUtil.dimensionImageVerification(dimensionX, dimensionY);
        StandardModelResponse statusResponse = null;

        //case of success
        if (validationSize && validationDimensions){
            boolean uploadStatus = uploadImageToRepository(imageName, size, dimensionX, dimensionY);
            if (uploadStatus) {
                statusResponse = new StandardModelResponse();
                statusResponse.setResponseCode("004");
                statusResponse.setResponseMsg("Upload image data ok!");
                statusResponse.setStatus(true);
            }
        }
        // case of error
        // both size and dimensions are bigger
        if (!validationSize && !validationDimensions){
            statusResponse = new StandardModelResponse();
            statusResponse.setResponseCode("005");
            statusResponse.setResponseMsg("Size and dimensions are too big!");
            statusResponse.setStatus(false);
        }
        //size is bigger
        if (!validationSize && validationDimensions){
            statusResponse = new StandardModelResponse();
            statusResponse.setResponseCode("006");
            statusResponse.setResponseMsg("Size too big!");
            statusResponse.setStatus(false);
        }
        //dimensions are bigger
        if (validationSize && !validationDimensions){
            statusResponse = new StandardModelResponse();
            statusResponse.setResponseCode("007");
            statusResponse.setResponseMsg("Dimensions too big!");
            statusResponse.setStatus(false);
        }
        return statusResponse;
    }

    public List<Image> getImageData(){
        UserAndDataSingleton userData = UserAndDataSingleton.getInstance();
        // add image by singleton instance
        return userData.getImageList();
    }
    public static List<User> getUserData(){
        UserAndDataSingleton userData = UserAndDataSingleton.getInstance();
        return userData.getUserList();
    }

   public boolean uploadImageToRepository(String imageName, double size, int dimensionX, int dimensionY){
        //create model by constructor
        Image image = new Image(imageName, size, dimensionX, dimensionY);

        // persist created model
        // call singleton
        UserAndDataSingleton userData = UserAndDataSingleton.getInstance();
        // add image by singleton instance
        userData.addImageToList(image);
        return true;
    }

    @Override
    public StandardModelResponse register(String username, String password){
        UserUtil userUtil = new UserUtil();
        List<User> userList = getUserData();
        boolean verifyUser = userUtil.checkUsername(username);
        StandardModelResponse statusResponse;
        if(verifyUser){
          for (User user: userList){
              if(user.getUsername().equals(username)){
                  statusResponse = new StandardModelResponse();
                  statusResponse.setResponseCode("008");
                  statusResponse.setResponseMsg("The user already exist");
                  statusResponse.setStatus(false);
                  return statusResponse;
              }
          }
          boolean verifyPassword = userUtil.checkPassword(password);
               if (verifyPassword) {
                      User user1 = new User(username, password);
                      userList.add(user1);
                      statusResponse = new StandardModelResponse();
                      statusResponse.setResponseCode("009");
                      statusResponse.setResponseMsg("The user have been added");
                      statusResponse.setStatus(true);
                  }else {
                      statusResponse = new StandardModelResponse();
                      statusResponse.setResponseCode("010");
                      statusResponse.setResponseMsg("Password is weak");
                      statusResponse.setStatus(false);
                  }
            }else {
            statusResponse = new StandardModelResponse();
            statusResponse.setResponseCode("003");
            statusResponse.setResponseMsg("Username format invalid");
            statusResponse.setStatus(false);
        }
        return statusResponse;
    }
}


