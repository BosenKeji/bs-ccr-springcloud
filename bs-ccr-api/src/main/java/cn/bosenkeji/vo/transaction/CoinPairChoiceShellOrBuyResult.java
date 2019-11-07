package cn.bosenkeji.vo.transaction;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author CAJR
 * @date 2019/11/7 12:28 下午
 */
public class CoinPairChoiceShellOrBuyResult {

    @ApiModelProperty("自选币 id")
    private int coinPairChoiceId;

    @ApiModelProperty("自选币名字")
    private String coinPairName;

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }

    public String getCoinPairName() {
        return coinPairName;
    }

    public void setCoinPairName(String coinPairName) {
        this.coinPairName = coinPairName;
    }
}
