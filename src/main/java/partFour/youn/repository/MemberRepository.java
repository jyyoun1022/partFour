package partFour.youn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import partFour.youn.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
