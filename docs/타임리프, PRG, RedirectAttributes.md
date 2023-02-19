# 스프링 MVC 웹페이지 만들기

### 타임리프 간단히 알아보기

**타임리프 사용 선언**

**<html xmlns:th=”http://www.thymeleaf.org”>**

**속성 변경 - th:href**

**th:href=”@{/css/bootstrap.min.css}”**

- **href=”value” 를 th:href=”value2” 의 값으로 변경한다.**
- **타임리프 뷰 템플릿을 거치게 되면 원래 값을 th:xxx 값으로 변경한다. 만약 값이 없다면 새로 생성한다.**
- **HTML을 그대로 볼 때는 href 속성이 사용되고, 뷰 템플릿을 거치면 th:href의 값이 href로 대체되면서 동적으로 변경할 수 있다.**
- **대부분의 HTML 속성을 th:xxx로 변경할 수 있다.**

**타임리프 핵심**

- **핵심은 th:xxx 가 붙은 부분은 서버사이드에서 렌더링 되고, 기존 것을 대체**
- **HTML을 파일로 직접 열었을 때, th:xxx 가 있어도 웹 브라우저는 th: 속성을 알지 못하므로 무시한다.**
- **따라서 HTML을 파일 보기로 유지하면서 템플릿 기능도 할 수 있다.**

**URL링크 표현식 - @{…}**

**th:href=”@{/css/bootstrap.min.css}**

- **@{..} 타임리프는 URL링크를 사용하는 경우 @{…} 을 사용한다. 이것을 URL 링크 표현식 이라고 한다.**
- **URL 링크 표현식을 사용하면 서블릿 컨텍스트를 자동으로 포함한다.**

**상품 등록폼으로 이동**

**속성 변경 - th:onclick**

- **onclick=”location.href=’addForm.html’”**
- **th:onclick=”|location.href=’@{/basic/items/add}’|”**

**여기에는 다음에 설명하는 리터럴 대체 문법이 사용되었다.**

**리터럴 대체 “|…|”**

**|…| 이렇게 사용한다.**

- **타임리프에서 문자와 표현식 등은 분리되어 있기 때문에 더해서 사용해야 한다.**
    - **<span th:text=”’Welcome to our application, ‘ + ${user.name} + ‘!’”>**
- **다음과 같이 리터럴 대체 문법을 사용하면, 더하기 없이 편리하게 사용할 수 있다.**
    - **<span th:text=”| Welcome to our application, ${user.name}! |”>**

- **결과를 다음과 같이 만들어야 하는데**
    - **‘location.href=’/basic/items/add’**
- **그냥 사용하면 문자와 표현식을 각각 따로 더해서 사용해야 하므로 다음과 같이 복잡해진다.**
    - **th:onclick=”’location.href=’ + ‘\’ ‘ + @{/basic/items/add} + ‘\’ ‘ ”**
- **리터럴 대체 문법을 사용하면 다음과 같이 편리하게 사용할 수 있다.**
    - **th:onclick=”| location.href=’@{/basic/items/add}’ |”**
    

**반복 출력 - each**

- **<tr th:each=”item : ${items}”>**
- **반복은 th:each 를 사용한다. 이렇게 하면 모델에 포함된 items 컬렉션 데이터가 item 변수에 하나씩 포함되고, 반복문 안에서 item 변수를 사용할 수 있다.**
- **컬렉션의 수 만큼 <tr>..</tr> 이 아휘 태그를 포함해서 생성된다.**

**변수 표현식 - ${…}**

- **<td th:text=”${item.price}”>10000</td>**
- **모델에 포함된 값이나, 타임리프 변수로 선언한 값을 조회할 수 있다.**
- **프로퍼티 접근법을 사용한다. (’item.getPrice()’)**

**내용변경 - th:text**

- **<td th:text=”${item.price}”>10000</td>**
- **내용의 값을 th:text 의 값으로 변경한다.**
- **여기서는 10000을 ${item.price} 의 값으로 변경한다.**

**URL링크 표현식 2 - @{…}**

- **th:href=”@{/baisc/items/{itemId} (itemId=${item.id})}”**
- **상품 ID를 선택하는 링크를 확인해보자**
- **URL 링크 표현식을 사용하면 경로를 템플릿처럼 편리하게 사용할 수 있다.**
- **예) th:href=”@{/basic/items/{itemId} (itemId = ${item.id}, query = ‘test’)}”**
    - **생성 링크 : [http://localhost:9000/basic/items/1](http://localhost:9000/basic/items/1)?query=test**

**URL 링크 간단히** 

`**"@{/baisc/items/{itemName} (itemName=${item.itemName})}"**`

- **th:href=”@{|/basic/items/${item.id}|}”**
- **리터럴 대체 문법을 활용해서 간단히 사용할 수도 있다.**

**참고** 

**타임리프는 순수 HTML 파일을 웹 브라우저에서 열어도 내용을 확인할 수 있고, 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과를 확인할 수 있다. JSP를 생각해보면 JSP파일은 웹 브라우저에서 그냥 열면 JSP코드와 HTML이 뒤죽박죽 되어야 정상적인 확인이 불가능하다. 오직 서버를 통해서 JSP를 열어야 한다.**

**이렇게 순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용할 수 있는 타임리프의 특징을 네츄럴 템플릿이라 한다.**

**속성 변경 - th:value**

**th:value=”${item.id}”**

- **모델에 있는 item 정보를 획득하고 프로퍼티 접근법으로 출력한다. item.getId()**
- **value 속성을 th:value 속성으로 변경한다.**

**상품수정 링크**

**th:onclick=”|location.href=’@{/basic/items/{itemId}/edit (itemId=${item.id})}’|”**

**속성 변경 - th:action**

- **th:action**
- **HTML From 에서 action에 값이 없으면 현재 URL에 데이터를 전송한다.**
- **상품 등록 폼의 URL과 실제 상품 등록을 처리하는 URL을 똑같이 맞추고 HTTP 메서드로 두 기능을 구분한다.**
    - **상품 등록 폼 : GET /baisc/items/add**
    - **상품 등록 처리 : POST /basic/items/add**
- **이렇게 하면 하나의 URL 등록 폼과, 등록 처리를 깔끔하게 처리할 수 있다.**

**취소**

- **취소시 상품 목록으로 이동한다.**
- **th:onclick=”|location.href=’@{/basic/items)’|”**

**상품 등록 처리 - @ModelAttribute**

**@RequestParam 으로 변수를 하나하나 받아서 Item 을 생성하는 과정은 불편했다.**

**그래서 @ModelAttribute를 사용해서 처리 해보았다.**

**@ModelAttribute - 요청 파라미터 처리**

**@ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.** 

**@ModelAttribute - Model 추가**

**@ModelAttribute 는 중요한 한가지 기능이 더 있는데, 바로 모델(Model)에 @ModelAttribute로 지정한 객체를 자동으로 넣어준다. 지금 코드를 보면 model.addAttribute(”item”, item) 가 주석처리 되어 있어도 잘 동작하는 것을 확인할 수 있다.**

**모델에 데이터를 담을 때는 이름이 필요하다. 이름은 @ModelAttribute 에 지정한 name(value) 속성을 사용한다. 만약 다음과 같이 @ModelAttribute의 이름을 다르게 지정하면 다른 이름으로 모델에 포함된다.**

**@ModelAttribute(”hello”) Item item → 이름을 hello로 지정**

**model.addAttribute(”hello”, item); 모델에 hello 이름으로 저장**

### **PRG Post/Redirect/Get**

**사실 지금까지 진행한 상품 등록 처리 컨트롤러는 심각한 결함이 있다. (addItemV1 - addItemV4)    상품 등록을 완료하고 웹 브라우저의 새로고침 버튼을 클릭하면 상품이 계속해서 중복 등록되는 것을 확인할 수 있다.**  
  ![image](https://user-images.githubusercontent.com/106207558/219943378-4eaf4917-d9c5-42f9-9a19-b71b8449771d.png)  

**상품 등록 폼 → 상품 저장 → 상품저장(내부 호출 상품 상세 View호출) URL은 상품 저장했던 곳으로 남아있는 것**  
  ![image](https://user-images.githubusercontent.com/106207558/219943389-4efb59a9-2ad3-4e7a-9a05-a7a5491e5052.png)  
  **웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송한다.**

**상품 등록 폼에서 데이터를 입력하고 저장을 선택하면 POST /add + 상품데이터를 서버로 전송한다.**

**이 상태에서 새로 고침을 또 선택하면 마지막에 전송한 POST /add + 상품 데이터를 서버로 다시 전송하게 된다.**

**그래서 내용은 같고 ID만 다른 상품 데이터가 계속 쌓이게 된다.**

**즉 내가 마지막에 한 행위를 계속 반복 하는 것** 

**해결 방법**  
  ![image](https://user-images.githubusercontent.com/106207558/219943422-1abdd691-9d7d-49fa-8e19-f975c16f189a.png)  
  **웹 브라우저의 새로 고침은 마지막에 서버에 전송한 데이터를 다시 전송한다. 새로 고침 문제를 해결 하려면 상품 저장 후에 뷰 템플릿으로 이동하는 것이 아니라, 상품 상세 화면으로 리다이렉트 호출해주면된다. 웹 브라우저는 리다이렉트의 영향으로 상품 저장 후에 실제 상품 상세 화면으로 다시 이동 따라서 마지막에 호출한 내용이 상품 상세 화면인 GET/items/{id}가 되는 것이다.**

**이러한 패턴을 PRG패턴 이라고 한다.**

**주의** 

**“redirect:/basic/items/” + item.getId()    redirect에서 + Item.getId() 처럼 URL변수를 더해서 사용하는 것은 URL인코딩이 안되기 때문에 위험. RedirectAttriutes를 사용할 것** 

### **RedirectAttributes**

**상품을 저장하고, 상품 상세 화면으로 리다이렉트 한 것 까지는 좋았다 그런데 고객 입장에서 저장이 잘 된 것인지 안 된 것인지 확신이 않는다.  그래서 저장이 잘 되었으면 상품 상세 화면에 저장되었습니다 라는 메시지를 보여달라는 요구사항이 있을 경우 해결 해보자.**

**RedirectAttributes 를 사용하면 URL 인코딩도 해주고, pathVarible, 쿼리 파라미터까지 처리해준다.**

- **redirect:/basic/items/{itemId}**
    - **pathVariable 바인딩 : {itemId}**
    - **나머지는 쿼리 파라미터로 처리 : ?status=true**

**뷰 템플릿 메시지 추가**

- **th:if : 해당 조건이 참이면 실행**
- **${param.status} : 타임리프에서 쿼리 파라미터를 편리하게 조회하는 기능**
    - **원래는 컨트롤러에서 모델에 직접 담고 값을 꺼내야 한다. 그런데 쿼리 파라미터는 자주 사용해서 타임리프에서 직접 지원한다.**



