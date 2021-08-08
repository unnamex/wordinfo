package com.wuing.wordinfo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuing.wordinfo.dao.VocTbMapper;
import com.wuing.wordinfo.model.VocTb;
import com.wuing.wordinfo.service.VocTbService;
@Service
public class VocTbServiceImpl extends ServiceImpl<VocTbMapper, VocTb> implements VocTbService{

}
