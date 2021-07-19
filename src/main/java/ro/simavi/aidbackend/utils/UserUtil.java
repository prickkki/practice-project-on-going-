package ro.simavi.aidbackend.utils;


public class UserUtil {
    public static final int MIN_PASSWORD_SIZE=8;

    public boolean checkUsername(String username) {
        int contor = 0;
        for(int i=0; i<username.length(); i++){
            if(username.charAt(i)== '@'){
                contor++;
            }
            if((contor == 1)&&(username.charAt(i) =='.')){
                contor++;
            }
        }
        if(contor>=2) {
          return true;
       } else{
          return false;
      }
    }

    public boolean checkPassword(String password){
        int contorSpecailCharacter = 0;
        int contorUpperCase = 0;
        int contorFigure = 0;
        int passwordSize = password.length();
        int startIntervalSpecialCharacter = 32;
        int endIntervalSpecialCharacter = 47;
        int startIntervalUpperCase = 65;
        int endIntervalUpperCase = 90;
        int startIntervalfigure = 48;
        int endIntervalfigure = 57;
        boolean strongPassword = false;
        if(passwordSize >= MIN_PASSWORD_SIZE){
        for(int i=0; i<passwordSize; i++) {
            int characterInt = (int)(password.charAt(i));
              for (int j = startIntervalSpecialCharacter; j<=endIntervalSpecialCharacter; j++)
              {
                  if(characterInt==j){
                      contorSpecailCharacter++;
                  }
              }
              for(int k=startIntervalUpperCase; k<=endIntervalUpperCase; k++) {
                  if (characterInt == k) {
                      contorUpperCase++;
                  }
              }
                  for(int l=startIntervalfigure; l<=endIntervalfigure; l++){
                      if(characterInt==l){
                          contorFigure++;
                      }
              }
        }
        }
        if((contorFigure>=1)&&(contorUpperCase>=1)&&(contorSpecailCharacter>=1)){
            strongPassword =true;
        }
        return strongPassword;
    }



    }

