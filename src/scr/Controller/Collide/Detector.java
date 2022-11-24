package scr.Controller.Collide;

/**
 * 检测器
 */
public class Detector implements Collider{

    protected ColliderShape shape;

    /**
     * 点和矩形碰撞
     */
    public boolean isPointWithRect(int x1, int y1, int x2, int y2, int w, int h) {
        if (x1 >= x2 && x1 <= x2 + w && y1 >= y2 && y1 <= y2 + h) {
            return true;
        }
        return false;
    }

    /**
     * 检测两个矩形是否碰撞
     */
    public boolean isCollisionWithRect(int x1, int y1, int w1, int h1,
                                       int x2,int y2, int w2, int h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        return true;
    }

    /**
     *  点和圆心
     */
    public boolean isPointWithOval(int x1,int y1,int x2,int y2,int r)
    {
        if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) <= r) {
            // 如果点和圆心距离小于或等于半径则认为发生碰撞
            return true;
        }
        return false;
    }

    /**
     * 圆和圆
     */
    private boolean isCollisionWithCircle(int x1, int y1, int x2, int y2,
                                          int r1, int r2) {
        // Math.sqrt:开平方
        // Math.pow(double x, double y): X的Y次方
        //直角坐标系，依点1和点2做平行线，|x1-x2|为横向直角边，|y1-y2|为纵向直角边 依勾股定理 c^2=a^2+b^2
        if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) <= r1 + r2) {
            // 如果两圆的圆心距小于或等于两圆半径和则认为发生碰撞
            return true;
        }
        return false;
    }


    public boolean isIntersect() {
        return false;
    }
}
