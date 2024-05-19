package site.metacoding.awsv5.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.awsv5.member.dto.LoginDto;
import site.metacoding.awsv5.member.entity.Member;
import site.metacoding.awsv5.member.repository.MemberRepository;

import java.util.List;

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

    @Transactional
    public void login(LoginDto loginDto) {
        Member member = memberRepository.findByIdentity(loginDto.getIdentity());
        Authentication auth = getAuthentication(member);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, member.getPassword(),
                List.of(new SimpleGrantedAuthority("USER_ROLE")));
    }
}
