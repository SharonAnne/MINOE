
/*
 * SearchWindow.java
 *
 */

package minoe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.openide.util.Exceptions;

/**
 * Displays a gui search window.  The user enters a search string
 * and a list of files are returned and displayed.
 * 
 * @author Daniel Spiteri
 */
public class SearchWindow extends javax.swing.JInternalFrame {

    private FilterPanel filterPanel;
    private Globals globals;
    private CustomSearchTableModel tableModel = new CustomSearchTableModel();
    Vector<String> columnIdentifiers = new Vector<String>();
    public String currentSearchString = null;

    /** Creates new form searchWindow */
    public SearchWindow(Globals globals) {
        this.globals = globals;
        columnIdentifiers.add("File ID (double-click to view file contents)");
        columnIdentifiers.add("Search Score");
        columnIdentifiers.add("Description");
        tableModel.setColumnIdentifiers(columnIdentifiers);
        initComponents();

        this.resultsTable.setModel(tableModel);
        this.resultsTable.setAutoCreateRowSorter(true);
        this.resultsTable.addMouseListener(new MouseClickHandler());
        this.resultsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        this.resultsTable.setFillsViewportHeight(true);
        JTableHeader header = this.resultsTable.getTableHeader();
        header.setReorderingAllowed(false);
        initColumnWidths();

        this.filterPanel = globals.createFilterPanel();
        this.filterPanel.disableTermDistance();
        this.filterContainerPanel.add(filterPanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        filterContainerPanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle(org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.title")); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/minoe/glass.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(50, 50));
        setPreferredSize(new java.awt.Dimension(500, 600));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.topPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        topPanel.setFont(new java.awt.Font("Arial", 0, 11));
        topPanel.setMaximumSize(new java.awt.Dimension(12000, 70));
        topPanel.setMinimumSize(new java.awt.Dimension(79, 70));
        topPanel.setPreferredSize(new java.awt.Dimension(394, 70));
        topPanel.setLayout(new javax.swing.BoxLayout(topPanel, javax.swing.BoxLayout.X_AXIS));

        searchField.setFont(new java.awt.Font("Arial", 0, 12));
        searchField.setText(org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.searchField.text")); // NOI18N
        searchField.setMaximumSize(new java.awt.Dimension(10000, 25));
        searchField.setMinimumSize(new java.awt.Dimension(250, 22));
        searchField.setPreferredSize(new java.awt.Dimension(200, 22));
        topPanel.add(searchField);

        searchButton.setFont(new java.awt.Font("Arial", 0, 12));
        searchButton.setText(org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.searchButton.text")); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        topPanel.add(searchButton);

        getContentPane().add(topPanel);

        filterContainerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.filterContainerPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 11))); // NOI18N
        filterContainerPanel.setMaximumSize(new java.awt.Dimension(32767, 500));
        filterContainerPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        filterContainerPanel.setPreferredSize(new java.awt.Dimension(500, 400));
        filterContainerPanel.setLayout(new javax.swing.BoxLayout(filterContainerPanel, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(filterContainerPanel);

        bottomPanel.setPreferredSize(new java.awt.Dimension(491, 250));
        bottomPanel.setLayout(new javax.swing.BoxLayout(bottomPanel, javax.swing.BoxLayout.Y_AXIS));

        statusLabel.setFont(new java.awt.Font("Arial", 0, 11));
        statusLabel.setText(org.openide.util.NbBundle.getMessage(SearchWindow.class, "SearchWindow.statusLabel.text")); // NOI18N
        bottomPanel.add(statusLabel);

        scrollPane.setFont(new java.awt.Font("Arial", 0, 11));

        resultsTable.setFont(new java.awt.Font("Arial", 0, 11));
        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Search Score", "Description"
            }
        ));
        resultsTable.setFillsViewportHeight(true);
        scrollPane.setViewportView(resultsTable);

        bottomPanel.add(scrollPane);

        getContentPane().add(bottomPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        search();
    }//GEN-LAST:event_searchButtonActionPerformed

        /**
     * Builds search filter criteria based upon which
     * check boxes are checked.
     * @return criteria Hashtable Associated key/value pairs.
     */
    public SearchCriteria getSearchCriteria(){
        return this.filterPanel.getSelectedItems();
    }

    public void initColumnWidths(){
        TableColumn column = null;
        for (int i = 0; i < resultsTable.getColumnCount(); i++) {
            column = resultsTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
    }

    public void autoFitColumns(){
        // Adjust column widths
        TableColumn column = null;
        for (int j = 0; j < resultsTable.getColumnCount(); j++) {
            column = resultsTable.getColumnModel().getColumn(j);
            JTableHeader header = resultsTable.getTableHeader();

            int rowCount = resultsTable.getRowCount();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int)header.getDefaultRenderer()
                       .getTableCellRendererComponent(resultsTable, column.getIdentifier()
                               , false, false, -1, col).getPreferredSize().getWidth();
               for(int row = 0; row<rowCount; row++){
                   int preferedWidth = (int)resultsTable.getCellRenderer(row, col).getTableCellRendererComponent(resultsTable,
                           resultsTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                   width = Math.max(width, preferedWidth);
               }
               header.setResizingColumn(column); // this line is very important
               column.setWidth(width+resultsTable.getIntercellSpacing().width);

        }
        revalidate();
    }

    public void search(){
        SearchCriteria searchCriteria = getSearchCriteria();
        if(searchCriteria == null || searchCriteria.getSize() == 0){
            JOptionPane.showMessageDialog(this, "Please specify search criteria.", "Choose Search Options", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String searchString = searchField.getText();
        if(searchString == null){
            return;
        }

        this.currentSearchString = searchString;

        SearchFiles searchFiles = this.globals.getSearchFiles();
        try {
            // returns a list of documents and search scores
            Hashtable<String, Float> results = searchFiles.returnResults(searchString, searchCriteria);
            if(results == null){
                statusLabel.setText("0 results.");
                return;
            }

            Vector<Vector> dataModel = new Vector<Vector>();
            if(results.size() > 0){
                for (String s : results.keySet()) {
                    Vector<String> rowData = new Vector<String>();
                    String desc = this.globals.getMetaDataController().getFileTitle(s);
                    float score = results.get(s);
                    rowData.add(s);
                    rowData.add("" + score);
                    rowData.add(desc);
                    dataModel.add(rowData);
                }
                this.tableModel.setDataVector(dataModel, this.columnIdentifiers);
                this.autoFitColumns();
                statusLabel.setText(results.size() + " results (click column title to sort).");
            } else{
                statusLabel.setText(results.size() + " results.");
            }

        } catch (CorruptIndexException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }


    }

    public void setFilterPanel(FilterPanel panel){
        this.filterPanel = panel;
        this.filterContainerPanel.add(panel);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel filterContainerPanel;
    private javax.swing.JTable resultsTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables


    class CustomSearchTableModel extends DefaultTableModel {
        public CustomSearchTableModel(){
            super();
        }
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
    
    class MouseClickHandler extends MouseAdapter{
        public MouseClickHandler(){
            
        }
        @Override
        public void mouseClicked(MouseEvent e) {

            // Gather table information on double click or alt click.
            if (e.getClickCount() == 2){
                
                int row = resultsTable.rowAtPoint(e.getPoint());
                int col = resultsTable.columnAtPoint(e.getPoint());

                if (col > 0){
                   return;
                }

                Object objCellContent;
                try{
                   objCellContent = resultsTable.getValueAt(row,col);
                } catch(Exception exception){
                    javax.swing.JOptionPane.showMessageDialog(null, exception.toString());
                   return;
                }

                String fileID = objCellContent.toString();
                String fileContents = null;
                try {
                    SearchFiles searchFiles = globals.getSearchFiles();
                    String baseLocation = globals.getBaseLocation();
                    String path = baseLocation + searchFiles.getPath(fileID);
                    fileContents = globals.openFile(path);
                    // Display contents on double-click
                    FileContentsWindow fileContentsWindow = new FileContentsWindow(fileID, fileContents, currentSearchString);
                    globals.parentOwner.add(fileContentsWindow);
                    fileContentsWindow.setFocusable(true);
                    try {
                        fileContentsWindow.setSelected(true);
                    } catch (PropertyVetoException ex) {
                    }
                } catch (IOException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Cannot open file: " + fileID);
                }
            }
        }//end mouse clicked
    }// end MouseClickHandler class
    
}// end SearchWindow class
