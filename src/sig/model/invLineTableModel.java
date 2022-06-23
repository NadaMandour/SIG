
package sig.model;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class invLineTableModel {
    
     private ArrayList<InvoiceLine> data;
    private String[] cols = {"Item Name", "Unit Price", "Count"};

    public invLineTableModel(ArrayList<InvoiceLine> data) {
        this.data = data;
    }
    
     public int getRowCount() {
        return data.size();
    }

   
    public int getColumnCount() {
        return cols.length;
    }

    
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return line.getName();
            case 1:
                return line.getPrice();
            case 2:
                return line.getCount();
        }
        return "";
    }

        public String getColumnName(int column) {
        return cols[column];
    }
    
   
/*    
        DefaultTableModel lineModel = new DefaultTableModel();


    public void createLineModel(DefaultTableModel model ){
    
        lineModel= model;
 String[] columnNames = new String[]{
            "invoice Number", "Item Name", "Pricce", "Count","Total"};
        lineModel.setColumnIdentifiers(columnNames);
        //lineModel.addRow(columnNames);
        //System.out.println("before");
        for (int j = 0; j < (lineModel.getRowCount()); j++) {
            for (int i = 0; i < lineModel.getColumnCount(); i++) {
                System.out.println(lineModel.getValueAt(j, i));
            }
           System.out.println("**lines**");
        }

        
        
  */      
        
}
    



    
