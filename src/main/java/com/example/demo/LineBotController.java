package com.example.demo;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.BuyerDAODB;
import com.example.demo.dao.CartDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ReplyDAO;
import com.example.demo.dao.TypeDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dao.ProductTypeDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.entity.Buyer;
import com.example.demo.flex.*;
import com.example.demo.replyTextMessage.BuyerInformation;
import com.example.demo.replyTextMessage.ExchangePickMoney;
import com.example.demo.replyTextMessage.ExchangePickMoneyAll;
import com.example.demo.replyTextMessage.WelcomeInformation;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class LineBotController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Autowired
    private BuyerDAO buyerDAO;
    @Autowired
    private OrderListDAO orderListDAO;
    @Autowired
    private ReplyDAO replyDAO;
    @Autowired
    private TypeDAO typeDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private ProductTypeDAO productTypeDAO;

    private Buyer buyer;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {

        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    @EventMapping
    public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {

        handleSticker(event.getReplyToken(), event.getMessage());
    }

    private void handleSticker(String replyToken, StickerMessageContent content) {
        reply(replyToken, new StickerMessage(content.getPackageId(), content.getStickerId()));
    }

    private void replySticker(String replyToken, String packageId, String stickerId) {
        this.reply(replyToken, new StickerMessage(packageId, stickerId));
    }
    List<String> order = new ArrayList<String>();
    @EventMapping
    public void handleDefaultEvent(PostbackEvent event) {
        System.out.println("event: " + event);
        System.out.println("event: " + event.getReplyToken());
        System.out.println("data: " + event.getPostbackContent().getData());
        String[] data= event.getPostbackContent().getData().split(" ");
        String replyToken= event.getReplyToken();
        
        switch (data[data.length-1]){
            case "黑巧克力(24入)": case"白巧克力(24入)": case"雙層玻璃杯": case"不鏽鋼胖胖杯": case "陶瓷變色馬克杯": case "綠茶洗面乳80ml": case "火山泥洗面乳70ml":
            String product_name=data[0];
            String quantity = data[1];
            String product_style = data[2];
            order.add(product_name);
            order.add(quantity);
            order.add(product_style);
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new UsePickmoneyFlexMessage(buyerDAO,buyer_id).get());
            // this.reply(replyToken, new StyleFlexMessage(productDAO,product_name).get());
        //    this.replyText(replyToken, "請輸入購買數量");
            break;

            case "Y":
            String usePickmoney=data[0];
            System.out.println(data);
            order.add(usePickmoney);
            this.reply(replyToken, new PaySelectionFlexMessage().get());
            break;

            case "N":
            usePickmoney=data[0];
            System.out.println(data);
            order.add(usePickmoney);
            this.reply(replyToken, new PaySelectionFlexMessage().get());
            break;
            case "LinePay": case "信用卡線上付款": case "超商取貨付款":
            String paymentChoice=data[0];
            order.add(paymentChoice);
            System.out.println("here is final");
            System.out.println(order);
            buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new OrderInformationFlexMessage(buyerDAO, buyer_id, order, orderListDAO, orderItemDAO, productDAO).get());
           order.clear();
            break;
            
        }
    }

    private void reply(String replyToken, List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void reply(String replyToken, Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void replyText(String replyToken, String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "....";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content) {
         String text = content.getText();
        // String quantity = "^[0-9]";
        // if (text.matches(quantity)){
        //     order.add(text);
        //     String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new UsePickmoneyFlexMessage(buyerDAO,buyer_id).get());
        // }
        
        switch (text) {
        case "賴皮客服": {
            this.reply(replyToken, new QAFlexMessage().get());  
            break;
        }
        case "圖文選單導覽":{
            this.reply(replyToken, new WelcomeInformation().get());
            break;
        }
        case "如何退貨":{
            this.replyText(replyToken, "請撥打02-12345678詢問詳細事項");
            break;
        }
        case "下單後過多久出貨":{
            this.replyText(replyToken, "一般來說7個工作天內出貨");
            break;
        }
        case "以上問題沒有您所想問的嗎":{
            this.replyText(replyToken, "請直接傳送訊息等待專人為您服務");
            break;
        }
        case "賴皮指數": {
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new PickPointFlexMessage(buyerDAO,buyer_id).get());
            break;
        }
        case "兌換": {
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new PickMoneyFlexMessage(buyerDAO,buyer_id).get());
            break;
        }
        case "快速購買": {
            String buyer_id = event.getSource().getUserId();
            int product_id=1;
            this.reply(replyToken, new QuickPurchase(buyer_id, product_id, cartDAO, productDAO, buyerDAO).get());
            break;
        }
        case "賴皮紀錄": {
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new OrderStatusFlexMessage(orderListDAO,buyer_id).get());
            break;
        }
        case "已完成": {
            String buyer_id = event.getSource().getUserId();
            int product_id = 1;
            String orderlist_status = "已完成";
            this.reply(replyToken, new OrderListFinishFlexMessage(buyer_id, product_id, orderlist_status, orderListDAO, productDAO, orderItemDAO, buyerDAO).get());
            
            break;
        }
        case "未出貨": {
            String buyer_id = event.getSource().getUserId();
            int product_id = 1;
            String orderlist_status = "未出貨";
            this.reply(replyToken, new OrderListNotYetFlexMessage(buyer_id, product_id, orderlist_status, orderListDAO, productDAO, orderItemDAO, buyerDAO).get());
            break;
        }
        case "運送中": {
            String buyer_id = event.getSource().getUserId();
            int product_id = 1;
            String orderlist_status = "運送中";
            this.reply(replyToken, new OrderListTransportFlexMessage(buyer_id, product_id, orderlist_status, orderListDAO, productDAO, orderItemDAO, buyerDAO).get());
            break;
        }
        case "兌換全部":{
        
            String buyer_id = event.getSource().getUserId();
           
            try{
                ExchangePickMoneyAll exchangePickMoneyAll = new ExchangePickMoneyAll(buyerDAO,buyer_id);
                this.reply(replyToken,exchangePickMoneyAll.get());
                
            }
            catch (Exception e){
                System.out.println(e);
            }
            break;
        }
        case "兌換一次":{
        
            String buyer_id = event.getSource().getUserId();
           
            try{
                ExchangePickMoney exchangePickMoney = new ExchangePickMoney(buyerDAO,buyer_id);
                this.reply(replyToken,exchangePickMoney.get());
                
            }
            catch (Exception e){
                System.out.println(e);
            }
            break;
        }
        case "加入賴皮": {
            String buyer_id = event.getSource().getUserId();
            
            if (buyer_id != null) {
                lineMessagingClient.getProfile(buyer_id).whenComplete((profile, throwable) -> {
                    if (throwable != null) {
                        this.replyText(replyToken, throwable.getMessage());
                        return;
                    }
                    String buyer_name = profile.getDisplayName();
                    try {
                        BuyerInformation buyer = new BuyerInformation(buyerDAO,buyer_name,buyer_id);
                        this.reply(replyToken,buyer.get());
                    }
                    catch (DuplicateKeyException e){
                        System.out.println("exists");
                        this.replyText(replyToken,buyer_name+"，您已經註冊過囉!");
                    }
                    catch (SQLException e){
                        System.out.println(e);
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }

                });
            }
            
            
            
            break;
        }
        default:
          //  this.replyText(replyToken, text);
            break;

        } 

    }
}