package cn.ye.reggie.service.impl;

import cn.ye.reggie.entity.Employee;
import cn.ye.reggie.mapper.EmployeeMapper;
import cn.ye.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
