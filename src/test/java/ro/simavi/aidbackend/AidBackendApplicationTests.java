package ro.simavi.aidbackend;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ro.simavi.aidbackend.Model.Image;
import ro.simavi.aidbackend.Model.StandardModelResponse;
import ro.simavi.aidbackend.Model.User;
import ro.simavi.aidbackend.Model.UserNameAndTokenResponse;
import ro.simavi.aidbackend.Service.AidBackendServiceImpl;
import ro.simavi.aidbackend.Singleton.UserAndDataSingleton;
import ro.simavi.aidbackend.utils.UserUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AidBackendApplicationTests {

	/*@Test
	public void contextLoads() {
	}*/


	/*@Before
	public void setup() throws IOException {



	}*/


	@Test
	public void addImageTest() {
		Image image = new Image("test1.jpg", 1, 200, 200);

		// persist created model
		// call singleton
		UserAndDataSingleton userData = UserAndDataSingleton.getInstance();
		// add image by singleton instance
		userData.addImageToList(image);

		System.out.println("test add image");

	}

	@Test
	public void checkImageTest() {
		// persist created model
		// call singleton
		UserAndDataSingleton userData = UserAndDataSingleton.getInstance();
		// add image by singleton instance
		List<Image> imageList = userData.getImageList();

		System.out.println("test get image");


	}
	@Test
	public void generateToken() {
		String username = "dqwqddq@.dqwdq";
		StringBuilder stringBuilder = new StringBuilder("Bearer-");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		stringBuilder.append(username);
		stringBuilder.append("-Standard-");
		stringBuilder.append(formatter.format(date));
		String bearer = stringBuilder.toString();
		System.out.println(bearer);
	}
	@Test
	public void validateToken() {
		String token = "dqwqddq@.qwdq-admin-2021-07-14 15:49";
		User user1 = new User("dqwqddq@.dqwdq","dqwqd");

		StandardModelResponse standardModelResponse = null;
		// start
		// first step - check if token exists
		if (token == null) {
			standardModelResponse = new StandardModelResponse();
			standardModelResponse.setResponseCode("015");
			standardModelResponse.setResponseMsg("Token authentication is not present!");
			standardModelResponse.setStatus(false);
			System.out.println(standardModelResponse.getResponseMsg());
		}

		//Getting the userList with Singleton
		AidBackendServiceImpl data = new AidBackendServiceImpl();
		List<User> userList = data.getUserData();
		userList.add(user1);

		String[] tokenArray = token.split("-");
		//Looking to see if the user exist in our list
		for (User user : userList) {
			if (!tokenArray[0].equals(user.getUsername())) {
				standardModelResponse = new StandardModelResponse();
				standardModelResponse.setResponseCode("003");
				standardModelResponse.setResponseMsg("This username does not exist");
				standardModelResponse.setStatus(false);
				System.out.println(standardModelResponse.getResponseMsg());
			}
		}
		//checking the role
		if (tokenArray[1].equals("Standard") || (tokenArray[1].equals("Admin"))) {
			//checking the date
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm");
			String tokenDate = formatter.format(date);
            System.out.println(formatter.format(date));
			int tokenLoginH = Integer.parseInt(tokenArray[4].substring(3, 5));
			int tokenLoginM = Integer.parseInt(tokenArray[4].substring(6, 8));
			int tokenCurrentH = Integer.parseInt(tokenDate.substring(12, 14));
			int tokenCurrentM = Integer.parseInt(tokenDate.substring(15, 17));
			int tokenCurrentTime = tokenCurrentM + tokenCurrentH * 60;
			int tokenLoginTime = tokenLoginM + tokenLoginH * 60;
			if (tokenCurrentTime - tokenLoginTime > 60){
				standardModelResponse = new StandardModelResponse();
				standardModelResponse.setResponseCode("018");
				standardModelResponse.setResponseMsg("Session has expired");
				standardModelResponse.setStatus(false);
				System.out.println(standardModelResponse.getResponseMsg());
				// success case
			}else{
				standardModelResponse = new StandardModelResponse();
				standardModelResponse.setResponseCode("019");
				standardModelResponse.setResponseMsg("The token verification was good");
				standardModelResponse.setStatus(true);
			}
		}else{
			standardModelResponse = new StandardModelResponse();
			standardModelResponse.setResponseCode("017");
			standardModelResponse.setResponseMsg("The role does not exist");
			standardModelResponse.setStatus(false);
		}


		System.out.println(standardModelResponse.getResponseMsg());

	}

}