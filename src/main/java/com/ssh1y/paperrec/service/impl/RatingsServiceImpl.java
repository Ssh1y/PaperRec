package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssh1y.paperrec.entity.Ratings;
import com.ssh1y.paperrec.mapper.RatingsMapper;
import com.ssh1y.paperrec.service.RatingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenweihong
 * @description 针对表【Ratings】的数据库操作Service实现
 * @createDate 2023-03-17 22:42:52
 */
@Service
public class RatingsServiceImpl extends ServiceImpl<RatingsMapper, Ratings>
        implements RatingsService {

    @Resource
    private RatingsMapper ratingsMapper;

    @Override
    public String[] selectAllQuery() {
        List<Object> objects = ratingsMapper.selectObjs(new QueryWrapper<Ratings>().select("distinct query"));
        return objects.stream()
                .filter(obj -> obj instanceof String)
                .map(obj -> (String) obj)
                .toArray(String[]::new);
    }

    @Override
    public Boolean saveRatings(Integer userId, Integer paperid, String query, Integer rating) {
        Ratings ratings = new Ratings();
        ratings.setUserid(userId);
        ratings.setPaperid(paperid);
        ratings.setQuery(query);
        ratings.setRating(rating);
        return this.saveOrUpdate(ratings, new LambdaQueryWrapper<Ratings>().eq(Ratings::getUserid, userId).eq(Ratings::getPaperid, paperid).eq(Ratings::getQuery, query));
    }

    @Override
    public Integer countByQuery(String q) {
        Long count = ratingsMapper.selectCount(new LambdaQueryWrapper<Ratings>().eq(Ratings::getQuery, q));
        return count.intValue();
    }

    /**
     * 通过query获取评分矩阵
     *
     * @param q query
     * @return 评分矩阵
     */
    @Override
    public double[][] getMatrixByQuery(String q) {
        List<Ratings> ratings = ratingsMapper.selectList(new LambdaQueryWrapper<Ratings>().eq(Ratings::getQuery, q));
        // 返回【用户id，论文id，评分】的矩阵
        double[][] matrix = new double[ratings.size()][3];
        for (int i = 0; i < ratings.size(); i++) {
            matrix[i][0] = ratings.get(i).getUserid();
            matrix[i][1] = ratings.get(i).getPaperid();
            matrix[i][2] = ratings.get(i).getRating();
        }
        return matrix;
    }
}
