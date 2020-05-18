mylexer:
	cd src/java/cyr7/lexer; rm -f MyLexer.java; jflex xi.flex

myparser:
	java -jar dependencies/java_cup.jar -locations -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

cowabunga: myparser mylexer

benchmark:
	./xic-build
	gradle benchmark

test.all:
	./xic-build
	gradle test
	make xth.test

gradle.test:
	./xic-build
	gradle test

gradle.coreTest:
	./xic-build
	gradle coreTest

xth.test:
	cd \
	&& xth -ec -testpath ~/xth/tests/pa5 -compilerpath ~/shared/cowabunga ~/xth/tests/pa5/xthScript -pdf ~/shared/cowabunga/instructor.pdf \
	&& python3 ~/shared/cowabunga/tests/xth/build.py \
	&& xth -ec -testpath . -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptAll -pdf ~/shared/cowabunga/unit.pdf  \
	&& xth -ec -testpath ~/shared/cowabunga/tests/resources/integration -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptIntegration -pdf ~/shared/cowabunga/integration.pdf

zip:
	rm -f cowabunga.zip
	git archive HEAD -o cowabunga.zip

