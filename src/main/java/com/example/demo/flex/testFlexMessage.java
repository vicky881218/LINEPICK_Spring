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

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;

public class testFlexMessage implements Supplier<FlexMessage> {
 
    private BuyerDAO buyerDAO;


    public testFlexMessage(BuyerDAO buyerDAO) {
        this.buyerDAO = buyerDAO;

    }

    public List<Buyer> retrieveBuyers() throws SQLException{
        return buyerDAO.findAll();
     }

     public Buyer retrieveOneBuyer(String buyer_id) throws SQLException{
        buyer_id = "U03f0c8f23e837621589cd133fad12490";
        // List <Buyer> allBuyers = buyerDAO.findAll();
        // System.out.println(allBuyers.size());
        // System.out.println(buyerDAO.findOne(buyer_id));
        return buyerDAO.findOne(buyer_id);  
     }


    @Override
    public FlexMessage get() {
        
        // System.out.println("here");
        // System.out.println(buyerDAO.findOne(buyer_id));

        final Image heroBlock =
                Image.builder()
                     .url(URI.create("https://example.com/cafe.jpg"))
                     .size(ImageSize.FULL_WIDTH)
                     .aspectRatio(ImageAspectRatio.R20TO13)
                     .aspectMode(ImageAspectMode.Cover)
                     .action(new URIAction("label", URI.create("http://example.com"), null))
                     .build();

        final Box bodyBlock = createBodyBlock();
        final Box footerBlock = createFooterBlock("U03f0c8f23e837621589cd133fad12490");
        final Bubble bubble =
                Bubble.builder()
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();

        return new FlexMessage("ALT", bubble);
    }

    private Box createFooterBlock(String buyer_id) {
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer(buyer_id);
        }
        catch (Exception e){
            System.out.println("Error:"+e);
        }
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new MessageAction("CALL", buyer.getBuyerName() ))
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(new URIAction("WEBSITE", URI.create("https://example.com"), null))
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
                    .text("Brown Cafe")
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
                            .text("Place")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text("Shinjuku, Tokyo")
                            .wrap(true)
                            .color("#666666")
                            .size(FlexFontSize.SM)
                            .flex(5)
                            .build()
                ))
                .build();
        final Box time =
                Box.builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("Time")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build(),
                           Text.builder()
                               .text("10:00 - 23:00")
                               .wrap(true)
                               .color("#666666")
                               .size(FlexFontSize.SM)
                               .flex(5)
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
        final Text point =
                Text.builder()
                    .text("4.0")
                    .size(FlexFontSize.SM)
                    .color("#999999")
                    .margin(FlexMarginSize.MD)
                    .flex(0)
                    .build();

        return Box.builder()
                  .layout(FlexLayout.BASELINE)
                  .margin(FlexMarginSize.MD)
                  .contents(asList(goldStar, goldStar, goldStar, goldStar, grayStar, point))
                  .build();
    }

}