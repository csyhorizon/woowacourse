package orinnetwork.javafilter8.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KeywordType reasonType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus resultStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public FilterLog(Long postId, KeywordType reasonType, PostStatus resultStatus) {
        this.postId = postId;
        this.reasonType = reasonType;
        this.resultStatus = resultStatus;
    }
}
