package partFour.youn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import partFour.youn.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
