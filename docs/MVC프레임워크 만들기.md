# MVC 프레임워크 만들기

## 프론트 컨트롤러 패턴 소개

### FrontController 패턴 특징

- **프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음**
- **프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출**
- **입구를 하나로!**
- **공통 처리 가능**
- **프론트 컨트롤러를 제외한 나머지 컨트롤러라는 서블릿을 사용하지 않아도 됨**

### **스프링 웹 MVC와 프론트 컨트롤러**

**스프링 웹 MVC의 핵심도 바로 FrontController**

**스프링 웹 MVC의 DispatcherServlet가 FrontController 패턴으로 구현되어 있음** 

## 프론트 컨트롤러 도입 - v1
![image](https://user-images.githubusercontent.com/106207558/218531715-d73fc9d0-b951-48bb-bdfe-136fadcb020c.png)  
**서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입 후 각 컨트롤러들은 이 인터페이스를 구현하면 된다. 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있다.**

**urlPatterns**

- **urlPattterns = “/front-controller/v1/*” :  /front-controller/v1 을 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.**
- **예)** `/front-controller/v1` , `/front-controller/v1/a` , `/front-controller/v1/a/b`

**controllerMap**

- **key : 매핑 URL**
- **value : 호출된 컨트롤러**

**service()**

**먼저** `requestURI` **를 조회해서 실제 호출할 컨트롤러를** `ControllerMap` **에서 찾는다 만약 없다면 404(SC_NOT_FOUND)상태 코드를 반환한다. 컨트롤러를 찾고** `controller.process(request, response);` **을 호출해서 해당 컨트롤러를 실행한다.**

**JSP**

**JSP는 이전 MVC에서 사용한 것을 그대로 사용한다.**

## View 분리 - v2

**모든 컨트롤러에서 뷰로 이동하는 부분에 중복이 있고, 깔끔하지 않다.**

```java
String viewPath = ("/WEB-INF/views/new-form.jsp");
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response);
```

**이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만들자.**  
![image](https://user-images.githubusercontent.com/106207558/218532046-b2d0a9e0-6e9b-4f81-80ef-454ef7cbc8b3.png)  
**프론트 컨트롤러의 도입으로 MyView 객체의 render를 호출하는 부분을 모두 일관되게 처리할 수 있다. 각각의 컨트롤러는 MyView객체를 생성만해서 반환하면 된다.**

## Model 추가 - v3

**서블릿 종속성 제거**

**컨트롤러 입장에서 HttpServletRequest, HttpServletResponse가 꼭 필요할까?**

**요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다. 그리고 request객체를 Model로 사용하는 대신에 별도의 Model객체를  만들어서 반환하면 된다. 우리가 구현하는 컨트롤러가 서블릿 기술을 전혀 사용하지 않아록 변경해보자. 이렇게 하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.**

**뷰 이름 중복 제거**

**컨트롤러에서는 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있다. 컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 단순화 하자. 이렇게 해두면 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러에서만 고치면 된다.**

- **/WEB-INF/views/new-form.jsp → new-form**
- **/WEB-INF/views/save-result.jsp → save-result.jsp**
- **/WEB-INF/views/members.jsp → members.jsp**

![image](https://user-images.githubusercontent.com/106207558/218532144-c47221a7-78e1-4c3e-81dc-ef168506e7aa.png)  
**ModelView**

**지금까지 컨트롤러에서 서블릿에 종속적인 HttpServletRequest를 사용했다. 그리고 Model도 reqeust.setAttribute()를 통해 데이터를 저장하고 뷰에 전달했다. 서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어보자. (이번 버전에서는 컨트롤러에서 HttpServletRequest를 사용할 수 없다. 따라서 직접 request.setAttribute() 를 호출할 수도 없다. 따라서 Model이 별도로 필요하다)**

**viewResolver**

**MyView view = viewResolver(viewName)**

**컨트롤러가 반환한 논리 뷰 이름을 실제 뷰 경로로 변경한다. 그리고 실제 물리 경로가 있는 MyView 객체를 반환한다.**

- **논리 뷰 이름 : members**
- **논리 뷰 경로 : /WEB-INF/views/members.jsp**
- **view.render(mv.getModel(), request, response)**
- **뷰 객체를 통해서 HTML화면을 렌더링 한다.**
- **뷰 객체의 render()는 모델 정보도 함께 받는다.**
- **JSP는 request.getAttribute() 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서 reqeust.setAttribute() 로 담아둔다.**
- **JSP로 포워드 해서 JSP를 렌더링 한다.**

## 단순하고 실용적인 컨트롤러 - v4

**앞서 만든 v3컨트롤러는 서블릿 종속성을 제거하고 뷰 경로의 중복을 제거하는 등, 잘 설계된 컨트롤러이다. 그런데 컨트롤러 인터페이스를 구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 부분이 조금 번거롭다. 좋은 프레임워크 아키텍처도 중요하지만, 그와 더불어 실제 개발하는 개발자가 단순하고 편리하게 사용할 수 있어야 한다.**  
![image](https://user-images.githubusercontent.com/106207558/218532281-2111f3ad-a73d-49bb-a75b-90ec3d23b0ff.png)  

- **기본적인 구조는 V3와 같다. 대신에 컨트롤러가 ModelView를 반환하지 않고, ViewName만 반환한다.**

**이번 버전은 인터페이스에 ModelView가 없고 model객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 된다.**

**모델 객체 전달**

**Map<String, Object> model = new HashMap();**

**모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다. 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 된다.**

**뷰의 논리 이름을 직접 반환**

```java
String viewName = controller.process(paramMap, model);
MyView view = viewResolver(viewName);
```

**컨트롤러가 직접 뷰의 논리 이름을 반환하므로 이 값을 사용해서 실제 물리 뷰를 찾을 수 있다.**

**정리**

**이번 버전의 컨트롤러는 매우 단순하고 실용적이다. 기존 구조에서 모델을 파라미터로 넘기고, 뷰의 논리 이름을 반환한다는 작은 아이디어를 적용했을 뿐인데, 컨트롤러에서 구현하는 개발자 입장에서 보면 이제 군더더기 없는 코드를 작성할 수 있다.또한 중요한 사실은 여기까지 한번에 온 것이 아니라는 점이다. 프레임워크가 점진적으로 발전하는 과정속에서 이런 방법도 찾을 수 있었다.**

## 유연한 컨트롤러1 -v5

**만약 어떤 개발자는 Controller3 방식으로 개발하고 싶고, 어떤 개발자는 ControllerV4방식으로 개발하고 싶다면 어떻게 해야 할까?**

```java
public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
```

```java
public interface ControllerV4 {
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
```

**어댑터 패턴**

**지금까지 우리가 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있었다. ControllerV3, ControllerV4 는 완전히 다른 인터페이스이다. 따라서 호환이 불가능하다. 마치 v3는 110v이고 ,v4는 220v 전기 콘센트 같은 것이다. 이럴 때 사용하는 것이 바로 어댑터이다. 어댑터 패턴을 사용해서 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경해보자.**  

![image](https://user-images.githubusercontent.com/106207558/218532451-8ba39f48-c186-4815-adda-1b94b82dc119.png)  

**핸들러 어댑터 : 중간에 어댑터 역활을 하는 어댑터가 추가되었는데 이름이 핸들러 어댑터이다. 여기서 어댑터 역활을 해주는 덕분에 다양한 종류의 컨트롤러를 호출할 수 있다.** 

**핸들러 : 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경했다 .그 이유는 이제 어댑터가 있기 때문에 꼭 컨트롤러의 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문이다.** 

- **boolean supports(Object handler)**
    - **handler는 컨트롤러를 말함**
    - **어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드다**
- **ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)**
    - **어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.**
    - **실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환해야 한다.**
    - **이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제는 이 어댑터를 통해서 실제 컨트롤러가 호출된다.**

## **유연한 컨트롤러2 - v5**

**핸들러 매핑 (’hnalderMappingMap’)에 ControllerV4를 사용하는 컨트롤러를 추가하고, 해당 컨트롤러를 처리할 수 있는 어댑터인 ControllerV4HandlerAdapter도 추가**

## **정리**

**지금까지 v1 ~ v5까지 점진적으로 프레임워크를 발전시켜 왔따** 

**지금까지 한 작업을 정리해보자.**

- **v1 : 프론트 컨트롤러 도입**
    - **기존 구조를 최대한 유지하면서 프론트 콘트롤러를 도입**
- **v2 : View 추가**
    - **단순 반복 되는 뷰 로직 분리**
- **v3 : Model 추가**
    - **서블릿 종속성 제거**
    - **뷰 이름 중복 제거**
- **v4 : 단순하고 실용적인 컨트롤러**
    - **v3와 거의 비슷**
    - **구현 입장에서는 ModelView를 직접 생성하지 반환하지 않도록 편리한 인터페이스 제공**
- **v5 : 유현한 컨트롤러**
    - **어댑터 도입**
    - **어댑터를 추가해서 프레임워크를 유연하게 확장성 있게 설계**

**여기에 애노테이션을 사용해서 컨트롤러를 더 편리하게 발전시킬 수도 있다. 만약 애노테이션을 사용해서 컨트롤러를 편리하게 사용할 수 있게 하려면 어떻게 해야 할까? 바로 애노테이션을 지원하는 어댑터를 추가하면 된다. 다형성과 어댑터 덕분에 기존 구조를 유지하면서, 프레임워크의 기능을 확장할 수 있다.** 

**스프링 MVC**

**여기서 더 발전시키면 좋겠지만, 스프링 MVC의 핵심 구조를 파악하는데 필요한 부분을 모두 만들어보았다. 사실은 지금까지 작성한 코드는 스프링 MVC 프레임워크의 핵심 코드의 축약 버전이고, 구조도 거의 같다. 스프링 MVC에는 지금까지 우리가 학습한 내용과 거의 같은 구조를 가지고 있다.**
