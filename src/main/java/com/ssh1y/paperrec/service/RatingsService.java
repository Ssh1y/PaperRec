package com.ssh1y.paperrec.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ssh1y.paperrec.entity.Ratings;

/**
 * @author chenweihong
 * @description 针对表【RecommendationRatings】的数据库操作Service
 * @createDate 2023-03-17 22:42:52
 */
public interface RatingsService extends IService<Ratings> {


    /**
     * 获取所有的query记录
     * @return query记录，以数组形式返回
     */
    String[] selectAllQuery();

    /**
     * 保存用户的评分
     *
     * @param userId  用户id
     * @param paperid 论文id
     * @param query   用户的搜索历史
     * @param rating
     * @return 是否保存成功
     */
    Boolean saveRatings(Integer userId, Integer paperid, String query, Integer rating);

    /**
     * 根据query查找评分数量
     * @param q 查询词
     * @return 评分数量
     */
    Integer countByQuery(String q);

    /**
     * 根据query查找评分矩阵
     * @param q 查询词
     * @return 评分矩阵
     */
    double[][] getMatrixByQuery(String q);
}
