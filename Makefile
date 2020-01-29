mylexer:
	cd src/java/cyr7/lexer; rm MyLexer.java; jflex xi.flex && sed -i '1i package cyr7.lexer;' MyLexer.java
