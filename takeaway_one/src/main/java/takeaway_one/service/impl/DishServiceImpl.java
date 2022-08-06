package takeaway_one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import takeaway_one.dto.DishDto;
import takeaway_one.mapper.DishMapper;
import takeaway_one.pojo.Dish;
import takeaway_one.pojo.DishFlavor;
import takeaway_one.service.IDishFlavorService;
import takeaway_one.service.IDishService;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    @Autowired
    private IDishFlavorService dishFlavorService;

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId=dishDto.getId();

        List<DishFlavor> dishFlavors=dishDto.getFlavors();
        for(int i=0;i<dishFlavors.size();i++)
        {
            dishFlavors.get(i).setDishId(dishId);
        }
        dishFlavorService.saveBatch(dishFlavors);
    }
}
