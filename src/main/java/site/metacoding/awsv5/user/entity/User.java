package site.metacoding.awsv5.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String provider;

    @Column(
            name = "oauth_id",
            nullable = false
    )
    private String oauthId;

    @Column
    private Integer age;

    @Column
    private String gender;

    public static User of(String email, String nickname, String provider, String oauthId) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .oauthId(oauthId)
                .build();
    }
}

