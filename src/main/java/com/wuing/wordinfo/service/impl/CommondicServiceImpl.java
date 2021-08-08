package com.wuing.wordinfo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuing.wordinfo.dao.CommondicMapper;
import com.wuing.wordinfo.model.Commondic;
import com.wuing.wordinfo.service.CommondicService;
@Service
public class CommondicServiceImpl extends ServiceImpl<CommondicMapper, Commondic> implements CommondicService{

}
