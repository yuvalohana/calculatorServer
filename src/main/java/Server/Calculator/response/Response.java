package Server.Calculator.response;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
   private int result;
   private String error = null;


   public Response(int result, String error) {
      this.result = result;
      this.error = error;
   }

   public Response(String error) {
      this.error = error;
   }

}
