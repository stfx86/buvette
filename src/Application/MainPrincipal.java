package Application;

import java.awt.Color;
import javax.swing.JLabel;
import ApiBDD.Parameter;
import ApiBDD.ResultSetTableModel;
import ApiBDD.db_connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainPrincipal extends javax.swing.JFrame {

    /*
     ***************************************************************************************
     * ResulSet
     * *************************************************************************************
     */
    ResultSet rs_produit;
    ResultSet rs_user;
    ResultSet rs_cahier;
    ResultSet rs_mouvement;
    ResultSet rs_be;
    ResultSet rs_be_2;
    ResultSet rs_bs;
    ResultSet rs_bs_2;

    /*
     ****************************************************************************************
     * Variable globale
     * **************************************************************************************
     */
    db_connection db;
    int old, dec, now;

    /**
     * Creates new form
     */
    public MainPrincipal() {
        db = new db_connection(new Parameter().HOST_DB, new Parameter().USERNAME_DB, new Parameter().PASSWORD_DB, new Parameter().IPHOST, new Parameter().PORT);

        initComponents();
        tableProduit();
        tableUser();
        tableCahier();
        jam();
        tableMouvement();
        tableBe();
        tableBe_2();
        tableBs();
        tableBs_2();
    }

    /*
     *****************************************************************************************
     * Table et actualiser Produits
     * ***************************************************************************************
     */
    public void tableProduit() {
        String p[] = {"id", "code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
        rs_produit = db.querySelect(p, "produit");
        tbl_prod.setModel(new ResultSetTableModel(rs_produit));
    }

    void actualiserProduit() {
        txtidprod.setText("");
        txtrefprod.setText("");
        txtdesprod.setText("");
        txtranprod.setText("");
        txtfouprod.setText("");
        txtremprod.setText("");
        txtpriprod.setText("");
        txtstoprod.setText("");
    }

    /*
     *****************************************************************************************
     * Table et actualiser User
     * ***************************************************************************************
     */
    public void tableUser() {
        String u[] = {"id", "id_user", "username", "password", "type"};
        rs_user = db.querySelect(u, "utilisateur");
        tbl_user.setModel(new ResultSetTableModel(rs_user));
    }

    void actualiserUser() {
        txtid_user.setText("");
        txtuser.setText("");
        txtpauser.setText("");
        comtyuser.setSelectedItem("Type");
    }

    /*
     *****************************************************************************************
     * Table, importer, jam Cahier
     * ***************************************************************************************
     */
    public void tableCahier() {
        String c[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
        rs_cahier = db.querySelect(c, "produit");
        tbl_res.setModel(new ResultSetTableModel(rs_cahier));
    }

    public void importer() {
        String f[] = {"num_facture", "code_produit", "reference", "prix_vente", "stock_sortie", "subtotal"};
        rs_cahier = db.fcSelectCommand(f, "vente", "num_facture='" + txtfac.getText() + "'");
        tbl_ven.setModel(new ResultSetTableModel(rs_cahier));
    }

    public void jam() {
        Date s = new Date();
        SimpleDateFormat tgl = new SimpleDateFormat("EEEE-dd-MMM-yyyy");
        SimpleDateFormat jam = new SimpleDateFormat("HH:mm");
        lbl1.setText(jam.format(s));
        lbl2.setText(tgl.format(s));
    }

    /*
     *****************************************************************************************
     * Table, actualiser mouvement
     * ***************************************************************************************
     */
    public void tableMouvement() {
        rs_mouvement = db.manySelect();
        tbl_mouvement.setModel(new ResultSetTableModel(rs_mouvement));
    }

    /*
     *****************************************************************************************
     * Table et actualiser BE
     * ***************************************************************************************
     */
    public void tableBe_2() {
        rs_be_2 = db.queryBeSelect();
        tbl_be_2.setModel(new ResultSetTableModel(rs_be_2));
    }

    public void tableBe() {
        String be[] = {"id", "produit_id", "num_be", "date_entree"};
        rs_be = db.querySelect(be, "bon_entree");
        tbl_be.setModel(new ResultSetTableModel(rs_be));
    }

    void actualiserBe() {
        txtnumbe.setText("");
        txtcodeproduitbe.setText("");
        txtqteentree.setText("");
        txtdateentree.setText("");
    }

    /*
     *****************************************************************************************
     * Table et actualiser BS
     * ***************************************************************************************
     */
    public void tableBs_2() {
        rs_bs_2 = db.queryBsSelect();
        tbl_bs_2.setModel(new ResultSetTableModel(rs_bs_2));
    }

    public void tableBs() {
        String bs[] = {"id", "produit_bsid", "vente_id", "num_bs", "date_sortie"};
        rs_bs = db.querySelect(bs, "bon_sortie");
        tbl_bs.setModel(new ResultSetTableModel(rs_bs));
    }

    public void actualiserBs() {
        txtnumbs.setText("");
        txtcodeproduitbs.setText("");
        txtqtesortie.setText("");
        txtdatesortie.setText("");
    }

    /*
     *****************************************************************************************
     * ON NE PEUT PAS MODIFIER LE CODE SUIVANT
     *****************************************************************************************
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jInternalFrameProduit1 = new javax.swing.JInternalFrame();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtdesprod = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtranprod = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtidprod = new javax.swing.JTextField();
        txtrefprod = new javax.swing.JTextField();
        txtremprod = new javax.swing.JTextField();
        txtpriprod = new javax.swing.JTextField();
        txtstoprod = new javax.swing.JTextField();
        txtfouprod = new javax.swing.JTextField();
        jButtonAjouterProduit = new javax.swing.JButton();
        jButtonModifierProduit = new javax.swing.JButton();
        jButtonSupprimerProduit = new javax.swing.JButton();
        jButtonActualiserProduit = new javax.swing.JButton();
        jScrollPaneProduit1 = new javax.swing.JScrollPane();
        tbl_prod = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButtonRechercherProduit = new javax.swing.JButton();
        comrechprod = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        txtrechprod = new javax.swing.JTextField();
        jInternalFrameMouvement2 = new javax.swing.JInternalFrame();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_mouvement = new javax.swing.JTable();
        jButtonActualiserMouvement = new javax.swing.JButton();
        jInternalFrameCahier4 = new javax.swing.JInternalFrame();
        jPanel20 = new javax.swing.JPanel();
        jScrollPaneCahier4 = new javax.swing.JScrollPane();
        tbl_res = new javax.swing.JTable();
        jButtonActualiserCahier = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        comrechcahier = new javax.swing.JComboBox();
        txtrechcahier = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtcodcahier = new javax.swing.JTextField();
        txtrefcahier = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtrancahier = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtfoucahier = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtpricahier = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtremcahier = new javax.swing.JTextField();
        txtnoucahier = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtstocahier = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jButtonAjouterVenteCahier = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        lbltot1 = new javax.swing.JLabel();
        jButtonRechercheCategCahier = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtfac = new javax.swing.JTextField();
        jButtonRechercheFactCahier = new javax.swing.JButton();
        jButtonSupprimerFactCahier = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_ven = new javax.swing.JTable();
        lbltot2 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtpaycahier = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtcascahier = new javax.swing.JTextField();
        jButtonImprimerCahier = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jButtonAnnulerCahier = new javax.swing.JButton();
        jInternalFrameUser5 = new javax.swing.JInternalFrame();
        jPanel22 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtid_user = new javax.swing.JTextField();
        txtuser = new javax.swing.JTextField();
        txtpauser = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        comtyuser = new javax.swing.JComboBox();
        jButtonAjouterUser = new javax.swing.JButton();
        jButtonActualiserUser = new javax.swing.JButton();
        jButtonModifierUser = new javax.swing.JButton();
        jButtonSupprimerUser = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        txtiduser = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jButtonRechercherUser = new javax.swing.JButton();
        comrechuser = new javax.swing.JComboBox();
        jLabel39 = new javax.swing.JLabel();
        txtrechuser = new javax.swing.JTextField();
        jScrollPaneUser5 = new javax.swing.JScrollPane();
        tbl_user = new javax.swing.JTable();
        jInternalFrameBE6 = new javax.swing.JInternalFrame();
        jPanel23 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtnumbe = new javax.swing.JTextField();
        txtcodeproduitbe = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jButtonAjouterBe = new javax.swing.JButton();
        jButtonActualiserBe = new javax.swing.JButton();
        jButtonModifierBe = new javax.swing.JButton();
        jButtonSupprimerBe = new javax.swing.JButton();
        txtqteentree = new javax.swing.JTextField();
        txtdateentree = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jButtonRechercherBe = new javax.swing.JButton();
        comrechbe = new javax.swing.JComboBox();
        jLabel58 = new javax.swing.JLabel();
        txtrechbe = new javax.swing.JTextField();
        jScrollPaneUser6 = new javax.swing.JScrollPane();
        tbl_be = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_be_2 = new javax.swing.JTable();
        jButtonActualiserBe_2 = new javax.swing.JButton();
        jInternalFrameBS7 = new javax.swing.JInternalFrame();
        jPanel24 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtnumbs = new javax.swing.JTextField();
        txtcodeproduitbs = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jButtonAjouterBs = new javax.swing.JButton();
        jButtonActualiserBs = new javax.swing.JButton();
        jButtonModifierBs = new javax.swing.JButton();
        jButtonSupprimerBs = new javax.swing.JButton();
        txtqtesortie = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtdatesortie = new javax.swing.JTextField();
        txtventeid_bs = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButtonRechercherBs = new javax.swing.JButton();
        comrechbs = new javax.swing.JComboBox();
        jLabel61 = new javax.swing.JLabel();
        txtrechbs = new javax.swing.JTextField();
        jScrollPaneUser7 = new javax.swing.JScrollPane();
        tbl_bs = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_bs_2 = new javax.swing.JTable();
        jButtonActualiserBs_2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        JPanelProduit1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        barP = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        JPanelMouvement2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        barM = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanelCahier4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        barC = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanelUser5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        barU = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanelBE6 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        barBE = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanelBS7 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        barBS = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 740));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 840));
        jPanel1.setPreferredSize(new java.awt.Dimension(1210, 820));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setBackground(new java.awt.Color(192, 27, 54));
        jLabel48.setOpaque(true);
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1010, 10));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("G e s t i o n  d e  s t o c k");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 530, -1));

        jLabel49.setBackground(new java.awt.Color(192, 27, 54));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setOpaque(true);
        jPanel8.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 650, 70));

        jLabel19.setBackground(new java.awt.Color(240, 240, 240));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ////
        ///
//        URL imageUrl = getClass().getResource("/images/window-close.png");
//        if (imageUrl != null) {
//            jLabel19.setIcon(new ImageIcon(imageUrl));
//        } else {
//            System.err.println("Image not found!!!!!!");
//        }

//        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/window-close.png"))); // NOI18N
//        jLabel19.setIcon(new javax.swing.ImageIcon(new ImageIcon("/home/stof/NetBeansProjects/buvette/src/images/window-close.png"))); // NOI18N
        ///////

        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel8.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 30, 40));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, -10, 1010, 90));

        jDesktopPane2.setBackground(new java.awt.Color(51, 51, 51));
        jDesktopPane2.setPreferredSize(new java.awt.Dimension(1020, 780));
        jDesktopPane2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameProduit1.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameProduit1.setBorder(null);
        jInternalFrameProduit1.setMaximizable(true);
        jInternalFrameProduit1.setAutoscrolls(true);
        jInternalFrameProduit1.setVisible(true);
        jInternalFrameProduit1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(850, 500));
        jPanel3.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("code produit  :");
        jPanel3.add(jLabel20);
        jLabel20.setBounds(10, 30, 110, 27);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("fournisseur  :");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(330, 30, 100, 20);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("remise   %   :");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(330, 90, 110, 27);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("reference       :");
        jPanel3.add(jLabel23);
        jLabel23.setBounds(10, 90, 110, 27);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("deseignation :");
        jPanel3.add(jLabel24);
        jLabel24.setBounds(10, 150, 120, 27);
        jPanel3.add(txtdesprod);
        txtdesprod.setBounds(120, 150, 170, 30);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("prix               :");
        jPanel3.add(jLabel25);
        jLabel25.setBounds(330, 150, 110, 27);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setText("stock            :");
        jPanel3.add(jLabel26);
        jLabel26.setBounds(330, 210, 110, 27);
        jPanel3.add(txtranprod);
        txtranprod.setBounds(120, 210, 170, 30);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("rangement    :");
        jPanel3.add(jLabel27);
        jLabel27.setBounds(10, 220, 100, 27);
        jPanel3.add(txtidprod);
        txtidprod.setBounds(120, 30, 170, 30);
        jPanel3.add(txtrefprod);
        txtrefprod.setBounds(120, 90, 170, 30);
        jPanel3.add(txtremprod);
        txtremprod.setBounds(440, 90, 170, 30);
        jPanel3.add(txtpriprod);
        txtpriprod.setBounds(440, 150, 170, 30);
        jPanel3.add(txtstoprod);
        txtstoprod.setBounds(440, 210, 170, 30);
        jPanel3.add(txtfouprod);
        txtfouprod.setBounds(440, 30, 170, 30);

        jButtonAjouterProduit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAjouterProduit.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAjouterProduit.setText("ajouter");
        jButtonAjouterProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterProduitActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonAjouterProduit);
        jButtonAjouterProduit.setBounds(40, 300, 97, 38);

        jButtonModifierProduit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonModifierProduit.setForeground(new java.awt.Color(51, 51, 51));
        jButtonModifierProduit.setText("modifier");
        jButtonModifierProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifierProduitActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonModifierProduit);
        jButtonModifierProduit.setBounds(190, 300, 95, 29);

        jButtonSupprimerProduit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonSupprimerProduit.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupprimerProduit.setText("supprimer");
        jButtonSupprimerProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerProduitActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonSupprimerProduit);
        jButtonSupprimerProduit.setBounds(320, 300, 110, 29);

        jButtonActualiserProduit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserProduit.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserProduit.setText("actualiser");
        jButtonActualiserProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserProduitActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonActualiserProduit);
        jButtonActualiserProduit.setBounds(480, 300, 104, 29);

        jPanel9.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 620, 380));

        tbl_prod.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null}
                },
                new String[]{
                    "id", "code_produit", "reference", "designation", "rangement", "fournisseur", "remise", "prix", "stock "
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_prod.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_prod.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_prod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_prodMouseClicked(evt);
            }
        });
        jScrollPaneProduit1.setViewportView(tbl_prod);

        jPanel9.add(jScrollPaneProduit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 980, 210));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        jButtonRechercherProduit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonRechercherProduit.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercherProduit.setText("recherche ");
        jButtonRechercherProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercherProduitActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonRechercherProduit);
        jButtonRechercherProduit.setBounds(0, 140, 111, 40);

        comrechprod.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comrechprod.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"id", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"}));
        comrechprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comrechprodActionPerformed(evt);
            }
        });
        jPanel4.add(comrechprod);
        comrechprod.setBounds(40, 70, 240, 40);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("Recherche par catégorie :");
        jPanel4.add(jLabel28);
        jLabel28.setBounds(40, 30, 250, 30);
        jPanel4.add(txtrechprod);
        txtrechprod.setBounds(140, 140, 180, 40);

        jPanel9.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 310, 360, 260));

        jInternalFrameProduit1.getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1040, 840));
        jPanel9.getAccessibleContext().setAccessibleDescription("");

        jDesktopPane2.add(jInternalFrameProduit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -50, 1070, 790));

        jInternalFrameMouvement2.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameMouvement2.setBorder(null);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameMouvement2.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setMinimumSize(new java.awt.Dimension(700, 770));
        jPanel16.setPreferredSize(new java.awt.Dimension(700, 770));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_mouvement.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "id", "num_be", "date_entree"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_mouvement.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_mouvement.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_mouvement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_mouvementMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_mouvement);

        jPanel16.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 940, 430));

        jButtonActualiserMouvement.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserMouvement.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserMouvement.setText("actualiser");
        jButtonActualiserMouvement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserMouvementActionPerformed(evt);
            }
        });
        jPanel16.add(jButtonActualiserMouvement, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, 140, -1));

        jInternalFrameMouvement2.getContentPane().add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 1040, 740));

        jDesktopPane2.add(jInternalFrameMouvement2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -40, 1070, 780));

        jInternalFrameCahier4.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameCahier4.setBorder(null);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameCahier4.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setMinimumSize(new java.awt.Dimension(700, 770));
        jPanel20.setPreferredSize(new java.awt.Dimension(700, 770));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_res.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null}
                },
                new String[]{
                    "code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock "
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_res.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_res.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_res.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_resMouseClicked(evt);
            }
        });
        jScrollPaneCahier4.setViewportView(tbl_res);

        jPanel20.add(jScrollPaneCahier4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 990, 80));

        jButtonActualiserCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserCahier.setText("actualiser");
        jButtonActualiserCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserCahierActionPerformed(evt);
            }
        });
        jPanel20.add(jButtonActualiserCahier, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 180, 20));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(null);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setText("recherche par catégorie :");
        jPanel7.add(jLabel32);
        jLabel32.setBounds(50, 0, 250, 20);

        comrechcahier.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"}));
        jPanel7.add(comrechcahier);
        comrechcahier.setBounds(50, 30, 240, 30);
        jPanel7.add(txtrechcahier);
        txtrechcahier.setBounds(150, 70, 180, 30);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setText("code_produit :");
        jPanel7.add(jLabel33);
        jLabel33.setBounds(30, 130, 100, 20);

        txtcodcahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodcahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtcodcahier);
        txtcodcahier.setBounds(150, 120, 160, 30);

        txtrefcahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrefcahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtrefcahier);
        txtrefcahier.setBounds(150, 160, 160, 30);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("reference     :");
        jPanel7.add(jLabel34);
        jLabel34.setBounds(30, 170, 100, 20);

        txtrancahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrancahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtrancahier);
        txtrancahier.setBounds(150, 200, 160, 30);

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("Prix        :");
        jPanel7.add(jLabel35);
        jLabel35.setBounds(30, 290, 100, 20);

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("rangement :");
        jPanel7.add(jLabel36);
        jLabel36.setBounds(30, 210, 100, 20);

        txtfoucahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfoucahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtfoucahier);
        txtfoucahier.setBounds(150, 240, 160, 30);

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("fournisseur :");
        jPanel7.add(jLabel40);
        jLabel40.setBounds(30, 250, 100, 20);

        txtpricahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpricahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtpricahier);
        txtpricahier.setBounds(150, 280, 160, 30);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("remise     :");
        jPanel7.add(jLabel41);
        jLabel41.setBounds(30, 340, 100, 20);

        txtremcahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtremcahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtremcahier);
        txtremcahier.setBounds(150, 330, 160, 30);

        txtnoucahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnoucahierActionPerformed(evt);
            }
        });
        jPanel7.add(txtnoucahier);
        txtnoucahier.setBounds(150, 370, 160, 30);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 102));
        jLabel42.setText("nouveau Prix  :");
        jPanel7.add(jLabel42);
        jLabel42.setBounds(30, 380, 100, 20);

        txtstocahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstocahierActionPerformed(evt);
            }
        });
        txtstocahier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtstocahierKeyReleased(evt);
            }
        });
        jPanel7.add(txtstocahier);
        txtstocahier.setBounds(150, 410, 160, 30);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText("Stock sortire  :");
        jPanel7.add(jLabel43);
        jLabel43.setBounds(30, 420, 100, 20);

        jButtonAjouterVenteCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAjouterVenteCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAjouterVenteCahier.setText("Ajouter au vente");
        jButtonAjouterVenteCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterVenteCahierActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonAjouterVenteCahier);
        jButtonAjouterVenteCahier.setBounds(40, 490, 260, 40);

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("RP : ");
        jPanel7.add(jLabel44);
        jLabel44.setBounds(60, 450, 50, 30);

        lbltot1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbltot1.setForeground(new java.awt.Color(102, 102, 102));
        lbltot1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltot1.setText("0");
        jPanel7.add(lbltot1);
        lbltot1.setBounds(110, 450, 170, 30);

        jButtonRechercheCategCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonRechercheCategCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercheCategCahier.setText("recherche ");
        jButtonRechercheCategCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercheCategCahierActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonRechercheCategCahier);
        jButtonRechercheCategCahier.setBounds(10, 70, 111, 30);

        jPanel20.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 360, 530));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(null);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("Num de facture :");
        jPanel12.add(jLabel45);
        jLabel45.setBounds(20, 130, 120, 20);

        txtfac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfacActionPerformed(evt);
            }
        });
        jPanel12.add(txtfac);
        txtfac.setBounds(140, 120, 160, 30);

        jButtonRechercheFactCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonRechercheFactCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercheFactCahier.setText("recherche ");
        jButtonRechercheFactCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercheFactCahierActionPerformed(evt);
            }
        });
        jPanel12.add(jButtonRechercheFactCahier);
        jButtonRechercheFactCahier.setBounds(310, 120, 120, 29);

        jButtonSupprimerFactCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonSupprimerFactCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupprimerFactCahier.setText("Supprimer");
        jButtonSupprimerFactCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerFactCahierActionPerformed(evt);
            }
        });
        jPanel12.add(jButtonSupprimerFactCahier);
        jButtonSupprimerFactCahier.setBounds(440, 120, 110, 29);

        tbl_ven.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "num_facture", "code_produit", "reference", "Prix de vente", "Stock sortire", "total "
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbl_ven);

        jPanel12.add(jScrollPane4);
        jScrollPane4.setBounds(10, 170, 620, 120);

        lbltot2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbltot2.setForeground(new java.awt.Color(102, 102, 102));
        lbltot2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltot2.setText("0");
        jPanel12.add(lbltot2);
        lbltot2.setBounds(380, 310, 190, 30);

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("payé aprés :");
        jPanel12.add(jLabel46);
        jLabel46.setBounds(290, 380, 90, 30);

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Rp");
        jPanel12.add(jLabel47);
        jLabel47.setBounds(300, 310, 70, 30);
        jPanel12.add(txtpaycahier);
        txtpaycahier.setBounds(400, 380, 130, 30);

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 102, 102));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("cash :");
        jPanel12.add(jLabel53);
        jLabel53.setBounds(80, 380, 50, 30);

        txtcascahier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcascahierKeyReleased(evt);
            }
        });
        jPanel12.add(txtcascahier);
        txtcascahier.setBounds(140, 380, 130, 30);

        jButtonImprimerCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonImprimerCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonImprimerCahier.setText("valider");
        jButtonImprimerCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImprimerCahierActionPerformed(evt);
            }
        });
        jPanel12.add(jButtonImprimerCahier);
        jButtonImprimerCahier.setBounds(140, 440, 350, 30);

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(102, 102, 102));
        jLabel54.setText("Vente");
        jLabel54.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        lbl1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl1.setForeground(new java.awt.Color(102, 102, 102));
        lbl1.setText("jLabel12");

        lbl2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl2.setForeground(new java.awt.Color(102, 102, 102));
        lbl2.setText("jLabel17");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(264, 264, 264)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50))
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(jPanel13Layout.createSequentialGroup()
                                                .addComponent(lbl2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lbl1)
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel12.add(jPanel13);
        jPanel13.setBounds(0, 0, 630, 70);

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Total ");
        jPanel12.add(jLabel55);
        jLabel55.setBounds(160, 310, 110, 30);

        jButtonAnnulerCahier.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAnnulerCahier.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAnnulerCahier.setText("annuler");
        jButtonAnnulerCahier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerCahierActionPerformed(evt);
            }
        });
        jPanel12.add(jButtonAnnulerCahier);
        jButtonAnnulerCahier.setBounds(140, 480, 350, 30);

        jPanel20.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 640, 520));

        jInternalFrameCahier4.getContentPane().add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, 1040, 740));

        jDesktopPane2.add(jInternalFrameCahier4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -40, 1070, 780));

        jInternalFrameUser5.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameUser5.setBorder(null);
        jInternalFrameUser5.setVisible(true);
        jInternalFrameUser5.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setMinimumSize(new java.awt.Dimension(700, 770));
        jPanel22.setPreferredSize(new java.awt.Dimension(700, 770));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("password    :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("username    :");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(102, 102, 102));
        jLabel37.setText("id_user        :");

        jLabel38.setBackground(new java.awt.Color(224, 234, 236));
        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("type           :");

        comtyuser.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Type ", "directeur", "Cashier"}));
        comtyuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comtyuserActionPerformed(evt);
            }
        });

        jButtonAjouterUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAjouterUser.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAjouterUser.setText("ajouter");
        jButtonAjouterUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterUserActionPerformed(evt);
            }
        });

        jButtonActualiserUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserUser.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserUser.setText("actualiser");
        jButtonActualiserUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserUserActionPerformed(evt);
            }
        });

        jButtonModifierUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonModifierUser.setForeground(new java.awt.Color(51, 51, 51));
        jButtonModifierUser.setText("modifier");
        jButtonModifierUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifierUserActionPerformed(evt);
            }
        });

        jButtonSupprimerUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonSupprimerUser.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupprimerUser.setText("supprimer");
        jButtonSupprimerUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerUserActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("id                 :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel52)
                                                .addGap(26, 26, 26)
                                                .addComponent(txtiduser))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jButtonAjouterUser, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27)
                                                .addComponent(jButtonModifierUser)
                                                .addGap(28, 28, 28)
                                                .addComponent(jButtonSupprimerUser)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButtonActualiserUser))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel37)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtuser)
                                                        .addComponent(txtid_user)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, Short.MAX_VALUE)
                                                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(24, 24, 24)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtpauser)
                                                        .addComponent(comtyuser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(64, 64, 64))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtiduser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtid_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtpauser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comtyuser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonActualiserUser)
                                        .addComponent(jButtonSupprimerUser)
                                        .addComponent(jButtonModifierUser)
                                        .addComponent(jButtonAjouterUser))
                                .addGap(53, 53, 53))
        );

        jPanel22.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 560, 420));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRechercherUser.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonRechercherUser.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercherUser.setText("recherche ");
        jButtonRechercherUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercherUserActionPerformed(evt);
            }
        });

        comrechuser.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"id", "id_user", "username", "password", "type"}));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("Recherche par catégorie :");

        txtrechuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrechuserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(comrechuser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jButtonRechercherUser, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtrechuser, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comrechuser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtrechuser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonRechercherUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(157, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 370, -1));

        tbl_user.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "ID", "id_user", "username", "password", "Type"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_user.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_user.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_userMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_userMouseEntered(evt);
            }
        });
        jScrollPaneUser5.setViewportView(tbl_user);

        jPanel22.add(jScrollPaneUser5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 990, 170));

        jInternalFrameUser5.getContentPane().add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -7, 1040, 760));

        jDesktopPane2.add(jInternalFrameUser5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -40, 1070, 780));

        jInternalFrameBE6.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameBE6.setBorder(null);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBE6.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setMinimumSize(new java.awt.Dimension(700, 770));
        jPanel23.setPreferredSize(new java.awt.Dimension(700, 770));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("produit id            :");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("id                         :");

        jLabel57.setBackground(new java.awt.Color(224, 234, 236));
        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText("num bon entree  :");

        jButtonAjouterBe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAjouterBe.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAjouterBe.setText("ajouter");
        jButtonAjouterBe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterBeActionPerformed(evt);
            }
        });

        jButtonActualiserBe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserBe.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserBe.setText("actualiser");
        jButtonActualiserBe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserBeActionPerformed(evt);
            }
        });

        jButtonModifierBe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonModifierBe.setForeground(new java.awt.Color(51, 51, 51));
        jButtonModifierBe.setText("modifier");
        jButtonModifierBe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifierBeActionPerformed(evt);
            }
        });

        jButtonSupprimerBe.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonSupprimerBe.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupprimerBe.setText("supprimer");
        jButtonSupprimerBe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerBeActionPerformed(evt);
            }
        });

        jLabel63.setBackground(new java.awt.Color(224, 234, 236));
        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(102, 102, 102));
        jLabel63.setText("date entree           :");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel63)
                                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(txtqteentree, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtnumbe, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtcodeproduitbe, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtdateentree, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
                                        .addGroup(jPanel14Layout.createSequentialGroup()
                                                .addComponent(jButtonAjouterBe, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonModifierBe)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonSupprimerBe)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButtonActualiserBe)))
                                .addGap(188, 188, 188))
        );
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtnumbe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtcodeproduitbe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtqteentree, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtdateentree, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonAjouterBe)
                                        .addComponent(jButtonModifierBe)
                                        .addComponent(jButtonSupprimerBe)
                                        .addComponent(jButtonActualiserBe))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 440, 310));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRechercherBe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonRechercherBe.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercherBe.setText("recherche ");
        jButtonRechercherBe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercherBeActionPerformed(evt);
            }
        });

        comrechbe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"id", "num_be", "produit_id", "date_entree"}));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText("Recherche par catégorie :");

        txtrechbe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrechbeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comrechbe, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jButtonRechercherBe, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtrechbe, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(comrechbe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtrechbe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButtonRechercherBe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel23.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 850, 50));

        tbl_be.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "ID", "id_user", "username", "password", "Type"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_be.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_be.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_be.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_beMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_beMouseEntered(evt);
            }
        });
        jScrollPaneUser6.setViewportView(tbl_be);

        jPanel23.add(jScrollPaneUser6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 540, 330));

        tbl_be_2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tbl_be_2.setSelectionBackground(new java.awt.Color(192, 27, 54));
        jScrollPane2.setViewportView(tbl_be_2);

        jPanel23.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 970, 160));

        jButtonActualiserBe_2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserBe_2.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserBe_2.setText("actualiser");
        jButtonActualiserBe_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserBe_2ActionPerformed(evt);
            }
        });
        jPanel23.add(jButtonActualiserBe_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 440, -1, -1));

        jInternalFrameBE6.getContentPane().add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -7, 1040, 760));

        jDesktopPane2.add(jInternalFrameBE6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -40, 1070, 780));

        jInternalFrameBS7.setBackground(new java.awt.Color(240, 240, 240));
        jInternalFrameBS7.setBorder(null);
        jInternalFrameBS7.setVisible(false);
        jInternalFrameBS7.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setMinimumSize(new java.awt.Dimension(700, 770));
        jPanel24.setPreferredSize(new java.awt.Dimension(700, 770));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setText("produit_id          :");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("id                       :");

        jLabel60.setBackground(new java.awt.Color(224, 234, 236));
        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText("num bon sortie  :");

        jButtonAjouterBs.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonAjouterBs.setForeground(new java.awt.Color(51, 51, 51));
        jButtonAjouterBs.setText("ajouter");
        jButtonAjouterBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjouterBsActionPerformed(evt);
            }
        });

        jButtonActualiserBs.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserBs.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserBs.setText("actualiser");
        jButtonActualiserBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserBsActionPerformed(evt);
            }
        });

        jButtonModifierBs.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonModifierBs.setForeground(new java.awt.Color(51, 51, 51));
        jButtonModifierBs.setText("modifier");
        jButtonModifierBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifierBsActionPerformed(evt);
            }
        });

        jButtonSupprimerBs.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonSupprimerBs.setForeground(new java.awt.Color(51, 51, 51));
        jButtonSupprimerBs.setText("supprimer");
        jButtonSupprimerBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupprimerBsActionPerformed(evt);
            }
        });

        jLabel66.setBackground(new java.awt.Color(224, 234, 236));
        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(102, 102, 102));
        jLabel66.setText("date sortie         :");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("vente_id             :");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel30)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel31))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtnumbs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                        .addComponent(txtcodeproduitbs, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtqtesortie, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtdatesortie))
                                .addGap(107, 107, 107))
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel18Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButtonAjouterBs, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonModifierBs, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonSupprimerBs)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonActualiserBs))
                                        .addGroup(jPanel18Layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(jLabel50)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtventeid_bs, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtnumbs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtcodeproduitbs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtventeid_bs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtqtesortie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtdatesortie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonAjouterBs)
                                        .addComponent(jButtonModifierBs)
                                        .addComponent(jButtonSupprimerBs)
                                        .addComponent(jButtonActualiserBs))
                                .addGap(34, 34, 34))
        );

        jPanel24.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 460, 380));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jButtonRechercherBs.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonRechercherBs.setForeground(new java.awt.Color(51, 51, 51));
        jButtonRechercherBs.setText("recherche ");
        jButtonRechercherBs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRechercherBsActionPerformed(evt);
            }
        });

        comrechbs.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"id", "num_bs", "produit_bsid", "vente_id", "date_sortie"}));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(102, 102, 102));
        jLabel61.setText("Recherche par catégorie :");

        txtrechbs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrechbsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel61)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comrechbs, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jButtonRechercherBs, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtrechbs, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
                jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButtonRechercherBs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtrechbs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(comrechbs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 900, 50));

        tbl_bs.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "ID", "id_user", "username", "password", "Type"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        tbl_bs.setSelectionBackground(new java.awt.Color(192, 27, 54));
        tbl_bs.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbl_bs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bsMouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_bsMouseEntered(evt);
            }
        });
        jScrollPaneUser7.setViewportView(tbl_bs);

        jPanel24.add(jScrollPaneUser7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 510, 310));

        tbl_bs_2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        tbl_bs_2.setSelectionBackground(new java.awt.Color(192, 27, 54));
        jScrollPane3.setViewportView(tbl_bs_2);

        jPanel24.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 970, 150));

        jButtonActualiserBs_2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButtonActualiserBs_2.setForeground(new java.awt.Color(51, 51, 51));
        jButtonActualiserBs_2.setText("actualiser");
        jButtonActualiserBs_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualiserBs_2ActionPerformed(evt);
            }
        });
        jPanel24.add(jButtonActualiserBs_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 460, -1, -1));

        jInternalFrameBS7.getContentPane().add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -7, 1040, 760));

        jDesktopPane2.add(jInternalFrameBS7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -40, 1070, 780));

        jPanel1.add(jDesktopPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 102, 1070, 650));

        jPanel2.setBackground(new java.awt.Color(42, 39, 41));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 740));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(42, 39, 41));
        jPanel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel15MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel15MouseExited(evt);
            }
        });
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(192, 27, 54));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/User-Administrator-Red-icon.png"))); // NOI18N
        jPanel15.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 200, 90));

        JPanelProduit1.setBackground(new java.awt.Color(42, 39, 41));
        JPanelProduit1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JPanelProduit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPanelProduit1MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPanelProduit1MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPanelProduit1MouseExited(evt);
            }
        });
        JPanelProduit1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sale-icon.png"))); // NOI18N
        jLabel3.setFocusTraversalPolicyProvider(true);
        JPanelProduit1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barP.setBackground(new java.awt.Color(192, 27, 54));
        barP.setOpaque(true);
        barP.setRequestFocusEnabled(false);
        JPanelProduit1.add(barP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Produit");
        JPanelProduit1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(JPanelProduit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 200, 50));

        JPanelMouvement2.setBackground(new java.awt.Color(42, 39, 41));
        JPanelMouvement2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JPanelMouvement2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPanelMouvement2MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JPanelMouvement2MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JPanelMouvement2MouseExited(evt);
            }
        });
        JPanelMouvement2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calendar-icon.png"))); // NOI18N
        jLabel6.setFocusTraversalPolicyProvider(true);
        JPanelMouvement2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barM.setBackground(new java.awt.Color(192, 27, 54));
        barM.setRequestFocusEnabled(false);
        JPanelMouvement2.add(barM, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Mouvement");
        JPanelMouvement2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(JPanelMouvement2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 200, 50));

        jPanelCahier4.setBackground(new java.awt.Color(42, 39, 41));
        jPanelCahier4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelCahier4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelCahier4MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelCahier4MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelCahier4MouseExited(evt);
            }
        });
        jPanelCahier4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Notebook-Bookmark-icon.png"))); // NOI18N
        jLabel11.setFocusTraversalPolicyProvider(true);
        jPanelCahier4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barC.setBackground(new java.awt.Color(192, 27, 54));
        barC.setRequestFocusEnabled(false);
        jPanelCahier4.add(barC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Cachier");
        jPanelCahier4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(jPanelCahier4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 200, 50));

        jPanelUser5.setBackground(new java.awt.Color(42, 39, 41));
        jPanelUser5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelUser5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelUser5MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelUser5MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelUser5MouseExited(evt);
            }
        });
        jPanelUser5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Male-user-add-icon.png"))); // NOI18N
        jLabel14.setFocusTraversalPolicyProvider(true);
        jPanelUser5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barU.setBackground(new java.awt.Color(192, 27, 54));
        barU.setRequestFocusEnabled(false);
        jPanelUser5.add(barU, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setText("Utilisateur");
        jPanelUser5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(jPanelUser5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 200, 50));

        jPanelBE6.setBackground(new java.awt.Color(42, 39, 41));
        jPanelBE6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelBE6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelBE6MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelBE6MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelBE6MouseExited(evt);
            }
        });
        jPanelBE6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Ticket-add-icon.png"))); // NOI18N
        jLabel62.setFocusTraversalPolicyProvider(true);
        jPanelBE6.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barBE.setBackground(new java.awt.Color(192, 27, 54));
        barBE.setRequestFocusEnabled(false);
        jPanelBE6.add(barBE, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(204, 204, 204));
        jLabel64.setText("Bon d'entrée");
        jPanelBE6.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(jPanelBE6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 200, 50));

        jPanelBS7.setBackground(new java.awt.Color(42, 39, 41));
        jPanelBS7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelBS7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelBS7MouseClicked(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelBS7MouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelBS7MouseExited(evt);
            }
        });
        jPanelBS7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Ticket-remove-icon.png"))); // NOI18N
        jLabel65.setFocusTraversalPolicyProvider(true);
        jPanelBS7.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 30, 30));

        barBS.setBackground(new java.awt.Color(192, 27, 54));
        barBS.setRequestFocusEnabled(false);
        jPanelBS7.add(barBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 50));

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(204, 204, 204));
        jLabel67.setText("Bon de sortie");
        jPanelBS7.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jPanel2.add(jPanelBS7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 200, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 740));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1210, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /*
     *****************************************************************************************
     * exit ou bien deconnexion
     *****************************************************************************************
     */
    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        Login l = new Login();
        l.setVisible(true);
        this.dispose();
        //System.exit(0);
    }//GEN-LAST:event_jLabel19MouseClicked

    /*
     *****************************************************************************************
     * Clic sur le bar Produit
     *****************************************************************************************
     */
    private void JPanelProduit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelProduit1MouseClicked
        bar(barP);
        jInternalFrameProduit1.setVisible(true);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameUser5.setVisible(false);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBS7.setVisible(false);
        // io true sy false io ilay manovanova an'ilay onglets
    }//GEN-LAST:event_JPanelProduit1MouseClicked

    private void JPanelProduit1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelProduit1MouseEntered
        JPanelProduit1.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_JPanelProduit1MouseEntered

    private void JPanelProduit1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelProduit1MouseExited
        JPanelProduit1.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_JPanelProduit1MouseExited

    /*
     *****************************************************************************************
     * Clic sur le bar BE - mouvement
     *****************************************************************************************
     */
    private void JPanelMouvement2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelMouvement2MouseClicked
        bar(barM);
        jInternalFrameProduit1.setVisible(false);
        jInternalFrameMouvement2.setVisible(true);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameUser5.setVisible(false);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBS7.setVisible(false);
    }//GEN-LAST:event_JPanelMouvement2MouseClicked

    private void JPanelMouvement2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelMouvement2MouseEntered
        JPanelMouvement2.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_JPanelMouvement2MouseEntered

    private void JPanelMouvement2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPanelMouvement2MouseExited
        JPanelMouvement2.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_JPanelMouvement2MouseExited

    /*
     *****************************************************************************************
     * Clic sur le bar Cahier
     *****************************************************************************************
     */
    private void jPanelCahier4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCahier4MouseClicked
        bar(barC);
        jInternalFrameProduit1.setVisible(false);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameCahier4.setVisible(true);
        jInternalFrameUser5.setVisible(false);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBS7.setVisible(false);
    }//GEN-LAST:event_jPanelCahier4MouseClicked

    private void jPanelCahier4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCahier4MouseEntered
        jPanelCahier4.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_jPanelCahier4MouseEntered

    private void jPanelCahier4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCahier4MouseExited
        jPanelCahier4.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_jPanelCahier4MouseExited

    /*
     *****************************************************************************************
     * Clic sur le bar User
     *****************************************************************************************
     */
    private void jPanelUser5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelUser5MouseClicked
        bar(barU);
        jInternalFrameProduit1.setVisible(false);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameUser5.setVisible(true);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBS7.setVisible(false);
    }//GEN-LAST:event_jPanelUser5MouseClicked

    private void jPanelUser5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelUser5MouseEntered
        jPanelUser5.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_jPanelUser5MouseEntered

    private void jPanelUser5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelUser5MouseExited
        jPanelUser5.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_jPanelUser5MouseExited

    /*
     *****************************************************************************************
     * Clic sur le bar TSY ILAINA satria ilay sary eo ambony io
     *****************************************************************************************
     */
    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked

    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseEntered

    }//GEN-LAST:event_jPanel15MouseEntered

    private void jPanel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseExited

    }//GEN-LAST:event_jPanel15MouseExited

    /*
     *****************************************************************************************
     * Produit
     *****************************************************************************************
     */
    private void jButtonSupprimerProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerProduitActionPerformed
        String idprod = String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux suuprimer", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("produit", "id=" + idprod);
        } else {
            return;
        }
        tableProduit();
    }//GEN-LAST:event_jButtonSupprimerProduitActionPerformed

    private void jButtonModifierProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifierProduitActionPerformed
        if (txtidprod.getText().equals("") || txtrefprod.getText().equals("") || txtdesprod.getText().equals("")
                || txtfouprod.getText().equals("") || txtranprod.getText().equals("") || txtremprod.getText().equals("")
                || txtpriprod.getText().equals("") || txtstoprod.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] colonprod = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
            String[] infprod = {txtidprod.getText(), txtrefprod.getText(), txtdesprod.getText(), txtranprod.getText(), txtfouprod.getText(), txtremprod.getText(), txtpriprod.getText(), txtstoprod.getText()};
            String id = String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 0));
            System.out.println(db.queryUpdate("produit", colonprod, infprod, "id='" + id + "'"));
            tableProduit();
            actualiserProduit();
        }
    }//GEN-LAST:event_jButtonModifierProduitActionPerformed

    private void jButtonRechercherProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercherProduitActionPerformed
        if (txtrechprod.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer quelque chose");
        } else {
            if (comrechprod.getSelectedItem().equals("code_produit")) {
                rs_produit = db.querySelectAll("produit", "code_produit LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("reference")) {
                rs_produit = db.querySelectAll("produit", "reference LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("deseignation")) {
                rs_produit = db.querySelectAll("produit", "deseignation LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("rangement")) {
                rs_produit = db.querySelectAll("produit", "rangement LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("fournisseur")) {
                rs_produit = db.querySelectAll("produit", "fournisseur LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("remise")) {
                rs_produit = db.querySelectAll("produit", "remise LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("prix")) {
                rs_produit = db.querySelectAll("produit", "prix LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));
            } else if (comrechprod.getSelectedItem().equals("stock")) {
                rs_produit = db.querySelectAll("produit", "stock LIKE '%" + txtrechprod.getText() + "%' ");
                tbl_prod.setModel(new ResultSetTableModel(rs_produit));

            }
        }
    }//GEN-LAST:event_jButtonRechercherProduitActionPerformed

    private void jButtonAjouterProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterProduitActionPerformed
        if (txtidprod.getText().equals("") || txtrefprod.getText().equals("") || txtdesprod.getText().equals("")
                || txtfouprod.getText().equals("") || txtranprod.getText().equals("") || txtremprod.getText().equals("")
                || txtpriprod.getText().equals("") || txtstoprod.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] colon = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
            String[] inf = {txtidprod.getText(), txtrefprod.getText(), txtdesprod.getText(), txtranprod.getText(), txtfouprod.getText(), txtremprod.getText(), txtpriprod.getText(), txtstoprod.getText()};
            System.out.println(db.queryInsert("produit", colon, inf));
            tableProduit();
            actualiserProduit();
        }
    }//GEN-LAST:event_jButtonAjouterProduitActionPerformed

    private void tbl_prodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_prodMouseClicked
        txtidprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 1)));
        txtrefprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 2)));
        txtdesprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 3)));
        txtranprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 4)));
        txtfouprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 5)));
        txtremprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 6)));
        txtpriprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 7)));
        txtstoprod.setText(String.valueOf(tbl_prod.getValueAt(tbl_prod.getSelectedRow(), 8)));

    }//GEN-LAST:event_tbl_prodMouseClicked

    private void jButtonActualiserProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserProduitActionPerformed
        actualiserProduit();
        tableProduit();        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonActualiserProduitActionPerformed

    /*
     *****************************************************************************************
     * User
     *****************************************************************************************
     */
    private void comtyuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comtyuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comtyuserActionPerformed

    private void jButtonRechercherUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercherUserActionPerformed
        if (txtrechuser.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer quelque chose");
        } else {
            if (comrechuser.getSelectedItem().equals("id_user")) {
                rs_user = db.querySelectAll("utilisateur", "id_user LIKE '%" + txtrechuser.getText() + "%' ");
                tbl_user.setModel(new ResultSetTableModel(rs_user));
            } else if (comrechuser.getSelectedItem().equals("username")) {
                rs_user = db.querySelectAll("utilisateur", "username LIKE '%" + txtrechuser.getText() + "%' ");
                tbl_user.setModel(new ResultSetTableModel(rs_user));
            } else if (comrechuser.getSelectedItem().equals("password")) {
                rs_user = db.querySelectAll("utilisateur", "password LIKE '%" + txtrechuser.getText() + "%' ");
                tbl_user.setModel(new ResultSetTableModel(rs_user));
            } else if (comrechuser.getSelectedItem().equals("type")) {
                rs_user = db.querySelectAll("utilisateur", "type LIKE '%" + txtrechuser.getText() + "%' ");
                tbl_user.setModel(new ResultSetTableModel(rs_user));
            }
        }

    }//GEN-LAST:event_jButtonRechercherUserActionPerformed

    private void txtrechuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrechuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechuserActionPerformed

    private void jButtonAjouterUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterUserActionPerformed
        if (txtiduser.getText().equals("") || txtid_user.getText().equals("") || txtuser.getText().equals("") || txtpauser.getText().equals("")
                || comtyuser.getSelectedItem().equals("Type")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] colon = {"id", "id_user", "username", "password", "type"};
            String[] inf = {txtiduser.getText(), txtid_user.getText(), txtuser.getText(), txtpauser.getText(), comtyuser.getSelectedItem().toString()};
            System.out.println(db.queryInsert("utilisateur", colon, inf));
            tableUser();
            actualiserUser();
        }
    }//GEN-LAST:event_jButtonAjouterUserActionPerformed

    private void jButtonModifierUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifierUserActionPerformed
        if (txtiduser.getText().equals("") || txtid_user.getText().equals("") || txtuser.getText().equals("") || txtpauser.getText().equals("")
                || comtyuser.getSelectedItem().equals("Type")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] colon_user = {"id", "id_user", "username", "password", "type"};
            String[] inf_user = {txtiduser.getText(), txtid_user.getText(), txtuser.getText(), txtpauser.getText(), comtyuser.getSelectedItem().toString()};
            String iduser = String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 0));
            System.out.println(db.queryUpdate("utilisateur", colon_user, inf_user, "id='" + iduser + "'"));
            tableUser();
            actualiserUser();
        }
    }//GEN-LAST:event_jButtonModifierUserActionPerformed

    private void jButtonSupprimerUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerUserActionPerformed
        String iduser = String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux suuprimer", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("utilisateur", "id=" + iduser);
        } else {
            return;
        }
        tableUser();
    }//GEN-LAST:event_jButtonSupprimerUserActionPerformed

    private void jButtonActualiserUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserUserActionPerformed
        actualiserUser();
        tableUser();
    }//GEN-LAST:event_jButtonActualiserUserActionPerformed

    private void tbl_resMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_resMouseClicked
        txtcodcahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 0)));
        txtrefcahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 1)));
        txtrancahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 3)));
        txtfoucahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 4)));
        txtremcahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 5)));
        txtpricahier.setText(String.valueOf(tbl_res.getValueAt(tbl_res.getSelectedRow(), 6)));
        cout();
    }//GEN-LAST:event_tbl_resMouseClicked

    /*
     *****************************************************************************************
     * Cahier
     *****************************************************************************************
     */
    private void jButtonActualiserCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserCahierActionPerformed
        tableCahier();   // TODO add your handling code here:
    }//GEN-LAST:event_jButtonActualiserCahierActionPerformed

    private void txtcodcahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodcahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodcahierActionPerformed

    private void txtrefcahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrefcahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrefcahierActionPerformed

    private void txtrancahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrancahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrancahierActionPerformed

    private void txtfoucahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfoucahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfoucahierActionPerformed

    private void txtpricahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpricahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpricahierActionPerformed

    private void txtremcahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtremcahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtremcahierActionPerformed

    private void txtnoucahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnoucahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnoucahierActionPerformed

    private void txtstocahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstocahierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstocahierActionPerformed

    private void txtstocahierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstocahierKeyReleased
        subtotal();        // TODO add your handling code here:
    }//GEN-LAST:event_txtstocahierKeyReleased

    private void jButtonAjouterVenteCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterVenteCahierActionPerformed
        if (txtcodcahier.getText().equals("") || txtrefcahier.getText().equals("") || txtrancahier.getText().equals("")
                || txtfoucahier.getText().equals("") || txtremcahier.getText().equals("") || txtpricahier.getText().equals("") || txtnoucahier.getText().equals("") || txtstocahier.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer vos donneé");
        } else if (txtfac.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer le num de facture");
        } else {
            String[] colon_cahier = {"num_facture", "code_produit", "reference", "prix_vente", "stock_sortie", "subtotal"};
            String[] isi_cahier = {txtfac.getText(), txtcodcahier.getText(), txtrefcahier.getText(), txtnoucahier.getText(), txtstocahier.getText(), lbltot1.getText()};
            System.out.println(db.queryInsert("vente", colon_cahier, isi_cahier));
            try {
                if (!test_stock()) {
                    JOptionPane.showMessageDialog(this, "le stock est Limiter");
                } else {
                    def(); //true
                    tableCahier(); //true
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("\n" + ex);
            }
            subtotal();
            importer();
            total();
        }
    }//GEN-LAST:event_jButtonAjouterVenteCahierActionPerformed

    private void jButtonRechercheCategCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercheCategCahierActionPerformed
        if (txtrechcahier.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP complete le champ de recherche");
        } else {
            if (comrechcahier.getSelectedItem().equals("code_produit")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "code_produit LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("reference")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "reference LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("deseignation")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "deseignation LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("rangement")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "rangement LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("fournisseur")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "fournisseur LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("remise")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "remise LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("prix")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "prix LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));
            } else if (comrechcahier.getSelectedItem().equals("stock")) {
                String colon_cahier[] = {"code_produit", "reference", "deseignation", "rangement", "fournisseur", "remise", "prix", "stock"};
                rs_cahier = db.fcSelectCommand(colon_cahier, "produit", "stock LIKE '" + txtrechcahier.getText() + "' ");
                tbl_res.setModel(new ResultSetTableModel(rs_cahier));

            }
        }
    }//GEN-LAST:event_jButtonRechercheCategCahierActionPerformed

    private void txtfacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfacActionPerformed

    private void jButtonRechercheFactCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercheFactCahierActionPerformed
        importer();
        total();
    }//GEN-LAST:event_jButtonRechercheFactCahierActionPerformed

    private void jButtonSupprimerFactCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerFactCahierActionPerformed
        String idcahier = String.valueOf(tbl_ven.getValueAt(tbl_ven.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux supprimer ", "Attention", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("vente", "id=" + idcahier);
        } else {
            return;
        }
        importer();
        total();
    }//GEN-LAST:event_jButtonSupprimerFactCahierActionPerformed

    private void txtcascahierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcascahierKeyReleased
        payaprés();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcascahierKeyReleased

    /*
     *****************************************************************************************
     * Imprimer -> je dirai plus tôt mandeha any am réçu
     *****************************************************************************************
     */
    private void jButtonImprimerCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImprimerCahierActionPerformed
        Recu n = new Recu();
        n.setVisible(true);
        payaprés();
    }//GEN-LAST:event_jButtonImprimerCahierActionPerformed

    private void jButtonAnnulerCahierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerCahierActionPerformed
        String invoice = txtfac.getText();
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux supprimer ", "attention", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("vente", "num_facture=" + invoice);
        } else {
            return;
        }
        importer();
        total();
    }//GEN-LAST:event_jButtonAnnulerCahierActionPerformed

    /*
     *****************************************************************************************
     * Clic sur le table user
     *****************************************************************************************
     */
    private void tbl_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_userMouseClicked
        txtiduser.setText(String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 0)));
        txtid_user.setText(String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 1)));
        txtuser.setText(String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 2)));
        txtpauser.setText(String.valueOf(tbl_user.getValueAt(tbl_user.getSelectedRow(), 3)));
    }//GEN-LAST:event_tbl_userMouseClicked

    private void tbl_userMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_userMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_userMouseEntered

    private void tbl_mouvementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_mouvementMouseClicked

    }//GEN-LAST:event_tbl_mouvementMouseClicked

    /*
     *****************************************************************************************
     * bon d'entree
     *****************************************************************************************
     */
    private void jButtonAjouterBeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterBeActionPerformed
        if (txtnumbe.getText().equals("") || txtcodeproduitbe.getText().equals("") || txtqteentree.getText().equals("")
                || txtdateentree.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] be = {"id", "produit_id", "num_be", "date_entree"};
            String[] inf_be = {txtnumbe.getText(), txtcodeproduitbe.getText(), txtqteentree.getText(), txtdateentree.getText()};
            System.out.println(db.queryInsert("bon_entree", be, inf_be));
            tableBe();
            actualiserBe();
        }
    }//GEN-LAST:event_jButtonAjouterBeActionPerformed

    private void jButtonActualiserBeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserBeActionPerformed
        actualiserBe();
        tableBe();
    }//GEN-LAST:event_jButtonActualiserBeActionPerformed

    private void jButtonModifierBeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifierBeActionPerformed
        if (txtnumbe.getText().equals("") || txtcodeproduitbe.getText().equals("") || txtqteentree.getText().equals("")
                || txtdateentree.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String[] be = {"id", "produit_id", "num_be", "date_entree"};
            String[] inf_be = {txtnumbe.getText(), txtcodeproduitbe.getText(), txtqteentree.getText(), txtdateentree.getText()};
            String id_be = String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 0));
            System.out.println(db.queryUpdate("bon_entree", be, inf_be, "id='" + id_be + "'"));
            tableBe();
            actualiserBe();
        }
    }//GEN-LAST:event_jButtonModifierBeActionPerformed

    private void jButtonSupprimerBeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerBeActionPerformed
        String idbe = String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux suuprimer", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("bon_entree", "id=" + idbe);
        } else {
            return;
        }
        tableBe();
    }//GEN-LAST:event_jButtonSupprimerBeActionPerformed

    private void jButtonRechercherBeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercherBeActionPerformed
        if (txtrechbe.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer quelque chose");
        } else {
            if (comrechbe.getSelectedItem().equals("id")) {
                rs_be = db.querySelectAll("bon_entree", "id LIKE '%" + txtrechbe.getText() + "%' ");
                tbl_be.setModel(new ResultSetTableModel(rs_be));
            } else if (comrechbe.getSelectedItem().equals("produit_id")) {
                rs_be = db.querySelectAll("bon_entree", "produit_id LIKE '%" + txtrechbe.getText() + "%' ");
                tbl_be.setModel(new ResultSetTableModel(rs_be));
            } else if (comrechbe.getSelectedItem().equals("num_be")) {
                rs_be = db.querySelectAll("bon_entree", "num_be LIKE '%" + txtrechbe.getText() + "%' ");
                tbl_be.setModel(new ResultSetTableModel(rs_be));
            } else if (comrechbe.getSelectedItem().equals("date_entree")) {
                rs_be = db.querySelectAll("bon_entree", "date_entree LIKE '%" + txtrechbe.getText() + "%' ");
                tbl_be.setModel(new ResultSetTableModel(rs_be));
            }
        }
    }//GEN-LAST:event_jButtonRechercherBeActionPerformed

    private void txtrechbeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrechbeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechbeActionPerformed

    private void tbl_beMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_beMouseClicked
        txtnumbe.setText(String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 0)));
        txtcodeproduitbe.setText(String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 1)));
        txtqteentree.setText(String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 2)));
        txtdateentree.setText(String.valueOf(tbl_be.getValueAt(tbl_be.getSelectedRow(), 3)));
    }//GEN-LAST:event_tbl_beMouseClicked

    private void tbl_beMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_beMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_beMouseEntered

    /*
     *****************************************************************************************
     * bon de sortie
     *****************************************************************************************
     */
    private void jButtonAjouterBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjouterBsActionPerformed
        if (txtnumbs.getText().equals("") || txtcodeproduitbs.getText().equals("") || txtventeid_bs.getText().equals("") || txtqtesortie.getText().equals("")
                || txtdatesortie.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String bs[] = {"id", "produit_bsid", "vente_id", "num_bs", "date_sortie"};
            String[] inf_bs = {txtnumbs.getText(), txtcodeproduitbs.getText(), txtventeid_bs.getText(), txtqtesortie.getText(), txtdatesortie.getText()};
            System.out.println(db.queryInsert("bon_sortie", bs, inf_bs));
            tableBs();
            actualiserBs();
        }
    }//GEN-LAST:event_jButtonAjouterBsActionPerformed

    private void jButtonActualiserBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserBsActionPerformed
        actualiserBs();
        tableBs();
    }//GEN-LAST:event_jButtonActualiserBsActionPerformed

    private void jButtonModifierBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifierBsActionPerformed
        if (txtnumbs.getText().equals("") || txtcodeproduitbs.getText().equals("") || txtventeid_bs.getText().equals("") || txtqtesortie.getText().equals("")
                || txtdatesortie.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer les informations complete");
        } else {
            String bs[] = {"id", "produit_bsid", "vente_id", "num_bs", "date_sortie"};
            String[] inf_bs = {txtnumbs.getText(), txtcodeproduitbs.getText(), txtventeid_bs.getText(), txtqtesortie.getText(), txtdatesortie.getText()};
            String id_bs = String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 0));
            System.out.println(db.queryUpdate("bon_sortie", bs, inf_bs, "id='" + id_bs + "'"));
            tableBs();
            actualiserBs();
        }
    }//GEN-LAST:event_jButtonModifierBsActionPerformed

    private void jButtonSupprimerBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupprimerBsActionPerformed
        String idbs = String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "est ce que tu es sure que tu veux suuprimer", "attention!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            db.queryDelete("bon_sortie", "id=" + idbs);
        } else {
            return;
        }
        tableBs();
    }//GEN-LAST:event_jButtonSupprimerBsActionPerformed

    private void jButtonRechercherBsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRechercherBsActionPerformed
        if (txtrechbs.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "SVP entrer quelque chose");
        } else {
            if (comrechbs.getSelectedItem().equals("id")) {
                rs_bs = db.querySelectAll("bon_sortie", "id LIKE '%" + txtrechbs.getText() + "%' ");
                tbl_bs.setModel(new ResultSetTableModel(rs_bs));
            } else if (comrechbs.getSelectedItem().equals("num_bs")) {
                rs_bs = db.querySelectAll("bon_sortie", "num_bs LIKE '%" + txtrechbs.getText() + "%' ");
                tbl_bs.setModel(new ResultSetTableModel(rs_bs));
            } else if (comrechbs.getSelectedItem().equals("date_sortie")) {
                rs_bs = db.querySelectAll("bon_sortie", "date_sortie LIKE '%" + txtrechbs.getText() + "%' ");
                tbl_bs.setModel(new ResultSetTableModel(rs_bs));
            } else if (comrechbs.getSelectedItem().equals("produit_bsid")) {
                rs_bs = db.querySelectAll("bon_sortie", "produit_idbs LIKE '%" + txtrechbs.getText() + "%' ");
                tbl_bs.setModel(new ResultSetTableModel(rs_bs));
            } else if (comrechbs.getSelectedItem().equals("vente_id")) {
                rs_bs = db.querySelectAll("bon_sortie", "vente_id LIKE '%" + txtrechbs.getText() + "%' ");
                tbl_bs.setModel(new ResultSetTableModel(rs_bs));
            }
        }
    }//GEN-LAST:event_jButtonRechercherBsActionPerformed

    private void txtrechbsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrechbsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrechbsActionPerformed

    private void tbl_bsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bsMouseClicked
        txtnumbs.setText(String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 0)));
        txtcodeproduitbs.setText(String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 1)));
        txtventeid_bs.setText(String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 2)));
        txtqtesortie.setText(String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 3)));
        txtdatesortie.setText(String.valueOf(tbl_bs.getValueAt(tbl_bs.getSelectedRow(), 4)));
    }//GEN-LAST:event_tbl_bsMouseClicked

    private void tbl_bsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_bsMouseEntered

    /*
     *****************************************************************************************
     * Clic sur le bar BE
     *****************************************************************************************
     */
    private void jPanelBE6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBE6MouseClicked
        bar(barBE);
        jInternalFrameProduit1.setVisible(false);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameUser5.setVisible(false);
        jInternalFrameBE6.setVisible(true);
        jInternalFrameBS7.setVisible(false);
    }//GEN-LAST:event_jPanelBE6MouseClicked

    private void jPanelBE6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBE6MouseEntered
        jPanelBE6.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_jPanelBE6MouseEntered

    private void jPanelBE6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBE6MouseExited
        jPanelBE6.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_jPanelBE6MouseExited

    /*
     *****************************************************************************************
     * Clic sur le bar BS
     *****************************************************************************************
     */
    private void jPanelBS7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBS7MouseClicked
        bar(barBS);
        jInternalFrameProduit1.setVisible(false);
        jInternalFrameMouvement2.setVisible(false);
        jInternalFrameCahier4.setVisible(false);
        jInternalFrameUser5.setVisible(false);
        jInternalFrameBE6.setVisible(false);
        jInternalFrameBS7.setVisible(true);
    }//GEN-LAST:event_jPanelBS7MouseClicked

    private void jPanelBS7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBS7MouseEntered
        jPanelBS7.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_jPanelBS7MouseEntered

    private void jPanelBS7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelBS7MouseExited
        jPanelBS7.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_jPanelBS7MouseExited

    private void jButtonActualiserBe_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserBe_2ActionPerformed
        actualiserBe();
        tableBe_2();
    }//GEN-LAST:event_jButtonActualiserBe_2ActionPerformed

    private void jButtonActualiserBs_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserBs_2ActionPerformed
        actualiserBs();
        tableBs_2();
    }//GEN-LAST:event_jButtonActualiserBs_2ActionPerformed

    private void jButtonActualiserMouvementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualiserMouvementActionPerformed
        tableMouvement();
    }//GEN-LAST:event_jButtonActualiserMouvementActionPerformed

    private void comrechprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comrechprodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comrechprodActionPerformed

    /*
     *****************************************************************************************
     * bar design
     *****************************************************************************************
     */
    public void bar(JLabel lab) {
        barP.setOpaque(false);
        barM.setOpaque(false);
        barC.setOpaque(false);
        barU.setOpaque(false);
        barBE.setOpaque(false);
        barBS.setOpaque(false);
        lab.setOpaque(true);
        jPanel2.repaint();

    }

    /*
     *****************************************************************************************
     * cout
     *****************************************************************************************
     */
    public void cout() {
        double a = Double.parseDouble(txtpricahier.getText());
        double b = Double.parseDouble(txtremcahier.getText());
        double c = a - a * (b / 100);
        txtnoucahier.setText(String.valueOf(c));
    }

    /*
     *****************************************************************************************
     * SubTotal sur le cahier
     *****************************************************************************************
     */
    public void subtotal() {
        double a = Double.parseDouble(txtnoucahier.getText());
        double b = Double.parseDouble(txtstocahier.getText());
        double c = a * b;
        lbltot1.setText(String.valueOf(c));
    }

    /*
     *****************************************************************************************
     * Total
     *****************************************************************************************
     */
    public void total() {
        rs_cahier = db.exécutionQuery("SELECT SUM(subtotal) as subtotal FROM vente WHERE num_facture = '" + txtfac.getText() + "'");
        try {
            rs_cahier.next();
            lbltot2.setText(rs_cahier.getString("subtotal"));
        } catch (SQLException ex) {
            Logger.getLogger(MainPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     *****************************************************************************************
     * Test du stock
     *****************************************************************************************
     */
    public boolean test_stock() throws SQLException {
        boolean teststock;
        rs_cahier = db.querySelectAll("produit", "code_produit='" + txtcodcahier.getText() + "'");
        while (rs_cahier.next()) {
            old = rs_cahier.getInt("stock");
        }
        dec = Integer.parseInt(txtstocahier.getText());
        if (old < dec) {
            teststock = false;
        } else {
            teststock = true;
        }
        return teststock;
    }

    /*
     *****************************************************************************************
     * Fonction dans le cahier
     *****************************************************************************************
     */
    public void def() throws SQLException {
        rs_cahier = db.querySelectAll("produit", "code_produit='" + txtcodcahier.getText() + "'");
        while (rs_cahier.next()) {
            old = rs_cahier.getInt("stock");
        }
        dec = Integer.parseInt(txtstocahier.getText());
        now = old - dec;
        String nvstock = Integer.toString(now);

        String a = String.valueOf(nvstock);
        String[] colon = {"stock"};
        String[] isi = {a};
        System.out.println(db.queryUpdate("produit", colon, isi, "code_produit='" + txtcodcahier.getText() + "'"));
    }

    /*
     *****************************************************************************************
     * Payé après dans le cahier
     *****************************************************************************************
     */
    public void payaprés() {
        int a = Integer.parseInt(lbltot2.getText());
        int b = Integer.parseInt(txtcascahier.getText());
        int c = b - a;
        txtpaycahier.setText(Integer.toString(c));
    }

    /*
     *****************************************************************************************
     * ON NE PEUT PAS MODIFIER LE CODE SUIVANT
     *****************************************************************************************
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelMouvement2;
    private javax.swing.JPanel JPanelProduit1;
    private javax.swing.JLabel barBE;
    private javax.swing.JLabel barBS;
    private javax.swing.JLabel barC;
    private javax.swing.JLabel barM;
    private javax.swing.JLabel barP;
    private javax.swing.JLabel barU;
    private javax.swing.JComboBox comrechbe;
    private javax.swing.JComboBox comrechbs;
    public static javax.swing.JComboBox comrechcahier;
    private javax.swing.JComboBox comrechprod;
    private javax.swing.JComboBox comrechuser;
    private javax.swing.JComboBox comtyuser;
    private javax.swing.JButton jButtonActualiserBe;
    private javax.swing.JButton jButtonActualiserBe_2;
    private javax.swing.JButton jButtonActualiserBs;
    private javax.swing.JButton jButtonActualiserBs_2;
    public static javax.swing.JButton jButtonActualiserCahier;
    private javax.swing.JButton jButtonActualiserMouvement;
    private javax.swing.JButton jButtonActualiserProduit;
    private javax.swing.JButton jButtonActualiserUser;
    private javax.swing.JButton jButtonAjouterBe;
    private javax.swing.JButton jButtonAjouterBs;
    private javax.swing.JButton jButtonAjouterProduit;
    private javax.swing.JButton jButtonAjouterUser;
    public static javax.swing.JButton jButtonAjouterVenteCahier;
    public static javax.swing.JButton jButtonAnnulerCahier;
    public static javax.swing.JButton jButtonImprimerCahier;
    private javax.swing.JButton jButtonModifierBe;
    private javax.swing.JButton jButtonModifierBs;
    private javax.swing.JButton jButtonModifierProduit;
    private javax.swing.JButton jButtonModifierUser;
    public static javax.swing.JButton jButtonRechercheCategCahier;
    public static javax.swing.JButton jButtonRechercheFactCahier;
    private javax.swing.JButton jButtonRechercherBe;
    private javax.swing.JButton jButtonRechercherBs;
    private javax.swing.JButton jButtonRechercherProduit;
    private javax.swing.JButton jButtonRechercherUser;
    private javax.swing.JButton jButtonSupprimerBe;
    private javax.swing.JButton jButtonSupprimerBs;
    public static javax.swing.JButton jButtonSupprimerFactCahier;
    private javax.swing.JButton jButtonSupprimerProduit;
    private javax.swing.JButton jButtonSupprimerUser;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JInternalFrame jInternalFrameBE6;
    private javax.swing.JInternalFrame jInternalFrameBS7;
    private javax.swing.JInternalFrame jInternalFrameCahier4;
    private javax.swing.JInternalFrame jInternalFrameMouvement2;
    private javax.swing.JInternalFrame jInternalFrameProduit1;
    private javax.swing.JInternalFrame jInternalFrameUser5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel32;
    public static javax.swing.JLabel jLabel33;
    public static javax.swing.JLabel jLabel34;
    public static javax.swing.JLabel jLabel35;
    public static javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    public static javax.swing.JLabel jLabel40;
    public static javax.swing.JLabel jLabel41;
    public static javax.swing.JLabel jLabel42;
    public static javax.swing.JLabel jLabel43;
    public static javax.swing.JLabel jLabel44;
    public static javax.swing.JLabel jLabel45;
    public static javax.swing.JLabel jLabel46;
    public static javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    public static javax.swing.JLabel jLabel53;
    public static javax.swing.JLabel jLabel54;
    public static javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public static javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBE6;
    private javax.swing.JPanel jPanelBS7;
    private javax.swing.JPanel jPanelCahier4;
    private javax.swing.JPanel jPanelUser5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JScrollPane jScrollPaneCahier4;
    private javax.swing.JScrollPane jScrollPaneProduit1;
    private javax.swing.JScrollPane jScrollPaneUser5;
    private javax.swing.JScrollPane jScrollPaneUser6;
    private javax.swing.JScrollPane jScrollPaneUser7;
    public static javax.swing.JLabel lbl1;
    public static javax.swing.JLabel lbl2;
    public static javax.swing.JLabel lbltot1;
    public static javax.swing.JLabel lbltot2;
    private javax.swing.JTable tbl_be;
    private javax.swing.JTable tbl_be_2;
    private javax.swing.JTable tbl_bs;
    private javax.swing.JTable tbl_bs_2;
    private javax.swing.JTable tbl_mouvement;
    private javax.swing.JTable tbl_prod;
    public static javax.swing.JTable tbl_res;
    private javax.swing.JTable tbl_user;
    public static javax.swing.JTable tbl_ven;
    public static javax.swing.JTextField txtcascahier;
    public static javax.swing.JTextField txtcodcahier;
    private javax.swing.JTextField txtcodeproduitbe;
    private javax.swing.JTextField txtcodeproduitbs;
    private javax.swing.JTextField txtdateentree;
    private javax.swing.JTextField txtdatesortie;
    private javax.swing.JTextField txtdesprod;
    public static javax.swing.JTextField txtfac;
    public static javax.swing.JTextField txtfoucahier;
    private javax.swing.JTextField txtfouprod;
    private javax.swing.JTextField txtid_user;
    private javax.swing.JTextField txtidprod;
    private javax.swing.JTextField txtiduser;
    public static javax.swing.JTextField txtnoucahier;
    private javax.swing.JTextField txtnumbe;
    private javax.swing.JTextField txtnumbs;
    private javax.swing.JTextField txtpauser;
    public static javax.swing.JTextField txtpaycahier;
    public static javax.swing.JTextField txtpricahier;
    private javax.swing.JTextField txtpriprod;
    private javax.swing.JTextField txtqteentree;
    private javax.swing.JTextField txtqtesortie;
    public static javax.swing.JTextField txtrancahier;
    private javax.swing.JTextField txtranprod;
    private javax.swing.JTextField txtrechbe;
    private javax.swing.JTextField txtrechbs;
    public static javax.swing.JTextField txtrechcahier;
    private javax.swing.JTextField txtrechprod;
    private javax.swing.JTextField txtrechuser;
    public static javax.swing.JTextField txtrefcahier;
    private javax.swing.JTextField txtrefprod;
    public static javax.swing.JTextField txtremcahier;
    private javax.swing.JTextField txtremprod;
    public static javax.swing.JTextField txtstocahier;
    private javax.swing.JTextField txtstoprod;
    private javax.swing.JTextField txtuser;
    private javax.swing.JTextField txtventeid_bs;
    // End of variables declaration//GEN-END:variables

}
