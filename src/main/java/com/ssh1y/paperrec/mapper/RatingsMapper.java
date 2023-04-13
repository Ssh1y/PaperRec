package com.ssh1y.paperrec.mapper;

import com.ssh1y.paperrec.entity.Ratings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author chenweihong
* @description 针对表【Ratings】的数据库操作Mapper
* @createDate 2023-03-17 22:42:52
* @Entity com.ssh1y.paperrec.entity.Ratings
*/
@Repository
public interface RatingsMapper extends BaseMapper<Ratings> {

    /**
     * 根据query查找所有的paperid
     * @param query 查询词
     * @return 所有的paperid
     */
    @Select("SELECT paperid FROM Ratings WHERE query = #{query} GROUP BY paperid HAVING AVG(rating)>=4 ORDER BY AVG(rating) DESC")
    List<Integer> getPaperIdsByQuery(String query);
}




