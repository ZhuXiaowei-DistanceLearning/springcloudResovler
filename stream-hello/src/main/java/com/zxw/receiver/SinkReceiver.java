package com.zxw.receiver;

import com.zxw.StreamServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author zxw
 * @date 2019/9/6 14:46
 */
@EnableBinding(Sink.class)
public class SinkReceiver {
    private static Logger logger = LoggerFactory.getLogger(StreamServerApplication.class);

    @StreamListener(Sink.INPUT)
    public void receiver(Object payload) {
        logger.info("Received:" + payload);
    }
}
