package takeaway_one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import takeaway_one.dto.DishDto;
import takeaway_one.pojo.Dish;

public interface IDishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
}
