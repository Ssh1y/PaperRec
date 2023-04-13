package com.ssh1y.paperrec.mapper;

import com.ssh1y.paperrec.entity.Papers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author chenweihong
* @description 针对表【Papers】的数据库操作Mapper
* @createDate 2023-03-17 22:41:52
* @Entity com.ssh1y.paperrec.entity.Papers
*/
@Repository
public interface PapersMapper extends BaseMapper<Papers> {

    /**
     * 根据论文id查找相似度最高的论文
     * @param paperid 论文id
     * @return 相关论文
     */
    @Select("SELECT * FROM Papers WHERE id IN (SELECT paperid1 FROM Relatedness WHERE paperid2 = #{paperid} UNION SELECT paperid2 FROM Relatedness WHERE paperid1 = #{paperid})")
    List<Papers> getRelatedPapers(Integer paperid);
}




