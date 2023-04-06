package com.ssh1y.paperrec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ssh1y.paperrec.entity.Ratings;

/**
 * @author chenweihong
 * @description 针对表【RecommendationRatings】的数据库操作Service
 * @createDate 2023-03-17 22:42:52
 */
public interface RatingsService extends IService<Ratings> {

    /**
     * 插入一条评分记录
     *
     * @param userId           用户id
     * @param recommendationId 推荐id
     * @param ratedPaperId     基于论文id
     * @param rating           评分
     */
    void insertRating(Long userId, Integer recommendationId, Integer ratedPaperId, Integer rating);

    /**
     * 更新一条评分记录
     *
     * @param id     评分id
     * @param rating 评分
     */
    void updateRating(Integer id, Integer rating);

    /**
     * 获取所有的query记录
     * @return query记录，以数组形式返回
     */
    String[] selectAllQuery();
}
