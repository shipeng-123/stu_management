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

    private byte[] defaultAvatarBytes;

    public AvatarRefresher() {
        loadDefaultAvatar();
    }

    private void loadDefaultAvatar() {
        try {
            BufferedImage defaultAvatar = ImageIO.read(new File("src/main/resources/Img/Add_Stu/menu-user.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(defaultAvatar, "png", baos);
            defaultAvatarBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshDatabase() {
        String selectSql = "SELECT student_id FROM students WHERE avatar IS NULL OR avatar = ''";
        String updateSql = "UPDATE students SET avatar = ? WHERE student_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    String studentId = rs.getString("student_id");
                    updateStmt.setBytes(1, defaultAvatarBytes);
                    updateStmt.setString(2, studentId);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
