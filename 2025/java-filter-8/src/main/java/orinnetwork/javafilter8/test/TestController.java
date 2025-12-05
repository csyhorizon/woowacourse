package orinnetwork.javafilter8.test;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orinnetwork.javafilter8.config.RabbitConfig;
import orinnetwork.javafilter8.domain.Post;
import orinnetwork.javafilter8.domain.PostStatus;
import orinnetwork.javafilter8.dto.PostCreatedEvent;
import orinnetwork.javafilter8.repository.PostRepository;

@Profile("local")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;

    public record PostCreateRequest(String title, String content) {}

    /**
     * [테스트] 게시글 생성 및 필터링 이벤트 발행
     * @param request 게시글 제목(title) 내용(content)
     * @return 생성된 Post 객체
     */
    @PostMapping("/posts")
    public ResponseEntity<Post> createTestPost(@RequestBody PostCreateRequest request) {

        Post post = Post.builder()
                .title(request.title)
                .content(request.content)
                .postStatus(PostStatus.PENDING)
                .build();
        Post savedPost = postRepository.save(post);

        PostCreatedEvent event = new PostCreatedEvent(savedPost.getId());

        rabbitTemplate.convertAndSend(
                RabbitConfig.POST_EXCHANGE_NAME,
                RabbitConfig.POST_CREATED_ROUTING_KEY,
                event
        );

        return ResponseEntity.ok(savedPost);
    }
}
