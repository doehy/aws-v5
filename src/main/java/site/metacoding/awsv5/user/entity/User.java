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

    @Column(
            unique = true,
            nullable = false
    )
    private String userToken;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String provider;

    @Column(
            nullable = false
    )
    private String oauthId;

    @Column
    private Long age;

    @Column
    private String gender;

    public static User of(String userToken, String email, String nickname, String provider, String oauthId) {
        return User.builder()
                .userToken(userToken)
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .oauthId(oauthId)
                .build();
    }
}

