# 코알라 프로젝트

## 프로젝트 기간

2021년 7월 20일 ~

## 프로젝트 내용

키워드 알림 서비스 + 키워드별 채팅

## 프로젝트 세팅

### 사용언어

kotlin

### 디자인 패턴

MVVM

### 코드 컨벤션

기본으로 kotlin 공식 convention에 따라서 진행합니다. ([링크](https://kotlinlang.org/docs/coding-conventions.html))

### ktlint

- [ktlint 세팅](https://github.com/JLLeitschuh/ktlint-gradle)
- ktlint 양식에 맞지 않는 경우 빌드 fail 됩니다.

## 진행 방식

1. 이슈 생성
2. 이슈에 맞는 브랜치 생성
( ex) feature/#9_add_issue_template)
3. 작업 완료 후 PR 요청
4. ktlint check fail, test case fail, build fail 시 수정 후 다시 push 합니다.
