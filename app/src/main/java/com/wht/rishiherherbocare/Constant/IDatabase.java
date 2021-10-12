package com.wht.rishiherherbocare.Constant;


public interface IDatabase {


    int DATABASE_VERSION = 1;

    String DATABASE_NAME = "order_table";
    String TABLE_ORDER_OBJECTIVE = "TABLE_ORDER_OBJECTIVE";

    String OrderID = "OrderID";
    String OrderName = "OrderName";
    String OrderBasicAmt = "OrderBasicAmt";
    String OrderCount = "OrderCount";
    String OrderQuantity = "OrderQuantity";
    String TotalAmt = "TotalAmt";


    String[] COLUMNS_ORDER = {OrderID, OrderName, OrderBasicAmt, OrderCount, OrderQuantity, TotalAmt};

    String CREATE_ORDER = "CREATE TABLE '" + TABLE_ORDER_OBJECTIVE + "' ( '"
            + OrderID + "' TEXT, '"
            + OrderName + "' TEXT, '"
            + OrderBasicAmt + "' TEXT, '"
            + OrderCount + "' TEXT, '"
            + OrderQuantity + "' TEXT, '"
            + TotalAmt + "' TEXT )";


    //  CartLocal

    String TABLE_CART = "TABLE_CART";

    String CART_ITEM_ID = "CART_ITEM_ID";
    String CART_BASE_QTY = "CART_BASE_QTY";
    String CART_ITEM_NAME = "CART_ITEM_NAME";
    String CART_CURRENCY_NAME_SYMBOL = "CART_CURRENCY_NAME_SYMBOL";
    String CART_DISC_AMT = "CART_DISC_AMT";
    String CART_DISC_PER = "CART_DISC_PER";
    String CART_IMAGE_URL = "CART_IMAGE_URL";
    String CART_ITEM_DESCRIPTION = "CART_ITEM_DESCRIPTION";
    String CART_PRODUCT_MRP = "CART_PRODUCT_MRP";
    String CART_SELLING_AMOUNT = "CART_SELLING_AMOUNT";
    String CART_ITEM_AMT = "CART_ITEM_AMT";

    String[] COLUMNS_CART = {CART_BASE_QTY, CART_ITEM_ID, CART_ITEM_NAME, CART_CURRENCY_NAME_SYMBOL, CART_DISC_AMT, CART_DISC_PER, CART_IMAGE_URL, CART_ITEM_DESCRIPTION,
            CART_PRODUCT_MRP, CART_SELLING_AMOUNT, CART_ITEM_AMT};

    String CREATE_CART = "CREATE TABLE '" + TABLE_CART + "' ( '"
            + CART_ITEM_ID + "' TEXT, '"
            + CART_BASE_QTY + "' INTEGER, '"
            + CART_ITEM_NAME + "' TEXT, '"
            + CART_CURRENCY_NAME_SYMBOL + "' TEXT, '"
            + CART_DISC_AMT + "' TEXT, '"
            + CART_DISC_PER + "' TEXT, '"
            + CART_IMAGE_URL + "' TEXT, '"
            + CART_ITEM_DESCRIPTION + "' TEXT, '"
            + CART_PRODUCT_MRP + "' INTEGER, '"
            + CART_SELLING_AMOUNT + "' INTEGER, '"
            + CART_ITEM_AMT + "' INTEGER )";

}
