package partFour.youn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import partFour.youn.entity.Movie;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie,Long> {
//    @Query("select m,avg(coalesce(r.grade,0)), count(distinct r) from  Movie m " +
//            "left join Review r on r.movie = m " +
//            "group by m")
//    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m,mi,avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
            "left join Review r on r.movie = m " +
            "left join MovieImage mi on mi.movie =m " +
            "group by m,mi")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m,mi ,avg(coalesce(r.grade,0)),count(distinct r.reviewNum) " +
            "from Movie m left join MovieImage mi on mi.movie=m " +
            "left join Review r on r.movie=m " +
            "where m.mno=:mno group by m,mi")
    List<Object[]> getMovieWithAll(@Param("mno")Long mno);
}
