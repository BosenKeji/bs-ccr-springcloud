package cn.bosenkeji.controller;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/strategy")
@RestController
public class ConsumerStrategyController {

    @Resource
    private IStrategyService strategyService;

    @GetMapping(value = "/{id}")
    public StrategyVO get(@PathVariable("id") Integer id) {
        return strategyService.getStrategy(id);
    }

    @GetMapping(value="/" )
    public PageInfo listByPage(){
        return strategyService.listByPage(0,10);
    }

}
