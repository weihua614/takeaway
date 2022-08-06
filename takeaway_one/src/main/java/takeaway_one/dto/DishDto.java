package takeaway_one.dto;

import lombok.Data;
import takeaway_one.pojo.Dish;
import takeaway_one.pojo.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
