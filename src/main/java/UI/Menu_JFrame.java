package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_JFrame extends JFrame implements ActionListener {
    // 定义按钮
    private final JButton jb_add;
    private final JButton jb_download_img;
    private final JButton jb_select;
    private final JButton jb_exit;
    private JPanel panel;

    // 构造函数，初始化界面
    public Menu_JFrame() {
        setTitle("学生信息管理系统"); // 设置窗口标题
        setBounds(100, 100, 800, 600); // 设置窗口位置和大小
        setLocationRelativeTo(null); // 设置窗口居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
        getContentPane().setLayout(new BorderLayout()); // 设置布局管理器

        // 创建侧边面板，并设置布局和样式
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 10, 10)); // 网格布局，10行1列，水平和垂直间距为10
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 设置边框为空边框，四个方向的间距均为10
        sidePanel.setBackground(new Color(220, 220, 220)); // 设置背景颜色
        getContentPane().add(sidePanel, BorderLayout.WEST); // 将侧边面板添加到窗口的左侧

        // 创建并添加查询学生信息按钮
        jb_select = new JButton("查询学生信息");
        jb_select.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        sidePanel.add(jb_select);

        // 创建并添加添加学生信息按钮
        jb_add = new JButton("添加学生信息");
        jb_add.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        sidePanel.add(jb_add);

        // 创建并添加保存学生头像按钮
        jb_download_img = new JButton("保存学生头像");
        jb_download_img.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        sidePanel.add(jb_download_img);

        // 创建并添加退出系统按钮
        jb_exit = new JButton("退出系统");
        jb_exit.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体
        sidePanel.add(jb_exit);

        // 创建中心面板，并设置布局和样式
        panel = new JPanel();
        panel.setLayout(null); // 设置为绝对布局
        panel.setBackground(Color.WHITE); // 设置背景颜色为白色
        getContentPane().add(panel, BorderLayout.CENTER); // 将中心面板添加到窗口的中间

        // 创建并添加标题标签
        JLabel jl_title = new JLabel("学生信息管理系统");
        jl_title.setFont(new Font("微软雅黑", Font.BOLD, 30)); // 设置字体
        jl_title.setBounds(160, 20, 400, 40); // 设置位置和大小
        jl_title.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平对齐方式为居中
        panel.add(jl_title);

        // 创建并添加欢迎标签
        JLabel lblNewLabel = new JLabel("欢迎使用本系统！");
        lblNewLabel.setForeground(Color.BLUE); // 设置字体颜色为蓝色
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 28)); // 设置字体
        lblNewLabel.setBounds(160, 200, 400, 40); // 设置位置和大小
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平对齐方式为居中
        panel.add(lblNewLabel);

        // 为按钮添加动作监听器
        jb_add.addActionListener(this);
        jb_download_img.addActionListener(this);
        jb_select.addActionListener(this);
        jb_exit.addActionListener(this);
        this.setResizable(false); // 设置窗口不可调整大小

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 根据不同的按钮执行不同的操作
        if (e.getSource() == jb_add) {
            // 添加学生信息
            JDialog addDialog = new JDialog(this, "添加学生信息", true);
            addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            addDialog.setSize(600, 500);
            addDialog.setLocationRelativeTo(this);
            addDialog.setContentPane(new ADD_Stu());
            addDialog.setVisible(true);
        } else if (e.getSource() == jb_select) {
            // 查询学生信息
            JDialog addDialog = new JDialog(this, "查询学生信息", true);
            addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            addDialog.setSize(900, 700);
            addDialog.setLocationRelativeTo(this);
            addDialog.setContentPane(new Select_Stu());
            addDialog.setVisible(true);
        } else if (e.getSource() == jb_download_img) {
            // 保存学生头像
            JDialog updateDialog = new JDialog(this, "保存学生头像", true);
            updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            updateDialog.setSize(600, 500);
            updateDialog.setLocationRelativeTo(this);
            updateDialog.setContentPane(new Download_Stu_Image());
            updateDialog.setVisible(true);
        } else if (e.getSource() == jb_exit) {
            // 退出系统
            System.exit(0);
        }
    }
}
