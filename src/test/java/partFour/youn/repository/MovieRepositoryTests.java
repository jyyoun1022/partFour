package partFour.youn.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import partFour.youn.entity.Movie;
import partFour.youn.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Test
    @Transactional
    @Commit
    void insertMovies(){

        IntStream.rangeClosed(1,100).forEach(i ->{
            Movie movie = Movie.builder()
                    .title("Title..."+i)
                    .build();

            movieRepository.save(movie);

            int count = (int) (Math.random() * 5) + 1;

            for(int j=0; j<count; j++){
                MovieImage movieImage= MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("Test"+j+".jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }

        });
    }
    @Test
    @DisplayName("페이징처리")
    void testListPage(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
//        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        List<Object[]> listArr = result.getContent();
        listArr.forEach(i-> System.out.println(Arrays.toString(i)));
    }

    @Test
    @DisplayName("특정영화조회")
    void testReadMovie(){

        List<Object[]> result = movieRepository.getMovieWithAll(12L);
        result.forEach(i-> System.out.println(Arrays.toString(i)));
    }
}


