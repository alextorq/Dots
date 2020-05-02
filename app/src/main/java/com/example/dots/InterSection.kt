package com.example.dots

//bool Circle::cCircle(Line l)
//{
//    double x1=l.getX();
//    double y1=l.getY();
//    double x2=l.getX2();
//    double y2=l.getY2();
//    double x4=((x2-x1)*(y2-y1)*(cy-y1)+x1*(y2-y1)*(y2-y1)+cx*(x2-x1)*(x2-x1))/((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
//    double y4=(y2-y1)*(x4-x1)/(x2-x1)+y1;
//    double s1=dist(cx,cy,x1,y1);
//    double s2=dist(cx,cy,x2,y2);
//    double s3=dist(cx,cy,x4,y4);
//    if ((s1<r) && (s2<r)) {return false;}
//    if (y1<y2){
//        if (cy+r<=y1 && cy-r>=y2){return false;}
//    }
//    if (y1>y2){
//        if (cy+r<=y2 && cy-r>=y1){return false;}
//    }
//    if (cx+r<=x1) {return false;}
//    if (cx-r>=x2) {return false;}
//    if (y1==y2 && x1==x2) {
//        if(s1<r){return true;}
//    }
//    if (r>s1){return true;}
//    if (r>s2){return true;}
//    if (r>s3) {return true;}
//    else {return false;}
//}