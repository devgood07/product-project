# [MUSINSA] Java Backend Engineer - 과제

 
## 개발환경
- Sdk 및 Language level : Java17
- Spring Boot 3.3.5
- Gradle
- 로컬DB(H2) (mysql)
  
## 구현범위

### API 명세
- Swagger를 통해 API 명세를 확인할 수 있습니다.
- http://localhost:8080/swagger-ui.html

### 요구사항 제한 조건 및 상세 설명
  1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 
     1. 단, 최저 가격인 브랜드가 여러개인 경우, 가장 최근에 등록된 브랜드를 선택합니다.
  2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API 
  3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 
  4. 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API
     1. 브랜드 추가
        1. 신규 브랜드 이름을 필수 입력받아 브랜드 이름을 업데이트합니다.
           - ```
             {
             "action": "add_brand",
             "brandName": "X"
             }
             ```
     3. 브랜드 업데이트
        1. 브랜드 id, 신규 브랜드 이름을 필수 입력받아 브랜드 이름을 업데이트합니다.
           - ```
             {
             "action": "update_brand",
             "brandId": 1,
             "brandName": "NEWBRANDNAME",
             }
             ```
     4. 브랙드 삭제
        1. 브랜드 id를 필수 입력받아 브랜드를 삭제합니다.
           -  ```
              {
               "action": "delete_brand",
               "brandId": 1,
               }
              ```
        2. 단, 브랜드에 속한 상품이 존재하는 경우, 브랜드를 삭제할 수 없습니다.
     5. 상품 추가
        1. 가격, 브랜드 이름, 카테고리 이름을 필수 입력받아 상품을 추가합니다.
            - ```
                {
                "action": "add_product",
                "categoryName": "상의",
                "brandName": "A",
                "price": "12000"
                }
              ```
        2. 기존에 브랜드가 없으면 신규 추가한뒤, 상품을 추가합니다.
        3. 기존에 존재하는 8개 카테고리 중 하나를 선택하여 상품을 추가합니다.
     6. 상품 업데이트
        1. 상품 id를 필수 입력받아 가격, 카테고리 이름, 브랜드 이름을 입력받아 업데이트합니다.
           - ```
              {
              "action": "update_product",
              "productId": 1,
              "price": "20000"
               }
             ```
           - ```
             {
             "action": "update_product",
             "productId": 1,
             "categoryName": "바지"
             }
             ```
           - ```
             {
             "action": "update_product",
             "productId": 1,
             "brandName": "UPDATERANDNAME"
             }
             ```
           - ```
             {
             "action": "update_product",
             "productId": 1,
             "categoryName": "모자",
             "brandName": "C",
             "price": "100"
             }
             ```
        2. 브랜드가 존재 하지 않을 경우, 신규 브랜드를 추가한뒤, 상품을 업데이트합니다.
        3. 존재 하지 않는 카테고리로 입력 할 경우, 업데이트를 거부합니다.
     7. 상품 삭제
        1. 상품 id를 필수 입력받아 상품을 삭제합니다.
           - ```
             {
              "action": "delete_product",
               "productId": 1
             }
             ```
        2. 브랜드 명을 추가로 입력받을 경우, 해당 브랜드의 상품을 모두 삭제합니다.
           - ```
             {
             "action": "delete_product",
             "brandId": 2
             }
              ```        
### 패키지 구조
현재 프로젝트의 패키지 구조는 다음과 같습니다:
- com.musinsa.product
  - controller : API 요청을 처리하는 컨트롤러 클래스
  - entity : JPA 엔티티 클래스
  - repository : JPA 레포지토리 인터페이스
  - service : 비즈니스 로직을 처리하는 서비스 클래스
  - util : 유틸리티 클래스
  - validation : API 요청에 대한 검증을 처리하는 클래스 

## 코드 빌드
Gradle 을 통한 빌드를 수행합니다.
- Preferences > Build, Execution, Deployment > Compiler > Annotation Processors Enable annotation processing 체크
- query dsl 사용하기 위한 Q 파일 생성 build
  - intellij > gradle에서 Tasks > other > compileQuerydsl

## 실행 방법
빌드 후에는 다음과 같이 실행할 수 있습니다.
- `product/src/main/java/com/musinsa/product/ProductApplication.java` > Run 실행하면, H2 db에 초기 데이터(JPA entity 기반)가 생성되며, 서버가 실행됩니다.
  - 브라우저에서 swagger url 접속하여 API 명세를 확인할 수 있습니다.
    - http://localhost:8080/swagger-ui.html
  - api 호출 가능합니다. (postman, curl, etc..)
  - `com.musinsa.product.entity` 패키지 내 엔티티 클래스들을 기반으로 테이블이 생성됩니다.
  - `product/src/main/resources/import.sql` 파일을 통해 초기 데이터가 생성됩니다.
  - H2 DB 접속 가능합니다.
      - http://localhost:8080/h2-console
      - JDBC URL : jdbc:h2:mem:test
      - User Name : sa
      - Password :

## 테스트 방법
### Integration test 
  - h2 db를 사용하여 application.yml (active profile : default) 설정으로 자동으로 초기 데이터가 저장 후 Spring boot 서버 실행과 함께 테스트 됩니다.
    - `product/src/test/java/com/musinsa/product/controller/*.java` > Run
    - `product/src/test/java/com/musinsa/product/service/data/*.java` > Run

### Unit test
  - `product/src/test/java/com/musinsa/product/repository/*.java` > Run
    - h2 db를 사용하여 application-test.yml (active profile : test) 설정으로 자동으로 초기 데이터가 저장 후 실행 됩니다.
  - `product/src/test/java/com/musinsa/product/service/*.java` > Run
  - `product/src/test/java/com/musinsa/product/util/*.java` > Run
  - `product/src/test/java/com/musinsa/product/validation/*.java` > Run

