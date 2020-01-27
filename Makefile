mylexer:
	rm ~/shared/cowabunga/src/java/cyr7/lexer/MyLexer.java;
	cd ~/shared/cowabunga/src/java/cyr7/lexer; jflex xi.flex && sed -i '1i package cyr7.lexer;' MyLexer.java
