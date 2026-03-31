# Ground-Zero 🚀

**Ground-Zero**는 SI 프로젝트 투입 시 즉각적으로 활용할 수 있도록 구성된 **Kotlin + Spring Boot + Vue.js** 기반의 마스터 프로젝트(Boilerplate)입니다. 
반복적인 초기 세팅 시간을 단축하고, 검증된 아키텍처와 필수 기능들을 사전 구축하여 비즈니스 로직 개발에 즉시 집중할 수 있는 환경을 제공합니다.

---

## 🛠 Tech Stack

### Back-end
- **Language**: Kotlin 1.9+
- **Framework**: Spring Boot 3.x
- **Data Access**: Spring Data JPA
- **Dynamic Query**: Querydsl (복잡한 검색 및 동적 쿼리 대응)

### Front-end
- **Framework**: Vue.js 3 (Composition API)
- **Build Tool**: Vite
- **State Management**: Pinia
- **HTTP Client**: Axios

### Database
- **Development**: H2 Database (In-Memory)
- **Production**: MySQL / PostgreSQL

---

## ✨ Key Features

Ground-Zero는 실무 환경에서 필수적인 핵심 기능들을 기본적으로 제공합니다:

- **공통 응답 규격 (ApiResponse)**: 일관된 API 응답 포맷(성공/실패, 데이터, 에러 메시지 등)을 제공하여 프론트엔드 연동의 편의성을 극대화합니다.
- **전역 예외 처리 (Global Exception Handler)**: `@RestControllerAdvice`를 활용하여 애플리케이션 전역에서 발생하는 예외를 표준화된 포맷으로 안전하게 처리합니다.
- **Data Auditing (BaseEntity)**: JPA Auditing을 적용하여 엔티티의 생성일(`createdAt`), 수정일(`updatedAt`)을 자동으로 추적 및 기록합니다.
- **JWT 인증 기본 구조**: JSON Web Token을 활용한 Stateless 한 인증/인가 기반 구조가 세팅되어 있어 보안 인증 구현이 용이합니다.

---

## 📂 Project Structure

본 프로젝트는 백엔드와 프론트엔드가 명확히 분리된 구조를 가집니다. 
향후 MSA 확장이나 독립적인 배포 파이프라인 구축에 유리합니다.

```text
ground-zero/
├── backend/                  # Spring Boot 기반 백엔드 애플리케이션
│   ├── src/main/kotlin/      # 애플리케이션 비즈니스 코드 (Controller, Service, Repository 등)
│   ├── src/main/resources/   # 설정 파일 (application.yml 등)
│   └── build.gradle.kts      # 의존성 관리 및 빌드 스크립트
│
├── frontend/                 # Vue.js 3 기반 프론트엔드 애플리케이션
│   ├── src/                  # 컴포넌트, 뷰, 스토어 등 UI 구현부
│   ├── public/               # 정적 애셋 포더
│   ├── vite.config.ts        # Vite 빌드 설정
│   └── package.json          # 프론트엔드 패키지 관리 및 스크립트
│
└── README.md                 # 프로젝트 개요, 기술 스택 및 안내
```

---

## 🚀 Getting Started

*(이후 프로젝트 실제 세팅이 완료되면, 로컬 실행 방법 및 빌드 가이드 등을 여기에 추가로 작성할 예정입니다.)*
