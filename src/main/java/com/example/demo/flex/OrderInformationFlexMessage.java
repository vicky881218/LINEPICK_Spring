package com.example.demo.flex;
import static java.util.Arrays.asList;

import java.sql.SQLException;
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
    private String buyer_id;
    private String product_name;
    private String product_style;
    private String product_size;
    private List<String> order;
    private ProductDAO productDAO;
    private OrderListDAO orderListDAO;
    private OrderItemDAO orderItemDAO;
    private OrderList orderList;
    private OrderItem orderItem;

    public OrderInformationFlexMessage(OrderListDAO orderListDAO,OrderItemDAO orderItemDAO,BuyerDAO buyerDAO, String buyer_id, List<String> order,ProductDAO productDAO) {
        this.buyerDAO = buyerDAO;
        this.buyer_id = buyer_id;
        this.order = order;
        this.productDAO = productDAO;
        this.orderListDAO=orderListDAO;
        this.orderItemDAO=orderItemDAO;        

    }

    public List<Buyer> retrieveBuyers() throws SQLException{
        return buyerDAO.findAll();
     }

     public Buyer retrieveOneBuyer() throws SQLException{
         System.out.println("buyer_id"+buyer_id);
        return buyerDAO.findOne(buyer_id);  
     }

     public Product retrieveThisProductPrice(String product_name,String product_style,String product_size) throws SQLException{
        return productDAO.findThisProductPrice(product_name,product_style,product_size);  
     }

     public OrderList retrieveFindThisOrderList(String pay_type,String pay_status,String orderlist_status,int orderlist_payment,String buyer_id) throws SQLException{
        return orderListDAO.findThisOrderList(pay_type,pay_status,orderlist_status,orderlist_payment,buyer_id);  
     }
        

    @Override
    public FlexMessage get(){
        
        System.out.println("here is information");
        System.out.println(order);
        product_name=order.get(0);
        product_style=order.get(1);
        product_size=order.get(2);
        int singleProductPrice=0;
        int singleProductId=0;
        try {
            Product thisProduct=retrieveThisProductPrice(product_name,product_style,product_size);
            System.out.println("here is thisProduct");
            System.out.println(thisProduct.getProductPrice());
            singleProductPrice=thisProduct.getProductPrice();
            singleProductId=thisProduct.getProductId();
            System.out.println("here is singleProductPrice");
            System.out.println(singleProductPrice);
            orderItem.setOrderItemQuantity(order.get(3));
            orderItem.setProductId(singleProductId);
            

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("get() 1");
        final Box footerBlock = createFooterBlock();
        System.out.println("get() 2");
        final Box bodyBlock = createBodyBlock(singleProductPrice);
        System.out.println("get() 3");
        final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();
        
        System.out.println("get() done");
        return new FlexMessage("OrderInformation", bubble);
    }

    private Box createFooterBlock(){
        System.out.println("createFooter() 1");
        final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        System.out.println("createFooter() 2");
        System.out.println(buyer_id);
        Buyer buyer = new Buyer();
        try {
            buyer = retrieveOneBuyer();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        System.out.println("createFooter() 3");
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.LINK)
                .height(ButtonHeight.SMALL)
                .action(new MessageAction("取消此訂單", buyer.getBuyerName()))
                .build();
        System.out.println("createFooter() 4");

        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .spacing(FlexMarginSize.SM)
                  .contents(asList(spacer, callAction))
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
        }
        catch (SQLException e){
            System.out.println(e);
        }
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

        final Box productDetail =
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
                                .text(order.get(0)+" "+order.get(1)+" "+order.get(2)+" "+order.get(3))
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
                                .text(""+Integer.valueOf(order.get(3))*singleProductPrice)
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                    ))
                    .build();

        final int pickmoney;
        if(order.get(4).equals("Y")){
            pickmoney=buyer.getPickmoney();
        }else{
            pickmoney=0;
        }

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
                                .text(""+pickmoney)
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
                                .text(""+(Integer.valueOf(order.get(3))*singleProductPrice-Integer.valueOf(pickmoney)))
                                .wrap(true)
                                .color("#666666")
                                .size(FlexFontSize.SM)
                                .flex(5)
                                .build()
                    ))
                    .build();

        String pay_type=order.get(5);
        String pay_status="N";
        String orderlist_status="未出貨";
        int orderlist_payment=0;
        orderList.setOrderListPayment(Integer.valueOf(order.get(3))*singleProductPrice-Integer.valueOf(pickmoney));
        orderList.setBuyerId(buyer_id);
        orderList.setPayType(order.get(5));
        orderList.setOrderListStatus("未出貨");
        orderList.setPayStatus("N");
        try {
            System.out.println("insert orderlist");
            orderListDAO.insert(orderList);
            OrderList thisOrderList=retrieveFindThisOrderList(pay_type,pay_status,orderlist_status,orderlist_payment,buyer_id);
            int orderlist_id=thisOrderList.getOrderListId();
            System.out.println("insert orderItem");
            System.out.println("insert orderlist");
            orderItem.setOrderListId(orderlist_id);
            orderItemDAO.insert(orderItem);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
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
                                .text(order.get(5))
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
                  .contents(asList(name, phone, mail, address,productDetail,totalPayment,usePickmoney,realPayment,paymentSelection))
                  .build();
    }

}
