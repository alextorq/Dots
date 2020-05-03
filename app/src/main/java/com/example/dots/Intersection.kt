package com.example.dots

import android.graphics.Point

class Intersection {

    fun check(center: Point, radius: Float, p1: Point, p2: Point): Boolean {

        val x01 = p1.x - center.x;
        val y01 = p1.y - center.y;

        val x02 = p2.x - center.x;
        val y02 = p2.y - center.y;

        val dx = x02 - x01;
        val dy = y02 - y01;

        val a = dx*dx + dy*dy;
        val b = 2.0f * (x01 * dx + y01 * dy);
        val c = x01*x01 + y01*y01 - radius*radius;

        if(-b < 0) {
            return (c < 0);
        }
        if(-b < (2.0f * a)){
            return (4.0f * a*c - b*b < 0);
        }
        return (a + b + c < 0);
    }

}