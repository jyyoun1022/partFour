package partFour.youn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import partFour.youn.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
