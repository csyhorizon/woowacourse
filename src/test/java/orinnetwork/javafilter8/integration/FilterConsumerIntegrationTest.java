package orinnetwork.javafilter8.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import orinnetwork.javafilter8.consumer.FilterConsumer;
import orinnetwork.javafilter8.domain.FilterLog;
import orinnetwork.javafilter8.domain.KeywordType;
import orinnetwork.javafilter8.domain.Post;
import orinnetwork.javafilter8.domain.PostStatus;
import orinnetwork.javafilter8.dto.PostEvent;
import orinnetwork.javafilter8.repository.FilterLogRepository;
import orinnetwork.javafilter8.repository.PostRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FilterConsumerIntegrationTest {

    @Autowired
    private FilterConsumer filterConsumer;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FilterLogRepository filterLogRepository;

    @MockitoBean
    private org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory;

    @Test
    @DisplayName("통합 테스트: 욕설 포함된 게시글은 ACTIVE 상태 / 로그 저장")
    void profanityFlowTest() {
        Post savedPost = postRepository.save(Post.builder()
                .title("아니 진짜")
                .content("왜 이딴 상황이냐고 씨발")
                .postStatus(PostStatus.PENDING)
                .build());

        PostEvent event = new PostEvent(savedPost.getId());
        filterConsumer.handleFilteringMessage(event);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElseThrow();
        assertThat(updatedPost.getPostStatus()).isEqualTo(PostStatus.ACTIVE);

        List<FilterLog> logs = filterLogRepository.findAll();
        assertThat(logs).hasSize(1);
        assertThat(logs.getFirst().getReasonType()).isEqualTo(KeywordType.PROFANITY);
    }

    @Test
    @DisplayName("통합 테시트: 모욕 포함된 게시글은 REJECTED 상태 / 로그 저장")
    void insultFlowTest() {
        Post savedPost = postRepository.save(Post.builder()
                .title("애미 진짜")
                .content("하")
                .postStatus(PostStatus.PENDING)
                .build());

        filterConsumer.handleFilteringMessage(new PostEvent(savedPost.getId()));

        Post updatedPost = postRepository.findById(savedPost.getId()).orElseThrow();
        assertThat(updatedPost.getPostStatus()).isEqualTo(PostStatus.REJECTED);

        List<FilterLog> logs = filterLogRepository.findAll();
        assertThat(logs).hasSize(1);
        assertThat(logs.getFirst().getReasonType()).isEqualTo(KeywordType.INSULT);
    }
}
