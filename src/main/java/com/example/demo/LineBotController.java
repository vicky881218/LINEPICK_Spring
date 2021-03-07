package com.example.demo;

import com.example.demo.flex.*;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class LineBotController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

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

    private void replySticker(String replyToken,String packageId,String stickerId) {
        this.reply(replyToken, new StickerMessage(packageId,stickerId));
    }

    @EventMapping
    public void handleDefaultEvent(Event event) {
        System.out.println("event: " + event);
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

        switch (text) {
            case "profile": { // 名字+個簽
                String userId = event.getSource().getUserId();
                if (userId != null) {
                    lineMessagingClient.getProfile(userId).whenComplete((profile, throwable) -> {
                        if (throwable != null) {
                            this.replyText(replyToken, throwable.getMessage());
                            return;
                        }

                        this.reply(replyToken,
                                Arrays.asList(new TextMessage("Display name: " + profile.getDisplayName()),
                                        new TextMessage("Status message: " + profile.getStatusMessage())));

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

                        this.reply(replyToken,
                                Arrays.asList(new TextMessage(profile.getDisplayName()+"歡迎光臨")));

                    });
                }
                break;
            }

            case "hello" :{//限定回覆字
                this.replyText(replyToken, "hihihi!");
                break;
            }

            case "sticker" :{//貼圖
                this.replySticker(replyToken,"11539","52114116");
                break;
            }

            case "quickreply" :{//貼圖
                this.reply(replyToken, new QuickReplyMessage().get());
                break;
            }

            case "flex": {
                this.reply(replyToken, new testFlexMessage().get());
                break;
            }
            default:
                this.replyText(replyToken, text);
                break;

        }

    }
}