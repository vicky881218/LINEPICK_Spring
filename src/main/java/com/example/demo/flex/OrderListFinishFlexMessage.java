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
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.Buyer;
import com.example.demo.entity.OrderItem;
public class OrderListFinishFlexMessage implements Supplier<FlexMessage> {
    private OrderListDAO orderListDAO;
    private OrderList orderList;
    private ProductDAO productDAO;
    private Product product;
    private int product_id;
    private OrderItemDAO orderItemDAO;
    private OrderItem orderitem;
    private String buyer_id;
    private String orderlist_status;
    private int orderlist_id;
    private BuyerDAO buyerDAO;
    private Buyer buyer;

    public OrderListFinishFlexMessage(String buyer_id, int product_id, String orderlist_status, OrderListDAO orderListDAO, ProductDAO productDAO, OrderItemDAO orderItemDAO, BuyerDAO buyerDAO) {
        this.buyer_id = buyer_id;
        this.product_id = product_id;
        this.orderlist_status = orderlist_status;
        this.orderListDAO = orderListDAO;
        this.productDAO = productDAO;
        this.orderItemDAO = orderItemDAO;
        this.buyerDAO = buyerDAO;
    }
    public Buyer retrieveOneBuyer(String buyer_id) throws SQLException{
        
         return buyerDAO.findOne(buyer_id);  
      }
    public List<OrderList> retrieveOrderListId(String orderlist_status, String buyer_id) throws SQLException{
       
        return orderListDAO.findAllMyOrderList(orderlist_status, buyer_id);  
     }

    public OrderList retrieveOrderListStatus(String orderlist_status) throws SQLException{
       orderlist_status = "已完成";
        return orderListDAO.findByOrderStatus(orderlist_status);  
     }
     
     public List<OrderItem> retrieveProductId(int orderlist_id) throws SQLException{
       
        return orderItemDAO.findProductId(orderlist_id);  
     }
     public OrderItem retrieveOneOrderItemQuantity(int orderlist_id) throws SQLException{
        
        return orderItemDAO.findOneOrderItemQuantity(orderlist_id); 
     }

     public Product retrieveOneProduct(int product_id) throws SQLException{
        product_id = 1;
        return productDAO.findOne(product_id); 
     }
    
     public List<Product> retrieveOrderProduct(int product_id) throws SQLException{
         return productDAO.findOrderProduct(product_id);
     }
    

    @Override
    public FlexMessage get(){
        
        List<OrderList> orderListId = new ArrayList<>();
        List<OrderItem> productId = new ArrayList<>();
        List<Product> AllOrderList = new ArrayList<>();

        List<FlexMessage> flex = new ArrayList<>();
        List<Bubble> bubble = new ArrayList<>();
        
        try {
            OrderList orderListStatus = retrieveOrderListStatus(orderlist_status);
            orderlist_status = orderListStatus.getOrderListStatus();
            orderListId = retrieveOrderListId(orderlist_status, buyer_id);
            
            product = retrieveOneProduct(product_id);
    
        for(OrderList x : orderListId){  
             orderlist_id = x.getOrderListId();
             productId = retrieveProductId(orderlist_id);
             for(OrderItem y: productId){
                 product_id = y.getProductId();
                 AllOrderList = retrieveOrderProduct(product_id);
                 for(Product z : AllOrderList){       
        final Box footerBlock = createFooterBlock(z);
        final Box bodyBlock = createBodyBlock(z,y,x);
      //  final Box info = createInfoBox(z,y);
      final Image heroBlock =
                Image.builder()
                     .url(URI.create(z.getProductPhoto()))
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
        }}}}catch (SQLException e){
            System.out.println("Error: "+e);
            }
    
        final Carousel carousel = Carousel.builder().contents(bubble).build();

        return new FlexMessage("已完成訂單", carousel);
     }

    private Box createFooterBlock(Product z){
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
                  .label("回購Pick")
                  .text("Pick"+z.getProductName()+z.getProductStyle())
                  .data(z.getProductName()+" "+z.getProductStyle())
                  .build())
                .build();
        final Separator separator = Separator.builder().build();
        

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer,separator, callAction))
                  .build();
    }

    private Box createBodyBlock(Product z, OrderItem y, OrderList x) {
        
        final Text title =
                Text.builder()
                    .text(z.getProductName())
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.XL)
                    .build();


        final Box info = createInfoBox(z, y, x);

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title,info))
                  .build();
    }

    private Box createInfoBox(Product z, OrderItem y, OrderList x) {
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer(buyer_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Product??????????????");
        System.out.println(z.getProductId());
        final int totalPrice = z.getProductPrice()*y.getOrderItemQuantity();
        
        final Box aprice = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("單價:"+z.getProductPrice()+"元")
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
                            .text("購買數量:"+y.getOrderItemQuantity()+"個")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build(); 
        int realpayment = x.getOrderListPayment()-x.getPickmoneyUse();
        final Box realPayment = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("實付金額:"+realpayment+"元")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
        final Box pickmoneyPayment = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("購物金折抵金額:"+x.getPickmoneyUse()+"元")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(1)
                            .build()
                ))
                .build();
        final Box payselection =
                Box.builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("付款方式:"+x.getPayType())
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(1)
                               .build()
                         
                   ))
                   .build();
            final Box orderdate =
                   Box.builder()
                      .layout(FlexLayout.BASELINE)
                      .spacing(FlexMarginSize.SM)
                      .contents(asList(
                              Text.builder()
                                  .text("下單日期:"+x.getOrderDate())
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
                               .text("規格:"+z.getProductStyle())
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
                  .contents(asList(aprice, price, realPayment, pickmoneyPayment, quantity, style, payselection, orderdate))
                  .build();
    }

}