package UI;

import sql.DBConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Download_Stu_Image extends JPanel implements ActionListener {
    private final JTextField jtf_num;
    private final JButton jb_search, jb_download;
    private JLabel jl_image;
    private BufferedImage currentImage;

    // 不可修改的显示框
    private final JTextField jtf_display_name, jtf_display_birthdate, jtf_display_age, jtf_display_major, jtf_display_dormitory;

    public Download_Stu_Image() {
        setSize(526, 600);
        setLayout(null);

        JLabel jl_title = new JLabel("保存学生图片");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(167, 10, 200, 34);
        add(jl_title);

        JLabel jl_num = new JLabel("学 号");
        jl_num.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_num.setBounds(10, 55, 79, 36);
        add(jl_num);

        jtf_num = new JTextField();
        jtf_num.setBounds(99, 54, 211, 41);
        add(jtf_num);
        jtf_num.setColumns(10);

        jb_search = new JButton("搜 索");
        jb_search.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_search.setBounds(320, 53, 93, 41);
        add(jb_search);

        // 添加图片显示区域
        jl_image = new JLabel();
        jl_image.setBounds(100, 150, 200, 200);
        jl_image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(jl_image);

        // 添加下载图片按钮
        jb_download = new JButton("下载头像");
        jb_download.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_download.setBounds(320, 250, 120, 41);
        add(jb_download);

        // 添加不可修改的显示框
        JLabel jl_display_name = new JLabel("姓名:");
        jl_display_name.setBounds(10, 370, 60, 30);
        jl_display_name.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(jl_display_name);

        jtf_display_name = new JTextField();
        jtf_display_name.setBounds(70, 370, 120, 30);
        jtf_display_name.setEditable(false);
        add(jtf_display_name);

        JLabel jl_display_birthdate = new JLabel("生日:");
        jl_display_birthdate.setBounds(200, 370, 60, 30);
        jl_display_birthdate.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(jl_display_birthdate);

        jtf_display_birthdate = new JTextField();
        jtf_display_birthdate.setBounds(260, 370, 120, 30);
        jtf_display_birthdate.setEditable(false);
        add(jtf_display_birthdate);

        JLabel jl_display_age = new JLabel("年龄:");
        jl_display_age.setBounds(390, 370, 60, 30);
        jl_display_age.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(jl_display_age);

        jtf_display_age = new JTextField();
        jtf_display_age.setBounds(450, 370, 50, 30);
        jtf_display_age.setEditable(false);
        add(jtf_display_age);

        JLabel jl_display_major = new JLabel("专业:");
        jl_display_major.setBounds(10, 410, 60, 30);
        jl_display_major.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(jl_display_major);

        jtf_display_major = new JTextField();
        jtf_display_major.setBounds(70, 410, 120, 30);
        jtf_display_major.setEditable(false);
        add(jtf_display_major);

        JLabel jl_display_dormitory = new JLabel("宿舍:");
        jl_display_dormitory.setBounds(200, 410, 60, 30);
        jl_display_dormitory.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        add(jl_display_dormitory);

        jtf_display_dormitory = new JTextField();
        jtf_display_dormitory.setBounds(260, 410, 120, 30);
        jtf_display_dormitory.setEditable(false);
        add(jtf_display_dormitory);

        jb_search.addActionListener(this);
        jb_download.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_search) {
            searchStudentById();
        } else if (e.getSource() == jb_download) {
            downloadImage();
        }
    }

    private void searchStudentById() {
        String studentId = jtf_num.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT name, birthdate, age, major, dormitory, avatar FROM students WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 设置不可修改的显示框内容
                jtf_display_name.setText(rs.getString("name"));
                jtf_display_birthdate.setText(rs.getString("birthdate"));
                jtf_display_age.setText(String.valueOf(rs.getInt("age")));
                jtf_display_major.setText(rs.getString("major"));
                jtf_display_dormitory.setText(rs.getString("dormitory"));

                // 设置头像
                byte[] avatarBytes = rs.getBytes("avatar");
                if (avatarBytes != null) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(avatarBytes);
                    currentImage = ImageIO.read(bais);
                    Image avatarImage = currentImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    jl_image.setIcon(new ImageIcon(avatarImage));
                } else {
                    resetImage();
                }
            } else {
                JOptionPane.showMessageDialog(this, "未找到该学生信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
                resetImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询学生信息时出错！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void downloadImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, "请先搜索学生信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("保存图片");
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(currentImage, "jpg", file);
                JOptionPane.showMessageDialog(this, "头像已成功下载！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "下载头像失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetImage() {
        currentImage = null;
        jl_image.setIcon(null);
        jtf_display_name.setText("");
        jtf_display_birthdate.setText("");
        jtf_display_age.setText("");
        jtf_display_major.setText("");
        jtf_display_dormitory.setText("");
    }

}
