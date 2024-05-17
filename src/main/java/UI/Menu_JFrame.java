package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_JFrame extends JFrame implements ActionListener {
    private final JButton jb_add;
    private final JButton jb_download_img;
    private final JButton jb_select;
    private final JButton jb_exit;
    private JPanel panel;

    public Menu_JFrame() {
        setTitle("学生信息管理系统");
        setBounds(100, 100, 800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 10, 10));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.setBackground(new Color(220, 220, 220));
        getContentPane().add(sidePanel, BorderLayout.WEST);

        jb_select = new JButton("查询学生信息");
        jb_select.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sidePanel.add(jb_select);

        jb_add = new JButton("添加学生信息");
        jb_add.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sidePanel.add(jb_add);

        jb_download_img = new JButton("保存学生头像");
        jb_download_img.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sidePanel.add(jb_download_img);

        jb_exit = new JButton("退出系统");
        jb_exit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sidePanel.add(jb_exit);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel jl_title = new JLabel("学生信息管理系统");
        jl_title.setFont(new Font("微软雅黑", Font.BOLD, 30));
        jl_title.setBounds(160, 20, 400, 40);
        jl_title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(jl_title);

        JLabel lblNewLabel = new JLabel("欢迎使用本系统！");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 28));
        lblNewLabel.setBounds(160, 200, 400, 40);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel);

        jb_add.addActionListener(this);
        jb_download_img.addActionListener(this);
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
            addDialog.setSize(900, 700);
            addDialog.setLocationRelativeTo(this);
            addDialog.setContentPane(new Select_Stu());
            addDialog.setVisible(true);
        } else if (e.getSource() == jb_download_img) {
            JDialog updateDialog = new JDialog(this, "保存学生头像", true);
            updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            updateDialog.setSize(600, 500);
            updateDialog.setLocationRelativeTo(this);
            updateDialog.setContentPane(new Download_Stu_Image());
            updateDialog.setVisible(true);
        } else if (e.getSource() == jb_exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu_JFrame menu = new Menu_JFrame();
            menu.setVisible(true);
        });
    }
}
