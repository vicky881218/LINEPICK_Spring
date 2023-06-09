package com.example.demo.flex;
import static java.util.Arrays.asList;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.List;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;

import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;


import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.entity.Buyer;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.Product;

public class OrderInformationFlexMessage implements Supplier<FlexMessage>{
    private BuyerDAO buyerDAO;
    private OrderListDAO orderListDAO;
    private OrderItemDAO orderItemDAO;
    private ProductDAO productDAO;
    private String buyer_id;
    private List<String> order;
    private OrderList orderlist;
    private OrderItem orderItem;
    private String product_name;
    private String product_style;
    private Product product;
    
    public OrderInformationFlexMessage(BuyerDAO buyerDAO, String buyer_id, List<String> order,OrderListDAO orderListDAO,OrderItemDAO orderItemDAO, ProductDAO productDAO) {
        this.buyerDAO = buyerDAO;
        this.buyer_id = buyer_id;
        this.order = order;
        this.orderListDAO=orderListDAO;
        this.orderItemDAO=orderItemDAO;
        this.productDAO = productDAO;
    }

    public List<Buyer> retrieveBuyers() throws SQLException{
        return buyerDAO.findAll();
     }

     public Buyer retrieveOneBuyer() throws SQLException{
        return buyerDAO.findOne(buyer_id);  
     }

     public List<OrderList> retrievefindTheLastOrderlistId(String buyer_id) throws SQLException{
        return orderListDAO.findTheLastOrderlistId(buyer_id);  
     }

     public OrderList retrievefindTheLastRecordOfOrderlist(String buyer_id,int totalLength) throws SQLException{
        return orderListDAO.findTheLastRecordOfOrderlist(buyer_id,totalLength);  
     }
     public Product retrieveOrderInformationProductId(String product_name, String product_style) throws SQLException{
         return productDAO.findOrderInformationProductId(product_name, product_style);
     }
     public Product retrieveThisProductPrice(String product_name,String product_style) throws SQLException{
        return productDAO.findThisProductPrice(product_name,product_style);  
     }
     public Product retrieveThidProductStock(int product_id) throws SQLException{
         return productDAO.findOne(product_id);
     }

    @Override
    public FlexMessage get(){
        orderlist = new OrderList();
        // System.out.println("here is information");
        // System.out.println(order);
        // System.out.println(buyer_id);
        int singleProductPrice=0;

        
        final Box footerBlock = createFooterBlock();
        final Box bodyBlock = createBodyBlock(singleProductPrice);
        final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();      
        List<OrderList> orderlistId = new ArrayList<>();
        product_name = order.get(0);
        product_style = order.get(2);
        try {
            System.out.println("here in orderlist id");
            System.out.println(buyer_id);
            orderlistId = retrievefindTheLastOrderlistId(buyer_id);
            product = retrieveOrderInformationProductId(product_name, product_style);
        
        }
        catch (SQLException e){
            System.out.println(e);
        } 
        System.out.println("here find last orderlistId");
        int totalLength=orderlistId.size();
        System.out.println(totalLength);

        OrderList orderlistLastRecord = new OrderList();
        
        try {
            orderlistLastRecord = retrievefindTheLastRecordOfOrderlist(buyer_id,totalLength);
            System.out.println("find orderlistLastRecord");
            System.out.println(orderlistLastRecord.getOrderListId());
    
        }
        catch (SQLException e){
            System.out.println(e);
        }   
        
        product.setProductStock(product.getProductStock()-Integer.valueOf(order.get(1)));
        productDAO.update(product);

        int product_id = product.getProductId();
        orderItem = new OrderItem();
        System.out.println("orderItem");
        System.out.println(orderItem);
        orderItem.setOrderItemQuantity(Integer.valueOf(order.get(1)));
        orderItem.setProductId(product_id);
        orderItem.setOrderListId(orderlistLastRecord.getOrderListId());
        orderItemDAO.insert(orderItem);
        
        return new FlexMessage("訂單資訊", bubble);
    }

    private Box createFooterBlock(){
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer();
        }
        catch (SQLException e){
            System.out.println(e);
        }
       

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer))
                  .build();
    }

    private Box createBodyBlock(int singleProductPrice) {
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer();
            
        }
        catch (SQLException e){
            System.out.println(e);
        }
        final Text title =
                Text.builder()
                    .text(buyer.getBuyerName() + "的訂單資訊如下")
                    .weight(TextWeight.BOLD)
                    .size(FlexFontSize.LG)
                    .build();

        final Box info = createInfoBox(singleProductPrice);


        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title,info))
                  .build();
    }

    private Box createInfoBox(int singleProductPrice) {

        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer();
            product_name = order.get(0);
            product_style = order.get(2);
            Product thisProduct=retrieveThisProductPrice(product_name,product_style);
            singleProductPrice=thisProduct.getProductPrice();
            System.out.println("singleProductPrice????");
            System.out.println(singleProductPrice);
        }
        catch (SQLException e){
            System.out.println(e);
        }
        String orderlist_status = "未出貨";
        String pay_type = order.get(4);
        String pay_status = "N";
        int orderlist_payment = singleProductPrice*Integer.valueOf(order.get(1));
        Date date = new Date();
       SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");       
       
       String order_date = bartDateFormat.format(date).toString();       
       System.out.println("date????????");
       System.out.println(order_date);
        
        orderlist.setOrderListPayment(orderlist_payment);
        orderlist.setOrderListStatus(orderlist_status);
        orderlist.setPayType(pay_type);
        orderlist.setPayStatus(pay_status);
        orderlist.setOrderDate(order_date);
        orderlist.setBuyerId(buyer_id);

        

        final Box name = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder()
                            .text("購買人姓名:")
                            .color("#aaaaaa")
                            .size(FlexFontSize.SM)
                            .flex(4)
                            .build(),
                        Text.builder()
                            .text(buyer.getBuyerName())
                            .wrap(true)
                            .color("#666666")
                            .size(FlexFontSize.SM)
                            .flex(5)
                            .build()
                ))
                .build();
        final Box phone =
                Box.builder()
                   .layout(FlexLayout.BASELINE)
                   .spacing(FlexMarginSize.SM)
                   .contents(asList(
                           Text.builder()
                               .text("購買人聯絡電話:")
                               .color("#aaaaaa")
                               .size(FlexFontSize.SM)
                               .flex(4)
                               .build(),
                           Text.builder()
                               .text(buyer.getBuyerPhone())
                               .wrap(true)
                               .color("#666666")
                               .size(FlexFontSize.SM)
                               .flex(5)
                               .build()
                   ))
                   .build();
        final Box mail =
                Box.builder()
                    .layout(FlexLayout.BASELINE)
                    .spacing(FlexMarginSize.SM)
                    .contents(asList(
                            Text.builder()
                                .text("購買人電子郵件:")
                                .color("#aaaaaa")
                                .size(FlexFontSize.SM)
                                .flex(4)
                                .build(),
                            Text.builder()
                                .text(buyer.getBuyerMail())
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                    ))
                    .build();
        final Box address =
                Box.builder()
                    .layout(FlexLayout.BASELINE)
                    .spacing(FlexMarginSize.SM)
                    .contents(asList(
                            Text.builder()
                                .text("送達地址:")
                                .color("#aaaaaa")
                                .size(FlexFontSize.SM)
                                .flex(4)
                                .build(),
                            Text.builder()
                                .text(buyer.getBuyerAddress())
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                    ))
                    .build();

        final Box productDtail =
                Box.builder()
                    .layout(FlexLayout.BASELINE)
                    .spacing(FlexMarginSize.SM)
                    .contents(asList(
                            Text.builder()
                                .text("購買商品:")
                                .color("#aaaaaa")
                                .size(FlexFontSize.SM)
                                .flex(4)
                                .build(),
                            Text.builder()
                                .text(order.get(0)+" "+order.get(2)+" "+order.get(1)+"件商品")
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                    ))
                    .build();
            final Box Payment =
                    Box.builder()
                        .layout(FlexLayout.BASELINE)
                        .spacing(FlexMarginSize.SM)
                        .contents(asList(
                                Text.builder()
                                    .text("單價:")
                                    .color("#aaaaaa")
                                    .size(FlexFontSize.SM)
                                    .flex(4)
                                    .build(),
                                Text.builder()
                                    .text(""+singleProductPrice+"元")
                                    .wrap(true)
                                    .color("#666666")
                                    .size(FlexFontSize.SM)
                                    .flex(5)
                                    .build()
                        ))
                        .build();        
            final Box totalPayment =
                    Box.builder()
                        .layout(FlexLayout.BASELINE)
                        .spacing(FlexMarginSize.SM)
                        .contents(asList(
                                Text.builder()
                                    .text("訂單總額:")
                                    .color("#aaaaaa")
                                    .size(FlexFontSize.SM)
                                    .flex(4)
                                    .build(),
                                Text.builder()
                                    .text(""+Integer.valueOf(order.get(1))*singleProductPrice+"元")
                                    .wrap(true)
                                    .color("#666666")
                                    .size(FlexFontSize.SM)
                                    .flex(5)
                                    .build()
                        ))
                        .build();
            final Box orderDate =
                        Box.builder()
                            .layout(FlexLayout.BASELINE)
                            .spacing(FlexMarginSize.SM)
                            .contents(asList(
                                    Text.builder()
                                        .text("下單日期:")
                                        .color("#aaaaaa")
                                        .size(FlexFontSize.SM)
                                        .flex(4)
                                        .build(),
                                    Text.builder()
                                        .text(order_date)
                                        .wrap(true)
                                        .color("#666666")
                                        .size(FlexFontSize.SM)
                                        .flex(5)
                                        .build()
                            ))
                            .build();
            final int pickmoney;
            if(order.get(3).equals("Y")){
                pickmoney=buyer.getPickmoney();
            }else{
                pickmoney=0;
            }
            final int usePickmoneyPrice;
            int Allprice = Integer.valueOf(order.get(1))*singleProductPrice;
            if(Allprice>=100 && pickmoney <= Allprice/100*10){
                usePickmoneyPrice = pickmoney;
            }else if(Allprice>=100 && pickmoney > Allprice/100*10){
                usePickmoneyPrice = Allprice/100*10;
            }
            else{
                usePickmoneyPrice = 0;
            }
            orderlist.setPickmoneyUse(usePickmoneyPrice);
            orderListDAO.insert(orderlist);
            
            System.out.println("usePickmoney?????");
            System.out.println(usePickmoneyPrice);
            
            int AllPayment = Integer.valueOf(order.get(1))*singleProductPrice-usePickmoneyPrice;
            int point = 0;
            if(AllPayment>=100){
                point += (AllPayment/100)*10;
            }
            buyer.setPickmoney(pickmoney-usePickmoneyPrice);
            buyer.setPickpoint(point+buyer.getPickpoint());
            buyerDAO.update(buyer);
            final Box usePickmoney =
                    Box.builder()
                        .layout(FlexLayout.BASELINE)
                        .spacing(FlexMarginSize.SM)
                        .contents(asList(
                                Text.builder()
                                    .text("使用購物金:")
                                    .color("#aaaaaa")
                                    .size(FlexFontSize.SM)
                                    .flex(4)
                                    .build(),
                                Text.builder()
                                    .text(""+usePickmoneyPrice)
                                    .wrap(true)
                                    .color("#666666")
                                    .size(FlexFontSize.SM)
                                    .flex(5)
                                    .build()
                        ))
                        .build();
    
            final Box realPayment =
                    Box.builder()
                        .layout(FlexLayout.BASELINE)
                        .spacing(FlexMarginSize.SM)
                        .contents(asList(
                                Text.builder()
                                    .text("實付金額:")
                                    .color("#aaaaaa")
                                    .size(FlexFontSize.SM)
                                    .flex(4)
                                    .build(),
                                Text.builder()
                                    .text(""+(Integer.valueOf(order.get(1))*singleProductPrice-usePickmoneyPrice))
                                    .wrap(true)
                                    .color("#666666")
                                    .size(FlexFontSize.SM)
                                    .flex(5)
                                    .build()
                        ))
                        .build();
             final Box paymentSelection =
                        Box.builder()
                            .layout(FlexLayout.BASELINE)
                            .spacing(FlexMarginSize.SM)
                            .contents(asList(
                                    Text.builder()
                                        .text("付款方式:")
                                        .color("#aaaaaa")
                                        .size(FlexFontSize.SM)
                                        .flex(4)
                                        .build(),
                                    Text.builder()
                                        .text(order.get(4))
                                        .wrap(true)
                                        .color("#666666")
                                        .size(FlexFontSize.SM)
                                        .flex(5)
                                        .build()
                            ))
                            .build();
            final Box gainpoint =
                            Box.builder()
                                .layout(FlexLayout.BASELINE)
                                .spacing(FlexMarginSize.SM)
                                .contents(asList(
                                        Text.builder()
                                            .text("可獲得賴皮指數:")
                                            .color("#aaaaaa")
                                            .size(FlexFontSize.SM)
                                            .flex(4)
                                            .build(),
                                        Text.builder()
                                            .text(point+"點")
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
                  .contents(asList(name, phone, mail, address,productDtail, Payment, totalPayment,orderDate, usePickmoney,realPayment,paymentSelection, gainpoint))
                  .build();
    }
    
}