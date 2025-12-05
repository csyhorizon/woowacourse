# 아호-코라식 (Aho-Corasick)
- 이번 우테코에서는 '고난도 문제'를 제시하였기 때문에 이러한 주제를 가져옴
- ACPC에서 뼈도 못추린 '아호-코라식'은 분명 넘어야 할 벽 중 하나
- 이는 도전해야할 목록 중 하나에 있었고 이번 기회에 도전하게 되었음

## 아호 코라식이란?
> 현재 광범위하게 알려진 거의 유일한 일대다 패터매칭 알고리즘
- KMP의 확장 버전이며, '트라이'와 'KMP'의 선행 지식이 요구

# 선행 지식
## 트라이 (Trie)
- 트라이는 여러 개의 문자열을 효율적으로 저장하고 '접두사'를 빠르게 찾기 위한 자료구조
- 예로 '바보', '바1보', '바bo'와 같이 '공통된 접두사'를 가진 단어가 많을 때 큰 효율을 가져옴

### 핵심 구조
- 트라이는 '트라이 노드(TrieNode)'와 '루트 노드(root)' 2가지가 핵심 요소
  - 트라이 노드는 '자식 노드'의 맵(Map)을 가지고 있으며, 이는 경로(간선) 정보
  - 단어의 끝에는 `isEndOfWord` 끝이라는 표시가 있음
    - 예로 'car', 'card'를 저장하면, 'r'과 'd'에 `true`
    - 이외 c와 a는 노드는 `false`
  - 루트 노드는 모든 검색과 삽입이 시작되는 '출발점'
    - 해당 root 노드에는 아무 글자도 의미하지 않는 빈 노드를 가지고 있음
- 동작 구조는 다음과 같음
  1. 'car' 삽입 시
     1. root 에서 시작
     2. root에 'c'가 있는 지 확인 (없음)
        - `c 노드를 새로 만들고 'c'와 연결`
     3. 'c' 노드로 이동
     4. 'c'에 'a'가 있는 지 확인 (없음)
        - `a 노드를 새로 만들고 'a'와 연결`
     5. 'a' 노드로 이동
     6. 'a'에 'r'가 있는 지 확인 (없음)
        - `r 노드를 새로 만들고 'r'와 연결`
     7. 'r' 노드로 이동
     8. 'car' 단어 완료 'r'에 `inEndOfWord = true`로 설정
  2. 'cat' 삽입 시
     1. root 에서 시작
     2. root에 'c'가 있는 지 확인 (있음)
     3. 'c' 노드로 이동
     4. 'c'에 'a'가 있는 지 확인 (있음)
     5. 'a' 노드로 이동
     6. 'a'에 't'가 있는 지 확인 (없음)
         - `t 노드를 새로 만들고 't'와 연결`
     7. 't' 노드로 이동
     8. 'cat' 단어 완료 't'에 `inEndOfWord = true`로 설정

### 시간 복잡도
#### `Trie`의 모든 연산은 **찾으려는 단어 길이**에만 의존
  - L = 찾으려는 단어의 길이
  - N = `Trie`에 이미 저장된 총 단어의 개수

#### 삽입
    - 시간 복잡도 : O(L)
    - 단어의 길이 L 만큼만 노드를 따라가거나 생성

#### 검색
    - 시간 복잡도 : O(L)
    - 단어의 길이 L 만큼만 노드를 따라가거나 생성

#### 접두사 검색
    - 시간 복잡도 : O(L)


### 공간 복잡도
- O(N X L_avg) (N: 총 단어 수, L_avg: 평균 단어 길이)
- `Trie`에 생성되는 총 노드의 개수에 비례
- 공동 접두사가 많으면 메모리가 절약되지만 그렇지 않다면 상당한 낭비가 될 수 있음

### Source Code
```java
import java.util.HashMap;
import java.util.Map;

/**
 * 트라이의 개별 노드를 나타내는 클래스
 */
class TrieNode {

    /**
     * 자식 노드들을 저장하는 맵
     * key: 문자 (Character)
     * value: 해당 문자에 해당하는 자식 노드 (TrieNode)
     */
    private final Map<Character, TrieNode> children = new HashMap<>();

    /**
     * 이 노드에서 끝나는 단어가 있는지 여부를 표시하는 깃발
     */
    private boolean isEndOfWord;

    Map<Character, TrieNode> getChildren() {
        return children;
    }

    boolean isEndOfWord() {
        return isEndOfWord;
    }

    void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}

/**
 * 트라이 (Trie) 자료구조 클래스
 */
public class Trie {

    private final TrieNode root;

    /**
     * Trie 생성자. 루트 노드를 초기화
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * 트라이에 새로운 단어를 삽입
     * 시간 복잡도: O(L) - L은 단어의 길이
     *
     * @param word 삽입할 단어
     */
    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            current = current.getChildren()
                             .computeIfAbsent(ch, c -> new TrieNode());
        }
        
        current.setEndOfWord(true);
    }

    /**
     * 트라이에서 특정 단어가 '완전히' 일치하는지 검색
     * 시간 복잡도: O(L) - L은 단어의 길이
     *
     * @param word 검색할 단어
     * @return 단어가 존재하고, 해당 노드가 '단어의 끝'이면 true, 아니면 false
     */
    public boolean search(String word) {
        
        TrieNode node = findNode(word);
        
        return node != null && node.isEndOfWord();
    }

    /**
     * 트라이에서 특정 '접두사'로 시작하는 단어가 있는지 검색
     * 시간 복잡도: O(L) - L은 접두사의 길이
     *
     * @param prefix 검색할 접두사
     * @return 접두사에 해당하는 노드 경로가 존재하면 true, 아니면 false
     */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * 문자열을 따라 트라이를 탐색하여 마지막 노드를 찾는 내부 헬퍼 메서드
     *
     * @param str 검색할 문자열 (단어 또는 접두사)
     * @return 문자열의 마지막 문자에 해당하는 노드. 경로가 없으면 null 반환
     */
    private TrieNode findNode(String str) {
        TrieNode current = root;
        for (char ch : str.toCharArray()) {
            TrieNode node = current.getChildren().get(ch);
            
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        // 1. 삽입
        trie.insert("apple");
        trie.insert("apply");
        trie.insert("banana");
        trie.insert("bat");

        // 2. 검색
        System.out.println("trie.search(\"apple\"): " + trie.search("apple"));   // true
        System.out.println("trie.search(\"app\"): " + trie.search("app"));     // false ("app" 자체는 삽입 안 함)
        System.out.println("trie.search(\"apply\"): " + trie.search("apply"));   // true
        System.out.println("trie.search(\"batman\"): " + trie.search("batman")); // false

        // 3. 접두사 검색
        System.out.println("trie.startsWith(\"app\"): " + trie.startsWith("app"));   // true ("apple", "apply")
        System.out.println("trie.startsWith(\"ban\"): " + trie.startsWith("ban"));   // true ("banana")
        System.out.println("trie.startsWith(\"cat\"): " + trie.startsWith("cat"));   // false
    }
}
```

## KMP
- KMP는 하나의 긴 텍스트에서 '하나의 특정 패턴'을 매우 빠르게 찾는 알고리즘
- 검색에 실패했을 때, 텍스트 포인터는 절대 뒤로 돌리지 않고 '패턴 포인터'만 점프시킴

### 핵심 구조
- `KMP`의 핵심 요소는 `pi`배열(실패 함수)
  - `pi` 배열은 **패턴 문자열** 자신을 미리 분석하여 만드는 '점프 맵(Map)'
  - 이 배열은 검색 중 `i + 1`에서 실패하면 다음엔 `pi[i]`번째부터 이어서 비교를 진행함
- `pi[i]`는 패턴의 '0'부터 'i'번째까지의 부분 문자열에서 '접두사와 접미사'가 일치하는 최대 길이
  - 예로 'ABAB'의 접두사 ('A', 'AB', 'ABA')와 접미사 ('B', 'AB', 'BAB')가 일치하는 최대 길이는 'AB'이므로, `pi[3] = 2`가 됨
- 동작 구조는 다음과 같음
  1. `pi` 배열 생성 (전처리)
     - 패턴 'ABABC'의 `pi`배열을 계산
     - `i=0` "A": 항상 0
       - `pi[0] = 0`
     - `i=1` "AB": 접두사 'A'와, 접미사 'B' -> 일치 없음
       - `pi[1] = 0`
     - `i=2` "ABA": 접두사('A', 'AB')와, 접미사('A', 'BA') -> 'A' 일치
       - `pi[2] = 1`
     - `i=3` "ABAB": 접두사('A','AB','ABA')와, 접미사('B','AB','BAB') -> 'AB' 일치
       - `pi[3] = 2`
     - `i=4` "ABABC": 접두와와 접미사 일치 없음
       - `pi[4] = 0`
     - "ABABC"의 `pi` 배열 = `[0, 0, 1, 2, 0]`
  2. 검색
     - 텍스트: ABABDABABC...
     - 패턴: ABABC
     - i(텍스트 포인터), j(패턴 포인터)

    | i | j | 일치 여부            | 이동                                                    |
    |---|---|------------------|-------------------------------------------------------|
    | 0 | 0 | 'A' 일치           | `i++`, `j++`                                          |
    | 1 | 1 | 'B' 일치           | `i++`, `j++`                                          |
    | 2 | 2 | 'A' 일치           | `i++`, `j++`                                          |
    | 3 | 3 | 'B' 일치           | `i++`, `j++`                                          |
    | 4 | 4 | 'D' != 'C' 불일치   | i는 4 고정, j는 `pi[j-1]`로 이동 / `j = pi[4-1] = pi[3] = 2` |
    | 4 | 2 | 'D'와 'A'의 비교 불일치 | j를 `pi[j-1]`로 이동 / `j = pi[2-1] = pi[1] = 0`          |
    | 4 | 0 | `D`와 `A`의 비교 불일치 | j가 0이므로 `i++`                                         |
    | 5 | 0 | 'A' 일치           | `i++`, `j++`                                          |

### 시간 복잡도
- `N` = 텍스트의 길이
- `M` = 패턴의 길이

#### 배열 생성 (전처리)
    - 시간 복잡도 : 0(M)
    - 패턴의 길이 'M' 만큼 1번 순회하여 'pi' 배열 생성

#### 검색
    - 시간 복잡도 O(M)
    - 텍스트의 길이 'N' 만큼만 1번 순회

#### 총 시간 복잡도
    - O(N + M)

### 공간 복잡도
- O(M) (패턴의 길이)
- 오직 `pi` 배열을 저장할 공간이 필요
- `KMP`는 하나의 패턴을 찾는 데 엄청난 속도를 보여주지만, `K`개의 패턴을 찾으려면 O(N + M) 연산을 `K`번 반복해야 함

### Source Code
```java
import java.util.ArrayList;
import java.util.List;

public class KMP {

    /**
     * KMP의 핵심인 'pi' 배열 (실패 함수)을 생성
     * pi[i] = pattern[0...i]의 접두사==접미사 최대 길이
     *
     * @param pattern 검색할 패턴 문자열
     * @return 계산된 pi 배열
     */
    private int[] getPi(String pattern) {
        int m = pattern.length();
        int[] pi = new int[m];

        int j = 0; 
        
        for (int i = 1; i < m; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                pi[i] = ++j;
            }
        }
        return pi;
    }

    /**
     * KMP 알고리즘을 사용해 텍스트에서 패턴을 검색
     *
     * @param text    전체 텍스트
     * @param pattern 찾을 패턴
     * @return 패턴이 시작되는 모든 인덱스의 리스트
     */
    public List<Integer> search(String text, String pattern) {
        List<Integer> foundIndices = new ArrayList<>();
        int[] pi = getPi(pattern);

        int n = text.length();
        int m = pattern.length();
        
        int j = 0;

        for (int i = 0; i < n; i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) {
                if (j == m - 1) {
                    foundIndices.add(i - m + 1);
                    j = pi[j];
                } else {
                    j++;
                }
            }
        }
        return foundIndices;
    }

    public static void main(String[] args) {
        KMP kmp = new KMP();
        String text = "ABABCABABCDA";
        String pattern = "ABABC";

        List<Integer> result = kmp.search(text, pattern);
        System.out.println("패턴 발견 위치: " + result); // [0, 5]
        
        text = "AAAAABAAABA";
        pattern = "AAAA";
        result = kmp.search(text, pattern);
        System.out.println("패턴 발견 위치: " + result); // [0, 1, 7]
    }
}
```

## 아호-코라식
- 여러 개의 패턴을 하나의 긴 텍스트에서 단 한번의 순회로 모두 찾아내는 알고리즘
- `Trie`는 여러 패턴을 저장
- `KMP`는 일치하지 않을 때 점프하고 찾아가는 역활

### 핵심 구조
- 아호-코라식은 `Trie` 구조를 그대로 사용하되, `TrieNode`에 KMP의 점프 기능을 사용
- `TrieNode`의 추가 요소
  - `Failure Link`
    - KMP의 `pi` 배열 역할
    - 현재 노드에서 다음 글자로 가는 길이 없을 때, 대신 탐색을 이어갈 "가장 가능성이 높은 다른 노드"
    - "가장 가능성이 높은 다른 노드"는 "현재까지 일치한 문자열의 가장 긴 접미사" 이면서 "다른 패턴의 접두사"인 노드
  - `Output Link`
    - 현재 노드에서 실패 링크를 타고 갔을 때, 단어의 끝을 만난다면 그 노드를 가리킴
    - 예로 `she`를 찾았을 때, `e` 노드를 타고 가면 `he`의 e노드 isEndOfWord를 만날 수 있으며, 이러면 숨겨진 일치를 빠르게 찾을 수 있음

### 동작 구조
- 아호-코라식은 `전처리`와 `검색` 둘로 나눠서 진행해야 함

#### 전처리
-`Trie`를 만든 후, `BFS`를 이용해 모든 노드의 `Failure Link`를 설정
1. 패턴 삽입 : 모든 금지어를 `Trie`에 삽입
2. 실패 링크 구축 (BFS)
   - `root` 노드에 큐를 넣음
     - `root`의 실패 링크는 null 혹은 자기 자신
     - BFS를 돌면서 큐에서 노드 `P`를 꺼냄
     - `P`의 모든 자식 노드 `C`의 경우
       - 실패 링크를 가리키는 노드 `F`로 점프
       - `F`에도 x의 자식이 있는가?
         - 있는 경우 : `C`의 실패 링크를 `F`의 x 자식 노드로 설정
         - 없는 경우 : `F`의 실패 링크를 타고 `F'`로 다시 점프를 2번 반복
         - 최종 실패 : `root`까지 갔는데 x 자식이 없다면 `C`의 실패 링크는 `root`를 가리킴
       - `C`를 큐에 넣음

#### 검색
- 게시글 텍스트를 한번 탐색하면서 완성된 '전처리' 엔진을 사용
  - EX) 텍스트: "나는 바보이고 멍청하다"
    - currentNode = root에서 시작
    - '나': root에 '나' 자식이 X. currentNode = root 유지
    - '는': root에 '는' 자식이 X. currentNode = root 유지
    - '바': root에 '바' 자식이 O currentNode를 '바' 노드로 이동
    - '보': '바' 노드에 '보' 자식이 O currentNode를 '보' 노드로 이동
    - (일치) '보' 노드는 isEndOfWord=true -> "바보" 찾음
    - '이': '보' 노드에 '이' 자식이 X
    - (KMP 점프!) '보' 노드의 **'실패 링크'**를 따라 점프 (root)
    - root에 '이' 자식이 X currentNode = root 유지
    - '멍': root에 '멍' 자식이 O currentNode를 '멍' 노드로 이동
    - '청': '멍' 노드에 '청' 자식이 O
    - '청' 노드는 isEndOfWord=true
    - ...

### 시간 복잡도
- N : 텍스트의 길이
- L : 모든 패턴의 길이
- M : 텍스트에서 발견된 총 매칭 횟수

#### 전처리
    - 시간 복잡도: O(L)
    - `Trie` 생성(O(L)) + `Failure Link` 생성 (O(L))
    - 서버 시작 시 최초 1회 수행

#### 검색
    - 시간 복잡도: O(N + M)
    - 텍스트 포인터는 KMP처럼 뒤로가지 않고 N번 전진함. 실패 링크를 따라 점프하는 횟수는 텍스트 전진 횟수를 넘을 수 없음
    - M은 발견된 결과를 기록하는 시간

### 공간 복잡도
    - O(L) : 모든 패턴의 총 길이
    - `Trie`를 저장할 공간만 필요


### Source Code
```java
import java.util.*;

public class AhoCorasick {

    /**
     * 아호-코라식은 기본 트라이 노드에 '실패 링크'와 '출력 셋'을 추가
     */
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        
        TrieNode failureLink = null;
        Set<String> output = new HashSet<>();

        public TrieNode getChild(char ch) {
            return children.get(ch);
        }
    }

    private final TrieNode root = new TrieNode();

    /**
     * 트라이에 패턴(금지어)을 삽입
     *
     * @param pattern 삽입할 패턴 문자열
     */
    public void insert(String pattern) {
        TrieNode current = root;
        for (char ch : pattern.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.output.add(pattern);
    }

    /**
     * 실패 링크(Failure Links)를 구축
     * * 이 작업은 서버 시작 시 1회만 수행
     */
    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();

        for (TrieNode child : root.children.values()) {
            child.failureLink = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode parent = queue.poll();

            for (Map.Entry<Character, TrieNode> entry : parent.children.entrySet()) {
                char ch = entry.getKey();
                TrieNode child = entry.getValue();

                queue.add(child);

                TrieNode fail = parent.failureLink; 

                while (fail != null && !fail.children.containsKey(ch)) {
                    fail = fail.failureLink;
                }

                if (fail == null) {
                    child.failureLink = root;
                } else {
                    child.failureLink = fail.children.get(ch);
                }

                child.output.addAll(child.failureLink.output);
            }
        }
    }

    /**
     * 텍스트를 순회하며 모든 패턴을 검색
     *
     * @param text 검색 대상이 되는 전체 텍스트
     * @return 찾은 패턴과, 해당 패턴이 시작된 인덱스 목록
     */
    public Map<String, List<Integer>> search(String text) {
        Map<String, List<Integer>> results = new HashMap<>();
        TrieNode current = root;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            while (current != null && !current.children.containsKey(ch)) {
                current = current.failureLink;
            }

            if (current == null) {
                current = root;
                continue;
            }

            current = current.children.get(ch);

            if (!current.output.isEmpty()) {
                for (String pattern : current.output) {
                    results.computeIfAbsent(pattern, k -> new ArrayList<>())
                           .add(i - pattern.length() + 1);
                }
            }
        }
        return results;
    }


    public static void main(String[] args) {
        AhoCorasick ac = new AhoCorasick();
        
        String[] patterns = {"he", "she", "his", "hers"};
        for (String p : patterns) {
            ac.insert(p);
        }

        ac.buildFailureLinks();

        String text = "ushers";
        Map<String, List<Integer>> results = ac.search(text);

        results.forEach((pattern, indices) -> 
                System.out.println("패턴 \"" + pattern + "\" 발견 위치: " + indices));
        
        /*
         출력 결과:
         패턴 "she" 발견 위치: [1]
         패턴 "he" 발견 위치: [2]
         패N "hers" 발견 위치: [2]
         */
    }
}
```