package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sql.DBConfig;

public class Select_Stu extends JPanel implements ActionListener {
    private final JTable table;
    private final JTextField jtf_id, jtf_name, jft_go;
    private final JLabel jl_gong;
    private final JButton jb_first, jb_up, jb_down, jb_search, jb_jump;
    private final DefaultTableModel stuTable;
    private final JButton jb_last;
    private static final String[] columnNames = { "头像", "学号", "姓名", "性别", "生日", "年龄", "系别", "宿舍" };

    public Select_Stu() {
        setLayout(null);
        setSize(800, 600); // 增加界面大小

        JLabel jl_title = new JLabel("学生信息查询");
        jl_title.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        jl_title.setBounds(300, 10, 200, 34);
        add(jl_title);

        // 表格显示
        Object[][] result = {};
        stuTable = new DefaultTableModel(result, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }
        };

        table = new JTable(stuTable);
        setWidth();
        table.setEnabled(false);
        JScrollPane line = new JScrollPane(table);
        line.setBounds(20, 150, 750, 400); // 调整表格大小和位置
        add(line);

        JLabel jl_id = new JLabel("学 号");
        jl_id.setFont(new Font("微软雅黑", Font.PLAIN, 18)); // 增加字体大小
        jl_id.setBounds(30, 80, 60, 30); // 调整位置和大小
        add(jl_id);

        JLabel jl_name = new JLabel("姓 名");
        jl_name.setBounds(300, 80, 60, 30);
        jl_name.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        add(jl_name);

        jtf_id = new JTextField();
        jtf_id.setBounds(100, 80, 150, 30);
        jtf_id.setColumns(10);
        add(jtf_id);

        jtf_name = new JTextField();
        jtf_name.setColumns(10);
        jtf_name.setBounds(370, 80, 150, 30);
        add(jtf_name);

        jb_search = new JButton("搜 索");
        jb_search.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_search.setBounds(550, 80, 100, 30);
        jb_search.addActionListener(this);
        add(jb_search);

        jb_first = new JButton("首页");
        jb_first.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_first.setBounds(20, 560, 80, 30);
        add(jb_first);

        jb_up = new JButton("上页");
        jb_up.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_up.setBounds(120, 560, 80, 30);
        add(jb_up);

        jb_down = new JButton("下页");
        jb_down.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_down.setBounds(220, 560, 80, 30);
        add(jb_down);

        jb_last = new JButton("尾页");
        jb_last.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_last.setBounds(320, 560, 80, 30);
        add(jb_last);

        jft_go = new JTextField();
        jft_go.setBounds(580, 560, 40, 30);
        add(jft_go);
        jft_go.setColumns(10);

        jb_jump = new JButton("跳转");
        jb_jump.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_jump.setBounds(640, 560, 80, 30);
        add(jb_jump);

        JLabel jl_di = new JLabel("第");
        jl_di.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_di.setBounds(540, 560, 20, 30);
        add(jl_di);

        JLabel jl_ye = new JLabel("页");
        jl_ye.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_ye.setBounds(620, 560, 20, 30);
        add(jl_ye);

        jl_gong = new JLabel("当前第  1 页 共  1 页");
        jl_gong.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_gong.setBounds(420, 560, 200, 30);
        add(jl_gong);
    }

    private void setWidth() {
        table.setRowHeight(40);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.setRowSelectionAllowed(false);
    }

    private List<Object[]> fetchData(String studentId, String studentName) {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT student_id, name, gender, birthdate, age, major, dormitory, avatar FROM students WHERE student_id LIKE ? AND name LIKE ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + studentId + "%");
            pstmt.setString(2, "%" + studentName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("student_id");
                    String name = rs.getString("name");
                    String gender = rs.getString("gender");
                    String birthdate = rs.getDate("birthdate").toString();
                    int age = rs.getInt("age");
                    String major = rs.getString("major");
                    String dormitory = rs.getString("dormitory");
                    byte[] avatarBytes = rs.getBytes("avatar");

                    ImageIcon avatar = null;
                    if (avatarBytes != null) {
                        try {
                            BufferedImage img = ImageIO.read(new ByteArrayInputStream(avatarBytes));
                            if (img != null) {
                                Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                                avatar = new ImageIcon(scaledImg);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    data.add(new Object[]{avatar, id, name, gender, birthdate, age, major, dormitory});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_search) {
            String studentId = jtf_id.getText();
            String studentName = jtf_name.getText();
            List<Object[]> data = fetchData(studentId, studentName);
            Object[][] dataArray = data.toArray(new Object[0][]);
            stuTable.setDataVector(dataArray, columnNames);
            setWidth();
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("学生信息查询系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700); // 增加主框架大小
        frame.setContentPane(new Select_Stu());
        frame.setVisible(true);
    }
}
