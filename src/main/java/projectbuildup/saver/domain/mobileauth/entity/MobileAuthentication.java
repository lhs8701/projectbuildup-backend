package projectbuildup.saver.domain.mobileauth.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "phone_authentication")
public class MobileAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NonNull
    @Column(name = "code", nullable = false)
    private String code;

    @NonNull
    @Column(name = "tried", nullable = false)
    private Long tried;
}
