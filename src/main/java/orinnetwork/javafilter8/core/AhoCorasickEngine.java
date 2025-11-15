package orinnetwork.javafilter8.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import orinnetwork.javafilter8.domain.KeywordType;

@Slf4j
@Component
@RequiredArgsConstructor
public class AhoCorasickEngine {

    private final AhoCorasickNode root = new AhoCorasickNode();

    public void initialize(Map<String, KeywordType> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            log.info("초기화 키워드가 존재하지 않음. 필터 엔진이 비어있는 상태로 동작");
            return;
        }

        for (Map.Entry<String, KeywordType> entry : keywords.entrySet()) {
            insert(entry.getKey(), entry.getValue());
        }

        buildFailureLinks();
        log.info("아호-코라식 엔진 초기화 완료. 총 {}개의 키워드를 로드", keywords.size());
    }

    /**
     * insert시 KeywordType을 받아서 Node의 output에 저장
     * @param pattern 문자
     * @param type 형태
     */
    private void insert(String pattern, KeywordType type) {
        AhoCorasickNode current = root;
        for (char ch : pattern.toCharArray()) {
            current = current.getChildren()
                    .computeIfAbsent(ch, c -> new AhoCorasickNode());
        }
        current.addOutputType(type);
    }


    private void buildFailureLinks() {
        Queue<AhoCorasickNode> queue = new LinkedList<>();

        for (AhoCorasickNode child : root.getChildren().values()) {
            child.setFailureLink(root);
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            AhoCorasickNode parent = queue.poll();

            for (Map.Entry<Character, AhoCorasickNode> entry : parent.getChildren().entrySet()) {
                char ch = entry.getKey();
                AhoCorasickNode child = entry.getValue();

                queue.add(child);

                AhoCorasickNode fail = parent.getFailureLink();
                while (fail != null && !fail.getChildren().containsKey(ch)) {
                    fail = fail.getFailureLink();
                }

                if (fail == null) {
                    child.setFailureLink(root);
                } else {
                    child.setFailureLink(fail.getChildren().get(ch));
                }

                child.addOutputTypes(child.getFailureLink().getOutputType());
            }
        }
    }

    /**
     * 입력된 텍스트 금지어가 하나라도 포함되어있는지 확인
     * @param text 검사할 텍스트
     * @return 금지어가 있는 경우 true, 아니면 false
     */
    public Set<KeywordType> search(String text) {
        Set<KeywordType> foundTypes = new HashSet<>();
        AhoCorasickNode current = root;

        for (char ch : text.toCharArray()) {
            while (current != null && !current.getChildren().containsKey(ch)) {
                current = current.getFailureLink();
            }

            if (current == null) {
                current = root;
                continue;
            }

            current = current.getChildren().get(ch);

            if (!current.getOutputType().isEmpty()) {
                foundTypes.addAll(current.getOutputType());
            }
        }
        return foundTypes;
    }
}