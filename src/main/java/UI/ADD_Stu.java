package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sql.DBConfig;

public class ADD_Stu extends JPanel implements ActionListener {
    private final JTextField jtf_stuNum, jtf_stuName, jtf_stuBirthday, jtf_stuAge, jtf_stuDorm;
    private final JButton jb_enter, jb_reset, jb_addImage;
    private final JRadioButton jrb_man, jrb_woman;
    private final JComboBox<String> jcb_stuMajor;
    private final JLabel jl_image;
    private ImageIcon imageIcon;
    public ADD_Stu() {
        setLayout(null);
        this.setSize(526, 461);

        JLabel jl_title = new JLabel("添加学生信息");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(176, 10, 170, 50);
        this.add(jl_title);

        JLabel jl_stuNum = new JLabel("学 号");
        jl_stuNum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuNum.setBounds(45, 71, 79, 36);
        this.add(jl_stuNum);

        JLabel jl_stuName = new JLabel("姓 名");
        jl_stuName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuName.setBounds(45, 125, 79, 36);
        this.add(jl_stuName);

        JLabel jl_stuGender = new JLabel("性 别");
        jl_stuGender.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuGender.setBounds(45, 171, 79, 36);
        this.add(jl_stuGender);

        JLabel jl_stuBirthday = new JLabel("生 日");
        jl_stuBirthday.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuBirthday.setBounds(45, 217, 79, 36);
        this.add(jl_stuBirthday);

        JLabel jl_stuAge = new JLabel("年 龄");
        jl_stuAge.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuAge.setBounds(45, 263, 79, 36);
        this.add(jl_stuAge);

        JLabel jl_stuMajor = new JLabel("专 业");
        jl_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuMajor.setBounds(45, 322, 79, 36);
        this.add(jl_stuMajor);

        JLabel jl_stuDorm = new JLabel("宿 舍");
        jl_stuDorm.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuDorm.setBounds(45, 368, 79, 36);
        this.add(jl_stuDorm);

        jtf_stuNum = new JTextField();
        jtf_stuNum.setColumns(10);
        jtf_stuNum.setBounds(145, 70, 211, 41);
        this.add(jtf_stuNum);

        jtf_stuName = new JTextField();
        jtf_stuName.setColumns(10);
        jtf_stuName.setBounds(145, 124, 211, 41);
        this.add(jtf_stuName);

        jtf_stuBirthday = new JTextField();
        jtf_stuBirthday.setColumns(10);
        jtf_stuBirthday.setBounds(145, 218, 211, 41);
        this.add(jtf_stuBirthday);

        jtf_stuAge = new JTextField();
        jtf_stuAge.setColumns(10);
        jtf_stuAge.setBounds(145, 264, 211, 41);
        this.add(jtf_stuAge);

        jtf_stuDorm = new JTextField();
        jtf_stuDorm.setColumns(10);
        jtf_stuDorm.setBounds(145, 367, 211, 41);
        this.add(jtf_stuDorm);

        jb_enter = new JButton("添 加");
        jb_enter.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_enter.setBounds(142, 414, 93, 41);
        this.add(jb_enter);

        jb_reset = new JButton("重 置");
        jb_reset.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_reset.setBounds(263, 414, 93, 41);
        this.add(jb_reset);

        ButtonGroup bg_stuGender = new ButtonGroup();
        jrb_man = new JRadioButton("男");
        jrb_man.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_man.setSelected(true);
        jrb_man.setBounds(181, 171, 54, 41);
        this.add(jrb_man);

        jrb_woman = new JRadioButton("女");
        jrb_woman.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_woman.setBounds(271, 171, 54, 41);
        this.add(jrb_woman);
        bg_stuGender.add(jrb_man);
        bg_stuGender.add(jrb_woman);

        jcb_stuMajor = new JComboBox<>();
        jcb_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jcb_stuMajor.addItem("请选择");
        jcb_stuMajor.addItem("计算机科学与技术");
        jcb_stuMajor.addItem("物联网工程");
        jcb_stuMajor.setBounds(145, 320, 211, 41);
        this.add(jcb_stuMajor);

        // 图片框
        jl_image = new JLabel();
        jl_image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jl_image.setBounds(400, 70, 100, 100);
        this.add(jl_image);
        // 设置默认头像
        try {
            BufferedImage defaultImage = ImageIO.read(new File("src/main/resources/Img/Add_Stu/menu-user.png"));
            Image scaledImage = defaultImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            jl_image.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 添加图片按钮
        jb_addImage = new JButton("添加图片");
        jb_addImage.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_addImage.setBounds(400, 180, 100, 41);
        this.add(jb_addImage);

        jb_enter.addActionListener(this);
        jb_reset.addActionListener(this);
        jb_addImage.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_reset) {
            reset();
        } else if (e.getSource() == jb_addImage) {
            addImage();
        } else if (e.getSource() == jb_enter) {
            insertStudentInfo();
        }
    }

    public void reset() {
        jtf_stuNum.setText("");
        jtf_stuAge.setText("");
        jtf_stuDorm.setText("");
        jrb_man.setSelected(true);
        jtf_stuName.setText("");
        jtf_stuBirthday.setText("");
        jcb_stuMajor.setSelectedIndex(0);
        jl_image.setIcon(null);
        jtf_stuNum.requestFocus();
    }

    public void addImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            // 这里可以根据需要调整图片的尺寸
            Image img = imageIcon.getImage();
            Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
            jl_image.setIcon(imageIcon);
        }
    }

    private void insertStudentInfo() {
        String studentId = jtf_stuNum.getText().trim();
        String name = jtf_stuName.getText().trim();
        String gender = jrb_man.isSelected() ? "男" : "女";
        String birthdate = jtf_stuBirthday.getText().trim();
        String ageText = jtf_stuAge.getText().trim();
        String major = (String) jcb_stuMajor.getSelectedItem();
        String dormitory = jtf_stuDorm.getText().trim();

        // 检查输入字段是否为空
        if (studentId.isEmpty() || name.isEmpty() || birthdate.isEmpty() || ageText.isEmpty() || major.equals("请选择") || dormitory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段！");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须是数字！");
            return;
        }

        byte[] avatar = imageIcon != null ? imageToBytes(imageIcon.getImage()) : null;

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "INSERT INTO students (student_id, name, gender, birthdate, age, major, dormitory, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setDate(4, java.sql.Date.valueOf(birthdate));
            pstmt.setInt(5, age);
            pstmt.setString(6, major);
            pstmt.setString(7, dormitory);
            pstmt.setBytes(8, avatar);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "学生信息已成功添加！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "添加学生信息失败！");
        }
    }


    // 将Image转换为字节数组的方法
    private byte[] imageToBytes(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("学生信息管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setContentPane(new ADD_Stu());
        frame.setVisible(true);
    }
}
