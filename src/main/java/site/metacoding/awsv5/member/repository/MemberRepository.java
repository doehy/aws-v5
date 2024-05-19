package site.metacoding.awsv5.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.metacoding.awsv5.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findById(Long id);
}
