package cn.ye.reggie.service;

import cn.ye.reggie.entity.OrderDetail;
import cn.ye.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);

    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
