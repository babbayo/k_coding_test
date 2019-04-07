# k_coding_test

### 개발 프레임워크 
spring boot (2.1.3)

### 문제해결 전략 
1. GIT 세팅
2. Spring boot 세팅
3. Program 을 추가/조회/수정 할 수 있는 API 개발
4. 그 외 요구 사항들에 http rea, res 포맷만 맞는 API 개발
5. 주요 계산 하는 기능들을 세분화하여 Test Case 를 만들고 디벨롭해 나감
  - JPA 를 활용하여 1:N, N:1, N:N 관계를 정의하고, 엔티티와 벨류 객체를 정의함
  - '지역정보'을 정의하고 구현
  - '키워드' 가중치 부여
  
### 문제해결 전략 상세 (참고사항)

    ```
    주소 값이 "강원도 속초, 양양, 고성" 인 case 를 보고,
    Program('프로그램')이 Region('지역') 이랑 1:N관계를 갖는다고 생각함.
    (예시: "강원도 속초, 양양, 고성" -> ["강원도 속초", "강원도 양양, "강원도 고성"])
    
    여러 프로그램들이 동일한 주소를 갖는 case를 보고,
    Region('지역')이 Program('프로그램') 들을 갖아야 하므로 N:N관계로 가야겠다고 생각함
    
    '남해'로 검색시 '경상남도 남해군', '경상남도 남해군 상주면 상주리' 가 모두 검색 되어야 한다는 걸 알고,
    '경상남도'로 검색시 '경상남도 남해군', '경상남도 남해군 상주면 상주리' 가 모두 조회 되어야 한다는 걸 알고,
    Region('지역') 을 주소의 한 단어 단위로 정의하되, 
    parent, child 필드를 만들어 recursive 하게 full-name 을 언제들 출력할 수 있도록 해야겠다고 판단
    (또한 DB 에 데이터 중복을 피하기 위한 좋은 방안이자, region 을 추후 좀더 유연하게 사용하려면 tree 구조를 이뤄야 겠다고 판단)
    따라서, Region('지역')은 parent 을 N:1, child 를 1:N 관계를 갖도록 해야 겠다고 생각함
    
    주소가 관련 기능의 복잡도가 늘어남에 따라 Program 객체에서 Address 를 분리해야 겠다고 생각함
    이때 Address 는 Program 에 1:1 매핑 이라 판단했지만, 
    Address 는 (추가/삭제 주기를 고려했을때) 독립적인 id 가 필요하지 않다고 생각되어 엔티티가 아닌 벨로 객체로 정의함
    ```
    
### 빌드 및 실행 방법
```bash
# 1. 서버 빌드 및 실행 (jdk 1.8 이상 부터 실행 가능)
> ./gradlew bootRun

# 2. API 참고 하여 API 테스트 (https://github.com/babbayo/k_coding_test/blob/master/rest.http)
> curl --header "Content-Type: application/json" \
  --request GET \
  --data '{"region": "평창군"}' \
  http://localhost:8080/search/summary
```