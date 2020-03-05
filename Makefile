mylexer:
	cd src/java/cyr7/lexer; rm -f MyLexer.java; jflex xi.flex

myparser:
	java -jar dependencies/java_cup.jar -locations -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

cowabunga: myparser mylexer

test: 
	./xic-build -ea \
	&& gradle test \
	&& cd \
	&& python3 ~/shared/cowabunga/tests/xth/build.py \
	&& xth -testpath . -workpath .  -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptAll

gradle.test:
	./xic-build -ea && gradle test

xth.test:
	./xic-build -ea \
	&& cd \
	&& python3 ~/shared/cowabunga/tests/xth/build.py \
	&& xth -testpath . -workpath .  -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptAll

zip:
	rm cowabunga.zip
	zip -r cowabunga.zip \
		README.md dependencies src tests xic-build build.gradle Makefile settings.gradle \
		-x *.DS_Store* -x *MyLexer.java* -x *XiParser.java* -x *sym.java* -x *test_JUNK.java* -x *xthScript.results* -x *testjunk.ixi* -x *testjunk.xi*
