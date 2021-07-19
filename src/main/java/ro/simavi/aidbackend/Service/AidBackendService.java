package ro.simavi.aidbackend.Service;

import ro.simavi.aidbackend.Model.StandardModelResponse;

public interface AidBackendService {

     Object login(String username, String password);
     StandardModelResponse uploadImageData(String imageName, double size, int dimensionX, int dimensionY);
     StandardModelResponse register(String username, String password);
}
