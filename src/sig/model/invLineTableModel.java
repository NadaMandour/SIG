
package sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class invLineTableModel extends AbstractTableModel {
    
     private ArrayList<InvoiceLine> data;
    private String[] cols = {"Item Name", "Unit Price", "Count","Total"};

    public invLineTableModel(ArrayList<InvoiceLine> data) {
        this.data = data;
    }
    public  void addRowLine(){
        
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
        InvoiceLine line = data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return line.getName();
            case 1:
                return line.getPrice();
            case 2:
                return line.getCount();
                case 3:
                return line.getLineTotal();
        }
        return "";
    }

   
    
     @Override
        public String getColumnName(int column) {
        return cols[column];
    }
    
   
}
    



    
