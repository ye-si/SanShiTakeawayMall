package cn.ye.reggie.dto;

import cn.ye.reggie.entity.Setmeal;
import cn.ye.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
