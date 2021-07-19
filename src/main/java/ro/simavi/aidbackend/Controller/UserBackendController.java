package ro.simavi.aidbackend.Controller;

import org.springframework.http.ResponseEntity;
import ro.simavi.aidbackend.Model.StandardModelResponse;
import ro.simavi.aidbackend.Model.User;
import ro.simavi.aidbackend.Service.AidBackendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/")
public class UserBackendController {

    @Autowired
    private AidBackendServiceImpl aidBackendService;

    //@PostMapping("/login")
    /*public UserNameAndTokenResponse login(@RequestBody Map <String,String> payload,
                                          @RequestHeader Map<String,String> headers)*/
    @PostMapping("/register")
    public ResponseEntity<StandardModelResponse> register(@RequestBody Map<String, String> payload,
                                          @RequestHeader Map<String,String> headers){
        String username = payload.get("username");
        String password = payload.get("password");
        ResponseEntity<StandardModelResponse> responseEntity = ResponseEntity.ok().body(aidBackendService.register(username, password));
        return responseEntity;
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map <String,String> payload,
                                @RequestHeader Map<String,String> headers){
        String username = payload.get("username");
        String password = payload.get("password");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().body(aidBackendService.login(username, password));
        return responseEntity;
    }
    @GetMapping("/getUserData")
    public List<User> getUserData(@RequestHeader Map<String,String> headers) {
        return aidBackendService.getUserData();
    }
}