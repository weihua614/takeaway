package takeaway_one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import takeaway_one.pojo.Category;
import takeaway_one.pojo.Employee;

@Component
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
