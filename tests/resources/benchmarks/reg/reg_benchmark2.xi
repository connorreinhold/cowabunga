use io use conv main(args: int[][]) {z: int = 0 counter: int = 0
while (z < 50000000) { a: int = 5 b: int = a-5 c: int = a+b-1
d: int = a+b-c+2 e: int = a-b+c*d+3 f: int = a-b+c*d-e+4
g: int = a-b+c*d-e*f+5 h: int = a-b+c*d-e*f-g-6 i: int = a-b+c*d-e*f-g / h-7
j: int = a-b+c*d-e*f-g / h+i-8 k: int = 5 l: int = k-5 n: int = k+l-1
o: int = k+l-n+2 p: int = k-l+n*o+3 q: int = k-l+n*o-p+4 r: int = k-l+n*o-p*q+5
s: int = k-l+n*o-p*q-r-6 t: int = k-l+n*o-p*q-r / s-7
u: int = k-l+n*o-p*q-r / s+t-8 counter = counter+u+j z = z+1}}