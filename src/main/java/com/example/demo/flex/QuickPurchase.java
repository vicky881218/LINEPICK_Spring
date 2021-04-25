package com.example.demo.flex;

import static java.util.Arrays.asList;

import java.net.URI;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;
import com.linecorp.bot.model.action.PostbackAction;
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
import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.CartDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.Buyer;
import com.example.demo.entity.Cart;
import com.example.demo.entity.OrderItem;
public class QuickPurchase implements Supplier<FlexMessage> {
    
    private ProductDAO productDAO;
    private Product product;
    private int product_id;
    private String buyer_id;
    private Cart cart;
    private CartDAO cartDAO;
    private BuyerDAO buyerDAO;
    private Buyer buyer;
    private int cart_id;
    public QuickPurchase(String buyer_id, int product_id, CartDAO cartDAO, ProductDAO productDAO, BuyerDAO buyerDAO) {
        this.buyer_id = buyer_id;
        this.product_id = product_id;
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        
        this.buyerDAO = buyerDAO;
    }
    public Buyer retrieveOneBuyer(String buyer_id) throws SQLException{
        
         return buyerDAO.findOne(buyer_id);  
      }

     public Product retrieveOneProduct(int product_id) throws SQLException{
        product_id = 1;
        return productDAO.findOne(product_id); 
     }
     public List<Cart> retrieveCartProduct(String buyer_id) throws SQLException{
         return cartDAO.findCartAllProduct(buyer_id);
     }
     public List<Cart> retrieveCartProductByCartId(int cart_id) throws SQLException{
        return cartDAO.findCartAllProductByCartId(cart_id);
    }
     public List<Product> retrieveOrderProduct(int product_id) throws SQLException{
         return productDAO.findOrderProduct(product_id);
     }
    

    @Override
    public FlexMessage get(){
        List<Cart> cartId = new ArrayList<>();
        List<Product> productId = new ArrayList<>();
        List<Product> AllOrderList = new ArrayList<>();
        
        List<FlexMessage> flex = new ArrayList<>();
        List<Bubble> bubble = new ArrayList<>();
        
        try {
            cartId = retrieveCartProduct(buyer_id);
            product = retrieveOneProduct(product_id);
            
        for(Cart x : cartId){  
             product_id = x.getProductId();
             productId = retrieveOrderProduct(product_id);
             for(Product y: productId){
                      
        final Box footerBlock = createFooterBlock(x,y);
        final Box bodyBlock = createBodyBlock(x,y);
      //  final Box info = createInfoBox(z,y);
      final Image heroBlock =
                Image.builder()
                     .url(URI.create(y.getProductPhoto()))
                     .size(ImageSize.FULL_WIDTH)
                     .aspectRatio(ImageAspectRatio.R20TO13)
                     .aspectMode(ImageAspectMode.Cover)
                     .action(new URIAction("label", URI.create("http://example.com"), null))
                     .build();
        bubble.add(
                Bubble.builder()
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build());
          }}}catch (SQLException e){
            System.out.println("Error: "+e);
            }
    
        final Carousel carousel = Carousel.builder().contents(bubble).build();
       
        return new FlexMessage("快速購買", carousel);
     }

    private Box createFooterBlock(Cart x, Product y){
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
                .action(PostbackAction.builder()
                  .label("一鍵購買")
                  .text("Pick"+y.getProductName()+y.getProductStyle()+x.getQuantity()+"件")
                  .data(y.getProductName()+" "+x.getQuantity()+" "+y.getProductStyle())
                  .build())
                .build(); 
        final Separator separator = Separator.builder().build();
        

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer,separator, callAction))
                  .build();
    }

    private Box createBodyBlock(Cart x, Product y) {
        
        final Text title =
                Text.builder()
                    .text(y.getProductName())
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();


        final Box info = createInfoBox(x, y);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title,info))
                  .build();
    }

    private Box createInfoBox(Cart x, Product y) {
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer(buyer_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        final int totalPrice = x.getQuantity()*y.getProductPrice();
        
        
        final Box aprice = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("單價:"+y.getProductPrice()+"元")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
        final Box price = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("總金額:"+totalPrice+"元")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
        
        final Box quantity = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("購買數量:"+x.getQuantity()+"個")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build(); 
       final Box style =
                Box.builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("款式:"+y.getProductStyle())
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
                  .contents(asList(aprice, price, quantity, style))
                  .build();
    }

}