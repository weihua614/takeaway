package takeaway_one.service;

import com.baomidou.mybatisplus.extension.service.IService;
import takeaway_one.pojo.Category;


public interface ICategoryService extends IService<Category> {
    public void remove(Long id);
}
