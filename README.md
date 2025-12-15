# 📌 TaskFlow Backend Project

기업용 태스크 관리 시스템 TaskFlow의 백엔드 서버를 구현한 프로젝트입니다.

### Author : 
- [Spring 9] LSJ 7조
- 백은서, 신호윤, 원민영, 이서준, 최정혁
---
# 📖 프로젝트 소개
TaskFlow는 팀 단위로 업무(Task)를 관리하고 <br>
댓글을 통한 협업, 대시보드 통계, 활동 로그를 제공하는 기업용 태스크 관리 시스템입니다.

본 프로젝트는 프론트엔드가 이미 완성된 상태에서 <br>
REST API 기반 백엔드 서버를 외주 형태로 개발하는 것을 목표로 진행되었습니다.
---
# 🎯 프로젝트 목표
- Spring Boot 기반의 안정적인 REST API 서버 구축
- JWT 기반 인증 / 인가 처리
- 사용자 권한에 따른 접근 제어
- 팀 & 작업 & 댓글 관리 기능 제공
- 대시보드용 통계 데이터 제공
- AOP 기반 활동 로그 기록
---
# ✅ 주요 기능
## 사용자 & 인증
- 회원 가입 / 로그인
- JWT 토큰
- 권한 기반 접근 제어 (ADMIN/USER)
## 멤버 & 팀 관리
- 팀 생성, 수정, 삭제
- 팀원 초대, 제외
## 작업(Task) 관리
- 작업 생성, 조회, 수정, 삭제
- 작업 상태 변경
- 담당자 지정 및 마감일 지정
## 댓글 기능
- 작업에 댓글 작성, 조회, 삭제
## 대시보드 및 통계
- 전체 작업 개수 확인
- 완료, 진행중, 기한 초과 확인
- 사용자별 작업 현환 확인
## 통합 검색
- 작업 명, 팀 명, 사용자 명 검색
## 활동 로그
- 사용자 행동 이력 기록
  - Task: 생성, 수정, 삭제, 상태 변경
  - Comment: 작성, 수정, 삭제
---
# API 명세서 분석
## Entity 도출 및 비교 테이블 명세서

 https://teamsparta.notion.site/API-Entity-2c32dc3ef5148066a94be816b76ceeab

## ERD
<img width="2084" height="865" alt="image" src="https://github.com/user-attachments/assets/35994306-4769-4a26-b706-bd657235fb02" />

## API 명세서
 https://teamsparta.notion.site/TaskFlow-API-2c32dc3ef51481139566e0201d71fe44
---
# 아키텍처 구조
 <img width="1767" height="517" alt="image" src="https://github.com/user-attachments/assets/2055c01e-a83c-404c-a13c-11cbec63239d" />

---
# 기술 스택
- Language : Java 17
- SpringBoot v3.5.8
- Build.Grald
- DB : MySQL
- ORM : Spring Data JPA
- Docker

---
# 테스트
- POSTMAN
- 단위 테스트 (JUnit)
- Docker기반 프론트엔드 연동 검증 완료

---
# 개발 기간
- 2025.12.08 ~ 2025.12.15

---
# 시연 영상

https://www.notion.so/teamsparta/2bc2dc3ef514810b820ac6c20e864361
---
# 프로젝트 정리본

### https://www.canva.com/design/DAG7Rt0OTAI/pQrGrO2Jjzc1MiG7nB_sTQ/view?utm_content=DAG7Rt0OTAI&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h299075f7f0https://www.canva.com/design/DAG7Rt0OTAI/pQrGrO2Jjzc1MiG7nB_sTQ/view?utm_content=DAG7Rt0OTAI&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h299075f7f0
