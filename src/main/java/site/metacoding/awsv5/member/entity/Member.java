package site.metacoding.awsv5.member.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identity;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

//    @Column(nullable = false)
//    private String provider;

//    @Column(
//            nullable = false
//    )
//    private String oauthId;

    @Column
    private Long age;

    @Column
    private String gender;

//    public static Member of(String email, String nickname, String provider, String oauthId) {
//        return Member.builder()
//                .email(email)
//                .nickname(nickname)
//                .provider(provider)
//                .oauthId(oauthId)
//                .build();
//    }
}

