package partFour.youn.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import partFour.youn.entity.Member;
import partFour.youn.entity.Movie;
import partFour.youn.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @EntityGraph(attributePaths = "member",type = EntityGraph.EntityGraphType.FETCH)
    List<Review>findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review r where r.member =:member")
    void deleteByMember(@Param("member") Member member);
}
