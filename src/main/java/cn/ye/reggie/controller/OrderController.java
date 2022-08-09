package cn.ye.reggie.controller;

import cn.ye.reggie.common.Result;
import cn.ye.reggie.dto.OrdersDto;
import cn.ye.reggie.entity.OrderDetail;
import cn.ye.reggie.entity.Orders;
import cn.ye.reggie.service.OrderDetailService;
import cn.ye.reggie.service.OrdersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) {

        log.info("订单信息:" + orders.toString());

        ordersService.submit(orders);
        return Result.success("已成功下单!");

    }


    //    http://localhost:8181/order/page?page=1&pageSize=10&number=11
    @GetMapping("/page")
    public Result<Page> showPage(int page, int pageSize, Long number,String beginTime,String endTime) {

        Page<Orders> ordersPage = new Page(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotEmpty(beginTime),Orders::getOrderTime,beginTime)
                .lt(StringUtils.isNotEmpty(endTime),Orders::getOrderTime,endTime);

        ordersService.page(ordersPage, queryWrapper);
        return Result.success(ordersPage);
    }


//    @PutMapping
//    public Result<Orders> updataStatus(Orders orders){
//
//        Integer status = orders.getStatus();
//
//        if (status != null){
//            orders.setStatus(3);
//        }
//
//        ordersService.updateById(orders);
//        return Result.success(orders);
//    }

//    @GetMapping("/userPage")
//    public Result<Page> page(int page, int pageSize) {
//
//        //分页构造器对象
//        Page<Orders> pageInfo = new Page<>(page, pageSize);
//        //构造条件查询对象
//        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
//
//        //添加排序条件，根据更新时间降序排列
//        queryWrapper.orderByDesc(Orders::getOrderTime);
//        ordersService.page(pageInfo, queryWrapper);
//
//        return Result.success(pageInfo);
//    }

    // http://localhost:8181/order/userPage?page=1&pageSize=5
    // 分页展示订单详情
//    @GetMapping("/userPage")
//    public Result<Page> ordersPage(int page, int pageSize) {
//
//        Long userId = BaseContext.getCurrentId();
//
//        Orders order = ordersService.getById(userId);
//        Long orderId = order.getId();
//
//
//        // 创建分页构造器对象
//        Page pageInfo = new Page(page, pageSize);
//        //  构造条件构造器
//        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper();
//
//        queryWrapper.eq(orderId != null, OrderDetail::getOrderId, orderId);
//
//        orderDetailService.page(pageInfo,queryWrapper);
//
//
//        //   name不为null，才会 比较 getUsername方法和前端传入的name是否匹配 的过滤条件
////        queryWrapper.eq();
//        //  根据 更新用户的时间升序 分页展示
////        queryWrapper.orderByAsc(Employee::getUpdateTime);
//
//        // 去数据库查询
////        employeeService.page(pageInfo,queryWrapper);
//        return Result.success(pageInfo);
//    }

    // http://localhost:8181/order/userPage?page=1&pageSize=5
    @GetMapping("/userPage")
    public Result<Page> page(int page, int pageSize){
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> pageDto = new Page<>(page,pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //这里树直接把分页的全部结果查询出来，没有分页条件
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Orders::getOrderTime);
        this.ordersService.page(pageInfo,queryWrapper);

        //通过OrderId查询对应的OrderDetail
        LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();

        //对OrderDto进行需要的属性赋值
        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> orderDtoList = records.stream().map((item) ->{
            OrdersDto orderDto = new OrdersDto();
            //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
            Long orderId = item.getId();//获取订单id
            List<OrderDetail> orderDetailList = this.ordersService.getOrderDetailsByOrderId(orderId);
            BeanUtils.copyProperties(item,orderDto);
            //对orderDto进行OrderDetails属性的赋值
            orderDto.setOrderDetails(orderDetailList);
            return orderDto;
        }).collect(Collectors.toList());

        //使用dto的分页有点难度.....需要重点掌握
        BeanUtils.copyProperties(pageInfo,pageDto,"records");
        pageDto.setRecords(orderDtoList);
        return Result.success(pageDto);
    }

}
