import net.sf.jsqlparser.expression.PrimitiveValue;

public interface Operator {
    public PrimitiveValue[] readOneTuple();
    public void reset();
}
