package site.metacoding.awsv5.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.awsv5.member.entity.Member;
import site.metacoding.awsv5.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void register(Member member) {
        String rawPassword = member.getPassword();
        String encPasswrod = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPasswrod);
        Member savedMember = memberRepository.save(member);
    }
}
