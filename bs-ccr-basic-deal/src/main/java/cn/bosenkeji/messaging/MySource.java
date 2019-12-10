package cn.bosenkeji.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author CAJR
 * @create 2019/7/26 15:39
 */
public interface MySource {
    @Output("huobi_output")
    MessageChannel huobiOutput();

    @Output("okex_output")
    MessageChannel okexOutput();

//    @Output("group_id_output_huobi")
//    MessageChannel groupIdOutPutHB();
//
//    @Output("group_id_output_okex")
//    MessageChannel groupIdOutPutOK();
}
