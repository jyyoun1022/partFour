package partFour.youn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import partFour.youn.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long> {
}
