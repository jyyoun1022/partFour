package partFour.youn.service;

import org.springframework.data.domain.PageRequest;
import partFour.youn.dto.PageRequestDTO;
import partFour.youn.dto.PageResultDTO;
import partFour.youn.entity.Movie;
import partFour.youn.entity.MovieImage;
import partFour.youn.dto.MovieDTO;
import partFour.youn.dto.MovieImageDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    Long register(MovieDTO movieDTO);

    PageResultDTO<Object[],MovieDTO> getList(PageRequestDTO pageRequestDTO);

    default Map<String,Object> dtoToEntity(MovieDTO movieDTO){  //Map타입으로 반환
        Map<String,Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        //MovieImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList",movieImageList);
        }
        return entityMap;
    }
    default MovieDTO entityToDto(Movie movie, List<MovieImage>movieImages,Double avg,Long reviewCount){

        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO>movieImageDTOList = movieImages.stream().map(movieImage->{
            MovieImageDTO movieImageDTO = MovieImageDTO.builder()
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
            return movieImageDTO;
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCount(reviewCount.intValue());

        return movieDTO;
    }
}
