package UI;

import sql.DBConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvatarRefresher {

    // 默认头像的字节数组
    private byte[] defaultAvatarBytes;

    // 构造函数，在实例化对象时加载默认头像
    public AvatarRefresher() {
        loadDefaultAvatar();
    }

    // 加载默认头像图片并转换为字节数组
    private void loadDefaultAvatar() {
        try {
            // 读取默认头像文件
            BufferedImage defaultAvatar = ImageIO.read(new File("src/main/resources/Img/Add_Stu/menu-user.png"));
            // 创建字节数组输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 将图片写入字节数组输出流
            ImageIO.write(defaultAvatar, "png", baos);
            // 将字节数组输出流转换为字节数组
            defaultAvatarBytes = baos.toByteArray();
        } catch (IOException e) {
            // 捕获并打印IO异常
            e.printStackTrace();
        }
    }

    // 刷新数据库，为缺少头像的学生设置默认头像
    public void refreshDatabase() {
        // 查询缺少头像的学生ID
        String selectSql = "SELECT student_id FROM students WHERE avatar IS NULL OR avatar = ''";
        // 更新学生头像的SQL语句
        String updateSql = "UPDATE students SET avatar = ? WHERE student_id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // 执行查询语句
            try (ResultSet rs = selectStmt.executeQuery()) {
                // 遍历结果集
                while (rs.next()) {
                    // 获取学生ID
                    String studentId = rs.getString("student_id");
                    // 设置更新语句中的参数
                    updateStmt.setBytes(1, defaultAvatarBytes);
                    updateStmt.setString(2, studentId);
                    // 执行更新语句
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // 捕获并打印SQL异常
            e.printStackTrace();
        }
    }
}
