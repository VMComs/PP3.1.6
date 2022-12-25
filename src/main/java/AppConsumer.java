import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class AppConsumer {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, String.class);
        String cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(cookie);

        User user = new User(3L, "James", "Brown", (byte)28);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        User userPut = new User(3L, "Thomas", "Shelby", (byte)28);
        HttpEntity<User> entityPut = new HttpEntity<>(userPut, headers);

        ResponseEntity<String> responseEntityPut = restTemplate.exchange(url, HttpMethod.PUT,
                entityPut, String.class);

        System.out.println();
        System.out.println(responseEntityPut.getStatusCode());
        System.out.println(responseEntityPut.getBody());

        HttpEntity<Object> entityDelete = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntityDelete = restTemplate.exchange(url+"/"+3, HttpMethod.DELETE,
                entityDelete, String.class);

        System.out.println();
        System.out.println(responseEntityDelete.getStatusCode());
        System.out.println(responseEntityDelete.getBody());

        System.out.println();
        System.out.println(response.getBody()+responseEntityPut.getBody()+responseEntityDelete.getBody());
    }
}
