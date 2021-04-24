package com.example.demo.entity;
    
    public class Cart {
        private int cart_id = 0;
        private String buyer_id = "";
        private int product_id = 0;
        private int quantity = 0;

        public int getCartId(){
            return cart_id;
        }
    
        public void setCartId(int cart_id){
            this.cart_id=cart_id;
        }

        public String getBuyerId(){
            return buyer_id;
        }
    
        public void setBuyerId(String buyer_id){
            this.buyer_id=buyer_id;
        }
    
        public int getProductId(){
            return product_id;
        }
    
        public void setProductId(int product_id){
            this.product_id=product_id;
        }

        public int getQuantity(){
            return quantity;
        }
    
        public void setQuantity(int quantity){
            this.quantity=quantity;
        }
    
    }
    

