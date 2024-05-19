package UI;

import Tool.Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sql.DBConfig;

public class LoginJFrame extends JFrame implements ActionListener, MouseListener {

    // 定义常量和组件
    String admin = "admin";
    String admin_password = "123456";
    JLabel username = new JLabel("用户名");
    JLabel password = new JLabel("密码");
    JLabel CAPTCHA = new JLabel("验证码");
    JTextField username_text = new JTextField();
    JPasswordField password_text = new JPasswordField();
    JTextField CAPTCHA_text = new JTextField();
    JButton CAPTCHA_button = new JButton();
    String current_login_img_path = "登录按钮.png";
    String login_img_path = "src/main/resources/Img/Login/";
    JButton login_Btn = new JButton(new ImageIcon(login_img_path + current_login_img_path));
    String visible_password_img_path = "src/main/resources/Img/Login/";
    String current_password_img = "显示密码.png";
    JButton visible_password_btn = new JButton(new ImageIcon(visible_password_img_path + current_password_img));
    private final Code code = new Code();
    private Menu_JFrame menu = new Menu_JFrame(); // 菜单界面
    private AvatarRefresher AV_rf = new AvatarRefresher();

    // 构造函数，初始化界面
    public LoginJFrame() {
        init_LoginJFrame(); // 初始化登录界面
        login_CSS(); // 设置样式
        init_in_last(); // 界面最后的初始化
    }

    // 设置样式
    private void login_CSS() {
        // 设置输入标签样式
        username_text.setBounds(200, 120, 200, 30);
        username_text.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(username_text);

        password_text.setBounds(200, 180, 200, 30);
        password_text.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(password_text);

        CAPTCHA_text.setBounds(200, 240, 110, 30);
        CAPTCHA_text.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(CAPTCHA_text);

        // 设置用户名标签样式
        username.setBounds(100, 120, 100, 30);
        username.setForeground(new Color(178, 139, 63));
        username.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(username);

        // 设置密码标签样式
        password.setBounds(100, 180, 100, 30);
        password.setForeground(new Color(178, 139, 63));
        password.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(password);

        // 设置验证码标签样式
        CAPTCHA.setBounds(100, 240, 100, 30);
        CAPTCHA.setForeground(new Color(178, 139, 63));
        CAPTCHA.setFont(new Font("宋体", Font.BOLD, 20));
        this.getContentPane().add(CAPTCHA);

        // 设置验证码按钮样式
        CAPTCHA_button.setBounds(320, 240, 80, 30);
        CAPTCHA_button.setFont(new Font("宋体", Font.BOLD, 20));
        CAPTCHA_button.setForeground(new Color(178, 139, 63));
        CAPTCHA_button.setBackground(new Color(255, 255, 255));
        CAPTCHA_button.setIcon(new ImageIcon(code.codeImage())); // 设置初始验证码图片
        CAPTCHA_button.setBorderPainted(false); // 不绘制边框
        CAPTCHA_button.setContentAreaFilled(false); // 不显示背景（透明）
        CAPTCHA_button.setFocusPainted(false); // 不绘制焦点状态下的边框
        this.getContentPane().add(CAPTCHA_button);
        CAPTCHA_button.addActionListener(this);

        // 设置登录按钮样式
        login_Btn.setBounds(180, 300, 128, 47);
        login_Btn.setBorderPainted(false); // 不绘制边框
        login_Btn.setContentAreaFilled(false); // 不显示背景（透明）
        login_Btn.setFocusPainted(false); // 不绘制焦点状态下的边框
        this.getContentPane().add(login_Btn);
        login_Btn.addActionListener(this);
        login_Btn.addMouseListener(this);

        // 设置显示密码按钮样式
        visible_password_btn.setBounds(410, 180, 18, 29);
        visible_password_btn.setBorderPainted(false); // 不绘制边框
        visible_password_btn.setContentAreaFilled(false); // 不显示背景（透明）
        visible_password_btn.setFocusPainted(false); // 不绘制焦点状态下的边框
        visible_password_btn.addMouseListener(this);
        this.getContentPane().add(visible_password_btn);

        // 设置背景图片
        JLabel bg = new JLabel(new ImageIcon("src/main/resources/image/login/background.png"));
        bg.setBounds(0, 0, 470, 390);
        this.getContentPane().add(bg);
    }

    // 界面最后的初始化设置
    private void init_in_last() {
        this.setVisible(true);
        this.setResizable(false); // 不可调整大小
    }

    // 初始化登录界面
    private void init_LoginJFrame() {
        this.setSize(488, 430);
        this.setTitle("用户登录"); // 设置界面的标题
        this.setAlwaysOnTop(true); // 设置窗口顶部显示
        this.setLocationRelativeTo(null); // 设置窗口居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置窗口关闭时，程序结束
        init_CAPTCHA_str(); // 初始化验证码
    }

    // 验证管理员用户名和密码
    private boolean authenticateAdmin(String username, String password) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM admin WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password); // 验证密码是否匹配
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object Btn = e.getSource();
        if (Btn.equals(CAPTCHA_button)) {
            init_CAPTCHA_str(); // 刷新验证码
        } else if (Btn.equals(login_Btn)) {
            String username = username_text.getText().trim(); // 获取并去掉前后空格的用户名
            String password = password_text.getText().trim(); // 获取并去掉前后空格的密码
            String CAPTCHA = CAPTCHA_text.getText().trim(); // 获取并去掉前后空格的验证码

            // 检查输入是否为空
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名或密码不能为空");
            } else if (CAPTCHA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入验证码");
            } else if (!code.verify(CAPTCHA)) {
                JOptionPane.showMessageDialog(this, "验证码错误");
                init_CAPTCHA_str(); // 刷新验证码
            } else if (authenticateAdmin(username, password)) {
                menu.setVisible(true); // 显示菜单界面
                this.dispose(); // 关闭当前界面
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误");
            }
        }
    }

    // 初始化验证码
    private void init_CAPTCHA_str() {
        CAPTCHA_button.setIcon(new ImageIcon(code.newCodeImage()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 不需要实现
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object btn = e.getSource();
        if (btn.equals(login_Btn)) {
            current_login_img_path = "登录按下.png";
            login_Btn.setIcon(new ImageIcon(login_img_path + current_login_img_path));
        } else if (btn.equals(visible_password_btn)) {
            current_password_img = "显示密码按下.png";
            visible_password_btn.setIcon(new ImageIcon(visible_password_img_path + current_password_img));
            password_text.setEchoChar((char) 0); // 显示密码
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object btn = e.getSource();
        if (btn.equals(login_Btn)) {
            current_login_img_path = "登录按钮.png";
            login_Btn.setIcon(new ImageIcon(login_img_path + current_login_img_path));
        } else if (btn.equals(visible_password_btn)) {
            current_password_img = "显示密码.png";
            visible_password_btn.setIcon(new ImageIcon(visible_password_img_path + current_password_img));
            password_text.setEchoChar('*'); // 隐藏密码
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // 不需要实现
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // 不需要实现
    }
}
