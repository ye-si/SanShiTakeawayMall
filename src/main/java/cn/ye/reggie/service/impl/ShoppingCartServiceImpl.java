package cn.ye.reggie.service.impl;

import cn.ye.reggie.entity.ShoppingCart;
import cn.ye.reggie.mapper.ShoppingCartMapper;
import cn.ye.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
