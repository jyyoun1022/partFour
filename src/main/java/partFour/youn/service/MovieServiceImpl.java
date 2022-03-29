package partFour.youn.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import partFour.youn.dto.MovieImageDTO;
import partFour.youn.dto.PageRequestDTO;
import partFour.youn.dto.PageResultDTO;
import partFour.youn.entity.Movie;
import partFour.youn.entity.MovieImage;
import partFour.youn.dto.MovieDTO;
import partFour.youn.repository.MovieImageRepository;
import partFour.youn.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<Object[], MovieDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        Function<Object[],MovieDTO> fn =(arr ->
                   entityToDto((Movie) arr[0],
                           (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                    (Double)arr[2],
                    (Long) arr[3]));

        return new PageResultDTO<>(result,fn);
    }
}
