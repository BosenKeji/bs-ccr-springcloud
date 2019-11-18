package cn.bosenkeji.interfaces;

public interface TradeTypeInterface {

    int BUY = 1;
    int SHELL = 2;

    //ai建仓
    int aiBuildingRepository = 1;
    //ai止盈
    int aiStopProfit = 2;
    //手动清仓
    int manualClearRepository =3;
}
