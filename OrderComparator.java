import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.create.table.CreateTable;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;

public class OrderComparator implements Comparator<PrimitiveValue[]> {
    HashMap<String, String> aliasHashMap = new HashMap<>();
    HashMap<String, CreateTable> createTableMap;
    HashMap<String, Integer> databaseMap;
    PrimitiveValue[] tuple;
    Integer columnIndex;
    Column[] schema;
    Boolean isAsc;

    public OrderComparator( HashMap<String, String> aliasHashMap, PrimitiveValue[] tuple,
            Integer columnIndex, Column[] schema, Boolean isAsc, HashMap<String, CreateTable> createTableMap,
                            HashMap<String, Integer> databaseMap
    ){
        this.aliasHashMap = aliasHashMap;
        this.tuple = tuple;
        this.columnIndex = columnIndex;
        this.schema = schema;
        this.isAsc = isAsc;
        this.createTableMap = createTableMap;
        this.databaseMap = databaseMap;
    }

    @Override
    public int compare(PrimitiveValue[] pv1, PrimitiveValue[] pv2) {
        Evaluator eval = new Evaluator();
        eval.setVariables(tuple,schema,aliasHashMap, createTableMap, databaseMap);

        if(isAsc){
            GreaterThan cmp = new GreaterThan();
            if(pv1[columnIndex] instanceof DateValue){
                cmp.setRightExpression(new DateValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new DateValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof LongValue){
                cmp.setRightExpression(new LongValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new LongValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof DoubleValue){
                cmp.setRightExpression(new DoubleValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new DoubleValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof StringValue){
                cmp.setRightExpression(new StringValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new StringValue(pv2[columnIndex].toString()));
            }

            try{
                PrimitiveValue result = eval.eval(cmp);
                if(!result.toBool()){
                    return 1;
                } else {
                    return -1;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else{
            MinorThan cmp = new MinorThan();

            if(pv1[columnIndex] instanceof DateValue){
                cmp.setRightExpression(new DateValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new DateValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof LongValue){
                cmp.setRightExpression(new LongValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new LongValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof DoubleValue){
                cmp.setRightExpression(new DoubleValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new DoubleValue(pv2[columnIndex].toString()));
            }
            else if(pv1[columnIndex] instanceof StringValue){
                cmp.setRightExpression(new StringValue(pv1[columnIndex].toString()));
                cmp.setLeftExpression(new StringValue(pv2[columnIndex].toString()));
            }

            try{
                PrimitiveValue result = eval.eval(cmp);
                if(!result.toBool()){
                    return 1;
                } else {
                    return -1;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return -1;
    }
}
