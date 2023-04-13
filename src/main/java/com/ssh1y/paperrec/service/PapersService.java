package com.ssh1y.paperrec.service;

import com.ssh1y.paperrec.entity.Papers;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenweihong
* @description 针对表【Papers】的数据库操作Service
* @createDate 2023-03-17 22:41:52
*/
public interface PapersService extends IService<Papers> {
    
    /**
     * 模糊搜索
     * @param query 搜索关键词
     * @return 符合条件的论文id列表
     */
    List<Integer> fuzzySearch(String query);

    /**
     * 根据论文id列表获取论文列表
     * @param paperIds 论文id列表
     * @return 论文列表
     */
    List<Papers> getByIds(List<Integer> paperIds);

    /**
     * 获取所有论文id
     * @return 论文id列表
     */
    List<Double> getPaperIds();
}
