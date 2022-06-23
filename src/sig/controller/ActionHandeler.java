package sig.controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import sig.model.InvoiceLine;
import sig.model.InvoiceHeader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import sig.model.invHeaderTableModel;
import sig.model.invLineTableModel;
import sig.view.NewJFrame;

public class ActionHandeler implements ActionListener {

    private NewJFrame frame;

    public ActionHandeler(NewJFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "New Invoice": {
                newInvoice();
            }
            break;
            case "Delete Invoice": {
                deleteInvoice();
            }
            break;
            case "New Item": {
                newItem();
            }
            break;
            case "Delete Item": {
                deleteItem();
            }
            break;
            case " Load file": {
                loadeFile();
            }
            break;
            case "Save file": {
                saveFile();
            }
            break;

        }

    }

    /**
     * ********************************************
     */
    int invoiceNum = 1;
    String invoiceCustomer = null;
    Date invoiceDate = null;
    InvoiceHeader invHeder;
    JTable headerTable = new JTable();
    invHeaderTableModel headerModel = new invHeaderTableModel();
    invLineTableModel lineModel = new invLineTableModel();

    /**
     * " New Invoice" **********************************
     */
    private void newInvoice() {

        frame.setTitle("New Invoice");
        System.out.println("Action New Invoie");

        JTextField invDate = new JTextField(20);
        JTextField customer = new JTextField(20);
        JButton okBtn = new JButton("OK");
        okBtn.setActionCommand("okInv");
        okBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelInv");
        cancelBtn.addActionListener(this);

        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Invoce Date"));
        frame.add(invDate); //textfield
        frame.add(new JLabel("Customer"));
        frame.add(customer);//textfield

        frame.add(okBtn);
        frame.add(cancelBtn);
//parameters

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from Ok Btn");
                invoiceCustomer = customer.getText();
                try {
                    invoiceDate = new SimpleDateFormat("dd/MM/yyyy").parse(invDate.getText());
                } catch (ParseException ex) {
                }
                invHeder = new InvoiceHeader(invoiceNum, invoiceCustomer, invoiceDate);
                System.out.println("customer=  " + invoiceCustomer + "   Date= " + invoiceDate);
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.setVisible(false);

                String[] rowData = {String.valueOf(invHeder.getNum()),
                    invHeder.getDate().toString(), invHeder.getCustomer()};

                // headerModel.createHeaderModel((headerModel.addRow(rowData)));
//headerTable.setModel(model);
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from cancel Btn");
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.setVisible(false);

            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 150);
        frame.setLocation(100, 100);
        frame.setVisible(true);

    }

    /**
     * ****************************************
     */
    private void deleteInvoice() {
        JFrame frame = new JFrame();

        System.out.println("Action Delete invoice");
        frame.setTitle("Delete Invoice");
        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("         Comfirm Deleting Invoice         "));
        JButton confirmBtn = new JButton("Confirm");
        JButton cancelBtn = new JButton("Cancel");
        frame.add(confirmBtn);
        frame.add(cancelBtn);

        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(275, 100);
        frame.setLocation(100, 100);
        frame.setVisible(true);

    }

    /**
     * ****************************************************
     */
    String itmName = null;
    double price;
    int count;

    private void newItem() {
        JFrame frame = new JFrame();

        System.out.println("Action New Item");
        frame.setTitle(" New Item");

        JTextField itemName = new JTextField(20);
        JTextField itemCount = new JTextField(20);
        JTextField ItemPrice = new JTextField(20);

        JButton okBtn = new JButton("OK");
        okBtn.setActionCommand("okItm");
        okBtn.addActionListener(this);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancelItm");
        cancelBtn.addActionListener(this);

        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("Item Name"));
        frame.add(itemName);
        frame.add(new JLabel("Item Count"));
        frame.add(itemCount);
        frame.add(new JLabel("Price     "));
        frame.add(ItemPrice);
        frame.add(okBtn);
        frame.add(cancelBtn);

        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from Ok Btn");
                itmName = itemName.getText();
                price = Integer.valueOf(ItemPrice.getText());
                count = Integer.valueOf(itemCount.getText());
                InvoiceLine invLine = new InvoiceLine(invHeder, itmName, price, count);
                System.out.println("invoiceHeder=" + invHeder + " item Name= " + itmName + " Price= " + price + "Count= " + count);

                frame.getContentPane().removeAll();
                frame.repaint();
                frame.setVisible(false);

            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                System.out.println("action from cancel Btn");
                frame.getContentPane().removeAll();
                frame.repaint();
                frame.setVisible(false);

            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocation(100, 100);
        frame.setVisible(true);

    }

    /**
     * **************************************************
     */
    private void deleteItem() {
        JFrame frame = new JFrame();

        System.out.println("Action Delete Item");
        frame.setTitle(" Delete Item");
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("         Comfirm Deleting Item         "));
        JButton confirmBtn = new JButton("Confirm");
        JButton cancelBtn = new JButton("Cancel");
        frame.add(confirmBtn);
        frame.add(cancelBtn);

        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        if (this.equals(confirmBtn)) {

        } else if (this.equals(cancelBtn)) {
        }

        frame.setSize(250, 100);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }

    /**
     * ************************************************
     */
    DefaultTableModel modelH = new DefaultTableModel();
    DefaultTableModel modelL = new DefaultTableModel();

    private void loadeFile() {
        System.out.println("Action Load File");

// header file reading
        JFileChooser fc = new JFileChooser();
        int results = fc.showOpenDialog(fc);
        if (results == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            File file = new File(path);
            try {
                BufferedReader buffer = new BufferedReader(new FileReader(file));
                String fileHeadLines = null;
                ArrayList<InvoiceHeader> invoiceHeadersList = new ArrayList<>();
                while ((fileHeadLines = buffer.readLine()) != null) {
                    String[] dataInHeaderRow = fileHeadLines.split(",");

                    String invNumS = dataInHeaderRow[0];
                    String invDateS = dataInHeaderRow[1];
                    String customerName = dataInHeaderRow[2];

                    int invNum = Integer.parseInt(invNumS);

                    Date invDate = new SimpleDateFormat("dd/MM/yyyy").parse(invDateS);
                    //System.out.println("inoice Header= { " + " Number" + invNumS + "InvoiceDate= " + invDateS + "Customer Name= " + customerName + "}\n");

                    InvoiceHeader invHeader = new InvoiceHeader(invNum, customerName, invDate);
                    invoiceHeadersList.add(invHeader);
                    
                }
                System.out.println("check");

                // open lines
                results = fc.showOpenDialog(fc);
                if (results == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getPath();
                    File fileLine = new File(path);
                    BufferedReader bufferLine = new BufferedReader(new FileReader(fileLine));
                    String fileLines = null;

                    while ((fileLines = bufferLine.readLine()) != null) {
                        String[] dataInLineRow = fileLines.split(",");
                        String invNumS = dataInLineRow[0];
                        String itemName = dataInLineRow[1];
                        String itemPriceS = dataInLineRow[2];
                        String itemCountS = dataInLineRow[2];

                        int invNumber = Integer.valueOf(invNumS);
                        double itemPrice = Double.valueOf(itemPriceS);
                        int itemCount = Integer.valueOf(itemCountS);
                        InvoiceHeader header = getInvoiceHeaderById(invoiceHeadersList, invNumber);/*= findInvoice(invNumber);*/
                        InvoiceLine invLine = new InvoiceLine(invHeder, itemName, price, count);
                        header.getLines().add(invLine);
                        //System.out.println(" invNum" + invNumS + "{ Item Name= " + itemName + "Item Price= " + itemPriceS + "Count= " + itemCountS + "}\n");

                    }
                     frame.setInvoiceHeadersList(invoiceHeadersList);

                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "File Not Found/n" + ex.getMessage() + JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Wrong Format /n" + ex.getMessage() + JOptionPane.ERROR_MESSAGE);

            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(ActionHandeler.class.getName()).log(Level.SEVERE, null, ex);
            }

            //javax.swing.JTable headerTable = getheaderTable();
        }

    }
 private InvoiceHeader getInvoiceHeaderById(ArrayList<InvoiceHeader> invoices, int id) {
        for (InvoiceHeader invoice : invoices) {
            if (invoice.getNum() == id) {
                return invoice;
            }
        }
        
        return null;
    }
    /**
     * ****************************************
     */
    private void saveFile() {
        // JTable headerTable = new JTable(model);

        System.out.println("Action Save File");
        JFileChooser fc = new JFileChooser();
        int resultsHeader = fc.showOpenDialog(fc);

        if (resultsHeader == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            File file = new File(path);
            try {
                FileWriter fileWrite = new FileWriter(file);
                BufferedWriter buffer = new BufferedWriter(fileWrite);
                for (int i = 0; i < headerTable.getRowCount(); i++) {
                    for (int j = 0; j < headerTable.getColumnCount(); j++) {
                        buffer.write(headerTable.getValueAt(i, j).toString() + ",");
                    }
                    buffer.newLine();
                }
                buffer.close();
                fileWrite.close();
            } catch (IOException ex) {
            }

        }
    }

}
