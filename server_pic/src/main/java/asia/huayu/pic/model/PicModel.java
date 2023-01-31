package asia.huayu.pic.model;

import java.awt.*;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 * 图片实体类
 */
public class PicModel {
    private String str;
    private int width;
    private int height;
    private Font font;
    private Color bg;
    private Color fontColor;
    private String path;

    public PicModel(String str, int width, int height, Font font, Color bg, Color fontColor, String path) {
        super();
        this.str = str;
        this.width = width;
        this.height = height;
        this.font = font;
        this.bg = bg;
        this.fontColor = fontColor;
        this.path = path;
    }

    @Override
    public String toString() {
        return "PicModel [str=" + str + ", width=" + width + ", height=" + height + ", font=" + font + ", bg=" + bg
                + ", fontColor=" + fontColor + ", path=" + path + "]";
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getwidth() {
        return width;
    }

    public void setwidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getBg() {
        return bg;
    }

    public void setBg(Color bg) {
        this.bg = bg;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

