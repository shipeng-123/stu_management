package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_JFrame extends JFrame implements ActionListener {
    private final JButton jb_add;
    private final JButton jb_download_img;
    private final JButton jb_update;
    private final JButton jb_delete;
    private final JButton jb_select;
    private final JButton jb_exit;
    private JPanel panel;

    public Menu_JFrame() {
        setTitle("学生信息管理系统");
        setBounds(100, 100, 700, 530);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jb_select = new JButton("查询学生信息");
        jb_select.setBounds(10, 50, 119, 36);
        jb_select.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_select);

        jb_add = new JButton("添加学生信息");
        jb_add.setBounds(10, 110, 119, 36);
        jb_add.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_add);

        jb_update = new JButton("修改学生信息");
        jb_update.setBounds(10, 170, 119, 36);
        jb_update.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_update);

        jb_delete = new JButton("删除学生信息");
        jb_delete.setBounds(10, 240, 119, 36);
        jb_delete.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_delete);

        jb_download_img = new JButton("保存学生相片");
        jb_download_img.setBounds(10, 310, 119, 36);
        jb_download_img.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_download_img);

        jb_exit = new JButton("退出系统");
        jb_exit.setBounds(10, 380, 119, 36);
        jb_exit.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        getContentPane().add(jb_exit);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(140, 10, 554, 481);

        getContentPane().add(panel);

        JLabel jl_title = new JLabel("学生信息管理系统");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(173, 10, 213, 36);
        panel.add(jl_title);

        JLabel lblNewLabel = new JLabel("欢迎使用本系统！");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 28));
        lblNewLabel.setBounds(162, 166, 224, 107);
        panel.add(lblNewLabel);

        jb_add.addActionListener(this);
        jb_download_img.addActionListener(this);
        jb_update.addActionListener(this);
        jb_delete.addActionListener(this);
        jb_select.addActionListener(this);
        jb_exit.addActionListener(this);

        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_add) {
            JDialog addDialog = new JDialog(this, "添加学生信息", true);
            addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            addDialog.setSize(600, 500);
            addDialog.setLocationRelativeTo(this);
            addDialog.setContentPane(new ADD_Stu());
            addDialog.setVisible(true);
        } else if (e.getSource() == jb_select) {
            JDialog addDialog = new JDialog(this, "查询学生信息", true);
            addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            addDialog.setSize(800, 600);
            addDialog.setLocationRelativeTo(this);
            addDialog.setContentPane(new Select_Stu());
            addDialog.setVisible(true);
        } else if (e.getSource() == jb_update) { // 添加处理修改学生信息按钮的逻辑
            JDialog updateDialog = new JDialog(this, "修改学生信息", true);
            updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            updateDialog.setSize(600, 500);
            updateDialog.setLocationRelativeTo(this);
            updateDialog.setContentPane(new Modify_stu());
            updateDialog.setVisible(true);
        }
        // 其他按钮的处理逻辑可以放在这里
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu_JFrame menu = new Menu_JFrame();
            menu.setVisible(true);
        });
    }
}
