package takeaway_one.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import takeaway_one.common.R;
import takeaway_one.pojo.Category;
import takeaway_one.service.impl.CategoryService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类信息
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> Page(Integer page,Integer pageSize){
        Page<Category> pageCategory = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(pageCategory,wrapper);
        return R.success(pageCategory);
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String>save(@RequestBody Category category){
        boolean result = categoryService.save(category);
        if(result)
        {
            return R.success("操作成功");
        }
        return R.error("操作失败");
    }

    @DeleteMapping
    public R<String>delete(Long ids){

        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        boolean result = categoryService.updateById(category);
        if(result){
            return R.success("修改成功");
        }
        else {
            return R.error("修改失败");
        }
    }

    @GetMapping("/list")
    public R<List> getCategory(Category category){
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(category.getType()!=null,Category::getType,category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }
}
