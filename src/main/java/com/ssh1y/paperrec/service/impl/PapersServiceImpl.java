package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssh1y.paperrec.entity.Papers;
import com.ssh1y.paperrec.mapper.PapersMapper;
import com.ssh1y.paperrec.service.PapersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chenweihong
 * @description 针对表【Papers】的数据库操作Service实现
 * @createDate 2023-03-17 22:41:52
 */
@Service
public class PapersServiceImpl extends ServiceImpl<PapersMapper, Papers>
        implements PapersService {

    @Resource
    private PapersMapper papersMapper;

    @Override
    public List<Integer> fuzzySearch(String query) {
        List<Object> PaperIds = papersMapper.selectObjs(
                new LambdaQueryWrapper<Papers>()
                        .like(Papers::getAuthor, query).or()
                        .like(Papers::getKeywords, query).or()
                        .like(Papers::getTitle, query).or()
                        .like(Papers::getSummary, query));
        return PaperIds.stream()
                .filter(obj -> obj instanceof Integer)
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
    }

    @Override
    public List<Papers> getByIds(List<Integer> paperIds) {
        List<Papers> unRankedPapers = papersMapper.selectList(new LambdaQueryWrapper<Papers>().in(Papers::getId, paperIds));
        Map<Integer, Papers> papersMap = unRankedPapers.stream()
                .collect(Collectors.toMap(Papers::getId, Function.identity()));
        List<Papers> RankedPapers = paperIds.stream()
                .map(papersMap::get)
                .collect(Collectors.toList());
        return RankedPapers;
    }

    /**
     * 获取所有论文id
     *
     * @return 所有论文id
     */
    @Override
    public List<Double> getPaperIds() {
        List<Object> paperIds = papersMapper.selectObjs(new LambdaQueryWrapper<Papers>().select(Papers::getId));
        return paperIds.stream()
                .filter(obj -> obj instanceof Double)
                .map(obj -> (Double) obj)
                .collect(Collectors.toList());
    }
}
