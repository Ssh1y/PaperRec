package com.ssh1y.paperrec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssh1y.paperrec.entity.Papers;
import com.ssh1y.paperrec.service.PapersService;
import com.ssh1y.paperrec.mapper.PapersMapper;
import org.springframework.stereotype.Service;

/**
* @author chenweihong
* @description 针对表【Papers】的数据库操作Service实现
* @createDate 2023-03-17 22:41:52
*/
@Service
public class PapersServiceImpl extends ServiceImpl<PapersMapper, Papers>
    implements PapersService{

}




