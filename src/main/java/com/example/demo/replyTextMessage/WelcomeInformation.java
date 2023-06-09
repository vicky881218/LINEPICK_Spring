package com.example.demo.replyTextMessage;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Supplier;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Text;

import org.springframework.beans.factory.annotation.Autowired;
public class WelcomeInformation implements Supplier<TextMessage> { 
    @Override
    public TextMessage get() {
        return new TextMessage("請在開始購物前，點選圖文選單中的「加入賴皮」，這樣才會成為我們的會員喔~"+"\n"+" "+"\n"+"若要開始購物，請點選圖文選單中的「開始購物」，即可進入購物頁面。"
        +"\n"+" "+"\n"+"若有問題，或者是想了解圖文選單的使用說明，請點選圖文選單中的「賴皮客服」。"+"\n"+" "+"\n"+
        "若想查看購物車內容，請點選圖文選單中的「賴皮願望」，或者是進入購物頁面也可查看購物車內的商品。"+"\n"+" "+"\n"+
        "若要將您所擁有的賴皮指數兌換成購物金，請點選圖文選單中的「賴皮指數」即可進行兌換。"+"\n"+" "+"\n"+
        "若想查看購買紀錄，或者是回購商品，請點選圖文選單中的「賴皮紀錄」。"+"\n"+" "+"\n"+
        "消費滿500元送賴皮指數100點，100點可兌換10元賴皮購物金供下次消費使用喔~"+"\n"+" "+"\n"+
        "!!!!!!!!下訂後不可取消以及更改訂單資訊，請再三考慮後再下訂喔!!!!!!!" );   
    }
}
