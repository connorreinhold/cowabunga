main(args: int[][]) {try(1000);}// Copy propagation in loops
try(count:int){i: int = 0;a: int;while (i < 100000) {if count==0 {return;}
b:int=a;c:int=b;d:int=c;e:int=d;f:int=e;g:int=f;h:int=g;x:int=h;y:int=x;w:int=y;
b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;b=13;
c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;c=13;
d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;d=13;
e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;e=13;
f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;f=13;
g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;g=13;
h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;h=13;
w=p(0,0,0);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);
w=p(w,w,w);w=p(w,d,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);
w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);w=p(w,w,w);
a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;a=d;
d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;d=d;
i = i + a;}a = p(1,2,3);a = p(3,2,1);a = p(1,3,2);a = p(2,3,1);a = p(2,1,3);
a = p(3,1,2);z:int=a;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;z=z;
z=z;z=z;z=z;if (z != 6) z = {}[0];else{try(count-1);}}p(a: int, b: int, c: int):
int {v:int=b;w:int=a;x:int=a;y:int=b;z:int=c;d1:int=a;d2:int=a;d4c:int=a;t:int=a
diamond:int=4;is:int=4;unbreakable:int=4;bizarre:int=8;j:int=4; return x+y+z;}