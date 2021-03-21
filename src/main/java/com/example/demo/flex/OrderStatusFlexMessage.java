package com.example.demo.flex;

import static java.util.Arrays.asList;

import java.net.URI;
import java.sql.SQLException;
import java.util.function.Supplier;

import java.util.List;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Icon;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import com.example.demo.dao.OrderListDAO;
import com.example.demo.entity.OrderList;

public class OrderStatusFlexMessage implements Supplier<FlexMessage> {
 
    private OrderListDAO orderListDAO;
    private OrderList orderList;
    private String buyer_id;
    private int orderlist_id;

    public OrderStatusFlexMessage(OrderListDAO orderListDAO, String buyer_id) {
        this.orderListDAO = orderListDAO;
        this.buyer_id = buyer_id;
       

    }

    public List<OrderList> retrieveOrderLists() throws SQLException{
        return orderListDAO.findAll();
     }

     public OrderList retrieveOneOrderList() throws SQLException{
        System.out.println("!!!!!!!!!!!!!!!!!");
        System.out.println(orderlist_id);
        //buyer_id = "U03f0c8f23e837621589cd133fad12490";
        // List <Buyer> allBuyers = buyerDAO.findAll();
        // System.out.println(allBuyers.size());
        // System.out.println(buyerDAO.findOne(buyer_id));
        return orderListDAO.findOne(buyer_id);  
        
     }


    @Override
    public FlexMessage get(){
        
        // System.out.println("here");
        // System.out.println(buyerDAO.findOne(buyer_id));

        final Box bodyBlock = createBodyBlock();
        final Box footerBlock = createFooterBlock();
        final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();
        

        return new FlexMessage("ALT", bubble);
    }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        OrderList orderList = new OrderList();
        
        try {
            orderList = retrieveOneOrderList();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new MessageAction("已完成", orderList.getOrderListStatus()))
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(new URIAction("未完成", URI.create("https://example.com"), null))
                      .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction, separator, websiteAction))
                  .build();
    }

    private Box createBodyBlock() {
        final Text title =
                Text.builder()
                    .text("賴皮紀錄")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();

        final Box review = createReviewBox();

        final Box info = createInfoBox();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, review, info))
                  .build();
    }

    private Box createInfoBox() {
        final Box place = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("若是要查詢已完成的訂單，請按已完成")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
        final Box time =
                Box.builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("若是要查詢未完成的訂單，請按未完成")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build()
                   ))
                   .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .margin(FlexMarginSize.LG)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(place, time))
                  .build();
    }

    private Box createReviewBox() {
        final Icon goldStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gold_star.png")).build();
        final Icon grayStar =
                Icon.builder().size(FlexFontSize.SM).url(URI.create("https://example.com/gray_star.png")).build();
       

        return Box.builder()
                  .layout(FlexLayout.BASELINE)
                  .margin(FlexMarginSize.MD)
                  .contents(asList(goldStar, goldStar, goldStar, goldStar, grayStar))
                  .build();
    }

}