# 스프링 기본 기능  

## 프로젝트 생성
**Packaging는 War가 아니라 Jar를 선택 JSP를 사용 Jar를 사용하는 것이 좋다.  
Jar를 사용하면 항상 내장 서버(톰캣등)을 사용하고, webapp 경로도 사용하지 않는다.  
내장 서버 사용에 최적화 되어 있는 기능!  
최근에는 주로 이 방식을 사용 War를 사용하면 내장 서버도 사용가능 하지만, 주로 외부 서버에 배포하는 목적으로 사용한다.**

## 스프링 기본 기능 
<details>
<summary><b>로깅 간단히 알아보기</b></summary>  

<div markdown="1">  
  
**운영 시스템에서는 System.out.println() 같은 시스템 콘솔을 사용해서 필요한 정보를 출력하지 않고, 별도의 로깅 라이브러리를 사용해서 로그를 출력한다. 참고로 로그 관련 라이브러리도 많다.**

**로깅 라이브러리** 

- **SLF4J**
- **Logback**

**로그 라이브러리는 Logback, Log4J, Log4J2 등등 수 많은 라이브러리가 있는데, 그것을 통합해서 인터페이스로 제공하는 것이 바로 SLF4J 라이브러리**

**쉽게 이야기해서 SLF4J는 인터페이스이고, 그 구현체로 Logback 같은 로그 라이브러리를 선택하면 된다. 실무에서는 스프링 부트가 기본으로 제공하는 logback을 대부분 사용**

**로그 선언**
- **private Logger log = LoggerFactory.getLogger(getClass());**
- **private static final Logger log  = LoggerFactory.getLogger(Xxx.class)**
- **@Slf4J : 롬복 사용가능**

**로그 호출**
- **log.info(”hello”)**
- **System.out.println(”hello”)**

**시스템 콘솔로 직접 출력하는 것 보다 로그를 사용하면 다음과 같은 장점이 있다. 실무에서는 항상 로그를 사용해야 한다.**

`log.trace();`

`log.debug();` **개발서버에서보는 로그** 

`log.info();`  **중요한 정보 or 비즈니스 정보거나 운영시스템에서 봐야할 정보**

`log.warn();`  **위험한 경고** 

`log.error();`  **에러 파일로 남길 수 있음** 

[`application.properties`](http://application.properties)**에 로그레벨 설정** 

```
#hello.springmvc 패키지와 그 하위 로그 레벨 설정
logging.level.hello.springmvc=trace 
```

**매핑 정보**

- `@RestController`
    - `@Controller` **는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 렌더링 된다.**
    - `@RestController` **는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 따라서 실행 결과로 ok메시지를 받을 수 있다.** `@RequestBody` **와 관련이 있다.**

**테스트**

- **로그가 출력되는 포멧 확인**
    - **시간, 로그, 레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그 메시지**
- **로그 레벨 설정을 변경해서 출력 결과를 보자**
    - `**LEVEL : TRACE > DEBUG > INFO > WARN > ERROR**`
    - **개발 서버는 debug가 출력**
    - **운영 서버는 info 출력**
- `**@Slf4J` 로 변경**

**올바른 로그 사용법**

- **`log.debug(”trace my log =” + data);`**
    - **로그 출력 레벨을 info로 설정해도 해당 코드에 있는 ”trace my log =” + data가 실제 실행이 되어 버린다. 결과적으로 문자 더하기 연산이 발생한다.**
- **`log.debug(”data={}”, data);`**
    - **로그 출력 레벨을 info로 설정하면 아무것도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이 발생하지 않는다.**

**로그 사용시 장점**

- **쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.**
- **로그 레벨에 따라, 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에 맞게 조절 할 수 있다.**
- **시스템 아웃 콘솔에서만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.**
- **성능도 일반 System.out 보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한다.**

- **logf4, logback 참고 사이트**
    - **SLF4J - [http://www.slf4j.org](http://www.slf4j.org)**
    - **Logback - [http://logback.qos.ch](http://logback.qos.ch)**
- **스프링 부트가 제공하는 로그 기능**
    - [**https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging**](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging)

</div>
</details>  

<details>
<summary><b>요청 매핑</b></summary>  

<div markdown="1">  
  
  - **RequestMapping(”/hello-basic”)**
    - **/hello-basic URL 호출이 오면 이 메서드가 실행되도록 매칭**
    - **대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {”/hello-baisc”, “hello-go”}**

**둘다 허용**

**다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑한다.**

- **매핑 :** `“/hello-basic`
- **URL 요청 :** `“/hello-basic”`, `“/hello-basic/”`

**HTTP 메서드** 

**@RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게 호출된다. 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE**

**PathVariable(경로 변수) 사용**

- **http://localhost:9000/mapping/userA**

**최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호**

- **/mapping/userA**
- **/users/1**
- **@RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 를 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.**
- **@PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.**

**Media타입 조건 매핑 Content-Type, Consume**

**HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑한다.**

**만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.**

</div>
</details>  

<details>
<summary><b>HTTP 요청 파라미터 - 쿼리 파라미터 HTML Form</b></summary>  

<div markdown="1">  
 
  **HTTP 요청 데이터 조회 - 개요**

**클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법을 사용.**

**GET - 쿼리 파라미터**

- **url?username=hello&age=20**
- **메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달**
- **예) 검색, 필터, 페이징등에서 많이 사용하는 방식**

**POST - HTML Form**

- **content-type : application/x-www-form-urlencoded**
- **메시지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20**
- **예) 회원가입, 상품 주문, HTML Form 사용**

**HTTP message body 에 데이터를 직접 담아서 요청**

- **HTTP API에서 주로 사용, JSON, XML, TEXT**
- **데이터 형식은 주로 JSON사용**

- **POST, PUT, PATCH**

**요청 파라미터 - 쿼리 파라미터, HTML Form**

**HttpServletRequest 의 request.getParameter() 를 사용하면 다음 두 가지 요청 파라미터를 조회할 수 있다.**

**GET, 쿼리 파라미터 전송**

**예시**

[](http://localhost:9000/request)**http://localhost:9000/request-param?username=hello&age=20**

**POST, HTML Form 전송**

**예시**

**POST /request-param …**

**content-type : application/x-www-form-urlencoded**

**username=hello&age=20**

**GET 쿼리 파라미터 전송 방식이든, POST HTML Form 전송 방식이든 둘다 형식이 같으므로 구분없이 조회할 수 있다. 이것을 간단히 요청 파라미터 (request parameter)이라고 한다.**

</div>
</details>  

<details>
<summary><b>HTTP 요청 파라미터 - @RequestParam</b></summary>  

<div markdown="1">  
  
**스프링이 제공하는 @RequestParam 을 사용하면 요청 파라미터를 매우 편리하게 사용할 수 있다.**

**HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name=”xx”)생략 가능**

**String, int, Integer 등의 단순 타입이면  @RequestParam 도 생략 가능 → 클래스 타입 불가**

**참고** 

**이렇게 애노테이션을 완전히 생략해도 되는데, 너무 없는 것도 약간 과하다는 주관적 생각이 있다.**

**@ReqeustParam  이 있으면 명확하게  파라미터에서 데이터를 읽는 다는 것을 알수 있다.**

- **@RequestParam.required**
    - **파라미터 필수 여부**
    - **기본값이 파라미터 필수(true)이다.**
- **/request-param 요청**
    - **username 이 없으므로 400예외가 발생한다.**
- **주의! 파라미터 이름만 사용**
    - **/request-param 요청**
    - **파라미터 이름만 있고 값이 없는 경우 → 빈문자로 통과 (””)**
- **주의! 기본형(primitive)에 null입력**
    - **/request-param요청**
    - **@RequestParam(required=flase) int age**

**null 을 int 에 입력하는 것은 불가능(500 예외 발생)**

**따라서 null을 받을 수 있는 Integer로 변경하거나 또는 다음에 나오는 defaultValue 사용**

**기본 값 적용 - @RequestParam(defaultValue=”guest”) String username**

**파라미터에 값이 없는 경우 defaultValue를 사용하면 기본 값을 적용할 수 있다.**

**이미 기본 값이 있기 때문에 required 는 의미 없다.**

**defaultValue는 빈 문자의 경우에도 설정한 기본 값이 적용된다.**

**/request-param?username=**

**파라미터 Map, MultiValueMap으로 조회**

- **@RequestParam Map**
    - **Map(key=value)**
- **@RequestParam MultiValueMap**
    - **MultiValueMap(key=[value1,value2,value…] ex(key=userId, value=[id1,id2,id…])**

**파라미터의 값이 1개가 확실하다면 Map을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자** 


</div>
</details>  

<details>
<summary><b>HTTP 요청 파라미터 - @ModelAttribute</b></summary>  

<div markdown="1">  
  
**실제 개발을 하면 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다. 보통 다음과 같이 작성할 것이다.**

```java
@RequestParam String username;
@RequestParam int age;

HelloData data = new HelloData();
data.setUsername(username);
data.setAge(age);
```

**스프링은 이 과정을 완전히 자동화해주는 @ModelAttribute 기능을 제공한다.**

**먼저 요청 파라미터를 바인딩 받을 객체를 만들자.**

- **롬복 @Data**
    - **@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 를 자동으로 적용해준다.**

```java
@ResponseBody
    @GetMapping("/model-attribute-v1")
    public String moodelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}",helloData.toString());
        return "ok";
    }
```

스프링MVC는 @ModelAttribute가 있으면 다음을 실행한다.

- HelloData 객체를 생성한다.
- 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter 를 호출해서 파라미터의 값을 입력(바인딩)한다.
- 예) 파라미터 이름이 username이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.

여기서 프로퍼티란 getXxx, setXxx 

바인딩 오류

age = abc 처럼 숫자가 들어가야 할 곳에 문자를 넣으면, BindException 이 발생

```java
@ResponseBody
    @GetMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
```

**modelAttribute는 생략할 수 있다.** 

**그런데 @RequestParam 도 생략 할 수 있으니 혼란이 발생할 수 있다.**

**스프링은 해당 생략시 다음과 같은 규칙을 적용**

- **Stirng, int, Integer 같은 단순 타입 → @RequestParam**
- **나머지 → ModelAttribute(argument resolver로 지정해둔 타입 외)**
- **argument resolver 이란 HttpServlet 같은 (예약)지정되어있는 것**
</div>
</details>  

<details>
<summary><b>HTTP 요청 요청 메시지 - 단순 텍스트</b></summary>  

<div markdown="1">  
  
- **HTTP message body 에 데이터를 직접 담아서 요청**
    - **HTTP API에서 주로 사용, JSON, XML, TEXT**
    - **데이터 형식은 주로 JSON사용**
    - **POST, PUT PATCH**

**요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우는 @RequestParam , @ModelAttribute를 사용할 수 있다. (물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정된다.)**

- **HTTP 메시지 바디의 데이터는 InputStream를 사용해서 직접 읽을 수 있다.**

**스프링 MVC는 다음 파라미터를 지원한다.**

- **HttpEntity : HTTP header, body 정보를 편리하게 조회**
    - **메시지 바디 정보를 직접 조회**
    - **요청 파라미터를 직접조회 하는 기능과 관계 없음 : @RequestParam X, @ModelAttribute X**
- **HttpEntity는 응답에도 사용 가능**
    - **메시지 바디 정보 직접 반환**
    - **바디 정보 포함 가능**
    - **view 조회 X**

**HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.**

- **@RequestEntity**
    - **HttpMethod, url 정보가 추가, 요청에서 사용**
- **@ResponseEntity**
    - **HTTP 상태 코드 설정 가능, 응답에서 사용**
    - **return new ResponseEntity<String>(”Hello world”, responseHeaders, HttpStatus.CREATED)**

**참고**

**스프링 MVC 내부에서 HTTP 메시지 바디를 읽어서 문자나 객체로 변환해서 전달해주는데, 이때 HTTP 메시지 컨버터 라는 기능을 사용한다.** 

**@RequestBody** 

**@RequestBody를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면 HttpEntity를 사용하거나 @RequestHeader를 사용하면 된다.**

**이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @ReuqestParam, @ModelAttribute와는 전혀 관계가 없다.**

**요청 파라미터 vs HTTP 메시지 바디**

- **요청 파라미터를 조회하는 기능 : @RequestParam, @ModelAttribute**
- **HTTP 메시지 바디를 직접 조회하는 기능 : @RequestBody**

**@ResponseBody**

**@ResponseBody 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할수 있다**  

**물론 이 경우에도 view를 사용하지 않는다.**
</div>
</details>  

<details>
<summary><b>HTTP 요청 메시지 - JSON</b></summary>  

<div markdown="1">  
  
**@ReqeustBody 객체 파라미터**

- **@RequestBody HelloData data**
- **@RequestBody 에 직접 만든 객체를 지정할 수 있다.**

**HttpEntity, @RequestBody를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다.** 

**HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해준다.**

**@RequestBody는 생략 불가능**

**@ResponseBody** 

**응답의 경우에도 @ResponseBody를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.**

**물론 이 경우에도 HttpEntity 를 사용해도 된다.**

- **@RequestBody 요청**
    - **JSON요청 → HTTP 메시지 컨버터 → 객체**
- **@ResponseBody 응답**
    - **객체 → HTTP 메시지 컨버터 → JSON 응답**

**HTTP 응답 → 정적 리소스, 뷰 템플릿**

**응답 데이터는 이미 아페서 일부 다룬 내용들이지만, 응답 부분에 초점을 맞추어서 정리해보자.**

**스프링(서버)에서 응답 데이터를 만드는 방법은 크게 3가지이다.**

 ****

- **정적 리소스**
    - **예) 웹 브라우저에 정적인 HTML, CSS, JS 를 제공할 때는 정적리소스를 사용한다.**
- **뷰 템플릿 사용**
    - **예) 웹 브라우저에 등록된 HTML을 제공할 때는 뷰 템플릿을 사용한다.**
- **HTTP메시지 사용**
    - **HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP메시지 바디에 JSON같은 형식으로 데이터를 실어 보낸다.**

**정적리소스**  

**스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다.**

**/static , /public, /resources, /META-INF/resources**

**뷰 템플릿**

**뷰 템플릿을 거쳐서 HTML이 생성되고 , 뷰가 응답을 만들어서 전달한다.**

**일반적으로 HTML을 동적으로 생성하는 용도로 사용되지만, 다른 것들도 가능하다. 뷰 템플릿이 만들 수 있는 것이라면 뭐든지 가능하다.**

**스프링 부트는 기본 뷰 템플릿 경로를 제공한다.**

**뷰 템플릿 경로** 

**/src/main/resources/templates**

**String을 반환하는 경우 - View of HTTP 메시지**

**@ResponseBody 가 없으면 response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링 한다.**

**@ResponseBody 가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접, response/hello 라는 문자가 입력된다.**

**여기서는 뷰의 논리 이름인 response/hello를 반환하면 다음 경로의  뷰 템플릿이 렌더링 되는 것을 확인할 수 있다.**

- **실행 : templates/resources/hello.html**

**void 를 반환하는 경우** 

- **@Controller 를 사용하고, HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용**
    - **요청 URL : /resources/hello**
    - **실행 : templates/response.hello.html**
- **참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않음**

**HTTP메시지 @ResponseBody, HttpEntity 를 사용하면 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 응답 데이터를 출력할 수 있다.**

**Thymeleaf 스프링 부트 설정**

**build.gradle에 Thymeleaf 라이브러리를 추가하면 스프링 부트가 자동으로 ThymeleafViewResolver 와 필요한 스프링 빈들을 등록한다. 그리고 다음 설정도 사용한다. 이 설정은 기본 값이기 때문에 변경이 필요한 때만 설정하면 된다.**

**application.properties**

```java
spring.thymeleaf.perfix=classpath:/templates/
spring.thymeleaf.suufix=.html
```

**HTTP 응답 - HTTP API, 메시지 바디에 직접 입력**

**HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON같은 형식으로 데이터를 실어 보낸다.**  

</div>
</details>  
  
<details>
<summary><b>HTTP 메시지 컨버터</b></summary>  

<div markdown="1">   
  
  **뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아니라, HTTP API처럼 JSON데이터를 HTTP 메시지 바디에서 직접 읽거나 쓰는 경우 HTTP 메시지 컨버터를 사용하면 편리하다.**

**@ResponseBody 사용 원리**

- **@ResponseBody 를 사용**
    - **HTTP의 Body에 문자 내용을 직접 반환**
    - **viewResolver 대신에 HttpMessageConverter 가 동작**
    - **기본 문자처리 : StringHttpMessageConverter**
    - **기본 객체처리 : MappingJackson2HttpMessageConverter**
    - **byte 처리 등등 기타 여러 HttpMessageConverter 가 기본으로 등록되어 있음**

**참고 : 응답의 경우 클라이언트 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서 HttpMessageConverter 가 선택된다.**

**스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.**

- **HTTP 요청 : @RequestBody, HttpEntity(RequestEntity);**
- **HTTP 응답 : @ResponseBody, HttpEntity(ResponseEntity);**

**HTTP 메시지 컨버터 인터페이스** 

**org.springframework.http.converter.HttpMessageConverter**

```java
package org.springframework.http.converter;

public interface HttpMessageConverter<T> {
	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
  boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);

  List<MediaType> getSupporteMediaTypes();

  T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException;
void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException;
}
```

**HTTP 메시지 컨버터는 HTTP 요청, HTTP  응답 둘 다 사용된다.**

- **canRead(), canWrite() : 메시지 컨버터가 해당 클래스, 미디어타입을 지원하는지 체크**
- **read(), write() : 메시지 컨버터를 통해서 메시지를 읽고 쓰는 기능**

**스프링 부트 기본 메시지 컨버터**

```java
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
```

**스프링 부트는 다양한 메시지 컨버터를 제공하는데, 대상 클래스 타입과 미디어 타입 둘을 체크해서 사용여부를 결정, 만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.**

**몇가지 주요한 메시지 컨버터를 알아보자.**

- **ByteArrayHttpMessageConverter : byte[] 데이터를 처리한다.**
    - **클래스 타입 : byte[], 미디어 타입 : “*/*”**
    - **요청 예 ) : @RequestBody byte[] data**
    - **응답 예 ) : @ResponseBody return byte[] : 쓰기 미디어타입 application/octet-stream**
- **StringHttpMessageConverter : String 문자로 데이터를 처리한다.**
    - **클래스 타입 : String, 미디어 타입 : “*/*”**
    - **요청 예 ) : @RequestBody String data**
    - **응답 예 ) : @ResponseBody return “ok” : 쓰기 미디어 타입 text**
- **MappingJackson2HttpMessageConverter : application/json**
    - **클래스 타입 : 객체 또는 HashMap , 미디어 타입 application/json**
        - **클래스 타입 : 객체 또는 HashMap, 미디어 타입 application/json 관련**
        - **요청 예 ) @RequestBody HelloData data**
        - **응답 예 ) @ResponseBody return hellodata 쓰기 미디어타입 application/json 관련**
        

**HTTP 요청 데이터 읽기**

- **HTTP 요청이 오고, 컨트롤러에서 @RequestBody, HttpEntity 파라미터를 사용한다.**
- **메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 canRead()를 호출**
    - **대상 클래스 타입을 지원하는가**
        - **예) @RequestBody 의 대상 클래스 (byte[], String, HelloData)**
    - **HTTP 요청의 Content-Type 미디어 타입을 지원하는가**
        - **예) text/plain, application/json, */***
- **canRead() 조건을 만족하면 read()를 호출해서 객체 생성하고, 반환한다.**

**HTTP 응답 데이터 생성**

- **컨트롤러에서 @ResponseBody, HttpEntity 로 같이 반환된다.**
- **메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 canWrite() 를 호출한다.**
    - **대상 클래스 타입을 지원하는가**
        - **예 ) return의 대상 클래스 (byte[]. String, HelloData)**
    - **HTTP 요청의 Accept 미디어 타입을 지원하는가 (더 정확히는 @ReqeustMapping 의 produces)**
        - **예 ) text/plain, application/json, **/****
- **canWrite() 조건을 만족하면 read()를 호출해서 객체 생성하고, 반환한다.**


</div>
</details>  

<details>
<summary><b>요청 매핑 핸들러 어댑터 구조</b></summary>  

<div markdown="1">  
  
**그렇다면 HTTP 메시지 컨버터는 스프링 MVC어디에서 사용되는 것일까**

**모든 비밀은 애노테이션 기반의 컨트롤러, 즉 @RequestMapping을 처리하는 핸들러 어댑터인 RequestMappingHandlerAdapter 요청 매핑 핸들러 어댑터에 있다.**

**RequestMappingHandlerAdapter 동작 방식**  

![image](https://user-images.githubusercontent.com/106207558/219628758-e6bf96d1-7e39-4bbc-a3f8-57dfb0e6d466.png)  

**ArgumentResolver**

**생각해보면, 애노테션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있었다. HttpServletRequest, Model 은 물론이고, @RequestParam, @ModelAttribute 같은 애노테이션 그리고 @RequestBody, HttpEntity 같은 HTTP 메시지를 처리하는 부분까지 매우 큰 유연함을 보여주었다. 이렇게 파라미터를 유연하게 처리할 수 있는 이유가 ArgumentResolver 덕분이다.**

**애노테이션 기반 컨트롤러를 처리하는 RequestMappingHandlerAdapter 는 바로 이 ArgumentResolver 를 호출하는 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터 값(객체)를 생성한다. 그리고 이렇게 파라미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.**

**스프링은 30개가 넘는 ArgumentResolver 를 기본으로 제공** 

```java
public interface HandlerMethodArgumentResolver {
  boolean supportsParameter(MethodParameter parameter);
  
  @Nullable
  Object resolveArgument(MethodParameter parameter, 
  @Nullable ModelAndViewContainermavContainer,
  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) 
throws Exception;
}
```

**동작 방식**

**ArgumentResolver 의 supportsParameter() 를 호출해서 해당 파라미터를 지원하는지 체크하고 지원하면 resolveArgument() 를 호출해서 실제 객체를 생성한다.**

**ReturnValueHandler**

**HandlerMethodReturnValueHandler 를 줄여서 ReturnValueHandler 라 부른다.**

**ArgumentResolver 와 비슷한데 이것은 응답 값을 변환하고 처리한다.**

**컨트롤러에서 String으로 뷰 이름을 변환해도, 동작하는 이유가 바로 ReturnValueHandler 덕분이다.**
</div>
</details>  
