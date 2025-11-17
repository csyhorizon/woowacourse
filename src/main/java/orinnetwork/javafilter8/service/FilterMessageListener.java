package orinnetwork.javafilter8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import orinnetwork.javafilter8.config.RabbitConfig;
import orinnetwork.javafilter8.dto.PostCreatedEvent;

@Component
@RequiredArgsConstructor
public class FilterMessageListener {

    private final FilterService filterService;

    @RabbitListener(queues = RabbitConfig.FILTER_QUEUE_NAME)
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        if (event == null || event.postId() == null) {
            return;
        }

        try {
            filterService.filterPost(event.postId());
        } catch (Exception e) {
            throw new IllegalArgumentException("[Post ID: " + event.postId() +"] 필터링 중 오류 발생");
        }
    }
}
