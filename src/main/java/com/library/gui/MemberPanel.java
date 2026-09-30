package com.library.gui;

import com.library.model.Member;
import com.library.service.MemberService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MemberPanel extends JPanel {
    private final MemberService service = new MemberService();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Name","Email","Phone","Address","Status","Member Since"},0);
    private final JTable table = new JTable(model);
    private final JTextField search = new JTextField(20);

    public MemberPanel() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        JButton add = new JButton("Register Member");
        JButton edit = new JButton("Edit Member");
        JButton delete = new JButton("Delete Member");
        JButton refresh = new JButton("Refresh");
        JButton find = new JButton("Search");

        top.add(add); top.add(edit); top.add(delete); top.add(refresh);
        top.add(new JLabel("Search:")); top.add(search); top.add(find);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> addMember());
        edit.addActionListener(e -> editMember());
        delete.addActionListener(e -> deleteMember());
        refresh.addActionListener(e -> load(service.findAll()));
        find.addActionListener(e -> load(service.search(search.getText())));

        load(service.findAll());
    }

    private void addMember() {
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField address = new JTextField();

        Object[] fields = {"Name",name,"Email",email,"Phone",phone,"Address",address};

        if (JOptionPane.showConfirmDialog(this,fields,"Register Member",
                JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                service.add(new Member(name.getText(),email.getText(),phone.getText(),address.getText()));
                load(service.findAll());
                JOptionPane.showMessageDialog(this,"Member registered successfully.");
            } catch(Exception e) {
                error(e);
            }
        }
    }

    private void editMember() {
        int row = table.getSelectedRow();
        if (row < 0) { error("Select a member."); return; }

        int id = (int) model.getValueAt(row,0);
        JTextField name = new JTextField(model.getValueAt(row,1).toString());
        JTextField email = new JTextField(model.getValueAt(row,2).toString());
        JTextField phone = new JTextField(model.getValueAt(row,3).toString());
        JTextField address = new JTextField(model.getValueAt(row,4).toString());
        JTextField status = new JTextField(model.getValueAt(row,5).toString());

        Object[] fields = {"Name",name,"Email",email,"Phone",phone,
                "Address",address,"Status",status};

        if (JOptionPane.showConfirmDialog(this,fields,"Edit Member",
                JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                Member m = new Member(id,name.getText(),email.getText(),phone.getText(),
                        address.getText(),status.getText(),
                        java.time.LocalDate.parse(model.getValueAt(row,6).toString()));
                service.update(m);
                load(service.findAll());
            } catch(Exception e) { error(e); }
        }
    }

    private void deleteMember() {
        int row = table.getSelectedRow();
        if (row < 0) { error("Select a member."); return; }
        try {
            service.delete((int)model.getValueAt(row,0));
            load(service.findAll());
        } catch(Exception e) { error(e); }
    }

    private void load(java.util.List<Member> members) {
        model.setRowCount(0);
        for(Member m:members) {
            model.addRow(new Object[]{m.getId(),m.getName(),m.getEmail(),m.getPhone(),
                    m.getAddress(),m.getStatus(),m.getMemberSince()});
        }
    }

    private void error(Exception e) { error(e.getMessage()); }
    private void error(String message) {
        JOptionPane.showMessageDialog(this,message,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
