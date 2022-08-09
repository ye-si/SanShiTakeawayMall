package cn.ye.reggie.service.impl;

import cn.ye.reggie.common.CustomException;
import cn.ye.reggie.entity.Category;
import cn.ye.reggie.entity.Dish;
import cn.ye.reggie.entity.Setmeal;
import cn.ye.reggie.mapper.CategoryMapper;
import cn.ye.reggie.service.CategoryService;
import cn.ye.reggie.service.DishService;
import cn.ye.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除前需要判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //1.添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关连了菜品，如果已经关联，抛出异常
        if (count > 0) {
            //已关联菜品，抛出业务异常
            throw new CustomException("已关联菜品，不能删除");
        }

        //2.查询当前分类是否关连了套餐，如果已经关联，抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count();
        if (count2 > 0) {
            //已经关联套餐，抛出一个异常
            throw new CustomException("已关联套餐，不能删除");
        }

        //3.正常删除分类
        super.removeById(id);
    }
}
