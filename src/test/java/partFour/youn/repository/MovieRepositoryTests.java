package partFour.youn.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import partFour.youn.entity.Movie;
import partFour.youn.entity.MovieImage;

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
}


