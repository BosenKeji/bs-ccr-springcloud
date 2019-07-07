package cn.bosenkeji.controller;

import cn.bosenkeji.vo.Coin;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ConsumerCoinController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerCoinController {

    public static final String COIN_GET_URL = "http://localhost:8080/coin/get/";
    public static final String COIN_LIST_URL="http://localhost:8080/coin/list/";
    public static final String COIN_ADD_URL = "http://localhost:8080/coin/add/";


    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpHeaders httpHeaders;


    @RequestMapping("/coin/get")
    public Object getProduct(int id) {
        Coin coin = restTemplate.exchange(COIN_GET_URL + id, HttpMethod.GET,
                new HttpEntity<Object>(httpHeaders), Coin.class).getBody();
        return coin;
    }

    @RequestMapping("/coin/list")
    public  Object listProduct() {
        List<Coin> list = restTemplate.exchange(COIN_LIST_URL,HttpMethod.GET,
                new HttpEntity<Object>(httpHeaders), List.class).getBody();
        return list;
    }

    @RequestMapping("/coin/add")
    public Object addCoin(Coin coin) {
        Boolean result = restTemplate.exchange(COIN_ADD_URL, HttpMethod.POST,
                new HttpEntity<Object>(coin,httpHeaders), Boolean.class).getBody();
        return result;
    }

}
