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

public class PickPointFlexMessage implements Supplier<FlexMessage> {
    private BuyerDAO buyerDAO;
    private Buyer buyer;
    private String buyer_id;
   

    public PickPointFlexMessage(BuyerDAO buyerDAO, String buyer_id) {
        this.buyerDAO = buyerDAO;
        this.buyer_id = buyer_id;
       
    }

    public List<Buyer> retrieveBuyers() throws SQLException{
        return buyerDAO.findAll();
     }

     public Buyer retrieveOneBuyerPickPoint() throws SQLException{
        System.out.println("!!!!!!!!!!!!!!!!!");
        System.out.println(buyer_id);
        //buyer_id = "U03f0c8f23e837621589cd133fad12490";
        // List <Buyer> allBuyers = buyerDAO.findAll();
        // System.out.println(allBuyers.size());
        // System.out.println(buyerDAO.findOne(buyer_id));
        return buyerDAO.findOne(buyer_id);  
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
        

        return new FlexMessage("兌換購物金", bubble);
    }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyerPickPoint();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        
        final Separator separator = Separator.builder().build();
        final Button pointAction = Button
            .builder()
            .style(ButtonStyle.LINK)
            .height(ButtonHeight.SMALL)
            .action(new MessageAction("兌換購物金","兌換"))
            .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, separator,pointAction))
                  .build();
    }

    private Box createBodyBlock() {
       
        final Text title =
                Text.builder()
                    .text("賴皮指數")
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
        // Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyerPickPoint();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        final Box place = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("目前您的賴皮指數有"+buyer.getPickpoint()+"點")
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
                               .text("若要將指數兌換成購物金請按")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build()
                   ))
                   .build();
                final Box exchange =
                   Box.builder()
                      .layout(FlexLayout.BASELINE)
                      .spacing(FlexMarginSize.SM)
                      .contents(asList(
                              Text.builder()
                                  .text("'兌換購物金'")
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
                  .contents(asList(place, time, exchange))
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
