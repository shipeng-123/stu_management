package UI;

import sql.DBConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class Select_Stu extends JPanel implements ActionListener {
    private final JTable table;
    private final JTextField jtf_id, jtf_name, jft_go;
    private final JLabel jl_gong;
    private final JButton jb_first, jb_up, jb_down, jb_search, jb_jump, jb_edit, jb_delete;
    private final DefaultTableModel stuTable;
    private final JButton jb_last;
    private static final String[] columnNames = { "头像", "学号", "姓名", "性别", "生日", "年龄", "系别", "宿舍" };

    private int currentPage = 1;
    private int totalPage = 1;
    private final int pageSize = 10;

    public Select_Stu() {
        setLayout(null);
        setSize(900, 700); // 增加界面大小

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
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 允许单行选择

        JScrollPane line = new JScrollPane(table);
        line.setBounds(20, 150, 850, 400); // 调整表格大小和位置
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
        jb_first.addActionListener(this);
        add(jb_first);

        jb_up = new JButton("上页");
        jb_up.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_up.setBounds(120, 560, 80, 30);
        jb_up.addActionListener(this);
        add(jb_up);

        jb_down = new JButton("下页");
        jb_down.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_down.setBounds(220, 560, 80, 30);
        jb_down.addActionListener(this);
        add(jb_down);

        jb_last = new JButton("尾页");
        jb_last.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_last.setBounds(320, 560, 80, 30);
        jb_last.addActionListener(this);
        add(jb_last);

        jft_go = new JTextField();
        jft_go.setBounds(600, 560, 40, 30);
        add(jft_go);
        jft_go.setColumns(10);

        jb_jump = new JButton("跳转");
        jb_jump.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_jump.setBounds(660, 560, 80, 30);
        jb_jump.addActionListener(this);
        add(jb_jump);

        JLabel jl_di = new JLabel("第");
        jl_di.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_di.setBounds(580, 560, 20, 30);
        add(jl_di);

        JLabel jl_ye = new JLabel("页");
        jl_ye.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_ye.setBounds(640, 560, 20, 30);
        add(jl_ye);

        jl_gong = new JLabel("当前第 1 页 共 1 页");
        jl_gong.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jl_gong.setBounds(420, 560, 200, 30);
        add(jl_gong);

        jb_edit = new JButton("编辑");
        jb_edit.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_edit.setBounds(640, 600, 80, 30);
        jb_edit.setEnabled(false);
        add(jb_edit);

        jb_delete = new JButton("删除");
        jb_delete.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        jb_delete.setBounds(740, 600, 80, 30);
        jb_delete.setEnabled(false);
        add(jb_delete);

        // 添加表格行选择事件监听器
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    jb_edit.setEnabled(true);
                    jb_delete.setEnabled(true);
                } else {
                    jb_edit.setEnabled(false);
                    jb_delete.setEnabled(false);
                }
            }
        });

        jb_edit.addActionListener(this);
        jb_delete.addActionListener(this);

        // 初始渲染所有学生信息
        refreshTable();
    }

    private void setWidth() {
        table.setRowHeight(40);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(60);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(200);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.setRowSelectionAllowed(true);
    }

    private List<Object[]> fetchData(String studentId, String studentName, int page) {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT student_id, name, gender, birthdate, age, major, dormitory, avatar FROM students " +
                "WHERE student_id LIKE ? AND name LIKE ? LIMIT ?, ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + studentId + "%");
            pstmt.setString(2, "%" + studentName + "%");
            pstmt.setInt(3, (page - 1) * pageSize);
            pstmt.setInt(4, pageSize);
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
                                Image scaledImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
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

    private int getTotalPage(String studentId, String studentName) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM students WHERE student_id LIKE ? AND name LIKE ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + studentId + "%");
            pstmt.setString(2, "%" + studentName + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_search) {
            currentPage = 1;
            refreshTable();
        } else if (e.getSource() == jb_first) {
            currentPage = 1;
            refreshTable();
        } else if (e.getSource() == jb_up) {
            if (currentPage > 1) {
                currentPage--;
                refreshTable();
            }
        } else if (e.getSource() == jb_down) {
            if (currentPage < totalPage) {
                currentPage++;
                refreshTable();
            }
        } else if (e.getSource() == jb_last) {
            currentPage = totalPage;
            refreshTable();
        } else if (e.getSource() == jb_jump) {
            int page = Integer.parseInt(jft_go.getText());
            if (page >= 1 && page <= totalPage) {
                currentPage = page;
                refreshTable();
            }
        } else if (e.getSource() == jb_edit) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String studentId = table.getValueAt(selectedRow, 1).toString();
                Window window = SwingUtilities.getWindowAncestor(this);
                JDialog modifyDialog;
                if (window instanceof Frame) {
                    modifyDialog = new JDialog((Frame) window, "修改学生信息", true);
                } else if (window instanceof Dialog) {
                    modifyDialog = new JDialog((Dialog) window, "修改学生信息", true);
                } else {
                    throw new IllegalStateException("Unexpected window ancestor");
                }
                modifyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                modifyDialog.setSize(600, 500);
                modifyDialog.setLocationRelativeTo(this);
                modifyDialog.setContentPane(new Modify_stu(studentId, new Modify_stu.RefreshTableListener() {
                    @Override
                    public void refreshTable() {
                        Select_Stu.this.refreshTable();
                    }
                }));
                modifyDialog.setVisible(true);
            }
        } else if (e.getSource() == jb_delete) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String studentId = table.getValueAt(selectedRow, 1).toString();
                String studentName = table.getValueAt(selectedRow, 2).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "您确定删除学号: " + studentId + " 姓名: " + studentName + " 的学生信息吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteStudent(studentId);
                }
            }
        }
    }

    private void deleteStudent(String studentId) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "学生信息删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "学生信息删除失败！", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "删除学生信息时出错！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        String studentId = jtf_id.getText();
        String studentName = jtf_name.getText();
        totalPage = getTotalPage(studentId, studentName);
        List<Object[]> data = fetchData(studentId, studentName, currentPage);
        Object[][] dataArray = data.toArray(new Object[0][]);
        stuTable.setDataVector(dataArray, columnNames);
        setWidth();
        jl_gong.setText("当前第 " + currentPage + " 页 共 " + totalPage + " 页");
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("学生信息查询系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700); // 增加主框架大小
        frame.setContentPane(new Select_Stu());
        frame.setVisible(true);
    }
}
