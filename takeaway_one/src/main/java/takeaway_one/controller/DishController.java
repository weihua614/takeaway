package takeaway_one.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import sun.rmi.runtime.Log;
import takeaway_one.common.R;
import takeaway_one.dto.DishDto;
import takeaway_one.pojo.Category;
import takeaway_one.pojo.Dish;
import takeaway_one.service.ICategoryService;
import takeaway_one.service.IDishFlavorService;
import takeaway_one.service.IDishService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private IDishService dishService;
    @Autowired
    private IDishFlavorService dishFlavorService;
    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name){
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name),Dish::getName,name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage,wrapper);
        List<Dish> listDish=dishPage.getRecords();
        List<DishDto> dishDtoList=new ArrayList<>();
        for(int i=0;i<listDish.size();i++)
        {
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(listDish.get(i),dishDto);
            Category category = categoryService.getById(dishDto.getCategoryId());
            dishDto.setCategoryName(category.getName());
            dishDtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dishDtoList);
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> update(@PathVariable("id") Long id)
    {
        DishDto dishDto = dishService.getDishDtoById(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);
        return R.success("添加成功");
    }

    @PostMapping("/status/{num}")
    @Transactional
    public R<String> status(@PathVariable("num")Integer status,String ids)
    {
        String[] strings = ids.split(",");
        Dish dish = new Dish();
        dish.setStatus(status);
        for(String id:strings)
        {
            Long tem=Long.parseLong(id);
            dish.setId(tem);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }
    @DeleteMapping
    @Transactional
    public R<String> delete(String ids){
        String[] strings = ids.split(",");
        for(String str:strings)
        {
            Long id=Long.parseLong(str);
            boolean result = dishService.removeById(id);
            if(!result) return R.error("删除失败");
        }
        return R.success("删除成功");
    }
}
