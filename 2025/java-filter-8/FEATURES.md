# 구현할 기능 목록
> 이 문서는 상황에 따라 문서 내부의 '수정'을 진행하며, 항목이 변경될 수 있음
## 아호-코라식 엔진
- [x] `Trie` 자료구조를 위한 `Node`클래스 구현
- [x] 금지어 목록을 `Trie`에 삽입하는 `buildTrie` 로직 구현
- [x] BFS를 활용하여 `Trie`의 실패 링크를 구축하는 `buildFailureLinks` 로직 구현
- [x] `Trie`와 실패 링크를 기반으로 텍스트 검색하는 `search` 로직 구현
- [x] 서버 시작 시 `resources`의 JSON 파일에서 금지어를 읽어오는 `KeywordLoader` 구현
- [x] 아호-코라식 로직을 감싸는 `FilterEngine` 서비스 클래스 구현

## RabbitMQ 설정
- [x] RabbitMQConfig 클래스 작성
- [x] `Exchange`, `Queue`, `Binding`을 Spring Bean으로 등록
- [x] 메시지를 JSON으로 자동 변환/파싱하기 위한 `Jackson2JsonMessageConverter` Bean 등록
- [x] `RabbitTemplate`이 JsonConverter를 사용하도록 설정

## 메시지 수신 및 처리
- [x] MQ 메시지를 파싱할 `PostEventDto`
- [x] `@RabbitListener` 어노테이션을 사용한 `FilterConsumer` 클래스 구현
- [x] `postId`로 DB를 조회할 `PostRepository` 인터페이스 구현
- [x] `Post` 엔티티 클래스 구현
- [x] `FilterService` Consumer로부터 `postId`를 받아 DB 조회 -> `FilterEngine` 호출 -> DB 상태 업데이트를 총괄하는 메인 서비스 로직 구현
- [x] DB 상태 업데이트 로직 (`status`를 변경)

## 예외 처리
- [x] 메시지 파싱 실패 시 로깅 및 Ack 처리
- [x] `postId` 조회 실패 시 로깅 및 Ack 처리
- [x] DB 업데이트 실패 등 일시적 오류 시, `Nack` 처리 로직 구현
- [x] 메시지를 별도로 저장하는 'Dead Letter Queue' 설정
  - 별도로 저장하는 메시지는 실패에 대해서만 해당하며, 일시적 오류는 제외

## 테스트 및 검증
- [x] 아호-코라식 알고리즘 단위 테스트
- [x] `FilterService` 로직 검증
- [x] 소비 흐름 통합 테스트