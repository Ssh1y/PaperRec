package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public void insertRating(Long userId, Integer recommendationId, Integer ratedPaperId, Integer rating) {
        Ratings ratings = new Ratings();
        ratings.setUserid(Integer.valueOf(userId.toString()));
        ratings.setPaperid(recommendationId);
        ratings.setQuery(ratedPaperId);
        ratings.setRating(rating);
        this.save(ratings);
    }

    @Override
    public void updateRating(Integer id, Integer rating) {
        Ratings ratings = new Ratings();
        ratings.setId(id);
        ratings.setRating(rating);
        this.updateById(ratings);
    }

    @Override
    public String[] selectAllQuery() {
        List<Object> objects = ratingsMapper.selectObjs(new LambdaQueryWrapper<Ratings>().select(Ratings::getQuery));
        return objects.stream()
                .filter(obj -> obj instanceof String)
                .map(obj -> (String) obj)
                .toArray(String[]::new);
    }
}




