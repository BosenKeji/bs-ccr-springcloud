package cn.bosenkeji.controller;

import cn.bosenkeji.service.IStrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/strategysequence")
public class ConsumerStrategySequenceController {

    @Resource
    private IStrategySequenceService strategySequenceService;

    @GetMapping(value = "/")
    public PageInfo<StrategySequence> findAll() {
        return strategySequenceService.findAll(0, 10);
    }

    @GetMapping(value = "/{id}")
    public StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") Integer id){
        return strategySequenceService.findSequenceByPrimaryKey(id);
    }

    @GetMapping(value = "/value/{strategyId}" )
    public String getSequenceValueByStrategyId(@PathVariable("strategyId") Integer strategyId) {
        return strategySequenceService.getSequenceValueByStrategyId(strategyId);
    }

    @PostMapping("/strategysequence/")
    boolean insertStrategySequenceBySelective(StrategySequence sequence) {
        return strategySequenceService.insertStrategySequenceBySelective(sequence);
    }

    @PostMapping("/strategysequence/value/")
    boolean insertStrategySequenceBySelective(StrategySequenceValue sequenceValue) {
        return strategySequenceService.insertStrategySequenceBySelective(sequenceValue);
    }
}
