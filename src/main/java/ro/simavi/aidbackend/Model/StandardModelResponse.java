package ro.simavi.aidbackend.Model;

public class StandardModelResponse{

   private boolean status;
   private String responseCode;
   private String responseMsg;

         public boolean getStatus(){
              return this.status;
         }

         public void setStatus (boolean status){
             this.status = status;
         }

        public String getResponseCode(){
             return this.responseCode;
        }

        public void setResponseCode (String responseCode){
             this.responseCode = responseCode;
        }

        public String getResponseMsg(){
             return this.responseMsg;
        }

        public void setResponseMsg (String responseMsg){
             this.responseMsg = responseMsg;
        }
}
