# cs4120

As cowabungans, we make compilers in Java

## Frequent Commands

Build jar: `mvn package`
Build compiler: `xic-build`
Run compiler: `xic [args]`

Build jflex: `cd ~/shared/cowabunga/src/java/cyr7/lexer; jflex xi.flex && sed -i '1i package cyr7.lexer;' MyLexer.java`
