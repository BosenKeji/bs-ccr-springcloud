package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoiceClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import cn.bosenkeji.vo.transaction.CoinPairChoicePositionDetailResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 14:50
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data" ,configuration = FeignClientConfig.class
        ,fallbackFactory = ICoinPairChoiceClientServiceFallbackFactory.class
        )
public interface ICoinPairChoiceClientService {

    /**
     * @param pageNum
     * @param pageSizeCommon
     * @param tradePlatformApiBindProductComboId
     * @param coinId
     * @return
     */
    @GetMapping("/coin_pair_choice/")
    public PageInfo getListCoinPairChoiceWithPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                                                  @RequestParam("tradePlatformApiBindProductComboId") int tradePlatformApiBindProductComboId,
                                                  @RequestParam("coinId") int coinId);

    @GetMapping("/coin_pair_choice/check_coin_pair_choice")
    public Result checkExistByCoinPairNameAndTradePlatformApiBindProductComboId(@RequestParam("coinPairName") String coinPairName,@RequestParam("tradePlatformApiBindProductComboId") int tradePlatformApiBindProductComboId);

    @GetMapping("/coin_pair_choice/{id}")
    public CoinPairChoice getOneCoinPairChoice(@PathVariable("id") int id);

    @PostMapping("/coin_pair_choice/")
    public Result addOneCoinPairChoice(@RequestParam("tradePlatformApiBindProductComboId")  int tradePlatformApiBindProductComboId, @RequestParam("isStrategy")  int strategyStatus, @RequestParam("coinPairId")   int coinPairId);

    @PutMapping("/coin_pair_choice/")
    public Result updateCoinPairChoice(@RequestBody CoinPairChoice coinPairChoice);

    @DeleteMapping("/coin_pair_choice/{id}")
    public Result deleteOneCoinPairChoice(@PathVariable("id") int id,@RequestParam("tradePlatformApiBindProductComboId")int tradePlatformApiBindProductComboId);

    @GetMapping("/coin_pair_choice/all")
    List<CoinPairChoice> findAll();

    @DeleteMapping("/coin_pair_choice/batch")
    public Result batchDelete(@RequestParam("coinPairChoiceIds") String coinPairChoiceIds,@RequestParam("tradePlatformApiBindProductComboId")int tradePlatformApiBindProductComboId);

    @GetMapping("/coin_pair_choice/record")
    public PageInfo recordByCoinId (@RequestParam(value="pageNum") int pageNum,
                                    @RequestParam(value = "pageSizeCommon") int pageSizeCommon,
                                    @RequestParam("tradePlatformApiBindProductComboId")  int tradePlatformApiBindProductComboId,
                                    @RequestParam("coinId")  int coinId,
                                    @RequestParam("type")  String type);

    @GetMapping("/coin_pair_choice/position_details")
    public Result getCoinPairChoicePositionDetails(@RequestParam("coinPairChoiceId") int coinPairChoiceId);

    @GetMapping("/coin_pair_choice/by_is_start")
    public List listByIsStart(
            @RequestParam("tradePlatformApiBindProductComboId") int tradePlatformApiBindProductComboId,
            @RequestParam("isStart") int isStart,
            @RequestParam("coinId") int coinId);

    }
