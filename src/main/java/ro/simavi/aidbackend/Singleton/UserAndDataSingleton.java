package ro.simavi.aidbackend.Singleton;

import ro.simavi.aidbackend.Model.Image;
import ro.simavi.aidbackend.Model.User;

import java.util.ArrayList;
import java.util.List;

public final class UserAndDataSingleton {

    private static UserAndDataSingleton DATA_INSTANCE;

    public static UserAndDataSingleton getInstance() {
        if(DATA_INSTANCE == null) {
            DATA_INSTANCE = new UserAndDataSingleton();
        }
        return DATA_INSTANCE;
    }

    private List<User> userList = new ArrayList<>();
    private List<Image> imageList = new ArrayList<>();

    public List<User> getUserList(){
        return this.userList;
    }
    public List<Image> getImageList(){
        return imageList;
    }

    public void addUserToList(User user) {
        userList.add(user);
    }

    public void addImageToList(Image image) {
        imageList.add(image);
    }

    public User findUserByUsername (String username) {
        User returnUser = null;
        for (User user : userList) {
            if(user.getUsername().equals(username)) {
                returnUser = user;
            }
        }
        return returnUser;
    }
}