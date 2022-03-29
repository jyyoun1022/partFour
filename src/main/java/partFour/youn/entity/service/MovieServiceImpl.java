package partFour.youn.entity.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import partFour.youn.entity.Movie;
import partFour.youn.entity.MovieImage;
import partFour.youn.entity.dto.MovieDTO;
import partFour.youn.repository.MovieImageRepository;
import partFour.youn.repository.MovieRepository;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;


    @Override
    @Transactional
    public Long register(MovieDTO movieDTO) {

        Map<String,Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList =(List<MovieImage>) entityMap.get("imgList");
        //Map에서 꺼내온 값들을 Movie,List<MovieImage>로 형변환

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });

        return movie.getMno();
    }
}
