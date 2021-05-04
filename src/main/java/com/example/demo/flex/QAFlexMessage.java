package com.example.demo.flex;

import static java.util.Arrays.asList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.example.demo.dao.ReplyDAO;
import com.example.demo.entity.Reply;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

public class QAFlexMessage implements Supplier<FlexMessage> {
    
     
    @Override
    public FlexMessage get(){
        
        
        
        final Box bodyBlock = createBodyBlock();
        final Box footerBlock = createFooterBlock();
        
        final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();
        

        return new FlexMessage("賴皮客服", bubble);
    }

    private Box createFooterBlock(){
        
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(PostbackAction.builder()
                                      .label("如何退貨")
                                      .text("如何退貨")
                                      .data("如何退貨")
                                      .build())
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(PostbackAction.builder()
                                            .label("下單後過多久出貨")
                                            .text("下單後過多久出貨")
                                            .data("下單後過多久出貨")
                                            .build())
                      .build();
 
        final Button messageAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(PostbackAction.builder()
                                            .label("以上問題沒有您所想問的嗎")
                                            .text("以上問題沒有您所想問的嗎")
                                            .data("以上問題沒有您所想問的嗎")
                                            .build())
                      .build();
           // final Separator separators = Separator.builder().build();
            final Button map =
                        Button.builder()
                                    .style(ButtonStyle.LINK)
                                    .height(ButtonHeight.SMALL)
                                    .action(PostbackAction.builder()
                                                          .label("圖文選單導覽")
                                                          .text("圖文選單導覽")
                                                          .data("圖文選單導覽")
                                                          .build())
                                    .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction, separator, websiteAction, separator,messageAction, separator, map))
                  .build();
    }

    private Box createBodyBlock() {
        final Text title =
                Text.builder()
                    .text("賴皮客服")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();


        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(title)
                  .build();
    }

}