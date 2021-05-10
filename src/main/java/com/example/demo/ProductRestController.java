package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.BuyerDAO;
import com.example.demo.dao.CartDAO;
import com.example.demo.dao.CartInfoDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.dao.OrderListDAO;
import com.example.demo.dao.ProductDAO;
import com.example.demo.dao.ProductTypeDAO;
import com.example.demo.dao.TypeDAO;
import com.example.demo.entity.Buyer;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartInfo;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderList;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductType;
import com.example.demo.entity.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private BuyerDAO buyerDAO;

    @Autowired
    private CartDAO cartDAO;
    
    @Autowired
    private CartInfoDAO cartInfoDAO;
   
    @Autowired
    private TypeDAO typeDAO;

    @Autowired
    private ProductTypeDAO productTypeDAO;

    @Autowired
    private OrderItemDAO orderItemDAO;

    @Autowired
    private OrderListDAO orderListDAO;

    //Home頁
    @GetMapping(value={"/Home"})
    public List<Product> retrieveAllProduct() throws SQLException{
        System.out.println("in Home spring");
        return productDAO.findAll();  
     }
    //顯示幾種type
     @GetMapping(value={"/Type"})
    public List<Type> retrievefindAll() throws SQLException{
        System.out.println("in Type spring");
        return typeDAO.findAll();  
     }
     //分類後商品
    @GetMapping(value={"/SecondType/{typeId}"})
    public List<ProductType> retrieveTypeProduct(@PathVariable int typeId) throws SQLException{
        System.out.println("in /SecondType/{typeId} spring");
        System.out.println("typeId:"+typeId);
        List<ProductType> All = productTypeDAO.findOneTypeAllProduct(typeId);
      //   List<String> nameList = new ArrayList<String>();
      //   List<Product> thisProduct = new ArrayList<>();

      //   for(ProductType z : All){
      //      int product_id= z.getProductId();
      //      System.out.println("product_id");
      //      System.out.println(product_id);
      //      Product oneTypeAll = productDAO.findOneTypeAllProduct(product_id);
      //      String productName = oneTypeAll.getProductName();
      //      boolean bool;
      //       if( bool = nameList.contains(productName)){
      //          System.out.println("已存在");
      //         }else{
      //          nameList.add(productName);
      //         }
      //      }
      //    for(var i=0;i<nameList.size();i++){
      //       System.out.println("product_name");
      //      System.out.println(nameList.get(i));
      //       String product_name= nameList.get(i);
      //       Product findIt = findOneTypeOneProduct(product_name);
      //       thisProduct.add(findIt);
      //    }
        return All;  
     }
     //單一商品不同款式
     @GetMapping(value={"/ProductsInfo/{name}"})
    public List<Product> retrieveOneNameAllStyle(@PathVariable("name") String name) throws SQLException{
      System.out.println("in /ProductsInfo/{name} spring");
      System.out.println("{name} "+name);  
      return productDAO.findOneProductAllStyle(name);  
     }

      // 單一款式的照片、價格、庫存
   @GetMapping(value = { "/StylesInfo/{product_style}" })
   public Product retrieveOneStyleAll(@PathVariable("product_style") String product_style) throws SQLException {
      System.out.println("in /StylesInfo/{product_style} spring");
      System.out.println("{product_style} " + product_style);
      return productDAO.findOrderInformationProductId2(product_style);
   }

   // 更改商品資料庫
   @PutMapping(value = "/ProductEdit")
   public void retrieveBuyerInformationUpdate(@RequestBody Product product) throws SQLException {
      System.out.println("in ProductEdit spring");
      System.out.println("product:" + product.getProductDesc());
      productDAO.update(product);
   }
     //買家資訊
     @GetMapping(value={"/Checkout/{id}"})
    public Buyer retrieveOneBuyer(@PathVariable("id") String id) throws SQLException{
        System.out.println("in checkout spring begin id:"+id);
        Buyer result = buyerDAO.findOne(id);
        System.out.println("in checkout spring end id:"+id);
        return result;
     }

     //checkout更改買家資訊
     @PutMapping(value = "/BuyerInformation")
     public void retrieveBuyerInformationUpdate(@RequestBody Buyer buyer) throws SQLException {
        System.out.println("in BuyerInformation Add spring");
        System.out.println(buyer);
        System.out.println("buyer.getBuyerName():"+buyer.getBuyerName());
        System.out.println("buyer.getBuyerName():"+buyer.getBuyerMail());
        int result = buyerDAO.update(buyer);
        System.out.println("in BuyerInformation result:"+result);
     }

     @GetMapping(value={"/findProductAll"})
    public List<Product> retrieveFindProductAll() throws SQLException{
        System.out.println("in findProductAll spring");
        return productDAO.findProductAll();  
     }

      //checkout更改商品庫存
     @PutMapping(value = "/ProductStock")
     public void retrieveProductInfoUpdate(@RequestBody Product product) throws SQLException {
        System.out.println("in retrieveProductInfoUpdate spring");
        System.out.println(product);
        System.out.println("product.getProductStock():"+product.getProductStock());
        productDAO.update(product);
     }

   //重新購買新增進購物車的資料庫
     @PostMapping(value = "/CartAddAgain")
     public void retrieveBuyerInformationUpdate(@RequestBody Cart cart) throws SQLException {
        System.out.println("in BuyerInformation Add spring");
        cartDAO.insert(cart);
     }

     @PutMapping(value = "/CartAdd")
     public void retrieveCartInformationAdd(@RequestBody Cart cart) throws SQLException {
        System.out.println("in CartAdd spring");
        System.out.println("cart:"+cart);
        System.out.println("buyer.getBuyerName():"+cart.getBuyerId());
        cartDAO.insert(cart);
     }

     //更改購物車的資料庫的checked
      @PutMapping(value = "/CartUpdate")
      public void retrieveCartInformationUpdate(@RequestBody Cart cart) throws SQLException {
         System.out.println("in CartUpdate spring");
         cartDAO.update(cart);
      }
     
     //delete購物車
     @DeleteMapping(value = "/CartDeleted/{id}")
     public void retrieveBuyerInformationUpdate(@PathVariable int id) throws SQLException {
        System.out.println("in /CartDeleted/ spring");
        System.out.println("id:"+id);
        cartDAO.delete(id);
     }

     //讀取cart跟product資料 放到cart
     @GetMapping(value={"/CartProductInfo/{id}"})
    public List<CartInfo> retrievefindJoinCartAllProduct(@PathVariable("id") String id) throws SQLException{
        System.out.println("in CartProductInfo spring");
        System.out.println("in CartProductInfo id:"+id);
        return cartInfoDAO.findJoinCartAllProduct(id);  
     }

    //讀取已勾選的cart商品
    @GetMapping(value={"/Review/{id}"})
    public List<CartInfo> retrievefindCheckedCart(@PathVariable("id") String id) throws SQLException{
         System.out.println("in Review spring");
         System.out.println("in Review id:"+id);
         return cartInfoDAO.findCheckedCart(id);  
     }

     //新增訂單到order_item
     @PutMapping(value = "/OrderItemAdd")
     public void retrieveOrderItemAdd(@RequestBody OrderItem orderItem) throws SQLException {
        System.out.println("in OrderItemAdd spring");
        orderItemDAO.insert(orderItem);
     }

     //新增訂單到orderlist
     @PutMapping(value = "/OrderListAdd")
     public int retrieveOrderListAdd(@RequestBody OrderList orderList) throws SQLException {
        System.out.println("in OrderListAdd spring");
        System.out.println(orderList.getOrderListStatus());
        int result = orderListDAO.inserts(orderList);
        System.out.println(result);
        return result;
     }

     //新增商品
     @PutMapping(value = "/ProductAdd")
     public int retrieveProductAdd(@RequestBody Product product) throws SQLException {
        System.out.println("in ProductAdd spring");
        System.out.println("product:"+product);
        int result =productDAO.insertToType(product);
        System.out.println("產生key");
        System.out.println(result);
        return result;
     }

     //新增商品完商品去produtc_type新增
     @PutMapping(value = "/ProductTypeAdd")
     public int retrieveProductTypeAdd(@RequestBody ProductType productType) throws SQLException {
        System.out.println("in ProductTypeAdd spring");
        return productTypeDAO.insert(productType);
     }

     // 新增進Type
   @PostMapping(value = "/TypeAdd")
   public void retrieveTypeUpdate(@RequestBody Type type) throws SQLException {
      System.out.println("in TypeAdd spring");
      System.out.println("type:" + type);
      typeDAO.insert(type);
   }

   // 刪除多的Type
   @DeleteMapping(value = "/TypeDelete/{type_name}")
   public void retrieveTypeDelete(@PathVariable("type_name") String type_name) throws SQLException {
      System.out.println("in TypeDelete spring");
      System.out.println("type:" + type_name);
      typeDAO.delete(type_name);
   }
}
