package trabalho.domainObjects.primaryKeys;

public class PkOrderDetails {
    private final int _orderId;
    private final int _productId;

    public int getOrderId() { return _orderId; }
    public int getProductId() { return _productId; }

    public PkOrderDetails(int orderId, int productId){
        _orderId = orderId;
        _productId = productId;
    }

    @Override
    public boolean equals(Object pk){
        return _orderId == ((PkOrderDetails)pk).getOrderId() &&
                _productId == ((PkOrderDetails)pk).getProductId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this._orderId;
        hash = 47 * hash + this._productId;
        return hash;
    }


}
