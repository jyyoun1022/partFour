package partFour.youn.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import partFour.youn.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository repository;

    @Test
    void insertDummies(){

        IntStream.rangeClosed(1,100).forEach(i->{

            Member member = Member.builder()
                    .email("jyyoun"+i+"@naver.com")
                    .password("1111")
                    .nickName("User"+i)
                    .build();

            repository.save(member);
        });
    }
}
