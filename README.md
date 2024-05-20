## 配置文件
+ 在 `database.properties` 把`stu_management`换成自己的数据库

## 创建表
```mysql
-- 管理员表
CREATE TABLE IF NOT EXISTS admin
(
    id       INT AUTO_INCREMENT PRIMARY KEY, -- 管理员ID，自动递增，主键
    username VARCHAR(20)  NOT NULL UNIQUE,   -- 用户名，长度不超过20，不能为空，唯一
    password VARCHAR(255) NOT NULL           -- 密码，长度不超过255，不能为空
);

-- 学生表
CREATE TABLE IF NOT EXISTS students
(
    student_id VARCHAR(20)       NOT NULL, -- 学生ID，长度不超过20，不能为空，主键
    name       VARCHAR(50)       NOT NULL, -- 学生姓名，长度不超过50，不能为空
    gender     ENUM ('男', '女') NOT NULL, -- 性别，只能为'男'或'女'，不能为空
    birthdate  DATE              NOT NULL, -- 出生日期，不能为空
    age        INT               NOT NULL, -- 年龄，不能为空
    major      VARCHAR(100)      NOT NULL, -- 专业，长度不超过100，不能为空
    dormitory  VARCHAR(100),               -- 宿舍地址，长度不超过100，可以为空
    avatar LONGBLOB,                     -- 头像，二进制数据，可以为空
    PRIMARY KEY (student_id)               -- 设置student_id为主键
);

```
