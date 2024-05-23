package Tool;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import java.awt.*;

public class Code {
    private LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 30, 4, 20);

    //获取初始验证图片
    public Image codeImage() {
        //System.out.println(lineCaptcha.getCode());
        return lineCaptcha.createImage(lineCaptcha.getCode());
    }

    //重新生成验证码和图片
    public Image newCodeImage() {
        lineCaptcha.createCode();
        //System.out.println(lineCaptcha.getCode());
        return lineCaptcha.createImage(lineCaptcha.getCode());
    }

    //验证验证是否正确
    public Boolean verify(String code) {
        //System.out.println("code:"+code);
        return lineCaptcha.verify(code);
    }

}
