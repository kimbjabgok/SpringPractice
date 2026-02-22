# Spring Boot 기반으로 구현한 학생 정보 관리 REST API

## 기술 스택
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- JSoup (HTML 파싱)
- Maven

## 주요 기능
- HTML 페이지에서 학생 데이터 자동 파싱 및 DB 저장
- 학생 학위 조회
- 학생 이메일 조회
- 학위별 학생 수 통계
- 신규 학생 등록

## API 명세
- GET/students/degree학생 학위 조회
- GET/students/email학생 이메일 조회
- GET/students/stat학위별 학생 수 조회
- PUT/students/register신규 학생 등록

## 실행 환경
- Java 17
- PostgreSQL 5432
- 서버 포트 8082
- DB명: hongik
