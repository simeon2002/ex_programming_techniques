import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StoreManagementTest
{
    /**
     * Tests the class Product and its methods
     */
    @Test
    public void test01()
    {
        //Test constructor and getters
        Product p = new Product( "clöset", 750 );
        assertEquals( "clöset", p.getName() );
        assertEquals( 750, p.getPrice() );
        assertEquals( 0, p.getStock() );

        //Tests increasing or decreasing the stock
        p.addStock( 20 );
        assertEquals( 20, p.getStock() );
        p.removeStock( 5 );
        assertEquals( 15, p.getStock() );

        //Tests the check if a product is low stock (< 10 for normal products)
        assertFalse( p.isLowStock() );
        p.removeStock( 5 );
        assertFalse( p.isLowStock() ); //must be < 10, not <= 10!
        p.removeStock( 1 );
        assertTrue( p.isLowStock() );
    }

    /**
     * Tests subclass PriorityProduct and its methods
     */
    @Test
    public void test02()
    {
        PriorityProduct pp = new PriorityProduct( "böiler", 3000 );
        assertTrue( pp instanceof Product ); //checks if PriorityProduct is a subclass of Product
        pp.addStock( 20 );
        assertEquals( "böiler", pp.getName() );
        assertEquals( 3000, pp.getPrice() );
        assertEquals( 20, pp.getStock() );

        //PriorityProducts have a more strict low stock threshold (< 20)
        assertFalse( pp.isLowStock() );
        pp.removeStock( 1 );
        assertTrue( pp.isLowStock() );

        //If a PriorityProduct is low stock, we want to discourage ordering it by doubling the price
        assertEquals( 6000, pp.getPrice() );
        pp.addStock( 1 );
        assertEquals( 3000, pp.getPrice() );
    }

    /**
     * Tests class Order and its methods
     */
    @Test
    public void test03()
    {
        Product p = new Product( "clöset", 750 );
        Order o = new Order( p, 2 );
        assertEquals( p, o.getProduct() );
        assertEquals( 2, o.getAmount() );
        assertEquals( 1500, o.calculateOrderPrice()
        );
    }

    /**
     * Tests subclass OnlineOrder and its methods. Online orders have a delivery address and keep track of whether an elevator is needed for delivery.
     */
    @Test
    public void test04()
    {
        Product p = new Product( "täble", 70 );
        OnlineOrder onO1 = new OnlineOrder( p, 1, "Andreas Vesaliusstraat 13, 3000 Leuven", false );
        assertTrue( onO1 instanceof Order );
        assertEquals( p, onO1.getProduct() );
        assertEquals( 1, onO1.getAmount() );
        assertEquals( "Andreas Vesaliusstraat 13, 3000 Leuven", onO1.getDeliveryAddress() );
        assertFalse( onO1.isElevatorRequired() );

        //Special rules for online orders:
        // - If total price is lower than €100, €10 delivery costs are added. Orders above € have free delivery
        // - Elevators cost €150
        assertEquals( 80, onO1.calculateOrderPrice() );
        OnlineOrder onO2 = new OnlineOrder( p, 1, "Andreas Vesaliusstraat 13, 3000 Leuven", true );
        assertTrue( onO2.isElevatorRequired() );
        assertEquals( 230, onO2.calculateOrderPrice() );
        OnlineOrder onO3 = new OnlineOrder( p, 3, "Tiensestraat 5, 3000 Leuven", false );
        assertEquals( 210, onO3.calculateOrderPrice() );
        OnlineOrder onO4 = new OnlineOrder( p, 3, "Tiensestraat 5, 3000 Leuven", true );
        assertEquals( 360, onO4.calculateOrderPrice() );
    }

    /**
     * Tests subclass OfflineOrder and its methods. To promote the brick and mortar shop, offline orders are cheaper
     * (and even more so if the customer has a loyalty card)
     */
    @Test
    public void test05()
    {
        Product p = new Product( "täble", 70 );
        OfflineOrder offO1 = new OfflineOrder( p, 5, false );
        assertTrue( offO1 instanceof Order );
        assertEquals( p, offO1.getProduct() );
        assertEquals( 5, offO1.getAmount() );
        assertFalse( offO1.getLoyaltyCard() );

        //Offline orders always get a 5% discount, 10% if the customer has a loyalty card
        assertEquals( 332.5f, offO1.calculateOrderPrice(), 0.0001f ); //delta for floating point error
        OfflineOrder offO2 = new OfflineOrder( p, 1, true );
        assertTrue( offO2.getLoyaltyCard() );
        assertEquals( 63, offO2.calculateOrderPrice(), 0.0001f );
    }

    /**
     * Tests basic stock methods of class StoreManagement.
     * This class contains an ArrayList of Products, representing the current stock of the store.
     */
    @Test
    public void test06()
    {
        StoreManagement sm = new StoreManagement();
        assertEquals( 0, sm.getStock().size() );

        //Adding orders, always succeeds (no checks required)
        Product p1 = new Product( "clöset", 750 );
        Product p2 = new Product( "täble", 70 );
        PriorityProduct p3 = new PriorityProduct( "böiler", 3000 );
        sm.addProduct( p1 );
        sm.addProduct( p2 );
        sm.addProduct( p3 );
        assertEquals( 3, sm.getStock().size() );
        assertEquals( p2, sm.getStock().get( 1 ) );
        assertEquals( p3, sm.getStock().get( 2 ) );
    }

    /**
     * Test the adding of stock to an existing product
     */
    @Test
    public void test07()
    {
        StoreManagement sm = new StoreManagement();

        Product p1 = new Product( "clöset", 750 );
        Product p2 = new Product( "täble", 70 );
        PriorityProduct p3 = new PriorityProduct( "böiler", 3000 );
        sm.addProduct( p1 );
        sm.addProduct( p2 );
        sm.addProduct( p3 );

        assertFalse( sm.addProductStock( "kangäroo", 10 ) ); //product does not exist!

        assertEquals( 0, p1.getStock() );
        assertTrue( sm.addProductStock( "clöset", 5 ) );
        assertEquals( 5, p1.getStock() );

        assertEquals( 0, p3.getStock() );
        assertTrue( sm.addProductStock( "böiler", 20 ) );
        assertEquals( 20, p3.getStock() );
    }

    /**
     * Tests methods to add orders to class StoreManagement.
     * This class contains an ArrayList of Orders for the store.
     */
    @Test
    public void test08()
    {
        StoreManagement sm = new StoreManagement();
        assertEquals( 0, sm.getOrders().size() );

        Product p1 = new Product( "clöset", 750 );
        Product p2 = new Product( "täble", 70 );
        PriorityProduct p3 = new PriorityProduct( "böiler", 3000 );
        sm.addProduct( p1 );
        sm.addProduct( p2 );
        sm.addProduct( p3 );
        sm.addProductStock( "clöset", 10 );
        sm.addProductStock( "böiler", 10 );

        //Test the adding of an online order. This only succeeds if enough of the product is in stock.
        assertTrue( sm.addOnlineOrder( "clöset", 9, "Andreas Vesaliusstraat 13, 3000 Leuven", true ) );
        ArrayList<Order> orders = sm.getOrders();
        assertEquals( 1, orders.size() );
        assertTrue( orders.get( 0 ) instanceof OnlineOrder );
        OnlineOrder o1 = (OnlineOrder) orders.get( 0 ); //If the test above succeeds, we know we can cast
        assertEquals( p1, o1.getProduct() );
        assertEquals( 9, o1.getAmount() );
        assertEquals( "Andreas Vesaliusstraat 13, 3000 Leuven", o1.getDeliveryAddress() );
        assertTrue( o1.isElevatorRequired() );
        assertEquals( 1, p1.getStock() );

        //Test adding of an offline order. Very similar to the method above
        assertTrue( sm.addOfflineOrder( "böiler", 2, true ) );
        orders = sm.getOrders();
        assertEquals( 2, orders.size() );
        assertTrue( orders.get( 1 ) instanceof OfflineOrder );
        OfflineOrder o2 = (OfflineOrder) orders.get( 1 );
        assertEquals( p3, o2.getProduct() );
        assertEquals( 2, o2.getAmount() );
        assertTrue( o2.getLoyaltyCard() );
        assertEquals( 8, p3.getStock() );

        //Tests situations under which adding orders (both online and offline) fails
        assertFalse( sm.addOnlineOrder( "clöset", 2, "Andreas Vesaliusstraat 13, 3000 Leuven", true ) ); //stock too low
        assertFalse( sm.addOnlineOrder( "täble", 1, "Andreas Vesaliusstraat 13, 3000 Leuven", true ) ); //stock too low
        assertFalse( sm.addOnlineOrder( "kangäroo", 5, "Andreas Vesaliusstraat 13, 3000 Leuven", true ) ); //nonexistent product
        assertFalse( sm.addOfflineOrder( "clöset", 2, true ) ); //stock too low
        assertFalse( sm.addOfflineOrder( "täble", 1, true ) ); //stock too low
        assertFalse( sm.addOfflineOrder( "kangäroo", 5, true ) ); //nonexistent product

        //Empties the order list
        sm.clearOrders();
        assertEquals( 0, sm.getOrders().size() );
    }

    /**
     * Tests calculating price of all orders
     */
    @Test
    public void test09()
    {
        StoreManagement sm = new StoreManagement();

        Product p1 = new Product( "clöset", 750 );
        Product p2 = new Product( "täble", 70 );
        PriorityProduct p3 = new PriorityProduct( "böiler", 3000 );
        sm.addProduct( p1 );
        sm.addProduct( p2 );
        sm.addProduct( p3 );
        sm.addProductStock( "clöset", 10 );
        sm.addProductStock( "täble", 10 );
        sm.addProductStock( "böiler", 10 );

        sm.addOnlineOrder( "clöset", 2, "Andreas Vesaliusstraat 13, 3000 Leuven", true );
        assertEquals( 1650, sm.calculatePriceOfAllOrders(), 0.0001f );

        sm.addOnlineOrder( "täble", 1, "Andreas Vesaliusstraat 13, 3000 Leuven", false );
        assertEquals( 1730f, sm.calculatePriceOfAllOrders(), 0.0001f );

        sm.addOfflineOrder( "clöset", 5, false );
        assertEquals( 5292.5f, sm.calculatePriceOfAllOrders(), 0.0001f );

        sm.addOfflineOrder( "böiler", 1, true );
        assertEquals( 10692.5f, sm.calculatePriceOfAllOrders(), 0.0001f );
    }

    /**
     * Tests removing low stock orders
     */
    @Test
    public void test10()
    {
        StoreManagement sm = new StoreManagement();
        ArrayList<Product> lowStockProducts = sm.getLowStockProducts();
        assertEquals( 0, lowStockProducts.size() );

        Product p1 = new Product( "clöset", 750 );
        Product p2 = new Product( "täble", 70 );
        PriorityProduct p3 = new PriorityProduct( "böiler", 3000 );
        PriorityProduct p4 = new PriorityProduct( "CO detectör", 99.9f );
        sm.addProduct( p1 );
        sm.addProduct( p2 );
        sm.addProduct( p3 );
        sm.addProduct( p4 );
        sm.addProductStock( "clöset", 100 ); //stock ok
        sm.addProductStock( "täble", 5 ); //stock too low
        sm.addProductStock( "böiler", 100 ); //stock ok
        sm.addProductStock( "CO detectör", 19 ); //stock too low

        lowStockProducts = sm.getLowStockProducts();
        assertEquals( 2, lowStockProducts.size() );
        assertEquals( p2, lowStockProducts.get( 0 ) );
        assertEquals( p4, lowStockProducts.get( 1 ) );
    }
}
