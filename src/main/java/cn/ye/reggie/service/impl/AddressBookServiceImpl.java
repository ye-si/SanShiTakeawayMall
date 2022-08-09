package cn.ye.reggie.service.impl;

import cn.ye.reggie.entity.AddressBook;
import cn.ye.reggie.mapper.AddressBookMapper;
import cn.ye.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>  implements AddressBookService {
}
