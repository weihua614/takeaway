package takeaway_one.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import takeaway_one.common.R;
import takeaway_one.pojo.Employee;
import takeaway_one.service.IEmployeeService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session){
        String password = employee.getPassword();
        String username = employee.getUsername();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,username);
        Employee emp = employeeService.getOne(wrapper);
        if(emp==null)
        {
            return R.error("登录失败");
        }
        if(!emp.getPassword().equals(md5Password))
        {
            return R.error("登录失败");
        }
        if(emp.getStatus()==0)
        {
            return R.error("账号已禁用");
        }
        session.setAttribute("employee",emp.getId());
        return  R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session)
    {
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpSession session,@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) session.getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<Employee>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if(one!=null&&one.getId()!=null)
        {
            return R.error("员工已创建");
        }
        boolean result = employeeService.save(employee);
        if(!result)
        {
            return R.error("保存失败");
        }
        else {
            return R.success("保存成功");
        }
    }





    @GetMapping("/page")
    public R<Page> zhanshi(Integer page, Integer pageSize, String name){
        Page<Employee> page1=new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name),Employee::getName,name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(page1,wrapper);
        System.out.println(page1);
        return R.success(page1);
    }
}
