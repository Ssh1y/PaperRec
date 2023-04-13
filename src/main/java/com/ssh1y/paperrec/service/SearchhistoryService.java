package com.ssh1y.paperrec.service;

import com.ssh1y.paperrec.entity.Searchhistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenweihong
* @description 针对表【SearchHistory】的数据库操作Service
* @createDate 2023-04-07 14:19:09
*/
public interface SearchhistoryService extends IService<Searchhistory> {

    /**
     * 根据用户id更新用户的最近一次搜索历史
     * @param query 用户的搜索历史
     * @param userId 用户id
     * @return 用户的搜索历史
     */
    Boolean updateQuery(String query, Integer userId);

    /**
     * 根据用户id获取用户的最近一次搜索历史
     * @param userId 用户id
     * @return 用户的搜索历史
     */
    String getQueryByUserId(Integer userId);

    /**
     * 获取热门搜索词
     * @return 热门搜索词
     */
    List<String> getHotWords();
}
