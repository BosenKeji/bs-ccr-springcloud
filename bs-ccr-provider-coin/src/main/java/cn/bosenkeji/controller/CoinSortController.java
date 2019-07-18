package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinSortEnum;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.vo.CoinSort;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 14:23
 */
@RestController
@RequestMapping("/coinsort")
@Api(value = "货币排序接口")
public class CoinSortController {

    @Resource
    CoinSortService coinSortService;

    @Resource
    DiscoveryClient discoveryClient;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取货币排序列表接口",httpMethod = "GET")
    @GetMapping("/")
    public PageInfo list(){
        return this.coinSortService.listByPage(0,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币排序接口",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinSort get(@PathVariable @Min(1) int id){
        return this.coinSortService.get(id).orElseThrow(()-> new NotFoundException(CoinSortEnum.NAME));
    }

    @ApiOperation(value = "添加单个货币排序接口",httpMethod = "POST")
    @PostMapping("/")
    public boolean add(@RequestBody CoinSort coinSort){
        coinSort.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinSortService.add(coinSort);
    }

    @ApiOperation(value = "更新货币排序接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody CoinSort coinSort){
        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinSortService.update(coinSort);
    }

    @ApiOperation(value = "删除货币排序接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinSortService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }
}
