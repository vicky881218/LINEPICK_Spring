package com.example.demo.flex;
import static java.util.Arrays.asList;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;


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
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.entity.Buyer;

public class UsePickmoneyFlexMessage implements Supplier<FlexMessage>{

    private BuyerDAO buyerDAO;
    private Buyer buyer;
    private String buyer_id;

    public UsePickmoneyFlexMessage(BuyerDAO buyerDAO, String buyer_id) {
        this.buyerDAO = buyerDAO;
        this.buyer_id = buyer_id;
    }

    public List<Buyer> retrieveBuyers() throws SQLException{
        return buyerDAO.findAll();
     }

     public Buyer retrieveOneBuyer(String buyer_id) throws SQLException{
        return buyerDAO.findOne(buyer_id);  
     }

     
    @Override
    public FlexMessage get(){

        System.out.println("here is buyer_id");
        System.out.println(buyer_id);
        Buyer buyer = new Buyer();

        try {
            buyer = retrieveOneBuyer(buyer_id);
            System.out.println("here is buyer_id");
            System.out.println(buyer.getPickmoney());
        }
        catch (SQLException e){
            System.out.println(e);
        }

        final Box bodyBlock = createBodyBlock(buyer);
        final Box footerBlock = createFooterBlock();
        final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();
        

        return new FlexMessage("是否使用購物金", bubble);
    }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(PostbackAction.builder()
                        .label("是")
                        .text("使用購物金")
                        .data("Y")
                        .build())
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(PostbackAction.builder()
                      .label("否")
                      .text("N")
                      .data("N")
                      .build())
                      .build();

        return Box.builder()
                  .layout(FlexLayout.HORIZONTAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction, separator, websiteAction))
                  .build();
    }

    private Box createBodyBlock(Buyer buyer) {
        final Text title =
                Text.builder()
                    .text("目前您的購物金有"+buyer.getPickmoney()+"元")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();
        final Text title1 =
                Text.builder()
                    .text("是否使用")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(title, title1)
                  .build();
    }
}