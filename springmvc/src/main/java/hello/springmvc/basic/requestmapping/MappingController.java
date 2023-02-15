package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class MappingController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //여기에 POST요청을 하면 스프링 MVC는 HTTP 405상태코드(Method Not Allowed)를 반환한다.
    @RequestMapping(value="/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        logger.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        logger.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping("mapping-get-v2")
    public String mappingGetV2() {
        logger.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     * @return
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        logger.info("mappingPath userId={}", data);
        return "ok";
    }
    //생략 버전
    /*@GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        logger.info("mappingPath userId={}", userId);
        return "ok";
    }*/

    /**
     * PathVariable 사용 다중 매핑
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mathPath(@PathVariable String userId, @PathVariable Long orderId) {
        logger.info("mappingPath userId={} orderId={}", userId, orderId);
        return "ok";
    }
    //버전 2
    /*@GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mathPath(@PathVariable("userId") String data, @PathVariable("orderId") Long code) {
        logger.info("mappingPath userId={} orderId={}", data, code);
        return "ok";
    }*/

    /**
     * 파라미터로 추가 매핑
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params={"mode=debug", "data=good"}
     */
    // http://localhost:9000/mapping-param?mode=debug
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        logger.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode"
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-headers", headers = "mode=debug")
    public String mappingHeader() {
        logger.info("mappingHeader");
        return "ok";
    }

    /**
     * 미디어 타입 조건 매핑 HTTP 요청 Content-Type,consume
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     * HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.
     * 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        logger.info("mappingConsumes");
        return "ok";
    }

    /**
     * 미디어 타입 조건 매핑 HTTP 요청 Accept, produce
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        logger.info("mappingProduces");
        return "ok";
    }

}
