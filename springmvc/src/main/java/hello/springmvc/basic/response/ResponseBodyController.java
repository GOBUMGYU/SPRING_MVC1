package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {
    /**
     * HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달한다.
     * response.getWriter().write(”ok”)
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * ResponseEntity 엔티티 HttpEntity 를 상속 받았는데, HttpEntity 는 Http 메시지의 헤더,
     * 바디 정보를 가지고 있다. ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
     * HttpStatus.CREATED로 변경하면, 201응답 코드로 나가는 것을 확인할 수 있다.
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    /**
     * @ResponseBody를 사용하면 view를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다.
     * ResponseEntity도 동일한 방식으로 동작한다.
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /**
     * ResponseEntity 를 반환한다. HTTP HTTP메시지 컨버터를 통해서 JSON형식으로 변환되어서 반환한다.
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("고범규");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * ResponseEntity 는 HTTP 응답 코드를 설정할 수 있는데,
     * @ResponseBody 를 사용하면 이런 것을 설정하기 까다롭다.
     * @ResponseStatus(상태코드) 애노테이션을 사용하면 응답 코드도 설정 가능
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("고범규");
        helloData.setAge(20);

        return helloData;
    }
    /**
     * @RestController
     * Controller 대신에 @RestController 애노테이션을 사용하면,
     * 해당 컨트롤러에 모두 @ResponseBody가 적용되는 효과
     * 따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력.
     * 이름 그대로 Rest Api(HTTP API)를 만들 때 사용하는 컨트롤러이다.
     */

    //참고로 @ResponseBody는 클래스 레벨에 두면 클래스 전체 메서드에 적용되는데,
    // @RestController 애노테이션 안에 @RestController가 적용되어 있음.
}
