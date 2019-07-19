package cn.bosenkeji.controller;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RequestMapping("/strategy")
@RestController
public class ConsumerStrategyController {

    @Resource
    private IStrategyService strategyService;

    @PostMapping(value = "/")
    boolean addStrategyBySelective(Strategy strategy) {
        return strategyService.addStrategyBySelective(strategy);
    }

    @PostMapping(value = "/attribute/")
    boolean addStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
        return strategyService.addStrategyAttributeBySelective(strategyAttribute);
    }

    @GetMapping(value = "/{id}")
    public StrategyVO get(@PathVariable("id") Integer id) {
        return strategyService.getStrategy(id);
    }

    @GetMapping(value="/{pageNum}/{pageSize}" )
    public PageInfo listByPage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize
    ){
        return strategyService.listByPage(pageNum,pageSize);
    }



}
