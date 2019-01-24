package com.ocean;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Ocean on 2019/1/23 15:08.
 */
@Component
public class Messageproducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println(correlationData);
            if(ack){
                System.err.println("已接收："+cause);
            }else{
                System.err.println("未接收："+cause);
            }
        }
    };

    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println(replyCode+"-"+replyText);
            System.err.println("exchange:"+exchange+"--routingkey:"+routingKey);
        }
    };

    public void sendMessage(Employee emp){

        //CorrelationData 对象作为消息的附加信息传递，通常用它作为消息的自定义ID
        CorrelationData correlationData = new CorrelationData(emp.getEmpno() + "-" + new Date().getTime());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("springboot.exchange" , "hr.employee" , emp , correlationData);

    }
}
