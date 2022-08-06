package takeaway_one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import takeaway_one.mapper.SetmealMapper;
import takeaway_one.pojo.Setmeal;
import takeaway_one.service.ISetmealService;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {
}
