use conv
use io
main(args: int[][]) {
checker: int[] = {0} ones: int = 0 tens: int = 0 hundreds: int = 0
thousands: int = 0 tenthousands: int = 0 hundredthousands: int = 0
counter: int = 0 while hundredthousands < 3000 { while tenthousands < 10 {
while thousands < 10 { while hundreds < 10 { while tens < 10 {
while ones < 10  { ones = ones + 1 counter = counter + 1
x: int = checker[ones + 10 * tens + 100 * hundreds + 1000 * thousands + 10000
* tenthousands + 100000 * hundredthousands - counter]
} ones = 0 tens = tens + 1 } tens = 0 hundreds = hundreds + 1
} hundreds = 0 thousands = thousands + 1 } thousands = 0 tenthousands =
tenthousands + 1 } tenthousands = 0 hundredthousands = hundredthousands + 1 } }