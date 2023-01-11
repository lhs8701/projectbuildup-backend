package projectbuildup.saver.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Notification")
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 200)
    private String content;

    @Column(length = 40)
    private LocalDateTime sentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
    private Member receiver;
}
