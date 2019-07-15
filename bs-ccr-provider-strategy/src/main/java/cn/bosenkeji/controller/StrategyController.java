package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.ResponseResult;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    @RequestMapping(value = "/strategy/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Strategy> getStrategy(@PathVariable("id") Integer id){
        Strategy strategy = strategyService.getStrategy(id);
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.OK,strategy);;
        if (strategy == null) {
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseResult.setData(null);
        }
        return responseResult;
    }

    @RequestMapping(value = "/strategies",method = RequestMethod.GET)
    public ResponseResult<List> getStrategies() {
        List<Strategy> strategies = strategyService.getStrategies();
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),strategies);;
        if (CollectionUtils.isEmpty(strategies)){
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseResult.setData(null);
        }
        return responseResult;
    }

    @RequestMapping(value = "/strategy/lever/{strategyid}",method = RequestMethod.GET)
    public ResponseResult<Integer> getLevelByStrategyId(@PathVariable("strategyid") Integer strategyId){
        Integer result = strategyService.getLeverByStrategyId(strategyId);
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.OK,result);
        if (result == null) {
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseResult.setData(null);
        }
        return responseResult;
    }


    @RequestMapping(value = "/strategy/attribute/{strategyid}",method = RequestMethod.GET)
    public ResponseResult<StrategyAttribute> getStrategyAttribute(@PathVariable("strategyid") Integer strategyId) {
        StrategyAttribute strategyAttribute = strategyService.getStrategyAttribute(strategyId);
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.OK,strategyAttribute);
        if (strategyAttribute == null) {
            responseResult.setData(null);
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return responseResult;
    }

}
