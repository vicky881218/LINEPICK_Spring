package com.example.demo.flex;

import static java.util.Arrays.asList;

import java.net.URI;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectMode;
import com.linecorp.bot.model.message.flex.component.Image.ImageAspectRatio;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;

public class SizeFlexMessage implements Supplier<FlexMessage> {
 
    private ProductDAO productDAO;
    private Product product;
    private int product_id;
    private Product x;
    private Product productAllSize;
    private String product_style;



    public SizeFlexMessage(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> retrieveProductsAllSize() throws SQLException{
        return productDAO.findAll();
     }

     public Product retrieveOneProduct(int product_id) throws SQLException{
        product_id = 1;
        Product p = productDAO.findOne(product_id);
        return p;  
     }

     public List<Product> retrieveOneProductsAllSize() throws SQLException{
        product_style = "黑";
        return productDAO.findOneProductAllSize(product_style);
     }



    @Override
    public FlexMessage get(){

        List<Product> productAllSize = new ArrayList<>();
        List<FlexMessage> flex = new ArrayList<>();
        List<Bubble> bubble = new ArrayList<>();

        try {
            product = retrieveOneProduct(product_id);
            productAllSize = retrieveOneProductsAllSize();
        }
        catch (SQLException e){
            System.out.println("Error: "+e);
        }

        for(Product x : productAllSize){

        final Image heroBlock =
                Image.builder()
                     .url(URI.create(x.getProductPhoto()))
                     .size(ImageSize.FULL_WIDTH)
                     .aspectRatio(ImageAspectRatio.R20TO13)
                     .aspectMode(ImageAspectMode.Cover)
                     .build();

        
        final Box footerBlock = createFooterBlock();
        final Box bodyBlock = createBodyBlock(x);

        bubble.add(
                Bubble.builder()
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build());
        }

        final Carousel carousel = Carousel.builder().contents(bubble).build();

        return new FlexMessage("SizeFlex", carousel);
    }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();

        try {
            product = retrieveOneProduct(product_id);
        }
        catch (SQLException e){
            System.out.println("Error: "+e);
        }
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new MessageAction("Pick", "SizePick"))
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(new MessageAction("加入賴皮願望", "加入賴皮願望"))
                      .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction, separator, websiteAction))
                  .build();
    }

    private Box createBodyBlock(Product x) {
        final Text title =
                Text.builder()
                    .text(x.getProductSize())
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();


        final Box info = createInfoBox(x);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title,info))
                  .build();
    }

    private Box createInfoBox(Product x) {
        final Box color = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("顏色")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(""+x.getProductStyle())
                            .wrap(true)
                            .color("#666666")
                            .size(FlexFontSize.SM)
                            .flex(5)
                            .build()
                ))
                .build();

        final Box place = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("價格")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(""+x.getProductPrice())
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
                               .text("折扣")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build(),
                           Text.builder()
                               .text("8折")
                               .wrap(true)
                               .color("#666666")
                               .size(FlexFontSize.SM)
                               .flex(5)
                               .build()
                   ))
                   .build();
        final Box stock = Box
                   .builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("庫存")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build(),
                           Text.builder()
                               .text(""+x.getProductStock())
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
                  .contents(asList(color,place, time,stock))
                  .build();
    }

}