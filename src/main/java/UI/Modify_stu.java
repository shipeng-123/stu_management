package UI;

import sql.DBConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Modify_stu extends JPanel implements ActionListener {
    private final JTextField jtf_num, jtf_stuNum, jtf_stuName, jtf_stuBirthday, jtf_stuAge, jtf_stuDorm;
    private final JButton jb_enter, jb_reset, jb_search_enter, jb_search_reset, jb_upload;
    private final JRadioButton jrb_man, jrb_woman;
    private final JComboBox<String> jcb_stuMajor;
    private JLabel jl_image;
    private File selectedImageFile;

    public Modify_stu() {
        setSize(526, 461);
        setLayout(null);

        JLabel jl_title = new JLabel("学生信息修改");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(167, 10, 165, 34);
        add(jl_title);

        JLabel jl_num = new JLabel("学 号");
        jl_num.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_num.setBounds(10, 55, 79, 36);
        add(jl_num);

        jtf_num = new JTextField();
        jtf_num.setBounds(99, 54, 211, 41);
        add(jtf_num);
        jtf_num.setColumns(10);

        jb_search_enter = new JButton("搜 索");
        jb_search_enter.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_search_enter.setBounds(320, 53, 93, 41);
        add(jb_search_enter);

        jb_search_reset = new JButton("清 空");
        jb_search_reset.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_search_reset.setBounds(423, 53, 93, 41);
        add(jb_search_reset);

        // 添加图片显示区域
        jl_image = new JLabel();
        jl_image.setBounds(400, 150, 100, 100);
        jl_image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(jl_image);
        // 加载默认图片
        try {
            Image img = ImageIO.read(new File("src/main/resources/Img/Add_Stu/menu-user.png"));
            img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            jl_image.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 添加上传图片按钮
        jb_upload = new JButton("上传图片");
        jb_upload.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_upload.setBounds(400, 260, 100, 41);
        add(jb_upload);

        JLabel jl_stuNum = new JLabel("学 号");
        jl_stuNum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuNum.setBounds(10, 127, 79, 36);
        add(jl_stuNum);

        JLabel jl_stuName = new JLabel("姓 名");
        jl_stuName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuName.setBounds(10, 173, 79, 36);
        add(jl_stuName);

        JLabel jl_stuGender = new JLabel("性 别");
        jl_stuGender.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuGender.setBounds(10, 219, 79, 36);
        add(jl_stuGender);

        JLabel jl_stuBrithday = new JLabel("生 日");
        jl_stuBrithday.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuBrithday.setBounds(10, 265, 79, 36);
        add(jl_stuBrithday);

        JLabel jl_stuAge = new JLabel("年 龄");
        jl_stuAge.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuAge.setBounds(10, 311, 79, 36);
        add(jl_stuAge);

        JLabel jl_stuMajor = new JLabel("专 业");
        jl_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuMajor.setBounds(10, 357, 79, 36);
        add(jl_stuMajor);

        JLabel jl_stuDorm = new JLabel("宿 舍");
        jl_stuDorm.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuDorm.setBounds(10, 403, 79, 36);
        add(jl_stuDorm);

        jtf_stuNum = new JTextField();
        jtf_stuNum.setColumns(10);
        jtf_stuNum.setBounds(100, 125, 211, 41);
        add(jtf_stuNum);

        jtf_stuName = new JTextField();
        jtf_stuName.setColumns(10);
        jtf_stuNum.setEditable(false); // 是否可编辑，保留原样
        jtf_stuName.setBounds(100, 171, 211, 41);
        add(jtf_stuName);

        jtf_stuBirthday = new JTextField();
        jtf_stuBirthday.setColumns(10);
        jtf_stuBirthday.setBounds(100, 263, 211, 41);
        add(jtf_stuBirthday);

        jtf_stuAge = new JTextField();
        jtf_stuAge.setColumns(10);
        jtf_stuAge.setBounds(100, 309, 211, 41);
        add(jtf_stuAge);

        jtf_stuDorm = new JTextField();
        jtf_stuDorm.setColumns(10);
        jtf_stuDorm.setBounds(100, 401, 211, 41);
        add(jtf_stuDorm);

        jb_enter = new JButton("修 改");
        jb_enter.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_enter.setBounds(321, 401, 93, 41);
        add(jb_enter);

        jb_reset = new JButton("重 置");
        jb_reset.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_reset.setBounds(423, 401, 93, 41);
        add(jb_reset);

        ButtonGroup bg_stuGender = new ButtonGroup();
        jrb_man = new JRadioButton("男");
        jrb_man.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_man.setBounds(123, 218, 85, 41);
        add(jrb_man);

        jrb_woman = new JRadioButton("女");
        jrb_woman.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_woman.setBounds(226, 218, 85, 41);
        add(jrb_woman);
        bg_stuGender.add(jrb_man);
        bg_stuGender.add(jrb_woman);

        jcb_stuMajor = new JComboBox<>();
        jcb_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jcb_stuMajor.addItem("");
        jcb_stuMajor.addItem("计算机科学与技术");
        jcb_stuMajor.addItem("物联网工程");
        jcb_stuMajor.setBounds(100, 357, 211, 41);
        add(jcb_stuMajor);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 101, 526, 13);
        add(separator);

        jb_enter.addActionListener(this);
        jb_reset.addActionListener(this);
        jb_search_enter.addActionListener(this);
        jb_search_reset.addActionListener(this);
        jb_upload.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_enter) {
            updateStudentInfo();
        }
        if (e.getSource() == jb_reset) {
            jtf_stuAge.setText("");
            jtf_stuDorm.setText("");
            jtf_stuName.setText("");
            jtf_stuBirthday.setText("");
            jtf_stuName.requestFocus();
            resetImage();
        }
        if (e.getSource() == jb_search_enter) {
            // 实现搜索逻辑
            String studentId = jtf_num.getText().trim();
            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入学号！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            searchStudentById(studentId);
        }
        if (e.getSource() == jb_search_reset) {
            jtf_num.setText("");
        }
        if (e.getSource() == jb_upload) {
            uploadImage();
        }
    }

    private void searchStudentById(String studentId) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT * FROM students WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                jtf_stuNum.setText(rs.getString("student_id"));
                jtf_stuName.setText(rs.getString("name"));
                if (rs.getString("gender").equals("男")) {
                    jrb_man.setSelected(true);
                } else {
                    jrb_woman.setSelected(true);
                }
                jtf_stuBirthday.setText(rs.getString("birthdate"));
                jtf_stuAge.setText(String.valueOf(rs.getInt("age")));
                jcb_stuMajor.setSelectedItem(rs.getString("major"));
                jtf_stuDorm.setText(rs.getString("dormitory"));

                byte[] avatarBytes = rs.getBytes("avatar");
                if (avatarBytes != null) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(avatarBytes);
                    BufferedImage bufferedImage = ImageIO.read(bais);
                    Image avatarImage = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    jl_image.setIcon(new ImageIcon(avatarImage));
                } else {
                    resetImage();
                }
            } else {
                JOptionPane.showMessageDialog(this, "未找到该学生信息！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询学生信息时出错！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedImageFile);
                Image avatarImage = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                jl_image.setIcon(new ImageIcon(avatarImage));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "上传图片失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetImage() {
        try {
            Image img = ImageIO.read(new File("src/main/resources/Img/Add_Stu/menu-user.png"));
            img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            jl_image.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentInfo() {
        String studentId = jtf_stuNum.getText().trim();
        String name = jtf_stuName.getText().trim();
        String gender = jrb_man.isSelected() ? "男" : "女";
        String birthdate = jtf_stuBirthday.getText().trim();
        int age = Integer.parseInt(jtf_stuAge.getText().trim());
        String major = jcb_stuMajor.getSelectedItem().toString().trim();
        String dormitory = jtf_stuDorm.getText().trim();
        byte[] avatarBytes = null;

        if (selectedImageFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedImageFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                avatarBytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "处理图片时出错！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "UPDATE students SET name = ?, gender = ?, birthdate = ?, age = ?, major = ?, dormitory = ?, avatar = ? WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setDate(3, java.sql.Date.valueOf(birthdate));
            pstmt.setInt(4, age);
            pstmt.setString(5, major);
            pstmt.setString(6, dormitory);
            if (avatarBytes != null) {
                pstmt.setBytes(7, avatarBytes);
            } else {
                pstmt.setNull(7, java.sql.Types.BLOB);
            }
            pstmt.setString(8, studentId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "学生信息更新成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "学生信息更新失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新学生信息时出错！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
