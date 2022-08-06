package takeaway_one.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import takeaway_one.mapper.DisFlavorMapper;
import takeaway_one.pojo.DishFlavor;
import takeaway_one.service.IDishFlavorService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DisFlavorMapper,DishFlavor> implements IDishFlavorService {
}
