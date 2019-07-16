package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.ResponseResult;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StrategySequenceController {

    @Autowired
    private StrategySequenceService strategySequenceService;


    @RequestMapping(value = "/strategy/sequence/{id}",method = RequestMethod.GET)
    public ResponseResult<StrategySequence> getStrategySequence(@PathVariable("id") Integer id){
        StrategySequence strategySequence = strategySequenceService.getStrategySequence(id);
        ResponseResult<StrategySequence> responseResult = new ResponseResult(HttpStatus.OK,strategySequence);
        if (strategySequence == null) {
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseResult.setData(null);
        }
        return responseResult;
    }

    @RequestMapping(value = "/strategy/sequencevalue/{sequenceId}",method = RequestMethod.GET)
    public ResponseResult<StrategySequenceValue> getSequenceValue(@PathVariable Integer sequenceId) {
        StrategySequenceValue strategySequenceValue = strategySequenceService.getSequenceValue(sequenceId);
        ResponseResult<StrategySequenceValue> responseResult = new ResponseResult(HttpStatus.OK,strategySequenceValue);
        if (strategySequenceValue == null) {
            responseResult.setData(null);
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return responseResult;
    }

    @RequestMapping(value = "/strategy/sequences",method = RequestMethod.GET)
    public ResponseResult<List> getSequences(){
        List<StrategySequence> sequences = strategySequenceService.getSequences();
        ResponseResult<List> responseResult = new ResponseResult(HttpStatus.OK,sequences);
        if (sequences == null) {
            responseResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseResult.setData(null);
        }
        return responseResult;
    }


}
