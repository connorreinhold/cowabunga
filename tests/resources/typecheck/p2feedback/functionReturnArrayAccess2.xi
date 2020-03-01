foo(foo:int) : bool {
     if (foo < 0) {
     return foo(foo(0)[0])[0]
     }
     else {
     return true;
     }
 }