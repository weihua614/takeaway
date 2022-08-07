package takeaway_one.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import takeaway_one.dto.DishDto;
import takeaway_one.mapper.DishMapper;
import takeaway_one.pojo.Category;
import takeaway_one.pojo.Dish;
import takeaway_one.pojo.DishFlavor;
import takeaway_one.service.ICategoryService;
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

    @Override
    public DishDto getDishDtoById(Long id) {
        Dish dish = super.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto){
        this.updateById(dishDto);

        Long dtoId = dishDto.getId();
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dtoId);
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);


        List<DishFlavor> dishFlavors=dishDto.getFlavors();
        for(int i=0;i<dishFlavors.size();i++)
        {
            dishFlavors.get(i).setDishId(dtoId);
        }
        dishFlavorService.saveBatch(dishFlavors);

    }

}
