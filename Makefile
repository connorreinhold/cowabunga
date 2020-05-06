mylexer:
	cd src/java/cyr7/lexer; rm -f MyLexer.java; jflex xi.flex

myparser:
	java -jar dependencies/java_cup.jar -locations -parser "XiParser" -destdir ./src/java/cyr7/parser/ ./src/java/cyr7/parser/xi.cup

cowabunga: myparser mylexer 

test: gradle.test gradle.asmtest xth.test

gradle.test:
	./xic-build -ea
	make integration.complex
	make integration.basic
	gradle precompiledASMTest

gradle.asmtest:
	./xic-build -ea -enableAssemblyLevelAssertions
	make integration.complex
	make integration.basic
	gradle precompiledASMOnlyTest

xth.test:
	cd \
	&& xth -ec -testpath ~/xth/tests/pa5 -compilerpath ~/shared/cowabunga ~/xth/tests/pa5/xthScript -pdf ~/shared/cowabunga/instructor.pdf \
	&& python3 ~/shared/cowabunga/tests/xth/build.py \
	&& xth -ec -testpath . -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptAll -pdf ~/shared/cowabunga/unit.pdf  \
	&& xth -ec -testpath ~/shared/cowabunga/tests/resources/integration -compilerpath ~/shared/cowabunga ~/shared/cowabunga/tests/xth/xthScriptIntegration -pdf ~/shared/cowabunga/integration.pdf

zip:
	rm -f cowabunga.zip
	git archive HEAD -o cowabunga.zip

integration.complex:
	echo "\033[0;32m" 'Precompiling assembly with complex tiler...' "\033[0m"
	find tests/resources/integration/ -name '*.xi' | xargs ~/shared/cowabunga/xic -libpath tests/resources/integration/lib/ -taggedASMFile -tiler complex
	find tests/resources/integration/ -name '*.s_COMPLEX' -print0 | sort -z | xargs -0 wc -l

integration.basic:
	echo "\033[0;32m" 'Precompiling assembly with basic tiler...' "\033[0m"
	find tests/resources/integration/ -name '*.xi' | xargs ~/shared/cowabunga/xic -libpath tests/resources/integration/lib/ -taggedASMFile -tiler basic
	find tests/resources/integration/ -name '*.s_BASIC' -print0 | sort -z | xargs -0 wc -l	
