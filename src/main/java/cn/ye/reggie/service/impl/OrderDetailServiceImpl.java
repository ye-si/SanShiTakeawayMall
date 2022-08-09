package cn.ye.reggie.service.impl;

import cn.ye.reggie.entity.OrderDetail;
import cn.ye.reggie.mapper.OrderDetailMapper;
import cn.ye.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
