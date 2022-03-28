package partFour.youn.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import partFour.youn.entity.Member;
import partFour.youn.entity.Movie;
import partFour.youn.entity.Review;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository repository;

    @Test
    @DisplayName("더미데이터 리뷰")
    void insertReviewDummies(){
        IntStream.rangeClosed(1,200).forEach(i->{
            //영화 번호
            long mno = (long) (Math.random() * 100) + 1;
            //리뷰어 번호
            long mid =((long)(Math.random()*100)+1);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .movie(Movie.builder().mno(mno).build())
                    .member(member)
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낀점 ..."+i)
                    .build();

            repository.save(movieReview);
        });
    }
}
