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