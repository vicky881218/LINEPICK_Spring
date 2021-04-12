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

import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Product;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.OrderItem;

public class OrderListNotYetFlexMessage implements Supplier<FlexMessage>{
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

    public OrderListNotYetFlexMessage(String buyer_id, int product_id, String orderlist_status, OrderListDAO orderListDAO, ProductDAO productDAO, OrderItemDAO orderItemDAO) {
        this.buyer_id = buyer_id;
        this.product_id = product_id;
        this.orderlist_status = orderlist_status;
        this.orderListDAO = orderListDAO;
        this.productDAO = productDAO;
        this.orderItemDAO = orderItemDAO;
    }
    public List<OrderList> retrieveOrderListId(String orderlist_status, String buyer_id) throws SQLException{
       
        return orderListDAO.findAllMyOrderList1(orderlist_status, buyer_id);  
     }

    public OrderList retrieveOrderListStatus(String orderlist_status) throws SQLException{
       
        return orderListDAO.findByOrderStatus1(orderlist_status);  
     }
     
     public List<OrderItem> retrieveProductId(int orderlist_id) throws SQLException{
       
        return orderItemDAO.findProductId(orderlist_id);  
     }

     public Product retrieveOneProduct(int product_id) throws SQLException{
      // product_id = 1;
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

        final Box footerBlock = createFooterBlock();
        final Box bodyBlock = createBodyBlock(z);
        bubble.add(
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build());
        }}}}catch (SQLException e){
            System.out.println("Error: "+e);
            }
    
        final Carousel carousel = Carousel.builder().contents(bubble).build();

        return new FlexMessage("同分類商品", carousel);
     }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();

        try {
            product = retrieveOneProduct(product_id);
        }
        catch (SQLException e){
            System.out.println("Error: "+e);
        }
        
       // final Separator separator = Separator.builder().build();
        

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer))
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
                            .text("商品規格:")
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
                            .text(z.getProductStyle())
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
                        .text(""+z.getProductPrice()+"元")
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
                  .contents(asList(desc,descDetail,place))
                  .build();
    }

}