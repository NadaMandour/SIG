package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class invHeaderTableModel extends AbstractTableModel {
 private ArrayList<InvoiceHeader> data;
    private String[] cols = {"Num", "Customer Name", "Invoice Date"};

    public invHeaderTableModel(ArrayList<InvoiceHeader> data) {
        this.data = data;
    }

    public ArrayList<InvoiceHeader> getData() {
        return data;
    }

    
    
 @Override
    public int getRowCount() {
        return data.size();
    }

 @Override
    public int getColumnCount() {
        return cols.length;
    }

 @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader header = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return header.getNum();
            case 1:
                return header.getCustomer();
            case 2:
                return header.getDate();
                
        }
        return "";
    }

 @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    
    
   }
