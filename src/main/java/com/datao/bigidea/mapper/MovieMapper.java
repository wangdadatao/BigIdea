package com.datao.bigidea.mapper;

import com.datao.bigidea.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 王 海涛 on 2017/1/10.
 */
public interface MovieMapper {

    void insert(List<Movie> movies);

    void insertOne(Movie movie);

    /**
     * 得到5000条电影对象
     *
     * @return
     */
    List<Movie> getMovie(@Param("start") Integer start, @Param("end") Integer end);

    List<Movie> getMovieJson();

    void updateMovie(Movie m);

    //更新json信息
    void updateJson(Movie m);
}
