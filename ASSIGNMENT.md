# kotlin-calculator-precourse

## 테스트 실행
```text
.\gradlew.bat clean test
```

## 과제 진행 요구 사항
- [문자열 덧셈 계산기](https://github.com/woowacourse-precourse/kotlin-calculator-8) 저장소를 포크하고 클론으로 시작
- 기능 구현 전 `README.md`에 정리한 기능 목록 단위 추가
- [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고하여 커밋 메시지 작성
  ```text
  <type>(<scope>): <subject>
  <BLANK LINE>
  <body>
  <BLANK LINE>
  <footer>
  ```
  - Allowed `<type>`
    - feat
    - fix
    - docs
    - style
    - refactor
    - test
    - chore
  - Allowed `<scope>`
    - 커밋 변경 범위
    - EX) \$location, \$browser, \$compile, $rootScope, ngHref, ngClick, ngView
  - `<subject>` text
    - 현재 시제 사용한 변경 사항 작성 (과거형, 미래형 금지)
    - 첫 글자 대문자 사용 금지
    - 끝에 점(.) 작성 X
  - Message body
    - 변화에 대한 동기 등 작성
    - 여기서도 과거형, 미래형 금지
  - Message Footer
    - 모든 호환이 깨지는 변경 사항 명시
    - 변경 내용의 설명
    - 변경의 이유
    - 이전 코드에서 새 버전으로 옮길 때 필요한 안내

## 기능 요구 사항
> 입력한 문자열에서 숫자를 추출하여 더하는 계산기 구현

- 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열 전달 시 구분자를 기준으로 분리한 각 숫자의 합을 반환
    - Ex) "" => 0, "1,2" => 3, "1,2,3" => 6, "1,2:3" => 6
- 앞의 기본 구분자(쉼표, 콜론) 외 커스텀 구분자를 지정할 수 있음.
    - 커스텀 구분자는 "//"와 "\n" 사이에 위치한 문자를 커스텀 구분자로 사용
    - Ex) "//;\n1;2;3"의 경우 //와 \n 사이에 있는 ;이 커스텀 구분자로 사용됨
- 사용자가 잘못된 값을 입력할 경우 'IllegalArgumentException'을 발생시킨 후 코드 종료

## 입출력 요구 사항
### 입력
- 구분자와 양수로 구성된 문자열

### 출력
- 덧셈 결과
```text
결과 : 6
```
- 실행 결과 예시
```text
덧셈할 문자열을 입력해 주세요.
1,2:3
결과 : 6
```

## 프로그래밍 요구 사항
- 실행 환경 : Kotlin 2.2.0
- Kotlin 코드로만 구현
- 프로그램 실행의 시작점은 Application의 main()
- 변경 금지
    - build.gradle.kts
- 요구 사항에 달리 명시하지 않는 한 파일, 패키지 등 이름 변경 또는 이동 금지
- 외부 라이브러리 사용 금지
- 프로그램 종료 시 호출 금지
    - System.exit()
    - exitProcess()
- 코드 컨벤션 준수 : [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)