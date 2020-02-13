mylexer:
	cd src/java/cyr7/lexer; rm -f MyLexer.java; jflex xi.flex

myparser:
	java -jar dependencies/java_cup.jar -locations -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

myparser.oc:
	java -jar dependencies/java-cup-11b.jar -nowarn -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

cowabunga: myparser mylexer

xth.lexer:
	xth --testpath ~/xth/tests/pa1 --compilerpath ~/shared/cowabunga/ ~/xth/tests/pa1/xthScript
	xth --testpath ~/shared/cowabunga/tests/resources/lexer/custom/ --compilerpath ~/shared/cowabunga/ ~/shared/cowabunga/tests/resources/lexer/custom/xthScript
	xth --testpath ~/shared/cowabunga/tests/resources/lexer/pa1/ --compilerpath ~/shared/cowabunga/ ~/shared/cowabunga/tests/resources/lexer/pa1/xthScript

xth.parser:
	xth --testpath ~/xth/tests/pa2 --compilerpath ~/shared/cowabunga/ ~/xth/tests/pa2/xthScript

zip:
	rm cowabunga.zip
	zip -r cowabunga.zip \
		README.md dependencies src tests xic-build build.gradle Makefile settings.gradle \
		-x *.DS_Store* -x *MyLexer.java*
