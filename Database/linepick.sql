-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- 主機： localhost
-- 產生時間： 
-- 伺服器版本： 5.7.27-log
-- PHP 版本： 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `linepick`
--

-- --------------------------------------------------------

--
-- 資料表結構 `buyer`
--

CREATE TABLE `buyer` (
  `buyer_id` varchar(120) NOT NULL,
  `buyer_name` varchar(10) NOT NULL,
  `buyer_phone` varchar(10) DEFAULT NULL,
  `buyer_mail` varchar(50) DEFAULT NULL,
  `buyer_address` varchar(60) DEFAULT NULL,
  `pickpoint` int(5) DEFAULT NULL,
  `pickmoney` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `buyer`
--

INSERT INTO `buyer` (`buyer_id`, `buyer_name`, `buyer_phone`, `buyer_mail`, `buyer_address`, `pickpoint`, `pickmoney`) VALUES
('Ub19b06294bf055b1a7574919684b7c32', '瑄瑄', '0912345678', 'A', 'A', 150, 0);

-- --------------------------------------------------------

--
-- 資料表結構 `cart`
--

CREATE TABLE `cart` (
  `cart_id` int(3) NOT NULL,
  `buyer_id` varchar(120) NOT NULL,
  `product_id` int(4) NOT NULL,
  `quantity` int(10) DEFAULT NULL,
  `checked` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `orderlist`
--

CREATE TABLE `orderlist` (
  `orderlist_id` int(10) NOT NULL,
  `pay_type` varchar(10) NOT NULL,
  `pay_status` varchar(1) NOT NULL,
  `orderlist_status` varchar(10) NOT NULL,
  `orderlist_payment` int(10) NOT NULL,
  `order_date` varchar(10) DEFAULT NULL,
  `pickmoney_use` int(10) DEFAULT NULL,
  `buyer_id` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `orderlist`
--

INSERT INTO `orderlist` (`orderlist_id`, `pay_type`, `pay_status`, `orderlist_status`, `orderlist_payment`, `order_date`, `pickmoney_use`, `buyer_id`) VALUES
(1, 'LinePay', 'N', '未出貨', 240, '20210523', 10, 'Ucc06fdce95599866988a3e9452f49f48');

-- --------------------------------------------------------

--
-- 資料表結構 `order_item`
--

CREATE TABLE `order_item` (
  `order_item_id` int(3) NOT NULL,
  `order_item_quantity` int(10) NOT NULL,
  `product_id` int(4) NOT NULL,
  `orderlist_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `order_item`
--

INSERT INTO `order_item` (`order_item_id`, `order_item_quantity`, `product_id`, `orderlist_id`) VALUES
(1, 2, 9, 1);

-- --------------------------------------------------------

--
-- 資料表結構 `product`
--

CREATE TABLE `product` (
  `product_id` int(4) NOT NULL,
  `product_name` varchar(50) DEFAULT NULL,
  `product_desc` varchar(100) DEFAULT NULL,
  `product_price` int(10) DEFAULT NULL,
  `product_stock` int(4) DEFAULT NULL,
  `product_photo` varchar(500) DEFAULT NULL,
  `product_style` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_desc`, `product_price`, `product_stock`, `product_photo`, `product_style`) VALUES
(1, '白色戀人巧克力', '是北海道一種著名的巧克力夾心薄餅，於2塊餅乾中夾著一層的白巧克力。及後有黑色的牛奶巧克力口味。', 780, 66, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E7%99%BD%E8%89%B2%E6%88%80%E4%BA%BA.jpg?alt=media&token=46dbaec3-21ee-471e-bd7e-0d24dd5743f2', '黑巧克力'),
(2, '白色戀人巧克力', '是北海道一種著名的巧克力夾心薄餅，於2塊餅乾中夾著一層的白巧克力。及後有黑色的牛奶巧克力口味。', 780, 599, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E7%99%BD%E8%89%B2%E6%88%80%E4%BA%BA.jpg?alt=media&token=46dbaec3-21ee-471e-bd7e-0d24dd5743f2', '白巧克力'),
(3, '星巴克春季櫻花杯', '2021 3月新上市 為韓國限定款商品 數量有限，趕快把握機會購買!', 800, 95, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E9%9B%99%E5%B1%A4%E7%8E%BB%E7%92%83%E6%9D%AF.jpg?alt=media&token=215bb4d4-0ecb-4daf-95bd-bc45e706266f', '雙層玻璃杯'),
(4, '星巴克春季櫻花杯', '2021 3月新上市 為韓國限定款商品 數量有限，趕快把握機會購買!', 1120, 15, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E4%B8%8D%E9%8F%BD%E9%8B%BC%E6%9D%AF.jpg?alt=media&token=2b00dd0d-8245-489a-86ad-180c86d53521', '不鏽鋼胖胖杯'),
(5, '星巴克春季櫻花杯', '2021 3月新上市 為韓國限定款商品 數量有限，趕快把握機會購買!', 885, 20, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E9%99%B6%E7%93%B7%E8%AE%8A%E8%89%B2%E9%A6%AC%E5%85%8B%E6%9D%AF.jpg?alt=media&token=5e4493b3-790c-4c8b-aabb-40065a2219f2', '陶瓷變色馬克杯'),
(6, 'Innisfree洗面乳', '依照自己的肌膚選擇合適的洗面乳吧! 功用:綠茶-保濕；火山泥-抗油;緊緻毛孔亮膚\n\n', 199, 18, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E7%B6%A0%E8%8C%B6%E6%B4%97%E9%9D%A2%E4%B9%B3.jpg?alt=media&token=aa016e1e-8bdc-419d-9c65-4cc9ea354127', '綠茶洗面乳80ml'),
(7, 'Innisfree洗面乳', '依照自己的肌膚選擇合適的洗面乳吧! 功用:綠茶-保濕；火山泥-抗油;緊緻毛孔亮膚\n\n', 199, 48, 'https://firebasestorage.googleapis.com/v0/b/line-pick-5da9a.appspot.com/o/%E7%81%AB%E5%B1%B1%E6%B3%A5%E6%B4%97%E9%9D%A2%E4%B9%B3.jpg?alt=media&token=e65dfffb-dbff-4236-86a4-23a3f95b7d11', '火山泥洗面乳70ml'),
(8, 'Innisfree洗面乳', '依照自己的肌膚選擇合適的洗面乳吧! 功用:綠茶-保濕；火山泥-抗油;緊緻毛孔亮膚\n\n', 249, 20, 'https://firebasestorage.googleapis.com/v0/b/line-pick.appspot.com/o/E8BD7D26-BEA6-4CFC-A26E-2C0E171F5541.jpg?alt=media&token=078242db-901e-4c6b-ae14-1b07724fcd9d', '毛孔緊緻亮膚140ml'),
(9, 'Innisfree控油蜜粉', '韓國熱銷第一名!!! innisfree x Dinotaeng聯名蜜粉～\n', 120, 89, 'https://firebasestorage.googleapis.com/v0/b/line-pick.appspot.com/o/DCDFE85E-B557-4701-83E0-CB03C9115D26.jpg?alt=media&token=df1cf765-d623-4b49-a50c-4db83929b136', '控油款');

-- --------------------------------------------------------

--
-- 資料表結構 `product_type`
--

CREATE TABLE `product_type` (
  `product_type_id` int(3) NOT NULL,
  `type_id` int(3) NOT NULL,
  `product_id` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `product_type`
--

INSERT INTO `product_type` (`product_type_id`, `type_id`, `product_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 2, 4),
(5, 2, 5),
(6, 3, 6),
(7, 3, 7),
(8, 3, 8),
(9, 3, 9);

-- --------------------------------------------------------

--
-- 資料表結構 `reply`
--

CREATE TABLE `reply` (
  `reply_id` int(3) NOT NULL,
  `reply_question` varchar(100) NOT NULL,
  `reply_answer` varchar(1000) NOT NULL,
  `seller_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `reply`
--

INSERT INTO `reply` (`reply_id`, `reply_question`, `reply_answer`, `seller_id`) VALUES
(1, '付款方式有哪些', '目前提供: LINE PAY、信用卡線上付款以及超商取貨付款這三種方式', 1),
(2, '下單後過多久出貨', '一般來說7個工作天內出貨', 1),
(3, '以上問題沒有您所想問的嗎', '請直接傳送訊息等待專人為您服務', 1),
(4, '圖文選單導覽', '圖文選單導覽', 1);

-- --------------------------------------------------------

--
-- 資料表結構 `seller`
--

CREATE TABLE `seller` (
  `seller_id` int(3) NOT NULL,
  `seller_account` varchar(50) NOT NULL,
  `seller_password` varchar(20) NOT NULL,
  `seller_mail` varchar(50) NOT NULL,
  `seller_phone` varchar(10) NOT NULL,
  `market_name` varchar(10) NOT NULL,
  `market_desc` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- 傾印資料表的資料 `seller`
--

INSERT INTO `seller` (`seller_id`, `seller_account`, `seller_password`, `seller_mail`, `seller_phone`, `market_name`, `market_desc`) VALUES
(1, 'a', 'a', 'a@gmail.com', '0912345678', 'linepick', 'Welcome~');

-- --------------------------------------------------------

--
-- 資料表結構 `type`
--

CREATE TABLE `type` (
  `type_id` int(3) NOT NULL,
  `type_name` varchar(10) NOT NULL,
  `seller_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `type`
--

INSERT INTO `type` (`type_id`, `type_name`, `seller_id`) VALUES
(1, '零食', 1),
(2, '生活雜貨', 1),
(3, '美妝保養', 1);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `buyer`
--
ALTER TABLE `buyer`
  ADD PRIMARY KEY (`buyer_id`);

--
-- 資料表索引 `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `buyer_id` (`buyer_id`) USING BTREE;

--
-- 資料表索引 `orderlist`
--
ALTER TABLE `orderlist`
  ADD PRIMARY KEY (`orderlist_id`),
  ADD KEY `buyer_id` (`buyer_id`) USING BTREE;

--
-- 資料表索引 `order_item`
--
ALTER TABLE `order_item`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `orderlist_id` (`orderlist_id`) USING BTREE;

--
-- 資料表索引 `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- 資料表索引 `product_type`
--
ALTER TABLE `product_type`
  ADD PRIMARY KEY (`product_type_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `type_id` (`type_id`);

--
-- 資料表索引 `reply`
--
ALTER TABLE `reply`
  ADD PRIMARY KEY (`reply_id`),
  ADD KEY `seller_id` (`seller_id`);

--
-- 資料表索引 `seller`
--
ALTER TABLE `seller`
  ADD PRIMARY KEY (`seller_id`);

--
-- 資料表索引 `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`type_id`),
  ADD KEY `seller_id` (`seller_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int(3) NOT NULL AUTO_INCREMENT;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `orderlist`
--
ALTER TABLE `orderlist`
  MODIFY `orderlist_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `order_item`
--
ALTER TABLE `order_item`
  MODIFY `order_item_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `product_type`
--
ALTER TABLE `product_type`
  MODIFY `product_type_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `reply`
--
ALTER TABLE `reply`
  MODIFY `reply_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `seller`
--
ALTER TABLE `seller`
  MODIFY `seller_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `type`
--
ALTER TABLE `type`
  MODIFY `type_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
