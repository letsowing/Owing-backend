```
📁 Project Structure
├── Owing-Api  
│       └── com.owing.api  
│           └── <각 api 별 패키지>
│               └── controller
│               └── dto
│               └── mapper
│               └── service # usecase를 기반으로 한 파사드. 각 DomainService를 사용하여 응답값을 생성하는 서비스.
├── Owing-Batch  # 배치 서비스 어플리케이션
├── Owing-Common # 공통 코드 (에러코드, 예외 등)
├── Owing-Domain:Owing-Entity(RDB) or Owing-Node(GraphDB) 
│       └── com.owing.entity or com.owing.node     
│           ├── common  # 해당 모듈에서 사용되는 공유코드(분산락 aop, 도메인 이벤트)
│           └── domains
│               └── <도메인>  # 각 도메인
│                   └── adaptor # 도메인 Repository를 한번 더 감싼 컴포넌트
│                   └── model # 도메인 엔티티
│                   └── exception # 도메인 예외 정의
│                   └── repostiory # 도메인 Repository
│                   └── service # 도메인 서비스, 도메인 이벤트 핸들러
└── Owing-Infrastructure  # Redis , feignClient(외부 api 콜) , 메일, s3 등.
```