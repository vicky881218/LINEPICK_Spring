package com.example.demo.flex;

import static java.util.Arrays.asList;

import java.net.URI;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
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

import com.example.demo.dao.ProductTypeDAO;
import com.example.demo.dao.TypeDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductType;
import com.example.demo.entity.Type;

public class ProductFlexMessage implements Supplier<FlexMessage> {
 
    private ProductDAO productDAO;
    private Product product;
    private int product_id;
    private ProductTypeDAO productTypeDAO;
    private ProductType productType;
    private int type_id;
    private String type_name;
    private Type Type;
    private TypeDAO typeDAO;

    public ProductFlexMessage(TypeDAO typeDAO,ProductDAO productDAO, ProductTypeDAO productTypeDAO,String type_name) {
        this.productDAO = productDAO;
        this.typeDAO = typeDAO;
        this.productTypeDAO = productTypeDAO;
        this.type_name = type_name;
    }

    public Type retrieveOneTypeId(String type_name) throws SQLException{
        return typeDAO.findTypeId(type_name);
        
     }

    public List<Product> retrieveProducts() throws SQLException{
        return productDAO.findAll();
     }

     public List<ProductType> retrievefindOneProuductType(int type_id) throws SQLException{
        return productTypeDAO.findProuductType(type_id); 
        //找同一個type_id內的所有produt_id 
     }

     public List<Product> retrievefindOneTypeAllProduct(int product_id) throws SQLException{
        return productDAO.findOneTypeAllProduct(product_id);  
     }

     public List<Product> retrievefindOneByName(String product_name) throws SQLException{
        return productDAO.findOneByName(product_name);  
     }

    @Override
    public FlexMessage get(){

        List<ProductType> oneTypeProductId = new ArrayList<>();
        List<Product> oneTypeProductName = new ArrayList<>();
        List<String> oneTypeProductDistinctName = new ArrayList<String>();
        List<Product> fineOneProductByName = new ArrayList<>();
        List<FlexMessage> flex = new ArrayList<>();
        List<Bubble> bubble = new ArrayList<>();

        System.out.println("here is type_name");
        System.out.println(type_name);
        
        try {
            System.out.println("here is type_id in flex");
            System.out.println(retrieveOneTypeId(type_name).getTypeId());
            System.out.println("upper");
            type_id=retrieveOneTypeId(type_name).getTypeId();
            //product = retrieveOneProduct(product_id);
            oneTypeProductId=retrievefindOneProuductType(type_id);

            for(ProductType x : oneTypeProductId){
                product_id=x.getProductId();
                // System.out.println("in for");
                // System.out.println(product_id);
                oneTypeProductName=retrievefindOneTypeAllProduct(product_id);
                // System.out.println("after");
                // System.out.println(oneTypeProductName);
                for(Product y : oneTypeProductName){
                    String product_name=y.getProductName();
                    if(oneTypeProductDistinctName.contains(product_name)){
                        //System.out.println("exist");
                    }else{
                        // System.out.println("!!!try!!!");
                        // System.out.println(product_name);
                        oneTypeProductDistinctName.add(product_name);
                        //System.out.println(oneTypeProductDistinctName);
                        fineOneProductByName = retrievefindOneByName(product_name);

                        // System.out.println("fineOneProductByName");
                        // System.out.println(fineOneProductByName);
                    

        for(Product z : fineOneProductByName){
        final Image heroBlock =
                Image.builder()
                     .url(URI.create(z.getProductPhoto()))
                     .size(ImageSize.FULL_WIDTH)
                     .aspectRatio(ImageAspectRatio.R20TO13)
                     .aspectMode(ImageAspectMode.Cover)
                     .action(new URIAction("label", URI.create("http://example.com"), null))
                     .build();

        
        final Box footerBlock = createFooterBlock(z);
        final Box bodyBlock = createBodyBlock(z);
        bubble.add(
                Bubble.builder()
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build());
        }
    }
}
}

}
catch (SQLException e){
System.out.println("Error: "+e);
}
        final Carousel carousel = Carousel.builder().contents(bubble).build();

        return new FlexMessage("同分類商品", carousel);
    }

    private Box createFooterBlock(Product z){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(PostbackAction.builder()
                                      .label("Pick")
                                      .text("Pick"+z.getProductName())
                                      .data(z.getProductName())
                                      .build())
                .build();
        final Separator separator = Separator.builder().build();
        final Button websiteAction =
                Button.builder()
                      .style(ButtonStyle.LINK)
                      .height(ButtonHeight.SMALL)
                      .action(PostbackAction.builder()
                                            .label("加入賴皮願望")
                                            .text("將"+z.getProductName()+"加入賴皮願望")
                                            .data(z.getProductName())
                                            .build())
                      .build();

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction, separator, websiteAction))
                  .build();
    }

    private Box createBodyBlock(Product z) {
        final Text title =
                Text.builder()
                    .text(z.getProductName())
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();


        final Box info = createInfoBox(z);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title,info))
                  .build();
    }

    private Box createInfoBox(Product z) {
        final Box desc = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("商品介紹:")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
                final Box descDetail = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text(z.getProductDesc())
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();

        final Box place =
                Box.builder()
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
                        .text(""+z.getProductPrice())
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

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .margin(FlexMarginSize.LG)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(desc,descDetail,place,time))
                  .build();
    }

}