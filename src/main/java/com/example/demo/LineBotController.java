package com.example.demo;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dao.ProductTypeDAO;
import com.example.demo.dao.TypeDAO;
import com.example.demo.flex.*;
import com.example.demo.replyTextMessage.BuyerInformation;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.postback.PostbackContent;
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
    private ProductDAO productDAO;
    @Autowired
    private ProductTypeDAO productTypeDAO;
    @Autowired
    private TypeDAO typeDAO;

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
        //List<String> order = new ArrayList<String>();
        //order.clear();

        switch (data[data.length-1]){
            case "上衣": case "褲裝":
            String type_name=data[0];
            this.reply(replyToken, new ProductFlexMessage(typeDAO,productDAO, productTypeDAO,type_name).get());
            break;

            case "背心": case "碎花裙":
            String product_name=data[0];
            order.add(product_name);
            this.reply(replyToken, new StyleFlexMessage(productDAO,product_name).get());
            break;

            case "黑":  case "白": case "紅":
            product_name=data[0];
            String product_style=data[1];
            order.add(product_style);
            this.reply(replyToken, new SizeFlexMessage(productDAO,product_style,product_name).get());
            break;

            case "S":  case "M": case "L":
            product_name=data[0];
            product_style=data[1];
            String product_size=data[2];
            order.add(product_size);           
            this.replyText(replyToken, "請輸入購買數量");
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

            case "LinePay": case "匯款": case "貨到付款":
                String paymentChoice=data[0];
                order.add(paymentChoice);
                System.out.println("here is final");
                System.out.println(order);
                String buyer_id = event.getSource().getUserId();
                this.reply(replyToken, new OrderInformationFlexMessage(buyerDAO, buyer_id,order).get());
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
        String quantity = "^[0-9]";
        if (text.matches(quantity)){
            order.add(text);
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new UsePickmoneyFlexMessage(buyerDAO,buyer_id).get());
        }
        switch (text) {
        case "profile": { // 名字+個簽
            String buyer_id = event.getSource().getUserId();
            if (buyer_id != null) {
                lineMessagingClient.getProfile(buyer_id).whenComplete((profile, throwable) -> {
                    if (throwable != null) {
                        this.replyText(replyToken, throwable.getMessage());
                        return;
                    }

                    this.reply(replyToken,
                            Arrays.asList(new TextMessage("Display name: " + profile.getDisplayName()),
                                    new TextMessage("Status message: " + profile.getStatusMessage()),
                                    new TextMessage("userId: " + buyer_id)));

                });
            }
            break;
        }

        case "hi": { // 名字+歡迎光臨
            String userId = event.getSource().getUserId();
            if (userId != null) {
                lineMessagingClient.getProfile(userId).whenComplete((profile, throwable) -> {
                    if (throwable != null) {
                        this.replyText(replyToken, throwable.getMessage());
                        return;
                    }

                    this.reply(replyToken, Arrays.asList(new TextMessage(profile.getDisplayName() + "歡迎光臨")));

                });
            }
            break;
        }

        case "hello": {// 限定回覆字
            this.replyText(replyToken, "hihihi!");
            break;
        }

        case "sticker": {// 貼圖
            this.replySticker(replyToken, "11539", "52114116");
            break;
        }

        case "quickreply": {
            this.reply(replyToken, new QuickReplyMessage().get());
            break;
        }

        case "flex": {
            String buyer_id = event.getSource().getUserId();
            this.reply(replyToken, new testFlexMessage(buyerDAO, buyer_id).get());
            break;
        }

        case "分類": {
            this.reply(replyToken, new TypeQuickReplyMessage(typeDAO).get());
            break;
        }

        // case "衣服flex": {
        //     this.reply(replyToken, new ProductFlexMessage(productDAO, productTypeDAO).get());
        //     break;
        // }

        // case "顏色flex": {
        //     // String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new StyleFlexMessage(productDAO).get());
        //     break;
        // }

        // case "sizeflex": {
        //     // String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new SizeFlexMessage(productDAO).get());
        //     break;
        // }

        // case "/[0-9]/": {
        //     // String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new UsePickmoneyFlexMessage().get());
        //     break;
        // }

        // case "payflex": {
        //     // String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new PaySelectionFlexMessage().get());
        //     break;
        // }

        // case "下單": {
        //     String buyer_id = event.getSource().getUserId();
        //     this.reply(replyToken, new BuyerInfFlexMessage(buyerDAO, buyer_id).get());
        //     break;
        // }

        case "我": {
            String buyer_id = event.getSource().getUserId();

            try {
                BuyerInformation buyer = new BuyerInformation(buyerDAO, text, buyer_id);
                this.reply(replyToken, buyer.get());
            } catch (DuplicateKeyException e) {
                System.out.println("exists");
            } catch (SQLException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }

            break;
        }

        default:
            //this.replyText(replyToken, text);
            break;

        }

    }
}