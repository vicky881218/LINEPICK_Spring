package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.linecorp.bot.model.action.CameraAction;
import com.linecorp.bot.model.action.CameraRollAction;
import com.linecorp.bot.model.action.LocationAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;

public class QuickReplyMessage implements Supplier<Message> {
    @Override
    public Message get() {
        final List<QuickReplyItem> items = Arrays.<QuickReplyItem>asList(
                QuickReplyItem.builder()
                              .action(new MessageAction("MessageAction", "MessageAction"))
                              .build(),
                QuickReplyItem.builder()
                              .action(CameraAction.withLabel("CameraAction"))
                              .build(),
                QuickReplyItem.builder()
                              .action(CameraRollAction.withLabel("CemeraRollAction"))
                              .build(),
                QuickReplyItem.builder()
                              .action(LocationAction.withLabel("Location"))
                              .build(),
                QuickReplyItem.builder()
                              .action(PostbackAction.builder()
                                                    .label("PostbackAction")
                                                    .text("PostbackAction clicked")
                                                    .data("{PostbackAction: true}")
                                                    .build())
                              .build()
        );

        final QuickReply quickReply = QuickReply.items(items);

        return TextMessage
                .builder()
                .text("Message with QuickReply")
                .quickReply(quickReply)
                .build();
    }
}
