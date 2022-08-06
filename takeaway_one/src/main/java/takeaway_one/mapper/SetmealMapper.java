package takeaway_one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import takeaway_one.pojo.Setmeal;

@Mapper
@Component
public interface SetmealMapper extends BaseMapper<Setmeal> {
}
