package scr.LogicalProcessing.Collide;

/**
 *
 * 检测器
 */
public abstract class Detector{
    /**
     * 点和矩形碰撞
     */
    private boolean isPointWithRect(int x1, int y1, int x2, int y2, int w, int h) {
        if (x1 >= x2 && x1 <= x2 + w && y1 >= y2 && y1 <= y2 + h) {
            return true;
        }
        return false;
    }

    /**
     * 检测两个矩形是否碰撞
     */
    private boolean isCollisionWithRect(int x1, int y1, int w1, int h1,
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
    private boolean isPointWithOval(int x1,int y1,int x2,int y2,int r)
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

    private boolean isArcRectCollides(int arcX, int arcY,
                                                  int arcR, int rectX, int rectY, int rectW, int rectH) {
        int arcOx = arcX + arcR;// 圆心X坐标
        int arcOy = arcY + arcR;// 圆心Y坐标
        if (((rectX - arcOx) * (rectX - arcOx) + (rectY - arcOy)
                * (rectY - arcOy)) <= arcR * arcR)
            return true;
        if (((rectX + rectW - arcOx) * (rectX + rectW - arcOx) + (rectY - arcOy)
                * (rectY - arcOy)) <= arcR * arcR)
            return true;
        if (((rectX - arcOx) * (rectX - arcOx) + (rectY + rectH - arcOy)
                * (rectY + rectH - arcOy)) <= arcR * arcR)
            return true;
        if (((rectX + rectW - arcOx) * (rectX + rectW - arcOx) + (rectY + rectH - arcOy)
                * (rectY + rectH - arcOy)) <= arcR * arcR)
            return true;

        // 分别判断矩形4个顶点与圆心的距离是否<=圆半径；如果<=，说明碰撞成功
        int minDisX = 0;
        if (arcOy >= rectY && arcOy <= rectY + rectH) {
            if (arcOx < rectX)
                minDisX = rectX - arcOx;
            else if (arcOx > rectX + rectW)
                minDisX = arcOx - rectX - rectW;
            else
                return true;
            if (minDisX <= arcR)
                return true;
        }// 判断当圆心的Y坐标进入矩形内时X的位置，如果X在(rectX-arcR)到(rectX+rectW+arcR)这个范围内，则碰撞成功

        int minDisY = 0;
        if (arcOx >= rectX && arcOx <= rectX + rectW) {
            if (arcOy < rectY)
                minDisY = rectY - arcOy;
            else if (arcOy > rectY + rectH)
                minDisY = arcOy - rectY - rectH;
            else
                return true;
            if (minDisY <= arcR)
                return true;
        }// 判断当圆心的X坐标进入矩形内时Y的位置，如果X在(rectY-arcR)到(rectY+rectH+arcR)这个范围内，则碰撞成功
        return false;
    }
    /**
     *
     * @param s1 源
     * @param s2 对象
     * @return 源是否与对象碰撞
     */
    public boolean isIntersect(ShapeProperty s1, ShapeProperty s2) {
        return switch (s1.shape) {
            case Point -> switch (s2.shape) {
                case Point -> s1.x == s2.x && s1.y == s2.y;
                case Rect -> isPointWithRect(s1.x, s1.y, s2.x, s2.y, s2.w, s2.h);
                case Oval -> isPointWithOval(s1.x, s1.y, s2.x, s2.y, s2.r);
            };
            case Oval -> switch (s2.shape) {
                case Rect -> isArcRectCollides(s1.x, s1.y, s1.r, s2.x, s2.y, s2.w, s2.h);
                case Oval -> isCollisionWithCircle(s1.x, s1.y, s2.x, s2.y, s1.r, s2.r);
                case Point -> isPointWithOval(s2.x, s2.y, s1.x, s1.y, s1.r);
            };
            case Rect -> switch (s2.shape) {
                case Rect -> isCollisionWithRect(s1.x, s1.y, s1.w, s1.h, s2.x, s2.y, s2.w, s2.h);
                case Oval -> isArcRectCollides(s2.x, s2.y, s2.r, s1.x, s1.y, s1.w, s1.h);
                case Point -> isPointWithRect(s2.x, s2.y, s1.x, s1.y, s1.w, s1.h);
            };
        };
    }
}
