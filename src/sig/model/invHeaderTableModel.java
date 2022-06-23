package sig.model;

import java.util.ArrayList;

public class invHeaderTableModel  {
 private ArrayList<InvoiceHeader> data;
    private String[] cols = {"Id", "Customer Name", "Invoice Date"};

    public invHeaderTableModel(ArrayList<InvoiceHeader> data) {
        this.data = data;
    }
    
    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return cols.length;
    }

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

    public String getColumnName(int column) {
        return cols[column];
    }
    
    
    
    
    /*public void createHeaderModel(DefaultTableModel model) {

        headerModel = model;
        String[] columnNames = new String[]{
            "No.", "Date", "Customer", "Total"
        };
       
        headerModel.setColumnIdentifiers(columnNames);
       // headerModel.addRow(columnNames);
                  System.out.println("***before***");

        for (int j = 0; j < headerModel.getRowCount(); j++) {
            for (int i = 0; i < headerModel.getColumnCount(); i++) {
                System.out.println(headerModel.getValueAt(j, i));
            }
           System.out.println("****");
        }

    }
   

    public invHeaderTableModel getModel() {
        return (invHeaderTableModel) headerModel;

    }*/
}
