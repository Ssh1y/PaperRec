package com.ssh1y.paperrec.controller;

import com.ssh1y.paperrec.cache.CacheManager;
import com.ssh1y.paperrec.dto.PagingResult;
import com.ssh1y.paperrec.dto.SearchForm;
import com.ssh1y.paperrec.entity.Papers;
import com.ssh1y.paperrec.mapper.PapersMapper;
import com.ssh1y.paperrec.mapper.RatingsMapper;
import com.ssh1y.paperrec.service.PapersService;
import com.ssh1y.paperrec.service.RatingsService;
import com.ssh1y.paperrec.service.SearchhistoryService;
import com.ssh1y.paperrec.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author chenweihong
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    /**
     * 定义启动推荐算法的最小阈值，只有当某个query的用户评分记录大于该值时，才会启动推荐算法
     */
    private final static Integer MIN_THRESHOLD = 1000;

    @Resource
    private PapersService papersService;

    @Resource
    private RatingsService ratingsService;

    @Resource
    private SearchhistoryService searchhistoryService;

    @Autowired
    private PapersMapper papersMapper;

    @Autowired
    private RatingsMapper ratingsMapper;

    /**
     * 推荐算法，对用户输入的query进行分词，
     */
    @RequestMapping("/search")
    public Result search(@RequestBody SearchForm searchForm, @RequestHeader("Authorization") String token) throws UnsupportedEncodingException {

        String query = searchForm.getQuery();
        Integer page = searchForm.getPage() == null ? 1 : searchForm.getPage();
        Integer limit = searchForm.getLimit() == null ? 10 : searchForm.getLimit();
        Boolean isUpdateCache = searchForm.getIsUpdateCache() == null ? false : searchForm.getIsUpdateCache();
        String[] words = QueryUtil.processQuery(query);
        // 获取用户id
        Long userId = JwtHelper.getUserId(token);
        List<Papers> papers = new ArrayList<>();
        // key 为query和今天时间的md5值
        String key = Md5Encrypt.encrypt(query + DateUtil.getToday());
        if (isUpdateCache && CacheManager.getInstance().contains(key)) {
            // 如果用户点击了更新缓存，则删除缓存中的数据
            CacheManager.getInstance().remove(key);
        }
        if (CacheManager.getInstance().contains(key)) {
            // 从缓存中获取数据
            papers = CacheManager.getInstance().get(key);
        } else {
            // 获取ratings表中的所有query数据
            String[] queryArray = ratingsService.selectAllQuery();
            String[] top5 = QueryUtil.getTopQuerys(queryArray, words);

            List<Integer> FuzzPaperIds = papersService.fuzzySearch(query);

            List<Integer> paperIds = new ArrayList<>();
            if (top5 != null) {
                for (String q : top5) {
                    if (q != null) {
                        // 判断是否采用模型训练，判断当前数据集的大小
                        Integer count = ratingsService.countByQuery(q);
                        // 如果数据集的大小大于阈值，则启动模型训练
                        if (count > MIN_THRESHOLD) {
                            // 使用LFM模型进行推荐
                            double[][] userRatingRecord = ratingsService.getMatrixByQuery(q);
                            // 传入评分矩阵，训练模型
                            LatentFactorModel model = new LatentFactorModel(userRatingRecord, 0.02, 0.01, 0.01, 10, 100);
                            model.fit();
                            // 获取paperId列表
                            List<Double> paperIdList = papersService.getPaperIds();
                            // 获取用户的评分列表
                            assert userId != null;
                            List<Double> ratingList = model.getPredictResult(userId.intValue(), paperIdList);
                            // 将List<Double>转换为List<Integer>
                            List<Integer> paperIdListInt = new ArrayList<>();
                            for (Double paperId : paperIdList) {
                                paperIdListInt.add(paperId.intValue());
                            }
                            paperIds.addAll(paperIdListInt);
                        } else {
                            // 查找平均评分大于4的paperId
                            paperIds.addAll(ratingsMapper.getPaperIdsByQuery(q));
                        }
                    }
                }
            }
            System.out.println("paperIds = " + paperIds);
            // 将模糊查询的paperId添加到paperIds的末尾，去重
            if (FuzzPaperIds.size() > 0) {
                paperIds.addAll(FuzzPaperIds);
                paperIds = new ArrayList<>(new LinkedHashSet<>(paperIds));
            }
            paperIds = new ArrayList<>(new ArrayList<>(paperIds));
            if (paperIds.size() > 0) {
                // 根据paperId获取paper
                papers = papersService.getByIds(paperIds);
            }
            // 将数据存入缓存
            CacheManager.getInstance().put(key, papers);
        }

        // 将用户的搜索词存入数据库
        if (words.length > 0) {
            assert userId != null;
            storeHistory(words, userId.intValue());
        }
        // 分页
        int total = papers.size();
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, total);
        if (start > end) {
            return Result.success(new PagingResult<>(Collections.emptyList(), total));
        }
        return Result.success(new PagingResult<>(papers.subList(start, end), total));
    }

    /**
     * 保存用户的搜索历史，将用户的搜索词存入数据库
     */
    public Boolean storeHistory(String[] words, Integer userId) {
        Boolean flag;
        // 将words内的词语按照逗号拼接成字符串
        String query = String.join(",", words);
        // 更新用户的最新一次的搜索词
        flag = searchhistoryService.updateQuery(query, userId);
        return flag;
    }

    /**
     * 获取论文详情
     *
     * @param id 论文id
     * @return 论文详情
     */
    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Papers paper = papersService.getById(id);
        return Result.success(paper);
    }

    /**
     * 获取论文的相关度，做推荐
     *
     * @param searchForm 搜索表单
     * @return 论文相关度
     */
    @RequestMapping("/searchByPaper")
    public Result searchByPaper(@RequestBody SearchForm searchForm) {
        Integer paperId = searchForm.getPaperId();
        Integer page = searchForm.getPage() == null ? 1 : searchForm.getPage();
        Integer limit = searchForm.getLimit() == null ? 10 : searchForm.getLimit();

        // 根据paperId获取相关度最高的20篇论文，按照相关度降序排列，如果不足20篇则返回所有
        List<Papers> relatedPapers = papersMapper.getRelatedPapers(paperId);
        int total = relatedPapers.size();
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, total);
        if (start > end) {
            return Result.success(new PagingResult<>(Collections.emptyList(), total));
        }
        return Result.success(new PagingResult<>(relatedPapers.subList(start, end), total));
    }

    /**
     * 获取热词
     *
     * @return 热词
     */
    @RequestMapping("/getHotWords")
    public Result getHotWords() {
        List<String> hotWords = searchhistoryService.getHotWords();
        return Result.success(hotWords);
    }
}
