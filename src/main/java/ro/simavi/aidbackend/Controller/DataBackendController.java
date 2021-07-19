package ro.simavi.aidbackend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.simavi.aidbackend.Model.Image;
import ro.simavi.aidbackend.Model.StandardModelResponse;
import ro.simavi.aidbackend.Service.AidBackendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.simavi.aidbackend.utils.AuthenticationTokenUtil;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/data")

public class DataBackendController {
    @Autowired
    private AidBackendServiceImpl aidBackendService;

    @PostMapping("/uploadImageData")
    public ResponseEntity<StandardModelResponse> uploadImageData(@RequestBody Image payload,
                                                                 @RequestHeader Map<String,String> headers){

        // validate header
        AuthenticationTokenUtil authenticationTokenUtil = new AuthenticationTokenUtil();
        StandardModelResponse standardModelResponseTokenValidation = authenticationTokenUtil.validateToken(headers);
        if(standardModelResponseTokenValidation.getStatus() == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(standardModelResponseTokenValidation);
        }
        // if header is valid then process the request to service
        // get needed parameters from request payload image name, size and dimmensions
        String imageName = payload.getImageName();
        double size = payload.getSize();
        int dimensionX = payload.getDimensionX();
        int dimensionY = payload.getDimensionY();
        return  ResponseEntity.ok().body(aidBackendService.uploadImageData(imageName, size, dimensionX, dimensionY));
    }

    @GetMapping("/getImageData")
    public List<Image> getImageData(@RequestHeader Map<String,String> headers){
        return aidBackendService.getImageData();
    }
}
