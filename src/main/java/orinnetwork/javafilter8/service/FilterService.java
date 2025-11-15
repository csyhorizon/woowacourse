package orinnetwork.javafilter8.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orinnetwork.javafilter8.core.AhoCorasickEngine;
import orinnetwork.javafilter8.domain.FilterLog;
import orinnetwork.javafilter8.domain.KeywordType;
import orinnetwork.javafilter8.domain.Post;
import orinnetwork.javafilter8.domain.PostStatus;
import orinnetwork.javafilter8.repository.FilterLogRepository;
import orinnetwork.javafilter8.repository.PostRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {

    private final AhoCorasickEngine engine;
    private final PostRepository postRepository;
    private final FilterLogRepository filterLogRepository;

    @Transactional
    public void filterPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        String title = post.getTitle();
        String content = post.getContent();
        String fullText = title + " " + content;

        Set<KeywordType> foundTypes = engine.search(fullText);

        if (foundTypes.isEmpty() || foundTypes.contains(KeywordType.PROFANITY)) {
            post.update(post.getTitle(), post.getContent(), PostStatus.ACTIVE);
            return;
        }

        PostStatus finalStatus = PostStatus.ACTIVE;

        for (KeywordType reason : foundTypes) {
            FilterLog logEntry = FilterLog.builder()
                    .postId(postId)
                    .reasonType(reason)
                    .resultStatus(finalStatus)
                    .build();

            filterLogRepository.save(logEntry);
        }
    }

}