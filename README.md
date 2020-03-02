# spring-security-example

#### AuthenticationManager

#### Spring Security Filter
- 기본적으로 15개의 필터가 존재
- SecurityContextPersistenceFilter
- UsernamePasswordAuthenticationFilter
    - 유저와 비밀번호 검증 필터

#### DelegatingFilterProxy
- 기본적으로 등록된 Filter
- FilterChainProxy
    - DispatcherServlet 느낌


#### AccessDecisionManager
- Access Control 결정을 내리는 인터페이스
    - 추상화 객체 : ㅠ AbstractAccessDecisionManager
    - AffirmativeBased : 1명이라도..
    - ConsensusBased : 다수결
    - 만장일치도 있음
- AccessDecisionVoter
    - Resource에 필요한 조건을 만족하는지 확인하는 책임
    - WebExpressionVoter 

    
#### FilterSecurityInterceptor
- AbstractSecurityInterceptor
- AccessDescisionManager 처리

#### ExceptionTranslationFilter
- AuthenticationException
    - AuthenticationEntryPoint
- AccessDeniedException
    - 익명사용자 -> AuthenticationEntryPoint
    - 사용자 -> AccessDeniedHandler 위임
