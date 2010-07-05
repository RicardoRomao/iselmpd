package serie3.grupo1.domainObjects.primaryKeys;

public class PkOrderDetails {
    private final int _orderId;
    private final int _productId;

    public int getOrderId() { return _orderId; }
    public int getProductId() { return _productId; }

    public PkOrderDetails(int orderId, int productId){
        _orderId = orderId;
        _productId = productId;
    }
}
