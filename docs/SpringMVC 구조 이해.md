# 스프링 MVC전체 구조 이해

![image](https://user-images.githubusercontent.com/106207558/219010095-4f3bc936-e60f-44b8-8d9c-7495423af551.png)  

**직접만들었던 MVC프레임워크 구조와 SpringMVC프레임워크는 위 사진을 보면 명칭의 차이는 있지만 구조는 변함이 없다.** 

**직접 만든 프레임워크 → 스프링 MVC 비교**

- **FrontController → DispatcherServlet**
- **handlerMappingMap → HandlerMapping**
- **MyHandlerAdapter → HandlerAdapter**
- **ModelView → ModelAndView**
- **viewResolver → ViewResolver**
- **MyView → View**

**DispatcherServlet구조 살펴보기**

**org.springframework.web.servlet.DispatcherServlet**

**스프링 MVC도 프론트 컨트롤러 패턴으로 구현되어 있다.**

**스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿이다.**

**그리고 이 디스패처 서블릿이 바로 스프릴ㅇ MVC의 핵심이다.**

**DispatcherServlet 서블릿 등록**

- **DispatcherServlet 도 부모 클래스에서 HttpServlet 를 상속 받아서 사용하고, 서블릿으로 동작한다.**
    - **DispatcherServlet  → FrameworkServlet → HttpServletBean → HttpServlet**
- **스프링 부트는 내장톰캣을 띄우고 DispatcherServlet 를 서블릿으로 자동으로 등록하면서 모든 경로 (urlPatterns=”/”) 에 대해서 매핑한다.**
    - **참고 : 더 자세한 경로가 우선순위가 높다. 그래서 기존에 등록한 서블릿도 함께 동작한다.**

**요청 흐름** 

- **서블릿이 호출되면 HttpServlet 이 제공하는 Service() 가 호출된다.**
- **스프링 MVC는 DispatcherServlet 의 부모인 FrameworkServlet 에서 Service() 를 오버라이드 해두었다.**
- **FrameworkServlet.service() 를 시작으로 여러 메서드가 호출되면서 DispatcherServlet .doDispatch() 가 호출된다.**

**동작 순서** 

1. **핸들러 조회 : 핸들러 매핑을 통해 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.**
2. **핸들러 어댑터 조회 : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.**
3. **핸들러 어댑터 실행 : 핸들러 어댑터를 실행한다.**
4. **핸들러 실행 : 핸들러어댑터가 실제 행들러를 실행한다.**
5. **ModelAndView 반환 : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다.**
6. **ViewResolver 호출 : 뷰 리졸버를 찾고 실행한다.**
    1. **JSP의 경우 : InternalResourceViewResolver 가 자동으로 등록되고, 사용된다.**
7. **Veiw 반환 : 뷰 리졸버는 뷰의 이름을 물리 이름으로 바꾸고, 렌더링 역활을 담당하는 뷰 객체를 반환한다.**
    1. **JSP의 경우 : InternalResourceViewResolver (JstlView) 를 반환하는데, 내부에 forward() 로직이 있다.**
8. **뷰 렌더링 : 뷰를 통해서 뷰를 렌더링 한다.**

**인터페이스 살펴보기**

- **스프링 MVC의 큰 강점은 DispatcherServlet 코드의 변경 없이, 원하는 기능을 변경하거나 확장할 수 있다는 점이다. 지금까지 학습한 내용 대부분을 확장 가능할 수 있게 인터페이스로 제공한다.**
- **이 인터페이스들만 구현해서 DispatcherServlet 에 등록하면 된다.**

**주요 인터페이스 목록**

- **핸들러 매핑 : org.springframework.web.servlet.HandlerMapping**
- **핸들러 어댑터 : org.springframework.web.servlet.HandlerAdapter**
- **뷰 리졸버 : org.spirngframework.web.servlet.ViewResolver**
- **뷰 : org.springframework.web.servlet.View**

**정리 스프링 MVC는 코드 분량도 매우 많고, 복잡해서 내부 구조를 다 파악하는 것은 쉽지 않다. 사실 해당 기능을 직접 확장하거나 나만의 컨트롤러를 만드는 일은 없다.** 

## **핸들러 매핑과 핸들러 어댑터**

**/springmvc/old-controller 이 컨트롤러가 호출되려면 다음 2가지가 필요** 

- **HandlerMapping(핸들러매핑)**
    - **핸들러 매핑에서 컨트롤러를 찾을 수 있어야 한다.**
    - **예) 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.**
- **HandlerAdapter(핸들러 어댑터)**
    - **핸들러 매핑을 통해서 찾을 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.**
    - **얘) Controller 인터페이스를 실행 할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.**

**스프링은 이미 필요한 핸들러 매핑과 핸들러 어댑터를 대부분 구현해두었다. 개발자가 직접 핸들러 매핑과 핸들러 어댑터를 만드는 일은 거의 없다.**

**스프링 부트가 자동 등록하는 핸들러 매핑과 핸들러 어댑터**

**HandlerMapping**

```java
0 = RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
1 = BeanNameUrlHandlerMapping    : 스프링 빈의 이름으로 핸들러를 찾는다
```

**HandlerAdapter**

```java
0 = RequestMappingHandlerAdapter  : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
1 = HttpRequestHandlerAdapter     : HttpRequestHandler 처리
2 = SimpleControllerHandlerAdapter: Controller 인터페이스(애노테이션X, 과거에 사용) 처리
```

**핸들러 매핑도, 핸들러 어댑터도 모두 순서대로 찾고 만약 없으면 다음 순서로 넘어간다.**

1. **핸들러 매핑으로 핸들러 조회**
    1. **HandlerMapping을 순서대로 실행해서, 핸들러를 찾는다.**
    2. **이 경우 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는 BeanNameUrlHandlerMapping 가 실행에 성공하고 핸들러인 OldController를 반환한다.**
2. **핸들러 어댑터 조회**
    1. **HandlerAdapter 의 supports()를 순서대로 호출**
    2. **SimpleControllerHandlerAdapter가 Controller 인터페이스를 지원하므로 대상이 된다.**
3. **핸들러 어댑터 실행**
    1. **디스패처 서블릿이 조회한 SimpleControllerHandlerAdapter 를 실행하면서 핸들러 정보도 함께 넘겨준다.** 
    2. **SimpleControllerHandlerAdapter  는 핸들러인 OldController를 내부에서 실행하고 그 결과를 반환한다.**

**정리 OldController 핸들러 매핑, 어댑터**

**OldController 를 실행하면서 사용된 객체는 다음과 같다.**

**HandlerMapping = BeanNameUrlHandlerMapping** 

**HandlerAdapter =  SimpleControllerHandlerAdapter**  

## **뷰 리졸버**

**스프링 부트는 InternalResourceViewResolver 라는 뷰 리졸버를 자동으로 등록하는데, 이 때 [application.properties](http://application.properties) 에 등록한 spring.mvc.view.prefix, spring.mvc.view.suffix 설정 정보를 사용해서 등록한다.**

**스프링 부트가 자동 등록하는 뷰 리졸버** 

```java
1 = BeanNameViewResolver         : 빈 이름으로 뷰를 찾아서 반환한다. 
2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.
```

1. **핸들러 어댑터 호출**
- **핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.**
1. **ViewResolver 호출**
- **new-form 이라는 뷰 이름으로 viewResolver 를 순서대로 호출한다.**
- **BeanNameViewResolver 는 new-form 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다.**
- **InternalResourceViewResolver  가 호출된다.**
1. **InternalResourceViewResolver**   
- **이 뷰 리졸버는 InternalResourceView  를 반환한다.**
1. **뷰 - InternalResourceView** 
- **InternalResourceView  는 JSP처럼 포워드 foward() 를 호출해서 처리할 수 있는 경우에 사용한다.**
1. **view.render()** 
- **view.render() 가 호출되고 InternalResourceView 는 foward() 를 사용해서 JSP를 실행한다.**

**참고** 

**다른 뷰는 실제 뷰를 렌더링하지만, JSP의 경우 forward() 를 통해서 해당 JSP로 이동(실행)해야 렌더링이 된다. JSP를 제외한 나머지 뷰 템플릿들은 forward() 과정 없이 바로 렌더링 된다.**

**Thymeleaf뷰 템플릿을 사용하면 ThymeleafViewResolver를 등록해야 한다. 최근에는 라이브러리만 추가하면 스프링 부트가 이런 작업도 모두 자동화해준다.**

## **스프링 MVC - 시작하기**

**스프링이 제공하는 컨트롤러는 애노테이션 기반으로 동작해서, 매우 유연하고 실용적이다. 과거에는 자바 언어에 애노테이션이 없기도 했고, 스프링도 처음부터 이런 유연한 컨트롤러를 제공한 것은 아니다.**

**@RequestMapping**

- **RequestMapingHandlerMapping**
- **RequestMapingHandlerAdapter**

**앞서 보았듯이 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 RequestMapingHandlerMapping, RequestMapingHandlerAdapter 이다.**

**@RequestMapping의 앞글자를 따서 만든 이름인데, 이것이 바로 지금 스프링에서 주로 사용하는 애노테이션 기반의 컨트롤러를 지원하는 핸들러 매핑과 어댑터이다 실무에서는 99.9% 이 방식의 컨트롤러를 사용한다고 한다.**

- **@Controller**
    - **스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트 스캔의 대상이 됨**
    - **스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.**
- **@RequestMapping 요청 정보를 매핑한다. 해당 URL이 호출되면서 이 메서드가 호출된다. 애노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.**
- **ModelAndView : 모델과 뷰 정보를 담아서 반환하면 된다.**

## **스프링 MVC - 컨트롤러 통합**

**@RequestMapping 를 잘 보면 클래스 단위가 아니라 메서드 단위에 적용된 것을 확인 할 수 있다. 따라서 컨트롤러 클래스를 유연하게 하나로 통합할 수 있다.**

- **클래스 레벨 @RequestMapping(”/springmvc/v2/members”)**
    - **메서드 레벨 @RequestMapping(”/new-form”) -> @RequestMapping(”/springmvc/v2/members/new-form”)**
    - **메서드 레벨 @RequestMapping(”/save”) → @RequestMapping(”/springmvc/v2/members/save”)**
    - **메서드 레벨 @RequestMapping  → @RequestMapping(”/springmvc/v2/members”)**

## **스프링 MVC - 실용적인 방식**

**MVC 프레임워크 만들기에서 v3는 ModelView 를 개발자가 직접 생성해서 반환했기 때문에, 불편했던 기억이 있다. 물론 v4를 만들면서 실용적으로 개선한 기억도 있다.**

**스프링 MVC는 개발자가 편리하게 개발할 수 있도록 수 많은 편의 기능을 제공한다.**

**Model 파라미터**

**save(), members() 를 보면 Model 파라미터로 받는 것을 확인 할 수 있다. 스프링 MVC도 이런 편의 기능을 제공한다.**

**ViewName 직접 반환**

**뷰의 논리 이름을 반환할 수 있다.**

**@RequestParam 사용**

**스프링은 HTTP 요청 파라미터를 @RequesetParam 으로 받을 수 있다. @Request(”username”) 은 request.getParameter(”username”) 와 거의 같은 코드라 생각하면 된다. 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다**
