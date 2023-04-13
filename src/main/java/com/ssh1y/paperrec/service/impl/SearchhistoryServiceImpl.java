package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssh1y.paperrec.entity.Searchhistory;
import com.ssh1y.paperrec.mapper.SearchhistoryMapper;
import com.ssh1y.paperrec.service.SearchhistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenweihong
 * @description 针对表【SearchHistory】的数据库操作Service实现
 * @createDate 2023-04-07 14:19:09
 */
@Service
public class SearchhistoryServiceImpl extends ServiceImpl<SearchhistoryMapper, Searchhistory>
        implements SearchhistoryService {

    @Resource
    private SearchhistoryMapper searchhistoryMapper;

    @Override
    public Boolean updateQuery(String query, Integer userId) {
        Searchhistory searchhistory = new Searchhistory();
        searchhistory.setQuery(query);
        searchhistory.setUserid(userId);
        return this.saveOrUpdate(searchhistory, new LambdaQueryWrapper<Searchhistory>().eq(Searchhistory::getUserid, userId));
    }

    @Override
    public String getQueryByUserId(Integer userId) {
        Searchhistory searchhistory = this.getOne(new LambdaQueryWrapper<Searchhistory>().eq(Searchhistory::getUserid, userId));
        return searchhistory == null ? null : searchhistory.getQuery();
    }

    @Override
    public List<String> getHotWords() {
        // 在searchhistory表中，按照query字段进行分组，统计每个query出现的次数，然后按照出现次数降序排序，取前5个，不足5个则取全部
        List<Searchhistory> searchhistories = searchhistoryMapper.selectList(new QueryWrapper<Searchhistory>()
                .select("query, count(query) as count")
                .groupBy("query")
                .orderByDesc("count(query)")
                .last("limit 5"));
        return searchhistories.stream().map(Searchhistory::getQuery).collect(Collectors.toList());
    }
}




