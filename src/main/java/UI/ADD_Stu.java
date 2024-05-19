package UI;

// 导入必要的类
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
    // 定义表单字段
    private final JTextField jtf_stuNum, jtf_stuName, jtf_stuBirthday, jtf_stuAge, jtf_stuDorm;
    private final JButton jb_enter, jb_reset, jb_addImage;
    private final JRadioButton jrb_man, jrb_woman;
    private final JComboBox<String> jcb_stuMajor;
    private final JLabel jl_image;
    private ImageIcon imageIcon;

    // 构造函数，初始化面板和组件
    public ADD_Stu() {
        setLayout(null);
        this.setSize(600, 500);

        // 添加标题标签
        JLabel jl_title = new JLabel("添加学生信息");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(200, 10, 200, 50);
        this.add(jl_title);

        // 添加学号标签和文本框
        JLabel jl_stuNum = new JLabel("学 号");
        jl_stuNum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuNum.setBounds(50, 80, 60, 30);
        this.add(jl_stuNum);

        jtf_stuNum = new JTextField();
        jtf_stuNum.setBounds(130, 80, 200, 30);
        this.add(jtf_stuNum);

        // 添加姓名标签和文本框
        JLabel jl_stuName = new JLabel("姓 名");
        jl_stuName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuName.setBounds(50, 130, 60, 30);
        this.add(jl_stuName);

        jtf_stuName = new JTextField();
        jtf_stuName.setBounds(130, 130, 200, 30);
        this.add(jtf_stuName);

        // 添加性别标签和单选按钮
        JLabel jl_stuGender = new JLabel("性 别");
        jl_stuGender.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuGender.setBounds(50, 180, 60, 30);
        this.add(jl_stuGender);

        ButtonGroup bg_stuGender = new ButtonGroup();
        jrb_man = new JRadioButton("男");
        jrb_man.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_man.setSelected(true);
        jrb_man.setBounds(130, 180, 60, 30);
        this.add(jrb_man);

        jrb_woman = new JRadioButton("女");
        jrb_woman.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jrb_woman.setBounds(200, 180, 60, 30);
        this.add(jrb_woman);
        bg_stuGender.add(jrb_man);
        bg_stuGender.add(jrb_woman);

        // 添加生日标签和文本框
        JLabel jl_stuBirthday = new JLabel("生 日");
        jl_stuBirthday.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuBirthday.setBounds(50, 230, 60, 30);
        this.add(jl_stuBirthday);

        jtf_stuBirthday = new JTextField();
        jtf_stuBirthday.setBounds(130, 230, 200, 30);
        this.add(jtf_stuBirthday);

        // 添加年龄标签和文本框
        JLabel jl_stuAge = new JLabel("年 龄");
        jl_stuAge.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuAge.setBounds(50, 280, 60, 30);
        this.add(jl_stuAge);

        jtf_stuAge = new JTextField();
        jtf_stuAge.setBounds(130, 280, 200, 30);
        this.add(jtf_stuAge);

        // 添加专业标签和下拉框
        JLabel jl_stuMajor = new JLabel("专 业");
        jl_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuMajor.setBounds(50, 330, 60, 30);
        this.add(jl_stuMajor);

        jcb_stuMajor = new JComboBox<>();
        jcb_stuMajor.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jcb_stuMajor.addItem("请选择");
        jcb_stuMajor.addItem("计算机科学与技术");
        jcb_stuMajor.addItem("物联网工程");
        jcb_stuMajor.addItem("电子信息工程");
        jcb_stuMajor.addItem("机械工程");
        jcb_stuMajor.addItem("土木工程");
        jcb_stuMajor.addItem("生物医学工程");
        jcb_stuMajor.addItem("材料科学与工程");
        jcb_stuMajor.addItem("化学工程与工艺");
        jcb_stuMajor.addItem("环境工程");
        jcb_stuMajor.setBounds(130, 330, 200, 30);
        this.add(jcb_stuMajor);

        // 添加宿舍标签和文本框
        JLabel jl_stuDorm = new JLabel("宿 舍");
        jl_stuDorm.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jl_stuDorm.setBounds(50, 380, 60, 30);
        this.add(jl_stuDorm);

        jtf_stuDorm = new JTextField();
        jtf_stuDorm.setBounds(130, 380, 200, 30);
        this.add(jtf_stuDorm);

        // 添加确认按钮
        jb_enter = new JButton("添 加");
        jb_enter.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_enter.setBounds(360, 80, 100, 30);
        this.add(jb_enter);

        // 添加重置按钮
        jb_reset = new JButton("重 置");
        jb_reset.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jb_reset.setBounds(360, 130, 100, 30);
        this.add(jb_reset);

        // 添加图片框
        jl_image = new JLabel();
        jl_image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jl_image.setBounds(360, 180, 100, 100);
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
        jb_addImage.setBounds(360, 300, 100, 30);
        this.add(jb_addImage);

        // 为按钮添加动作监听器
        jb_enter.addActionListener(this);
        jb_reset.addActionListener(this);
        jb_addImage.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_reset) {
            // 处理重置按钮点击事件
            reset();
        } else if (e.getSource() == jb_addImage) {
            // 处理添加图片按钮点击事件
            addImage();
        } else if (e.getSource() == jb_enter) {
            // 处理确认按钮点击事件
            insertStudentInfo();
        }
    }

    // 重置表单字段的方法
    public void reset() {
        jtf_stuNum.setText("");
        jtf_stuAge.setText("");
        jtf_stuDorm.setText("");
        jrb_man.setSelected(true);
        jtf_stuName.setText("");
        jtf_stuBirthday.setText("");
        jcb_stuMajor.setSelectedIndex(0);
        jl_image.setIcon(imageIcon);
        jtf_stuNum.requestFocus();
    }

    // 添加图片的方法
    public void addImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            // 调整图片尺寸
            Image img = imageIcon.getImage();
            Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImg);
            jl_image.setIcon(imageIcon);
        }
    }

    // 插入学生信息的方法
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
}
