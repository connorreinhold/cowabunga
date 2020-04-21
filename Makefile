mylexer:
	cd src/java/cyr7/lexer; rm -f MyLexer.java; jflex xi.flex

myparser:
	java -jar dependencies/java_cup.jar -locations -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

irlexer:
	cd src/java/cyr7/ir/parse; rm -f IRLexer.java; jflex ir.flex

irparser:
	java -jar dependencies/java_cup.jar -locations -parser "IRParser" -symbols "IRSym" -destdir ./src/java/cyr7/ir/parse ./src/java/cyr7/ir/parse/ir.cup

cowabunga: myparser mylexer irlexer irparser

test: gradle.test xth.test

gradle.test:
	./xic-build-daemon -ea
	make integration.complex
	make integration.basic
	gradle precompiledASMTest

xth.test:
	./xic-build-daemon -ea \
	&& cd \
	&& python3 ~/shared/cowabunga/tests/xth/build.py \
	&& xth -testpath . -workpath .  -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptAll

zip:
	rm -f cowabunga.zip
	zip -r cowabunga.zip \
		README.md dependencies src tests xic-build build.gradle Makefile settings.gradle \
		-x *.DS_Store* -x *MyLexer.java* -x *XiParser.java* -x *sym.java* -x *test_JUNK.java* -x *xthScript.results* -x *testjunk.ixi* -x *testjunk.xi* -x *IRLexer.java* -x *IRParser.java* -x *IRSym.java*

integration.complex:
	echo '\033[0;32m' 'Precompiling assembly with complex tiler...' '\033[0m' 
	find tests/resources/integration/ -name '*.xi' | xargs ~/shared/cowabunga/xic -libpath tests/resources/integration/lib/ -taggedASMFile -tiler complex
	find tests/resources/integration/ -name '*.s_COMPLEX' -print0 | sort -z | xargs -0 wc -l

integration.basic:
	echo '\033[0;32m' 'Precompiling assembly with basic tiler...' '\033[0m'
	find tests/resources/integration/ -name '*.xi' | xargs ~/shared/cowabunga/xic -libpath tests/resources/integration/lib/ -taggedASMFile -tiler basic
	find tests/resources/integration/ -name '*.s_BASIC' -print0 | sort -z | xargs -0 wc -l	
